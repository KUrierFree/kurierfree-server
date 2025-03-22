# OpenJDK 21 기반 이미지 사용
FROM openjdk:21-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# JAR 파일 복사
COPY build/libs/server-0.0.1-SNAPSHOT.jar app.jar

# 포트 8080 열기
EXPOSE 8080

# 컨테이너 실행 시 Spring Boot 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]