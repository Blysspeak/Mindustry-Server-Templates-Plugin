# Руководство по разработке плагинов и работе с сервером Mindustry

## Введение

Этот проект представляет собой совмещённый репозиторий с сервером Mindustry и системой плагинов.
Mindustry поддерживает загрузку JAR-файлов с Java байткодом на десктопных платформах и Android.
Плагины - это Java моды, предназначенные для работы только на серверах. Обычно они добавляют новые команды или игровые режимы.

### Ключевые особенности:

- Плагины работают только на серверной стороне
- Клиентам не нужно скачивать плагин для подключения к серверу
- Поддерживаются все JVM языки (Java, Kotlin, Scala и др.)
- Полный доступ к Java API без ограничений

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
│   ├── settings.gradle              # Настройки проекта Gradle
│   ├── build.gradle                # Основной build файл
│   └── gradle.properties           # Свойства Gradle
└── scripts/                # Скрипты для запуска сервера
    ├── start-server.sh              # Скрипт запуска сервера (Linux/macOS)
    └── start-server.bat             # Скрипт запуска сервера (Windows)
```

## Работа с сервером

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

### Команды плагинов

После запуска сервера доступны следующие команды плагинов:

#### WaveControl

- `skipwave` - Пропустить волну
- `setwave <n>` - Установить волну
- `waveinfo` - Информация о волне
- `speedwave [x]` - Ускорить таймер

#### AdSystem (Рекламная система)

- `ad-add <текст>` - Добавить рекламу
- `ad-remove <индекс>` - Удалить рекламу
- `ad-list` - Список реклам
- `ad-start` - Запустить рекламу
- `ad-stop` - Остановить рекламу
- `ad-interval <сек>` - Интервал показа
- `ad-test` - Показать тестовую рекламу
- `ad-clear` - Очистить все рекламы
- `ad-random` - Переключить случайный порядок

## Система плагинов

Проект содержит систему плагинов для сервера Mindustry с удобным CLI инструментом для создания новых плагинов.

### Использование CLI инструмента

Для создания нового плагина используйте одну из следующих команд:

```bash
./blyssPlugin/gradlew new -PnewPlugin=myPlugin
./blyssPlugin/gradlew new -PpluginName=myPlugin
./blyssPlugin/gradlew new -Ppname=myPlugin
```

Например:

```bash
./blyssPlugin/gradlew new -PnewPlugin=my-awesome-plugin
```

Команда автоматически:

1. Создаст структуру папок для нового плагина
2. Сгенерирует необходимые файлы на основе шаблонов
3. Обновит settings.gradle для включения нового плагина

Для удаления существующего плагина используйте одну из следующих команд:

```bash
./blyssPlugin/gradlew remove -PremovePlugin=myPlugin
./blyssPlugin/gradlew remove -PdelPlugin=myPlugin
./blyssPlugin/gradlew remove -Pdel=myPlugin
```

Например:

```bash
./blyssPlugin/gradlew remove -PremovePlugin=my-awesome-plugin
```

### Сборка плагинов

Для сборки всех плагинов используйте:

```bash
./blyssPlugin/gradlew jar
```

Для сборки конкретного плагина:

```bash
./blyssPlugin/gradlew :src:<имя-плагина>:jar
```

Например:

```bash
./blyssPlugin/gradlew :src:testplugin:jar
```

Для просмотра списка всех доступных плагинов используйте команду:

```bash
./blyssPlugin/gradlew list
```

Собранные плагины будут находиться в папке `build/libs` каждого плагина.

### Примеры плагинов

В проекте уже есть несколько примеров плагинов:

- `advertisementSystem` - система рекламных сообщений
- `helpSystem` - система помощи для игроков
- `waveControl` - управление волнами врагов
- `beautifulMessages` - красивые сообщения для игроков

Изучите их для понимания структуры и возможностей плагинов Mindustry.

## Руководство по созданию плагинов

### Требования и настройка

#### Системные требования:

- Java Development Kit (JDK) 8 или выше
- Gradle (рекомендуется)
- IDE (IntelliJ IDEA, Eclipse или VS Code)
- Mindustry Server 151.1

#### Зависимости:

```gradle
dependencies {
    compileOnly 'com.github.Anuken:Mindustry:v151.1'
}
```

### Структура плагина

```
plugin-name/
├── src/
│   └── main/
│       ├── java/
│       │   └── yourpackage/
│       │       └── YourPluginMod.java
│       └── resources/
│           └── plugin.hjson
├── build.gradle
└── README.md
```

### Создание основного класса

#### Базовый шаблон плагина:

```java
package yourpackage;

import mindustry.mod.*;
import arc.util.*;
import mindustry.entities.type.*;
import mindustry.gen.*;
import mindustry.plugin.Plugin;

public class YourPluginMod extends Plugin {

    // Вызывается при инициализации плагина
    @Override
    public void init() {
        Log.info("Плагин загружен!");

        // Регистрация событий
        Events.on(EventType.PlayerJoin.class, event -> {
            Log.info("Игрок @ присоединился", event.player.name);
        });

        Events.on(EventType.PlayerLeave.class, event -> {
            Log.info("Игрок @ покинул сервер", event.player.name);
        });
    }

    // Регистрация команд
    @Override
    public void registerServerCommands(CommandHandler handler) {
        handler.register("mycommand", "<параметр>", "Описание команды", (args) -> {
            Log.info("Выполнена команда с параметром: " + args[0]);
        });
    }

    // Регистрация клиентских команд (в чате)
    @Override
    public void registerClientCommands(CommandHandler handler) {
        handler.<Player>register("help", "Показать справку", (args, player) -> {
            player.sendMessage("[green]Доступные команды:");
            player.sendMessage("[yellow]!help - Показать справку");
            player.sendMessage("[yellow]!info - Информация о сервере");
        });

        handler.<Player>register("players", "Список игроков", (args, player) -> {
            StringBuilder players = new StringBuilder();
            players.append("[green]Игроки онлайн (").append(Groups.player.size()).append("):\n");

            for (Player p : Groups.player) {
                players.append("[white]- ").append(p.name).append("\n");
            }

            player.sendMessage(players.toString());
        });
    }
}
```

### Файл конфигурации mod.hjson

#### Базовая структура:

```hjson
{
    name: "YourPluginName"
    author: "Your Name"
    main: "yourpackage.YourPluginMod"
    description: "Описание вашего плагина"
    version: "1.0.0"
    minGameVersion: "151"
}
```

### Компиляция и установка

#### Gradle Build Script (build.gradle):

```gradle
plugins {
    id 'java'
    id 'application'
}

version '1.0.0'
sourceCompatibility = 1.8
targetCompatibility = 1.8

repositories {
    mavenCentral()
    maven { url 'https://www.jitpack.io' }
}

dependencies {
    compileOnly 'com.github.Anuken:Mindustry:v151.1'
}

jar {
    from configurations.runtimeClasspath.collect { it.isDirectory() ? it : zipTree(it) }
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

task copyPlugin(type: Copy) {
    from jar
    into '../server/config/mods/'
}

build.finalizedBy copyPlugin
```

#### Команды сборки:

```bash
# Сборка плагина
./gradlew build

# Очистка и пересборка
./gradlew clean build

# Копирование в папку сервера
./gradlew copyPlugin
```

#### Установка плагина:

1. Скомпилируйте плагин в JAR файл
2. Поместите JAR файл в `<server directory>/config/mods/`
3. Перезапустите сервер
4. Плагин загрузится автоматически

## Безопасность и ограничения

⚠️ **ВАЖНО**: JAR моды загружаются без песочницы и имеют полный доступ к системе:

- Полный доступ к Java API
- Возможность использования рефлексии
- Доступ к файловой системе
- Возможность изменения игровых файлов

### Рекомендации по безопасности:

- Устанавливайте только плагины из доверенных источников
- Просматривайте исходный код перед установкой
- Используйте изолированную среду для тестирования

## Многопоточность

⚠️ **Критически важно**: Код Mindustry НЕ является потокобезопасным.

### Правильный способ выполнения в главном потоке:

```java
// НЕПРАВИЛЬНО - вызов из другого потока
new Thread(() -> {
    player.sendMessage("Это вызовет краш!");
}).start();

// ПРАВИЛЬНО - использование Core.app.post()
new Thread(() -> {
    Core.app.post(() -> {
        player.sendMessage("Это безопасно!");
    });
}).start();

// Пример с таймером
Timer.schedule(() -> {
    Core.app.post(() -> {
        Call.sendMessage("[green]Автоматическое сообщение!");
    });
}, 60f, 60f); // Каждые 60 секунд
```

## Полезные ресурсы

### Официальные источники:

- [Mindustry Wiki - Plugins & JVM Mods](https://mindustrygame.github.io/wiki/modding/2-plugins/)
- [Mindustry API Documentation](https://mindustrygame.github.io/docs/)
- [Mindustry GitHub Repository](https://github.com/Anuken/Mindustry)

### Шаблоны и примеры:

- [Mindustry Plugin Template](https://github.com/Anuken/MindustryPluginTemplate)
- [Mindustry Java Mod Template](https://github.com/Anuken/MindustryJavaModTemplate)
- [Authorize Plugin Example](https://github.com/Anuken/AuthorizePlugin)

---

## Заключение

Создание плагинов для Mindustry сервера - это мощный способ расширения функциональности игры. С помощью Java API вы можете создавать сложные системы управления, модерации и игровых механик. Помните о безопасности и всегда тестируйте плагины в изолированной среде перед использованием на продакшн сервере.

**Версия документа**: 1.0  
**Совместимость**: Mindustry Server 151.1  
**Дата создания**: Сентябрь 2025
