FROM openjdk:17-jdk-slim

# Создание непривилегированного пользователя
RUN groupadd -r mindustry && useradd -r -g mindustry mindustry

# Установка зависимостей
RUN apt-get update && apt-get install -y \
    wget \
    && rm -rf /var/lib/apt/lists/*

# Создание директории для сервера
WORKDIR /app

# Копирование файлов сервера
COPY server/server.jar ./server.jar

# Изменение владельца файлов
RUN chown -R mindustry:mindustry /app

# Переключение на непривилегированного пользователя
USER mindustry

# Открытие портов (6567 для игрового трафика, 6868 для RCON)
EXPOSE 6567/tcp
EXPOSE 6567/udp
EXPOSE 6868

# Запуск сервера с ограничением памяти 512 МБ
CMD ["java", "-Xmx512M", "-jar", "server.jar"]