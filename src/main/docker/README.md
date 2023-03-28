# Pasos a seguir para doquerizar el proyecto

1- Desde la raiz del proyecto ejecutar el siguiente comando para crear el .jar de la aplicacion: mvn clean package
    Una vez lo ejecuteis, se ejecuta todo el main de la aplicacion, por tanto, si habeis creado una nueva instancia a la bd se va a añadir, es importante que la elimineis de
    la bd antes de ejecutar el docker-compose up para evitar que os de errores de PK de SQL.

2- Desde la raiz del proyecto, haceis: cp target/plugfinder-0.0.1-SNAPSHOT.jar src/main/docker

3- Una vez copiado, entrais en la carpeta docker: cd src/main/docker y haceis docker-compose down (por si habia algun contenedor levantado) y docker rmi plugfinder-backend (por si habia alguna imagen creada),
    y por ultimo docker-compose up.

4- Cuando se acabe de ejecutar vereis que en la consola os sale: "plugfinder-backend_1  | Started PlugfinderBackendApplication in 8.5 seconds (JVM running for 9.5)" o algo parecido,
    eso significa que la aplicacion se ha levantado correctamente. Si mirais la bd en el dbeaver i actualizais deberia aparecer la nueva instancia que se crea en el main.

### **COMANDOS:**

mvn clean package

**BORRAR FILA DE LA BD** 

cp target/plugfinder-0.0.1-SNAPSHOT.jar src/main/docker

cd src/main/docker

docker-compose down

docker rmi plugfinder

docker-compose up

### **ENCENDER BD**

En carpeta plugfinder/src/main/docker ejecutar:

docker-compose up db

Ejecutar PlugfinderApplication desde Intellij