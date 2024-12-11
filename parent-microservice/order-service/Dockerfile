#-----------------
# Build Stage
#-----------------

# This line specifies the base image for the build stage.
# It uses a Gradle image with JDK 23 on Alpine Linux, which is a lightweight Linux distribution.
# The AS builder part names this stage "builder," allowing you to refer to it later in the Dockerfile.
FROM gradle:8-jdk23-alpine AS builder

# This line copies the contents of the current directory (the one containing your Gradle project)
# into the /home/gradle/src directory of the container.
# The --chown=gradle.gradle option sets the ownership of the copied files to the gradle user,
# ensuring that the Gradle process can access them without permission issues.
COPY --chown=gradle.gradle . /home/gradle/src

# This command sets the working directory inside the container to /home/gradle/src.
# Any subsequent commands (like RUN, CMD, etc.) will be executed in this directory.
WORKDIR /home/gradle/src

# This command runs the Gradle build process, excluding the tests (-x test).
# It compiles the project and packages it into a JAR file,
# which will be stored in the build/libs directory of the project.
RUN gradle build -x test

#------------------
# Package Stage
#------------------

# This line starts the second stage of the Dockerfile, which is the package stage.
# It uses the OpenJDK 23 image as the base. This image will be used to run the application.
FROM openjdk:23-jdk

# This command creates a new directory named /app in the container.
# This is where the compiled JAR file will be placed.
RUN mkdir /app

# This line copies the JAR file created in the builder stage into the /app directory
# of the current stage (the runtime environment). It assumes that the build produces a single JAR file.
COPY --from=builder /home/gradle/src/build/libs/*.jar /app/order-service.jar

# This line sets environment variables for the container.
# The SPRING_DATASOURCE_USERNAME and SPRING_DATASOURCE_PASSWORD variables are set to admin and password, respectively.
# These variables can be used by the application to connect to PostgreSQL.
ENV SPRING_DATASOURCE_USERNAME=admin \
    SPRING_DATASOURCE_PASSWORD=password

# This command informs Docker that the container listens on port 8087 at runtime.
# It doesn't publish the port but serves as documentation for users and tools about which ports should be published.
EXPOSE 8087

# This line specifies the command to run when the container starts.
# It tells Docker to execute the Java command with the -jar option, running the order-service.jar file
# located in the /app directory. This is how the Spring Boot application will be launched when the container starts.
ENTRYPOINT ["java", "-jar", "/app/order-service.jar"]

# This Dockerfile consists of two main stages: the Build Stage and the Package Stage.

# Build Stage: It compiles the Java application using Gradle and packages it into a JAR file.
# Package Stage: It creates a minimal runtime image based on OpenJDK, copies the JAR file from the build stage,
# sets up environment variables for PostgreSQL, exposes a port for the application,
# and specifies the command to run the application.
