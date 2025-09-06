# ---- Dockerfile (Spring Boot, Java 21) ----
FROM eclipse-temurin:21-jre

# create app dir
WORKDIR /app

# copy the fat jar built by Maven
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# copy SSL keystore (self-signed cert)
COPY src/main/resources/certs/gateway.p12 /app/certs/gateway.p12

# tunables via env (optional)
ENV SERVER_PORT=8443
ENV JAVA_OPTS="-XX:+UseG1GC -XX:MaxRAMPercentage=75"

# expose HTTPS port (hardcode or use same as SERVER_PORT)
EXPOSE 8443

# health-friendly start
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar --server.port=${SERVER_PORT}
