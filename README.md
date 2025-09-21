# Mindustry Plugin Server

Совмещённый репозиторий с сервером Mindustry и системой плагинов.

Подробное руководство по разработке плагинов и работе с сервером находится в файле [DEVELOPMENT-GUIDE.md](DEVELOPMENT-GUIDE.md).

## Структура проекта

```
mindustry-plugin-server/
├── server/                 # Сервер Mindustry
│   └── server.jar          # Исполняемый файл сервера
├── blyssPlugin/            # Система плагинов
│   ├── src/                # Все плагины находятся здесь
│   │   ├── advertisementSystem/    # Система рекламных сообщений
│   │   ├── beautifulMessages/       # Красивые сообщения для игроков
│   │   ├── waveControl/            # Управление волнами врагов
│   │   └── helpSystem/              # Система помощи для игроков
│   ├── create-plugin.sh             # Скрипт создания плагинов (устарел)
│   ├── new-plugin                   # Обертка для CLI (устарела)
│   └── ...                          # Другие файлы системы плагинов
└── scripts/                # Скрипты для запуска сервера
    ├── start-server.sh              # Скрипт запуска сервера (Linux/macOS)
    └── start-server.bat             # Скрипт запуска сервера (Windows)
```

## Сервер

Сервер Mindustry с предустановленными плагинами. Для запуска сервера используйте один из следующих способов:

### Подготовка к запуску

Перед первым запуском сделайте скрипт исполняемым (для Linux/macOS):

```bash
chmod +x scripts/start-server.sh
```

### Запуск сервера

#### Linux/macOS:
```bash
# Запуск с выделением 1 ГБ памяти
./scripts/start-server.sh
```

#### Windows:
```cmd
# Запуск с выделением 1 ГБ памяти
scripts\start-server.bat
```
