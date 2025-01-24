FROM postgres:14

RUN apt-get update \
    && apt-get install -y postgis postgresql-14-postgis-3 \
    && rm -rf /var/lib/apt/lists/*

COPY init.sql /docker-entrypoint-initdb.d/

ENV POSTGRES_USER=ze
ENV POSTGRES_PASSWORD=123
ENV POSTGRES_DB=zeDB

EXPOSE 5432
