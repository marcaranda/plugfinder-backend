#!/bin/bash

# Compilar el projecte
cd ../../../
mvn clean package
cp target/plugfinder-0.0.1-SNAPSHOT.jar src/main/docker

# Generar l'imatge docker
# shellcheck disable=SC2164
cd src/main/docker
docker-compose down
docker rmi plugfinder-backend:latest
docker build -t plugfinder-backend:latest .

# Guardar l'imatge Docker en un fitxer .tar
docker save -o plugfinder.tar plugfinder-backend:latest

# Pujar el fitxer a l'instància EC2
scp plugfinder.tar ec2-user@34.251.236.67:/home/ec2-user/plugfinder.tar

# Conexió SSH a la instancia de EC2
ssh ec2-user@34.251.236.67 << EOF

# Carregar la nova imatge docker a la instancia EC2
sudo docker load -i /home/ec2-user/plugfinder.tar

# Reiniciar contenidor docker
sudo systemctl restart docker.plugfinder.service

EOF
