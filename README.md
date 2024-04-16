# SoundClown
This is a SoundCloud clone for [ECE366](https://cooper.edu/engineering/courses/electrical-and-computer-engineering-undergraduate/ece-366).
This project intends to use the following frameworks and techonologies:
* Backend: Java
* Frontend: ReactJS
* Database: PostgreSQL


---
# MORE TO COME

# Build System
You first need to create location to store database data
```
mkdir -p $HOME/srv/postgres
```

Then run 
```
docker run --rm --name lil-postgres -e POSTGRES_PASSWORD=password -d -v $HOME/srv/postgres:/var/lib/postgresql/data -p 5432:5432 postgres
```

Run 
```
docker compose up
```

# Web App

```sh
# to build web app & install dependencies
$ cd ./client/soundclownui/ && npm install
# to start web app
$ cd ./client/soundclownui/ && npm start
```
