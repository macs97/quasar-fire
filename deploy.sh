git pull
sh gradlew bootJar
sudo docker build -t quasar-fire --build-arg accessKey=AWS_ACCESS_KEY_ID --build-arg secretKey=AWS_SECRET_ACCESS_KEY .