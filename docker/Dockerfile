# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine

#run as non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy jar file
COPY biometric-match-0.0.1-SNAPSHOT.jar biometric-match.jar

# run the app
CMD ["/usr/bin/java", "-jar", "/biometric-match.jar"]
