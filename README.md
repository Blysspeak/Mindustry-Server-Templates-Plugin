# 🚀 Mindustry Plugin Server

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Mindustry](https://img.shields.io/badge/Mindustry-v151.1-orange.svg)](https://mindustrygame.github.io/)
[![Plugins](https://img.shields.io/badge/plugins-4-green.svg)](blyssPlugin/src/)

Совмещённый репозиторий с сервером Mindustry и системой плагинов.

## 🌟 Возможности

- 🎮 Готовый к использованию сервер Mindustry с предустановленными плагинами
- 🔧 Удобная система управления плагинами с CLI инструментами
- 📦 Поддержка пользовательских плагинов для расширения функциональности
- 🚀 Простой запуск сервера на Linux/macOS и Windows
- 📚 Полное руководство по разработке плагинов в [DEVELOPMENT-GUIDE.md](DEVELOPMENT-GUIDE.md)

Подробное руководство по разработке плагинов и работе с сервером находится в файле [DEVELOPMENT-GUIDE.md](DEVELOPMENT-GUIDE.md).

## 📁 Структура проекта

```
mindustry-plugin-server/
├── server/                 # Сервер Mindustry
│   └── server.jar          # Исполняемый файл сервера
├── blyssPlugin/            # Система плагинов
│   ├── src/                # Все плагины находятся здесь
│   ├── advertisementSystem/    # Система рекламных сообщений
│   │   ├── beautifulMessages/       # Красивые сообщения для игроков
│   │   ├── waveControl/            # Управление волнами врагов
│   │   └── helpSystem/              # Система помощи для игроков
│   └── ...                          # Другие файлы системы плагинов
└── scripts/                # Скрипты для запуска сервера
    ├── start-server.sh              # Скрипт запуска сервера (Linux/macOS)
    └── start-server.bat             # Скрипт запуска сервера (Windows)
```

## 🚀 Быстрый старт

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

## 📚 Документация

- [Полное руководство по разработке плагинов](DEVELOPMENT-GUIDE.md) - Подробное описание системы плагинов и API
- [Список предустановленных плагинов](blyssPlugin/src/) - Просмотр доступных плагинов
- [Команды плагинов](DEVELOPMENT-GUIDE.md#команды-плагинов) - Описание всех доступных команд

## 🧩 Предустановленные плагины

В комплекте идут следующие плагины для расширения функциональности сервера:

- 📢 **advertisementSystem** - Система рекламных сообщений
- 💬 **beautifulMessages** - Красивые сообщения для игроков
- 🌊 **waveControl** - Управление волнами врагов
- ℹ️ **helpSystem** - Система помощи для игроков

## 🤝 Вклад в проект

Мы приветствуем вклад сообщества в развитие проекта!
Если у вас есть идеи по улучшению или вы хотите добавить новый функционал:

1. Форкните репозиторий
2. Создайте ветку для вашей функции (`git checkout -b feature/AmazingFeature`)
3. Зафиксируйте изменения (`git commit -m 'Add some AmazingFeature'`)
4. Запушьте ветку (`git push origin feature/AmazingFeature`)
5. Откройте Pull Request

📨 Связаться с автором: [@Blysspeak](https://t.me/Blysspeak)

## 📄 Лицензия

Этот проект лицензирован под MIT License - см. файл [LICENSE](LICENSE) для подробностей.
