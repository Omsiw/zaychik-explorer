FROM openjdk:17-jdk-slim

# Устанавливаем postgresql-client И dos2unix
RUN apt-get update && apt-get install -y postgresql-client dos2unix && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Сначала копируем скрипт
COPY wait-for-postgres.sh .

# --- НОВАЯ КОМАНДА ДЛЯ ИСПРАВЛЕНИЯ ФАЙЛА ---
# Принудительно конвертируем окончания строк в Linux-формат (LF)
RUN dos2unix wait-for-postgres.sh

# Копируем остальные файлы
COPY target/backend-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/application.yml ./application.yml

# Даем права на исполнение
RUN chmod +x wait-for-postgres.sh

ENTRYPOINT ["sh", "./wait-for-postgres.sh"]
CMD ["java", "-jar", "app.jar"]