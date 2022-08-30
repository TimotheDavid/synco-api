FROM amd64/openjdk:18.0.2.1-jdk

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ARG profile
ARG port
ARG address
ARG security
ENV SECURITY $security
ENV PROFILE $profile
ENV PORT $port
ENV ADDRESS $address
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.actives.profile=${PROFILE}", "--server.port=${PORT}", "--server.address=${ADDRESS}"]
