@echo off
for /f "delims=" %%A in ('"mvn help:evaluate -Dexpression=project.version -q -DforceStdout"') do set version=%%A
@echo on
echo version is %version%
echo %version% > k8s/.ver

call mvn install

cd ./kustomization/overlays/prod
kustomize edit set image hylmar/inventory:BLUE=:%version% & 
kustomize edit set image hylmar/inventory:GREEN=:%version% & 
kustomize build . -o ../../../../k8s/inventory/prod.yaml
cd ../../..

cd ..
git add *
git commit -m "ROLLOUT %version%"
git push origin main
cd inventory
