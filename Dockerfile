FROM openjdk:17-jdk-slim

# Создание непривилегированного пользователя
RUN groupadd -r mindustry && useradd -r -g mindustry mindustry

# Установка зависимостей
RUN apt-get update && apt-get install -y \
    wget \
    iproute2 \
    && rm -rf /var/lib/apt/lists/*

# Создание директории для сервера
WORKDIR /app

# Копирование файлов сервера
COPY server/server.jar ./server.jar

# Создание скрипта запуска с информационным выводом
RUN echo '#!/bin/bash\n\
echo "==============================================="\n\
echo "         MINDUSTRY SERVER STARTING            "\n\
echo "==============================================="\n\
echo "Сервер запускается с параметрами:"\n\
echo "- Память: 512MB"\n\
echo "- Порты: 6567 (TCP/UDP), 6868 (RCON)"\n\
echo ""\n\
\n\
# Получаем IP адрес контейнера\n\
CONTAINER_IP=$(hostname -i)\n\
\n\
# Запуск сервера и вывод информации\n\
echo "==============================================="\n\
echo "         СЕРВЕР УСПЕШНО ЗАПУЩЕН               "\n\
echo "==============================================="\n\
echo "Подключение к серверу:"\n\
echo "  Локальный адрес: localhost:6567"\n\
echo "  IP контейнера: $CONTAINER_IP:6567"\n\
echo "  Порт: 6567 (TCP/UDP)"\n\
echo ""\n\
echo "RCON (для администраторов):"\n\
echo "  Порт: 6868"\n\
echo ""\n\
echo "Логи сервера:"\n\
echo " docker-compose logs -f mindustry-server"\n\
echo "==============================================="\n\
\n\
# Запуск сервера\n\
java -Xmx512M -jar server.jar' > start-with-info.sh && \
    chmod +x start-with-info.sh

# Изменение владельца файлов
RUN chown -R mindustry:mindustry /app

# Переключение на непривилегированного пользователя
USER mindustry

# Открытие портов (6567 для игрового трафика, 6868 для RCON)
EXPOSE 6567/tcp
EXPOSE 6567/udp
EXPOSE 6868

# Запуск сервера с информационным выводом
CMD ["./start-with-info.sh"]