# Mindustry Server

Этот репозиторий содержит сервер Mindustry с набором плагинов и инструментов для развертывания.

## Структура проекта

- `server/` - Основной сервер Mindustry (server.jar)
- `blyssPlugin/` - Набор пользовательских плагинов
- `scripts/` - Скрипты для запуска сервера
- `logs/` - Директория для логов сервера

## Запуск сервера

### Вариант 1: Прямой запуск через Java

```bash
cd server
java -Xmx512M -jar server.jar
```

### Вариант 2: Через скрипты

Для Linux/Mac:
```bash
./scripts/start-server.sh
```

Для Windows:
```cmd
scripts\start-server.bat
```

### Вариант 3: Через PM2

```bash
pm2 start ecosystem.config.js
```

Просмотр статуса:
```bash
pm2 list
pm2 logs mindustry-server
```

### Вариант 4: Через Docker (оптимизировано для слабых серверов)

Сборка и запуск:
```bash
docker-compose up -d
```

Остановка:
```bash
docker-compose down
```

Просмотр логов:
```bash
docker-compose logs -f
```

## Конфигурация для слабых серверов

Для слабых серверов все конфигурации оптимизированы для использования 512 МБ памяти:
- Docker Compose ограничивает память 600 МБ (512 МБ резерв + 88 МБ для системы)
- Java запускается с флагом -Xmx512M
- PM2 перезапускает процесс при превышении 512 МБ памяти

## Плагины

Сервер включает следующие плагины:
- advertisementSystem
- beautifulMessages
- helpSystem
- waveControl

## Конфигурация

- Конфигурационные файлы сервера находятся в `server/config/`
- Карты сервера находятся в `server/maps/`
- Плагины находятся в `server/mods/`