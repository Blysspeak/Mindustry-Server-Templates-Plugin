#!/bin/bash

# Скрипт для запуска сервера Mindustry с выделением 1 ГБ памяти

# Определяем директорию, где находится скрипт
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(dirname "$SCRIPT_DIR")"

# Переходим в директорию сервера
cd "$PROJECT_DIR/server" || { echo "Ошибка: Не удалось перейти в директорию сервера"; exit 1; }

# Проверяем наличие server.jar
if [ ! -f "server.jar" ]; then
    echo "Ошибка: Файл server.jar не найден в директории server"
    exit 1
fi

# Запускаем сервер с выделением 1 ГБ памяти
echo "Запуск сервера Mindustry с 1 ГБ памяти..."
java -Xmx1G -jar server.jar host