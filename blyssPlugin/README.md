# Mindustry Plugin System

Этот проект содержит систему плагинов для сервера Mindustry с удобным CLI инструментом для создания новых плагинов.

Подробное руководство по разработке плагинов и работе с сервером находится в файле [DEVELOPMENT-GUIDE.md](../DEVELOPMENT-GUIDE.md).

## Структура проекта

```
blyssPlugin/
├── src/                    # Все плагины находятся здесь
│   ├── advertisementSystem/    # Пример плагина
│   ├── helpSystem/              # Пример плагина
│   ├── waveControl/            # Пример плагина
│   └── testplugin/              # Тестовый плагин
├── create-plugin.sh        # Скрипт создания плагинов (устарел)
├── new-plugin              # Обертка для CLI (устарела)
├── settings.gradle         # Настройки проекта Gradle
├── build.gradle           # Основной build файл
└── gradle.properties      # Свойства Gradle
```

## Использование CLI инструмента

Для создания нового плагина используйте одну из следующих команд:

```bash
./gradlew new -PnewPlugin=myPlugin
./gradlew new -PpluginName=myPlugin
./gradlew new -Ppname=myPlugin
```

Например:
```bash
./gradlew new -PnewPlugin=my-awesome-plugin
```

Команда автоматически:
1. Создаст структуру папок для нового плагина
2. Сгенерирует необходимые файлы на основе шаблонов
3. Обновит settings.gradle для включения нового плагина

Для удаления существующего плагина используйте одну из следующих команд:

```bash
./gradlew remove -PremovePlugin=myPlugin
./gradlew remove -PdelPlugin=myPlugin
./gradlew remove -Pdel=myPlugin
```

Например:
```bash
./gradlew remove -PremovePlugin=my-awesome-plugin
```

## Сборка плагинов

Для сборки всех плагинов используйте:

```bash
./gradlew jar
```

Для сборки конкретного плагина:
```bash
./gradlew :src:<имя-плагина>:jar
```

Например:
```bash
./gradlew :src:testplugin:jar
```

Собранные плагины будут находиться в папке `build/libs` каждого плагина.

## Структура плагина

Каждый плагин имеет следующую структуру:
```
<имя-плагина>/
├── build.gradle          # Конфигурация сборки
├── src/
│   └── main/
│       └── java/
│           └── <имя-плагина>/
│               ├── <имя-плагина>.java    # Основной класс плагина
│               └── mod.hjson             # Метаданные плагина
└── build/
    └── libs/            # Собранный JAR файл
```

## Разработка плагина

1. Создайте новый плагин с помощью CLI инструмента
2. Откройте проект вашей IDE
3. Добавьте функциональность в файлы плагина:
   - `<имя-плагина>.java` - основной класс плагина
   - `mod.hjson` - метаданные плагина
4. Соберите плагин с помощью Gradle

## Примеры плагинов

В проекте уже есть несколько примеров плагинов:
- `advertisementSystem` - система рекламных сообщений
- `helpSystem` - система помощи для игроков
- `waveControl` - управление волнами врагов

Изучите их для понимания структуры и возможностей плагинов Mindustry.