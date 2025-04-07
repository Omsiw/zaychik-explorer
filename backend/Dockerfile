FROM openjdk:17-jdk-slim

# Устанавливаем postgresql-client для pg_isready
RUN apt-get update && apt-get install -y postgresql-client && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY wait-for-postgres.sh .
COPY target/backend-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/application.yml ./application.yml

RUN chmod +x wait-for-postgres.sh

ENTRYPOINT ["./wait-for-postgres.sh"]
CMD ["java", "-jar", "app.jar"]
