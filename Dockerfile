# Dockerfile Spring
# build environment
FROM openjdk:22-jdk-slim
WORKDIR /build/
COPY . .
RUN apt-get update && apt-get install -y dos2unix
RUN dos2unix mvnw
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
COPY target/*.jar /app/webapp.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","webapp.jar"]


# docker build -t mon-app-backend .

# docker run -p 8080:8080   -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/egypt2   -e SPRING_DATASOURCE_USERNAME=root   -e SPRING_DATASOURCE_PASSWORD=NextWorld@1   -e JWT_SECRET=jwt.secret=jefaisuntestpourmodifiercettephrase11115555:  -e FRONTEND_URL=http://localhost:4200   mon-app-backend
