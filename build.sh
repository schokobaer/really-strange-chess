# build webapp
cd ./src/main/webapp
npm run build

# build server
cd ../../..
mvn clean package

# build docker
docker build --tag=really-strange-chess .

# release docker image
docker tag really-strange-chess:latest fundreas/really-strange-chess:latest
docker push fundreas/really-strange-chess:latest