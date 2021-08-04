# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine

# Copy war file
COPY biometric-match-0.0.1-SNAPSHOT.jar /biometric-match.war

# run the app
CMD ["/usr/bin/java", "-jar", "/biometric-match.war"]