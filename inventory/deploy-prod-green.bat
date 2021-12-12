@echo off
for /f "delims=" %%A in ('"mvn help:evaluate -Dexpression=project.version -q -DforceStdout"') do set version_green=%%A
@echo on
echo version green is %version_green%

for /f "delims=" %%A in (../k8s/inventory/.ver) do set version_blue=%%A
echo version blue is %version_blue%

call mvn clean install

cd ./kustomization/overlays/prod-green
kustomize edit set image hylmar/inventory:BLUE=:%version_blue% & 
kustomize edit set image hylmar/inventory:GREEN=:%version_green% & 
kustomize build . -o ../../../../k8s/inventory/prod.yaml
cd ../../..

cd ..
git add *
git commit -m "BLUE:GREEN %version_blue%:%version_green%"
git push origin main
cd inventory