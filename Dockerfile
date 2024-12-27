# Use the official image as a parent image
FROM openjdk:17-jdk-alpine

# AWS 액세스 키와 비밀 키를 Docker 이미지 빌드 시 전달받고 환경 변수로 설정
ARG AWS_ACCESS_KEY_ID
ARG AWS_SECRET_ACCESS_KEY

# 환경 변수로 설정
ENV AWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID}
ENV AWS_SECRET_ACCESS_KEY=${AWS_SECRET_ACCESS_KEY}
ENV MAIL_USER_NAME=${MAIL_USER_NAME}
ENV MAIL_PASSWORD=${MAIL_PASSWORD}

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the host to the container
COPY build/libs/*.jar app.jar

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
