FROM adoptopenjdk/openjdk11:latest
EXPOSE 9091
ADD target/client-service.jar client-service.jar
ADD /configCS.txt configCS.txt
ENTRYPOINT ["java", "-jar", "/client-service.jar"]