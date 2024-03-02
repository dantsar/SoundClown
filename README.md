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

Connect to the database using 
```
psql -h localhost -U postgres
```

Create the database
```
CREATE DATABASE soundclown;
\c soundclown
```
From there populate the database


Run 
```
docker compose up
```
start the db and run JDBCExecutor
