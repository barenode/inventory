@echo off
for /f "delims=" %%A in ('"mvn help:evaluate -Dexpression=project.version -q -DforceStdout"') do set version=%%A
@echo on
echo version is %version%

cd ./kustomization/overlays/prod-green
kustomize edit set image hylmar/inventory:CHANGEME=:%version% & 
kustomize build . -o ../../../k8s/prod.yaml
cd ../../..