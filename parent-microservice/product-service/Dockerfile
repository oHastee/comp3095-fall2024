FROM gradle:8-jdk22-alpine AS builder

COPY --chown=gradle:gradle . /home/grade/src

WORKDIR /home/gradle/src

RUN gradle build -X test


# ----------------
# Package Stage
# ---------------

FROM openjdkL22-jdk

RUN mkdir /app

COPY --from=builder /home/gradle/src/build/libs/*.jar /app/product-service.jar
#  app/product-service.jar   product-service will have to change with new docker files
#  name it to the microservice youre working on

ENV MONGO_DB_USERNAME=admin \
    MONGO_DB_PWD=password

EXPOSE 8087

ENTRYPOINT ["java", "-jar", "/app/product-service.jar"]

