FROM openjdk:17-jdk-slim

WORKDIR /app

# Скрипт запуска
COPY scripts/start.sh /app/start.sh
RUN chmod +x /app/start.sh

EXPOSE 6567/tcp 6567/udp 6868/tcp

ENTRYPOINT ["./start.sh"]
