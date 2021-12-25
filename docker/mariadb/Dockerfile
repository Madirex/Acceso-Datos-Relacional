# MariaDB
FROM yobasystems/alpine-mariadb:10
# FROM mariadb:10.5

ENV MYSQL_ROOT_PASSWORD empresa123
ENV MYSQL_USER empresa
ENV MYSQL_PASSWORD empresa123
ENV MYSQL_DATABASE empresa

COPY ./sql /docker-entrypoint-initdb.d/
