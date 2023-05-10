FROM eclipse-temurin:17-jdk-alpine
ADD target/moments-*.jar moments.jar
ENTRYPOINT ["java", "-jar", "/moments.jar"]
