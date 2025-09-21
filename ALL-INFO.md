# Полное руководство по созданию плагинов для Mindustry Server 151.1

## Оглавление
1. [Введение](#введение)
2. [Требования и настройка](#требования-и-настройка)
3. [Структура плагина](#структура-плагина)
4. [Создание основного класса](#создание-основного-класса)
5. [Файл конфигурации plugin.hjson](#файл-конфигурации-pluginhjson)
6. [События и API](#события-и-api)
7. [Компиляция и установка](#компиляция-и-установка)
8. [Безопасность и ограничения](#безопасность-и-ограничения)
9. [Многопоточность](#многопоточность)
10. [Примеры кода](#примеры-кода)
11. [Полезные ресурсы](#полезные-ресурсы)

## Введение

Mindustry поддерживает загрузку JAR-файлов с Java байткодом на десктопных платформах и Android. Плагины - это Java моды, предназначенные для работы только на серверах. Обычно они добавляют новые команды или игровые режимы.

### Ключевые особенности:
- Плагины работают только на серверной стороне
- Клиентам не нужно скачивать плагин для подключения к серверу
- Поддерживаются все JVM языки (Java, Kotlin, Scala и др.)
- Полный доступ к Java API без ограничений

## Требования и настройка

### Системные требования:
- Java Development Kit (JDK) 8 или выше
- Gradle (рекомендуется)
- IDE (IntelliJ IDEA, Eclipse или VS Code)
- Mindustry Server 151.1

### Зависимости:
```gradle
dependencies {
    compileOnly 'com.github.Anuken:Mindustry:v151.1'
}
```

## Структура плагина

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

## Создание основного класса

### Базовый шаблон плагина:

```java
package yourpackage;

import mindustry.mod.*;
import arc.*;
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

### Расширенный пример с событиями:

```java
package yourpackage;

import mindustry.mod.*;
import mindustry.plugin.Plugin;
import arc.*;
import arc.util.*;
import mindustry.entities.type.*;
import mindustry.gen.*;
import mindustry.game.EventType.*;
import mindustry.net.Administration.*;

public class AdvancedPlugin extends Plugin {
    
    @Override
    public void init() {
        Log.info("=== РАСШИРЕННЫЙ ПЛАГИН ЗАГРУЖЕН! ===");
        
        // Событие входа игрока
        Events.on(PlayerJoin.class, event -> {
            Player player = event.player;
            Call.sendMessage("[green]Добро пожаловать, " + player.name + "!");
            Log.info("Игрок @ [ID: @] присоединился", player.name, player.uuid());
        });
        
        // Событие выхода игрока
        Events.on(PlayerLeave.class, event -> {
            Player player = event.player;
            Call.sendMessage("[yellow]Игрок " + player.name + " покинул сервер");
            Log.info("Игрок @ покинул сервер", player.name);
        });
        
        // Событие чата
        Events.on(PlayerChatEvent.class, event -> {
            if (event.message.startsWith("!")) {
                Log.info("Команда от @: @", event.player.name, event.message);
            }
        });
        
        // Событие смерти игрока
        Events.on(UnitDestroyEvent.class, event -> {
            if (event.unit.isPlayer()) {
                Player player = event.unit.getPlayer();
                Call.sendMessage("[red]" + player.name + " погиб!");
            }
        });
        
        // Событие строительства
        Events.on(BlockBuildEndEvent.class, event -> {
            if (event.unit.isPlayer()) {
                Player player = event.unit.getPlayer();
                Log.info("Игрок @ построил @", player.name, event.tile.block().name);
            }
        });
    }
    
    @Override
    public void registerClientCommands(CommandHandler handler) {
        // Команда справки
        handler.<Player>register("help", "Показать все доступные команды", (args, player) -> {
            player.sendMessage("[accent]Доступные команды:");
            player.sendMessage("[white]!help - Показать эту справку");
            player.sendMessage("[white]!info - Информация о сервере");
            player.sendMessage("[white]!players - Список игроков онлайн");
            player.sendMessage("[white]!discord - Ссылка на Discord");
            player.sendMessage("[white]!rules - Правила сервера");
        });
        
        // Информация о сервере
        handler.<Player>register("info", "Показать информацию о сервере", (args, player) -> {
            player.sendMessage("[green]Информация о сервере:");
            player.sendMessage("[white]Версия: " + Version.build);
            player.sendMessage("[white]Карта: " + Vars.state.map.name());
            player.sendMessage("[white]Режим: " + Vars.state.rules.mode().name());
            player.sendMessage("[white]Игроков: " + Groups.player.size());
        });
        
        // Список игроков
        handler.<Player>register("players", "Показать список игроков онлайн", (args, player) -> {
            if (Groups.player.size() == 0) {
                player.sendMessage("[yellow]На сервере нет игроков.");
                return;
            }
            
            StringBuilder sb = new StringBuilder();
            sb.append("[green]Игроки онлайн (").append(Groups.player.size()).append("):\n");
            
            int i = 1;
            for (Player p : Groups.player) {
                sb.append("[white]").append(i).append(". ")
                  .append(p.name).append(" [gray](ID: ").append(p.id).append(")\n");
                i++;
            }
            
            player.sendMessage(sb.toString());
        });
        
        // Ссылка на Discord
        handler.<Player>register("discord", "Получить ссылку на Discord сервер", (args, player) -> {
            player.sendMessage("[royal]Присоединяйтесь к нашему Discord серверу:");
            player.sendMessage("[white]https://discord.gg/your-server");
        });
        
        // Правила сервера
        handler.<Player>register("rules", "Показать правила сервера", (args, player) -> {
            player.sendMessage("[scarlet]Правила сервера:");
            player.sendMessage("[white]1. Не используйте читы");
            player.sendMessage("[white]2. Уважайте других игроков");
            player.sendMessage("[white]3. Не разрушайте чужие постройки");
            player.sendMessage("[white]4. Следуйте указаниям администраторов");
        });
    }
    
    @Override
    public void registerServerCommands(CommandHandler handler) {
        // Команда для кика игрока
        handler.register("kickplayer", "<имя>", "Кикнуть игрока", (args) -> {
            Player target = Groups.player.find(p -> p.name.equalsIgnoreCase(args[0]));
            if (target != null) {
                target.kick("Вы были кикнуты администратором");
                Log.info("Игрок @ был кикнут", target.name);
            } else {
                Log.info("Игрок с именем @ не найден", args[0]);
            }
        });
        
        // Команда для отправки сообщения всем игрокам
        handler.register("announce", "<сообщение...>", "Отправить объявление", (args) -> {
            String message = String.join(" ", args);
            Call.sendMessage("[accent][ОБЪЯВЛЕНИЕ] " + message);
            Log.info("Отправлено объявление: @", message);
        });
    }
}
```

## Файл конфигурации plugin.hjson

### Базовая структура:
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

### Расширенная конфигурация:
```hjson
{
    name: "AdvancedServerPlugin"
    displayName: "Расширенный серверный плагин"
    author: "YourName"
    main: "yourpackage.AdvancedPlugin"
    description: '''
    Многофункциональный плагин для сервера Mindustry.
    Добавляет команды управления, события и утилиты.
    '''
    version: "2.1.0"
    minGameVersion: "151"
    hidden: true
}
```

## События и API

### Основные события для плагинов:

```java
// События игроков
Events.on(PlayerJoin.class, event -> {
    // Игрок присоединился
});

Events.on(PlayerLeave.class, event -> {
    // Игрок покинул сервер
});

Events.on(PlayerChatEvent.class, event -> {
    // Игрок написал в чат
    if (event.message.startsWith("spam")) {
        event.player.kick("Спам запрещен!");
    }
});

// События игрового процесса
Events.on(BlockBuildEndEvent.class, event -> {
    // Блок был построен
});

Events.on(BlockDestroyEvent.class, event -> {
    // Блок был разрушен
});

Events.on(UnitDestroyEvent.class, event -> {
    // Юнит был уничтожен
});

// События сервера
Events.on(PlayEvent.class, event -> {
    // Игра началась
});

Events.on(GameOverEvent.class, event -> {
    // Игра окончена
});

Events.on(WaveEvent.class, event -> {
    // Новая волна врагов
});
```

### Работа с игроками:

```java
// Получить всех игроков
Groups.player.each(player -> {
    // Действие для каждого игрока
    player.sendMessage("Привет!");
});

// Найти игрока по имени
Player target = Groups.player.find(p -> p.name.equals("PlayerName"));

// Проверить права администратора
if (player.admin) {
    // Игрок является администратором
}

// Кик игрока
player.kick("Причина кика");

// Отправить сообщение игроку
player.sendMessage("[green]Цветное сообщение!");

// Получить позицию игрока
float x = player.x;
float y = player.y;
```

## Компиляция и установка

### Gradle Build Script (build.gradle):

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

### Команды сборки:

```bash
# Сборка плагина
./gradlew build

# Очистка и пересборка
./gradlew clean build

# Копирование в папку сервера
./gradlew copyPlugin
```

### Установка плагина:

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

## Примеры кода

### Пример 1: Система автоматических сообщений

```java
public class AutoMessagePlugin extends Plugin {
    private String[] messages = {
        "[green]Добро пожаловать на наш сервер!",
        "[yellow]Используйте !help для списка команд",
        "[cyan]Присоединяйтесь к нашему Discord!"
    };
    
    private int messageIndex = 0;
    
    @Override
    public void init() {
        // Автоматические сообщения каждые 5 минут
        Timer.schedule(() -> {
            Core.app.post(() -> {
                if (Groups.player.size() > 0) {
                    Call.sendMessage(messages[messageIndex]);
                    messageIndex = (messageIndex + 1) % messages.length;
                }
            });
        }, 300f, 300f); // 300 секунд = 5 минут
    }
}
```

### Пример 2: Система защиты от гриферов

```java
public class AntiGriefPlugin extends Plugin {
    private final ObjectMap<String, Integer> buildCount = new ObjectMap<>();
    private final int MAX_BUILDS_PER_MINUTE = 50;
    
    @Override
    public void init() {
        Events.on(BlockBuildEndEvent.class, event -> {
            if (event.unit.isPlayer()) {
                Player player = event.unit.getPlayer();
                String uuid = player.uuid();
                
                int count = buildCount.get(uuid, 0) + 1;
                buildCount.put(uuid, count);
                
                if (count > MAX_BUILDS_PER_MINUTE) {
                    player.kick("Слишком быстрое строительство!");
                    Log.info("Игрок @ кикнут за подозрительную активность", player.name);
                }
            }
        });
        
        // Сброс счетчиков каждую минуту
        Timer.schedule(() -> {
            buildCount.clear();
        }, 60f, 60f);
    }
}
```

### Пример 3: Система рангов игроков

```java
public class RankSystem extends Plugin {
    private final ObjectMap<String, PlayerRank> playerRanks = new ObjectMap<>();
    
    enum PlayerRank {
        NEWBIE("Новичок", "[gray]"),
        REGULAR("Обычный", "[white]"),
        VETERAN("Ветеран", "[green]"),
        ADMIN("Админ", "[red]");
        
        public final String name;
        public final String color;
        
        PlayerRank(String name, String color) {
            this.name = name;
            this.color = color;
        }
    }
    
    @Override
    public void init() {
        Events.on(PlayerJoin.class, event -> {
            Player player = event.player;
            PlayerRank rank = getRank(player);
            player.name = rank.color + "[" + rank.name + "] " + player.name;
        });
    }
    
    private PlayerRank getRank(Player player) {
        if (player.admin) return PlayerRank.ADMIN;
        
        // Здесь можно добавить логику определения ранга
        // на основе времени игры, достижений и т.д.
        
        return PlayerRank.NEWBIE;
    }
}
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

### Списки плагинов:
- [MindustryPlugins Repository](https://github.com/MindustryInside/MindustryPlugins)
- [GitHub Topic: mindustry-plugin](https://github.com/topics/mindustry-plugin)

### Дополнительные ресурсы:
- [Mindustry Modding Guide](https://simonwoodburyforget.github.io/mindustry-modding/)
- [Javadocs for Mindustry](https://github.com/MindustryGame/docs)

---

## Заключение

Создание плагинов для Mindustry сервера - это мощный способ расширения функциональности игры. С помощью Java API вы можете создавать сложные системы управления, модерации и игровых механик. Помните о безопасности и всегда тестируйте плагины в изолированной среде перед использованием на продакшн сервере.

**Версия документа**: 1.0  
**Совместимость**: Mindustry Server 151.1  
**Дата создания**: Сентябрь 2025