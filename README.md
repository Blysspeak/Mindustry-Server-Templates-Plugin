# 🚀 Mindustry Server с плагинами

[![Mindustry](https://img.shields.io/badge/Mindustry-v151.1-blue)](https://mindustrygame.github.io/)
[![Java](https://img.shields.io/badge/Java-8%2B-orange)](https://www.java.com/)
[![License](https://img.shields.io/badge/license-MIT-green)](LICENSE)

Этот репозиторий содержит **совмещённый сервер Mindustry с системой плагинов** 🎮, готовый для развертывания на любом сервере.

## 📋 Содержание

- [🌟 Особенности](#-особенности)
- [📂 Структура проекта](#-структура-проекта)
- [🎮 Плагины](#-плагины)
- [🚀 Запуск сервера](#-запуск-сервера)
- [🔧 Разработка плагинов](#-разработка-плагинов)
- [📚 Полезные ресурсы](#-полезные-ресурсы)
- [📞 Контакты](#-контакты)

## 🌟 Особенности

✨ **Что включено в этот проект:**

- 🖥️ **Сервер Mindustry v151.1** с предустановленными плагинами
- 🧩 **Система плагинов** с удобным CLI инструментом
- 🐳 **Docker поддержка** для легкого развертывания
- ⚡ **PM2 конфигурация** для управления процессами
- 📦 **Готовые скрипты** запуска для Linux/macOS/Windows
- 🔧 **Оптимизировано** для слабых серверов (512MB RAM)

## 📂 Структура проекта

```
mindustry-plugin-server/
├── 📁 server/                 # Сервер Mindustry
│   └── server.jar             # Исполняемый файл сервера
├── 📁 blyssPlugin/            # Система плагинов
│   ├── 📁 src/                # Все плагины находятся здесь
│   │   ├── advertisementSystem/    # Система рекламных сообщений
│   │   ├── beautifulMessages/      # Красивые сообщения для игроков
│   │   ├── waveControl/           # Управление волнами врагов
│   │   └── helpSystem/            # Система помощи для игроков
│   ├── settings.gradle            # Настройки проекта Gradle
│   ├── build.gradle              # Основной build файл
│   └── gradle.properties         # Свойства Gradle
├── 📁 scripts/                # Скрипты для запуска сервера
│   ├── start-server.sh           # Скрипт запуска сервера (Linux/macOS)
│   └── start-server.bat          # Скрипт запуска сервера (Windows)
├── 📁 logs/                   # Директория для логов сервера
├── Dockerfile                 # Docker конфигурация
├── docker-compose.yml         # Docker Compose конфигурация
└── ecosystem.config.js        # PM2 конфигурация
```

## 🎮 Плагины

🎯 **В комплекте идут 4 мощных плагина:**

### 📢 Advertisement System (Рекламная система)

Система автоматических рекламных сообщений с полным управлением:

**Команды администратора:**

- `ad-add <текст>` - Добавить рекламу
- `ad-remove <индекс>` - Удалить рекламу
- `ad-list` - Список реклам
- `ad-start` - Запустить рекламу
- `ad-stop` - Остановить рекламу
- `ad-interval <сек>` - Интервал показа
- `ad-test` - Показать тестовую рекламу
- `ad-clear` - Очистить все рекламы
- `ad-random` - Переключить случайный порядок

### 💬 Beautiful Messages (Красивые сообщения)

Плагин для улучшенного отображения сообщений в чате игроков.

### 🆘 Help System (Система помощи)

Интуитивная система помощи для новых игроков с командами и правилами сервера.

### 🌊 Wave Control (Управление волнами)

Полный контроль над волнами врагов:

**Команды:**

- `skipwave` - Пропустить волну
- `setwave <n>` - Установить волну
- `waveinfo` - Информация о волне
- `speedwave [x]` - Ускорить таймер

## 🚀 Запуск сервера

### 🐳 Вариант 1: Docker (Рекомендуется)

```bash
# 🐳 Запуск Docker daemon (если не запущен)
sudo systemctl start docker

# 🚀 Сборка и запуск контейнера
sudo docker compose build
sudo docker compose up -d

# 📋 Просмотр логов
sudo docker compose logs -f

# 🔧 Подключение к консоли сервера
sudo docker attach mindustry-server
# Для выхода: CTRL+P, затем CTRL+Q
```

### ☕ Вариант 2: Прямой запуск через Java

```bash
cd server
java -Xmx512M -jar server.jar host
```

### 📜 Вариант 3: Через скрипты

**Linux/macOS:**

```bash
chmod +x scripts/start-server.sh
./scripts/start-server.sh
```

**Windows:**

```cmd
scripts\start-server.bat
```

### ⚙️ Вариант 4: Через PM2

```bash
pm2 start ecosystem.config.js
pm2 list
pm2 logs mindustry-server
```

## 🔧 Разработка плагинов

Подробная информация о разработке плагинов находится в файле [DEVELOPMENT-GUIDE.md](DEVELOPMENT-GUIDE.md).

## 📚 Полезные ресурсы

### 🌐 Официальные источники

- 📚 [Mindustry Wiki - Plugins](https://mindustrygame.github.io/wiki/modding/2-plugins/)
- 📖 [Mindustry API Documentation](https://mindustrygame.github.io/docs/)
- 🐙 [Mindustry GitHub](https://github.com/Anuken/Mindustry)

### 🎨 Шаблоны и примеры

- 📋 [Plugin Template](https://github.com/Anuken/MindustryPluginTemplate)
- 🧱 [Java Mod Template](https://github.com/Anuken/MindustryJavaModTemplate)
- 🔐 [Authorize Plugin](https://github.com/Anuken/AuthorizePlugin)

### 📦 Репозитории плагинов

- 📚 [MindustryPlugins](https://github.com/MindustryInside/MindustryPlugins)
- 🔍 [GitHub Topic: mindustry-plugin](https://github.com/topics/mindustry-plugin)

## 📞 Контакты

👨‍💻 **Разработчик:** blysspeak  
📧 **Email:** blysspeak@gmail.com
🐙 **GitHub:** [blysspeak](https://github.com/blysspeak)

---

💡 **Совет:** Сервер автоматически запускает карту при запуске контейнера. Наслаждайтесь игрой! 🎮
