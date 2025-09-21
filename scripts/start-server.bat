@echo off
:: Скрипт для запуска сервера Mindustry с выделением 1 ГБ памяти на Windows

:: Определяем директорию, где находится скрипт
set SCRIPT_DIR=%~dp0
set PROJECT_DIR=%SCRIPT_DIR:~0,-1%

:: Переходим в директорию сервера
cd /d "%PROJECT_DIR%\server" || (echo Ошибка: Не удалось перейти в директорию сервера & pause & exit /b 1)

:: Проверяем наличие server.jar
if not exist "server.jar" (
    echo Ошибка: Файл server.jar не найден в директории server
    pause
    exit /b 1
)

:: Запускаем сервер с выделением 1 ГБ памяти
echo Запуск сервера Mindustry с 1 ГБ памяти...
java -Xmx1G -jar server.jar host

:: Пауза, чтобы окно не закрывалось после завершения
pause