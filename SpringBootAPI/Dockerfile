# ---- build stage ----
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -DskipTests package

# ---- run stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app
# if your version is a SNAPSHOT:
COPY --from=build /app/target/*-SNAPSHOT.jar /app/app.jar
# if you later release a non-SNAPSHOT, change the line above to:
# COPY --from=build /app/target/*.jar /app/app.jar

ENV SPRING_PROFILES_ACTIVE=prod
EXPOSE 8080
# Bind to Render's $PORT (fallback to 8080 locally) and allow JAVA_OPTS
ENTRYPOINT ["sh","-c","java -Dserver.port=${PORT:-8080} $JAVA_OPTS -jar /app/app.jar"]

# (Optional hardening)
# USER 10001
