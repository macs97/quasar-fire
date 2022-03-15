git pull
sh gradlew bootJar
sudo docker build -t quasar-fire --build-arg accessKey=AWS_ACCESS_KEY_ID --build-arg secretKey=AWS_SECRET_ACCESS_KEY .
sudo docker run -p 8080:8080 quasar-fire