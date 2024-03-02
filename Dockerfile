FROM postgres

LABEL author="sound-clown"
LABEL description="database for soundclown"
LABEL version="1.0"

COPY *.sql /docker-entrypoint-initdb.d/
