FROM openjdk:11-jre-stretch
ARG accessKey
ARG secretKey
ENV AWS_ACCESS_KEY_ID=$accessKey
ENV AWS_SECRET_ACCESS_KEY=$secretKey
ENV AWS_DEFAULT_REGION=us-east-1
ENV TZ=America/Bogota
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENV APP_FILE quasar-fire-1.0-SNAPSHOT.jar
ENV APP_HOME /usr/apps
EXPOSE 8080
COPY build/libs/$APP_FILE $APP_HOME/
WORKDIR $APP_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar $APP_FILE"]
