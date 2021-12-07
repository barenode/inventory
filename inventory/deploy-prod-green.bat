@echo off
for /f "delims=" %%A in ('"mvn help:evaluate -Dexpression=project.version -q -DforceStdout"') do set version_green=%%A
@echo on
echo version green is %version_green%

for /f "delims=" %%A in (k8s/.ver) do set version_blue=%%A
echo version blue is %version_blue%

@REM @REM TODO: join mvn
@REM call mvn clean install
@REM call mvn jib:build

cd ./kustomization/overlays/prod-green
kustomize edit set image hylmar/inventory:CHANGEME=:%version_blue% & 
kustomize edit set image hylmar/inventory:GREEN=:%version_green% & 
kustomize build . -o ../../../k8s/prod.yaml
cd ../../..

cd ..
git add *
git commit -m "BLUE:GREEN %version_blue%:%version_green%"
git push origin main
cd inventory