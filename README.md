# Acceso a Datos Relacional

<p align="center">
  <img src="https://i.imgur.com/z5ABytN.png" alt="Acceso a datos relacional"/>
</p>

## Descripción
Se ha codificado un programa para realizar operaciones en una base de datos.
Se ha hecho un mapeo objeto relacional de manera manual realizando operaciones CRUD sobre las entidades, diseñando el 
diagrama de clases, creación de la base de datos y de su estructura, realización de test unitarios con JUnit5 para 
comprobar que las operaciones CRUD de los repository se realicen de manera correcta.

## ⚠ Requisitos
- Docker y Docker Compose
- Crear archivo .env con el usuario y la contraseña de la aplicación

## ✏ Instrucciones
Inicializar la base de datos:
1. Abrir la consola y acceder a la carpeta **docker**.
2. **Escribir el siguiente comando para inicializar la base de datos con Docker Compose:** sudo docker-compose up -d
3. Ejecutar el programa con el parámetro de la ubicación donde se guardarán los datos a exportar.

## 🐛 En caso de fallo (reinicio base de datos)
1. **Para parar el adminer** sudo docker stop adminer
2. **Para parar MariaDB** sudo docker stop mariadb
3. **Para eliminar todas las imágenes, volúmenes, contenedores y redes no utilizadas:** sudo docker system prune -a --volumes

## Puesto en práctica
- Diagrama de clases.
- Implemtación del sistema y realización del Mapeo Objeto Relacional de forma manual.
- Operaciones CRUD.
- Test Unitarios para comprobar las operaciones CRUD con JUnit5.

## Librerías usadas 📚
- Junit-jupiter.
- Dotenv-java.
- Lombok.
- Gson.
- Mybatis.

## Autores
- Ángel: <https://github.com/Madirex>
- Carlos: <https://github.com/Nerd-Geek>
