#!/bin/bash
docker_image="soundclown_env"
SOUNDCLOWN_DOCKERFILE="."

# NGL bash scripts suck. This is currently hack and we'll probably switch to python

echo $@
# uid=$(id -u)
# gid=$(id -g)

REBUILD=false

REBUILD=false
for arg in "$@"; do
    if [ "$arg" = "rebuild" ]; then
        REBUILD=true
        break
    fi
done

# Build docker image if it doesn't exist
# https://stackoverflow.com/a/30543453
if [ "$(docker images -q ${docker_image} 2> /dev/null)" == "" ] || $REBUILD; then
    docker build -t ${docker_image} "${SOUNDCLOWN_DOCKERFILE}/"
fi

echo "Welcome to the SoundClown Dev Environment"
docker run -it --rm     \
    ${docker_image}  /bin/bash
