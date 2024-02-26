# SoundClown
This is a SoundCloud clone for [ECE366](https://cooper.edu/engineering/courses/electrical-and-computer-engineering-undergraduate/ece-366).
This project intends to use the following frameworks and techonologies:
* Backend: Java
* Frontend: ReactJS
* Database: PostgreSQL


---
# MORE TO COME

# Build System
To start we're going to use Maven.
To install Maven on Linux (make sure that the Maven version is >3.9.6)

Run the following to enter the docker container:
```
./docker_buiid.sh
```

Run the Java App using the following
```
mvn compile exec:java -Dexec.mainClass="com.SoundClown.Main"
```
