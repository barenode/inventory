@echo off
for /f "delims=" %%A in ('"mvn help:evaluate -Dexpression=project.version -q -DforceStdout"') do set version=%%A
@echo on
echo version is %version%

call mvn clean install
call mvn jib:build

cd ./kustomization/overlays/prod
kustomize edit set image hylmar/inventory:CHANGEME=:%version% & 
kustomize build . -o ../../../k8s/prod.yaml
cd ../../..

cd ..
git add *
git commit -m "reelase %version%"
git push origin main
cd inventory
