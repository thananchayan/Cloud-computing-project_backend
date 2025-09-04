# ---- Dockerfile (Spring Boot, Java 21) ----
FROM eclipse-temurin:21-jre
# copy the fat jar built by Maven
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# tunables via env (optional)
ENV SERVER_PORT=8081
ENV JAVA_OPTS="-XX:+UseG1GC -XX:MaxRAMPercentage=75"

EXPOSE ${SERVER_PORT}

# health-friendly start
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app.jar --server.port=${SERVER_PORT}"]
