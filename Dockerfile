FROM openjdk:8
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp
RUN ./gradlew jar
CMD ["java", "-jar", "build/libs/tictac.jar"]