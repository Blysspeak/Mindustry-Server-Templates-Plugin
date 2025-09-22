#!/bin/bash
set -e

# Получаем IP контейнера
CONTAINER_IP=$(hostname -i)

# Получаем внешний IP (если получится)
EXTERNAL_IP=$(curl -s --connect-timeout 5 http://checkip.amazonaws.com || echo "не удалось определить (используйте IP сервера)")

echo "==============================================="
echo " MINDUSTRY SERVER STARTING "
echo "==============================================="
echo "Подключение к серверу:"
echo " - Локальный адрес (внутри сети Docker): $CONTAINER_IP:6567"
echo " - Внешний адрес: $EXTERNAL_IP:6567"
echo "==============================================="

exec java -Xmx512M -jar server.jar host
