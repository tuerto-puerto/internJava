# Базовый образ с JDK 23 (OpenJDK, если доступен, или используем альтернативу)
FROM openjdk:21-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем JAR-файл из локальной папки target в контейнер
COPY ./target/demo-0.0.1-SNAPSHOT.jar /app/demo.jar

ENV DB_HOST=localhost:8080
ENV DB_PORT=5432
ENV DB_USER=postgres
ENV DB_PASSWORD=1337

# Устанавливаем команду для запуска JAR-файла
CMD ["java", "-jar", "demo.jar"]

EXPOSE 8080