# Dockerfile
FROM bellsoft/liberica-openjdk-alpine:17

# 시스템 시간대를 설정
ENV TZ=Asia/Seoul
RUN apk add --no-cache tzdata \
    && cp /usr/share/zoneinfo/${TZ} /etc/localtime \
    && echo "${TZ}" > /etc/timezone

# JAR 파일 복사
ARG JAR_FILE=build/libs/HomePage-BE-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# JVM 시간대 설정을 포함한 애플리케이션 실행
# -Xmx256m	최대 힙 메모리를 256MB로 제한
# -Xms128m	초기 힙 메모리 크기를 128MB로 설정
ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-Xmx256m", "-Xms128m", "-jar", "/app.jar"]
