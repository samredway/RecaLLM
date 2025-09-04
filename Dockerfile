# syntax=docker/dockerfile:1.7

# -------- Build stage (Java 17 + Maven) --------
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Cache deps
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 \
    mvn -q -DskipTests dependency:go-offline

# Build
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 \
    mvn -q -DskipTests package

# -------- Runtime stage (JRE 17, Debian/Ubuntu) --------
FROM eclipse-temurin:17-jre-jammy
ENV TZ=UTC \
    JAVA_OPTS="" \
    SPRING_PROFILES_ACTIVE=prod
WORKDIR /app

# Create non-root user/group (Debian/Ubuntu syntax)
RUN groupadd --system spring \
 && useradd  --system --gid spring --home-dir /app --shell /usr/sbin/nologin spring

# Copy app as root, then fix ownership
COPY --from=build /app/target/*.jar /app/app.jar
RUN chown -R spring:spring /app

# Drop privileges
USER spring:spring

EXPOSE 8080
ENTRYPOINT ["sh","-c","exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar"]
