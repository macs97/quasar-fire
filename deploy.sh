git pull
sh gradlew bootJar
docker build -t quasar-fire --build-arg accessKey=AWS_ACCESS_KEY_ID --build-arg secretKey=AWS_SECRET_ACCESS_KEY .