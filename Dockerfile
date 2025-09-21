FROM openjdk:17-jdk-slim

RUN groupadd -r mindustry && useradd -r -g mindustry mindustry

RUN apt-get update && apt-get install -y \
    wget \
    curl \
    iproute2 \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY server/server.jar ./server.jar

RUN mkdir -p config/logs config/maps config/mods \
    && chown -R mindustry:mindustry /app

# Создаем скрипт запуска
RUN echo '#!/bin/bash\n\
# Получаем внутренний IP контейнера\n\
CONTAINER_IP=$(hostname -i)\n\
# Пытаемся получить внешний IP\n\
EXTERNAL_IP=$(curl -s http://checkip.amazonaws.com --connect-timeout 5) || EXTERNAL_IP="не удалось получить (используйте IP вашего сервера)"\n\
\n\
echo "==============================================="\n\
echo " MINDUSTRY SERVER STARTING "\n\
echo "==============================================="\n\
echo "Подключение к серверу:"\n\
echo " - Локальный адрес (внутри сети Docker): $CONTAINER_IP:6567"\n\
echo " - Внешний адрес: $EXTERNAL_IP:6567"\n\
echo "==============================================="\n\
\n\
exec java -Xmx512M -jar server.jar host' > /app/start.sh && \
chmod +x /app/start.sh && \
chown mindustry:mindustry /app/start.sh

USER mindustry

EXPOSE 6567/tcp 6567/udp 6868/tcp

CMD ["/app/start.sh"]