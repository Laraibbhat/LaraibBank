# Start with a base image containing the Java runtime
FROM eclipse-temurin:17-jdk

# Maintainer of the image
#LABEL org.opencontainers.image.authors="Laraib Mushtaq"
MAINTAINER Laraib Mushtaq
# Copy the application jar to the image
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

# Execute the application
ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]