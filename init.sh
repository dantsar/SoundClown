#!/bin/bash

# This script builds the app development and database containers
# and runs the corresponding containers

# **NGL bash scripts suck. This is currently hack and we'll probably switch to python**

# ---Useful variables---
PROJ_ROOT=$(pwd)

# Names of the generated docker images
APP_IMAGE="soundclown_env"
DB_IMAGE="soundclown_db"

APP_DOCKERFILE="${PROJ_ROOT}/backend/"
DB_DOCKERFILE="${PROJ_ROOT}/database/"

# Command Line Flags to sete
REBUILD_ALL=false
BUILD_DB=false
BUILD_APP=false
RUN_APP=false
RUN_DB=false

for arg in "$@"; do
    if [ "$arg" = "rebuild" ]; then
        REBUILD_ALL=true
        continue
    fi
    if [ "$arg" = "build_db" ]; then
        BUILD_DB=true
        continue
    fi
    if [ "$arg" = "build_app" ]; then
        BUILD_APP=true
        continue
    fi
    if [ "$arg" = "run_app" ]; then
        RUN_APP=true
        continue
    fi
    if [ "$arg" = "run_db" ]; then
        RUN_DB=true
        continue
    fi
done

# ---App container---

# Build docker image if it doesn't exist
# https://stackoverflow.com/a/30543453
if [ "$(docker images -q ${APP_IMAGE} 2> /dev/null)" == "" ] || $BUILD_APP || $REBUILD_ALL; then
    docker build -t ${APP_IMAGE} "${APP_DOCKERFILE}/"
fi

# Run the app container
if $RUN_APP; then
    APP_PWD="$(pwd)/backend"
    echo "Welcome to the SoundClown Dev Environment"
    docker run -it --rm     \
        -v "${APP_PWD}:/code"  \
        ${APP_IMAGE} /bin/bash
fi

# ---Database container---

# Build docker image if it doesn't exist
if [ "$(docker images -q ${DB_IMAGE} 2> /dev/null)" == "" ] || $BUILD_DB || $REBUILD_ALL; then
    docker build -t ${DB_IMAGE} "${DB_DOCKERFILE}/"
fi

# Run the database container
if $RUN_DB; then
    DB_PWD="$(pwd)/database"
    docker run -it --rm \
        -v "${DB_PWD}:/var/lib/postgresql/data" \
        -d -p 5432:5432 \
        -e POSTGRES_PASSWORD=password \
        postgres
        # ${DB_IMAGE}
fi


# docker run --rm --name lil-postgres -e POSTGRES_PASSWORD=password -d -v $HOME/srv/postgres:/var/lib/postgresql/data -p 5432:5432 postgres
