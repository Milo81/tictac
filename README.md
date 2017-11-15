# tictac
Example Tic-Tac-Toe Java program.

# Running tests
```
./gradlew tests
```

# Running & building the app
There are different ways how to tun the app.

## Running using gradle
```
./gradlew run
```

## Running plain old java jar
```
./gradlew jar
java -jar build/libs/tictac.jar
```

## Running in docker
You can either build your own docker image from provided Dockerfile and then run it:
```
docker build -t tictac-app .
docker run -it tictac-app
```

There is also available pre-built image that can be run directly:
```
docker run -it milo81/tictactoe:latest
```
