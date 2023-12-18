# Dockerfile Spring
# build environment
FROM openjdk:17-jdk-slim
WORKDIR /build/
COPY . .
RUN chmod +x mvnw
RUN ./mvnw -Dmaven.test.skip clean package

ENV PORT 8080

ARG SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL}
ENV SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL}
ARG SPRING_DATASOURCE_USERNAME=${SPRING_DATASOURCE_USERNAME}
ENV SPRING_DATASOURCE_USERNAME=${SPRING_DATASOURCE_USERNAME}
ARG SPRING_DATASOURCE_PASSWORD=${SPRING_DATASOURCE_PASSWORD}
ENV SPRING_DATASOURCE_PASSWORD=${SPRING_DATASOURCE_PASSWORD}
ARG JWT_SECRET=${JWT_SECRET}
ENV JWT_SECRET=${JWT_SECRET}
ARG FRONTEND_URL=${FRONTEND_URL}
ENV FRONTEND_URL=${FRONTEND_URL}

# production environment
# COPY target/*.jar /app/webapp.jar
# WORKDIR /app
# EXPOSE 8080
# ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","webapp.jar"]

COPY target/*.jar /build/webapp.jar
WORKDIR /build
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","webapp.jar"]
