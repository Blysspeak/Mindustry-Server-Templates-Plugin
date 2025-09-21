package helpSystem;

import arc.util.CommandHandler;
import mindustry.mod.Plugin;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import static mindustry.Vars.*;
import arc.Core;

public class helpSystem extends Plugin {
    
    private static final String PREFIX = "[royal]《[cyan]ПОМОЩЬ[royal]》[white] ";
    
    @Override
    public void init() {
        // Красивое уведомление о загрузке плагина
        System.out.println("══════════");
        System.out.println("   [HelpSystem] СИСТЕМА ПОМОЩИ!");
        System.out.println("   Версия: 1.0");
        System.out.println("   Автор: blysspeak");
        System.out.println("   Тип: Серверный плагин");
        System.out.println("══════════");
    }
    
    @Override
    public void registerServerCommands(CommandHandler handler) {
        System.out.println("[HelpSystem] Регистрация серверных команд...");
    }
    
    @Override
    public void registerClientCommands(CommandHandler handler) {
        // Главная команда помощи - переопределяем стандартную
        handler.register("help", "[команда]", "Показать список команд или помощь по конкретной команде", args -> {
            if (args.length > 0) {
                // Показать помощь по конкретной команде (стандартное поведение)
                showSpecificCommandHelp(args[0]);
            } else {
                // Показать красивое меню со всеми командами
                showBeautifulHelpMenu();
            }
        });
        
        // Альтернативная команда
        handler.register("commands", "Показать все доступные команды", args -> {
            showBeautifulHelpMenu();
        });
        
        // Информация о сервере
        handler.register("serverinfo", "Подробная информация о сервере", args -> {
            showServerInfo();
        });
        
        // Список игроков
        handler.register("players", "Список игроков онлайн", args -> {
            showPlayersList();
        });
        
        // Правила сервера  
        handler.register("rules", "Правила сервера", args -> {
            showServerRules();
        });
        
        // Информация об админах
        handler.register("admins", "Список администраторов", args -> {
            showAdminsList();
        });
        
        // Статистика игрока
        handler.register("stats", "[игрок]", "Показать статистику игрока", args -> {
            if (args.length > 0) {
                showPlayerStats(args[0]);
            } else {
                // Получаем игрока, который вызвал команду (это сложно в клиентском моде)
                showPlayerStats(""); // Покажем статистику для текущего игрока
            }
        });
    }
    
    private void showBeautifulHelpMenu() {
        StringBuilder message = new StringBuilder();
        
        // Заголовок
        message.append("[royal]╔══════════╗\n");
        message.append("[royal]║[cyan]         СИСТЕМА ПОМОЩИ         [royal]║\n");
        message.append("[royal]╚════════╝\n\n");
        
        // Команды для всех игроков
        message.append("[yellow]📋 ОСНОВНЫЕ КОМАНДЫ:\n");
        message.append("[white]• [cyan]/help [команда][white] - Помощь по команде\n");
        message.append("[white]• [cyan]/commands[white] - Все команды\n");
        message.append("[white]• [cyan]/serverinfo[white] - Информация о сервере\n");
        message.append("[white]• [cyan]/players[white] - Список игроков\n");
        message.append("[white]• [cyan]/rules[white] - Правила сервера\n");
        message.append("[white]• [cyan]/stats [игрок][white] - Статистика игрока\n");
        message.append("[white]• [cyan]/admins[white] - Список администрации\n\n");
        
        // Команды управления игрой
        message.append("[green]🎮 ИГРОВЫЕ КОМАНДЫ:\n");
        message.append("[white]• [cyan]/wave[white] - Информация о волне\n");
        message.append("[white]• [cyan]/surrender[white] - Сдаться (голосование)\n");
        message.append("[white]• [cyan]/votekick <игрок>[white] - Кикнуть игрока\n");
        message.append("[white]• [cyan]/sync[white] - Синхронизация с сервером\n");
        
        // Дополнительные команды
        message.append("[sky]🔧 ДОПОЛНИТЕЛЬНО:\n");
        message.append("[white]• [cyan]/t <сообщение>[white] - Командный чат\n");
        message.append("[white]• [cyan]/discord[white] - Ссылка на Discord\n");
        message.append("[white]• [cyan]/donate[white] - Поддержать сервер\n");
        message.append("[white]• [cyan]/version[white] - Версия сервера\n\n");
        
        // Подвал
        message.append("[royal]╔════════╗\n");
        message.append("[royal]║[yellow] Используйте [cyan]/help <команда>[yellow] для  [royal]║\n");
        message.append("[royal]║[yellow]    получения подробной помощи   [royal]║\n");
        message.append("[royal]╚══════════════════╝");
        
        Call.sendMessage(message.toString());
    }
    
    private void showSpecificCommandHelp(String command) {
        String helpText = "";
        
        switch (command.toLowerCase()) {
            case "help":
                helpText = "[cyan]help [команда][white] - Показывает список всех команд или помощь по конкретной команде";
                break;
            case "serverinfo":
                helpText = "[cyan]serverinfo[white] - Отображает подробную информацию о сервере: название, игроков, карту, волну";
                break;
            case "players":
                helpText = "[cyan]players[white] - Показывает список всех игроков онлайн с их ID и статусом";
                break;
            case "stats":
                helpText = "[cyan]stats [игрок][white] - Показывает статистику игрока: время игры, постройки, убийства";
                break;
            case "wave":
                helpText = "[cyan]wave[white] - Показывает информацию о текущей волне и времени до следующей";
                break;
            case "surrender":
                helpText = "[cyan]surrender[white] - Начинает голосование за сдачу (требуется больше 50% голосов)";
                break;
            case "votekick":
                helpText = "[cyan]votekick <игрок>[white] - Начинает голосование за исключение игрока";
                break;
            case "sync":
                helpText = "[cyan]sync[white] - Принудительная синхронизация с сервером (при лагах)";
                break;
            case "t":
                helpText = "[cyan]t <сообщение>[white] - Отправляет сообщение только союзникам по команде";
                break;
            default:
                helpText = "[red]Команда '" + command + "' не найдена. Используйте /help для списка команд.";
        }
        
        Call.sendMessage(PREFIX + helpText);
    }
    
    private void showServerInfo() {
        StringBuilder info = new StringBuilder();
        
        info.append("[sky]╔════════╗\n");
        info.append("[sky]║[yellow]        ИНФОРМАЦИЯ О СЕРВЕРЕ      [sky]║\n");
        info.append("[sky]╚══════════╝\n\n");
        
        info.append("[yellow]🖥️ Сервер: [white]").append(Core.settings.getString("servername", "Mindustry Server")).append("\n");
        info.append("[yellow]👥 Игроков онлайн: [white]").append(Groups.player.size()).append("\n");
        info.append("[yellow]🗺️ Карта: [white]").append(state.map != null ? state.map.name() : "Неизвестно").append("\n");
        info.append("[yellow]📊 Режим: [white]").append(state.rules.pvp ? "PvP" : (state.rules.waves ? "Survival" : "Sandbox")).append("\n");
        
        if (state.rules.waves) {
            info.append("[yellow]🌊 Волна: [white]").append(state.wave).append("\n");
            float timeLeft = state.wavetime / 60f;
            info.append("[yellow]⏱️ До следующей волны: [white]").append(String.format("%.0f", timeLeft)).append(" сек\n");
        }
        
        info.append("[yellow]⚡ Версия: [white]").append("v150.1").append("\n");
        info.append("[yellow]🔧 Сборка: [white]").append("150.1").append("\n");
        
        Call.sendMessage(info.toString());
    }
    
    private void showPlayersList() {
        if (Groups.player.size() == 0) {
            Call.sendMessage(PREFIX + "[red]Нет игроков онлайн!");
            return;
        }
        
        StringBuilder list = new StringBuilder();
        
        list.append("[green]╔════════════════╗\n");
        list.append("[green]║[yellow]         ИГРОКИ ОНЛАЙН           [green]║\n");
        list.append("[green]╚════════╝\n\n");
        
        int index = 1;
        for (Player p : Groups.player) {
            String status = p.admin ? "[red][АДМИН]" : "[white][ИГРОК]";
            String team = p.team() != null ? "[#" + p.team().color + "]" + p.team().name : "[white]None";
            
            list.append("[yellow]").append(index++).append(". ")
                .append(status).append(" [white]").append(p.name)
                .append(" ").append(team).append("\n");
        }
        
        list.append("\n[green]Всего игроков: [yellow]").append(Groups.player.size());
        
        Call.sendMessage(list.toString());
    }
    
    private void showServerRules() {
        StringBuilder rules = new StringBuilder();
        
        rules.append("[red]╔══════════╗\n");
        rules.append("[red]║[yellow]        ПРАВИЛА СЕРВЕРА          [red]║\n");
        rules.append("[red]╚══════════════╝\n\n");
        
        rules.append("[white]1. [cyan]Уважайте других игроков\n");
        rules.append("[white]2. [cyan]Запрещен спам и флуд\n");
        rules.append("[white]3. [cyan]Не используйте читы или баги\n");
        rules.append("[white]4. [cyan]Не разрушайте постройки союзников\n");
        rules.append("[white]5. [cyan]Слушайте администрацию\n");
        rules.append("[white]6. [cyan]Играйте честно и получайте удовольствие!\n\n");
        
        rules.append("[yellow]⚠️ Нарушение правил может привести к бану!\n");
        rules.append("[green]📞 Связь с админами: Discord или /admins");
        
        Call.sendMessage(rules.toString());
    }
    
    private void showAdminsList() {
        StringBuilder admins = new StringBuilder();
        
        admins.append("[red]╔══════════════╗\n");
        admins.append("[red]║[yellow]        АДМИНИСТРАЦИЯ            [red]║\n");
        admins.append("[red]╚══════════╝\n\n");
        
        // Показываем онлайн админов
        boolean hasOnlineAdmins = false;
        admins.append("[green]🟢 Онлайн:\n");
        for (Player p : Groups.player) {
            if (p.admin) {
                admins.append("[white]• [red]").append(p.name).append("\n");
                hasOnlineAdmins = true;
            }
        }
        
        if (!hasOnlineAdmins) {
            admins.append("[gray]• Нет администраторов онлайн\n");
        }
        
        admins.append("\n[red]📋 Главная администрация:\n");
        admins.append("[white]• [red]blysspeak [gray](Владелец)\n");
        admins.append("[white]• [yellow]Admin1 [gray](Модератор)\n");
        admins.append("[white]• [yellow]Admin2 [gray](Модератор)\n\n");
        
        admins.append("[cyan]💬 Связь: Discord сервер или личные сообщения");
        
        Call.sendMessage(admins.toString());
    }
    
    private void showPlayerStats(String playerName) {
        Player target = null;
        
        if (playerName != null && !playerName.isEmpty()) {
            target = Groups.player.find(p -> p.name.equalsIgnoreCase(playerName));
        } else {
            // Для клиентского мода сложно получить текущего игрока
            // Покажем статистику для первого игрока в списке
            if (Groups.player.size() > 0) {
                target = Groups.player.first();
            }
        }
        
        if (target == null) {
            Call.sendMessage(PREFIX + "[red]Игрок '" + playerName + "' не найден!");
            return;
        }
        
        StringBuilder stats = new StringBuilder();
        
        stats.append("[sky]╔══════════════╗\n");
        stats.append("[sky]║[yellow]    СТАТИСТИКА: ").append(target.name).append("   [sky]║\n");
        stats.append("[sky]╚══════════════╝\n\n");
        
        stats.append("[yellow]👤 Игрок: [white]").append(target.name).append("\n");
        stats.append("[yellow]🏷️ Статус: ").append(target.admin ? "[red]Администратор" : "[white]Игрок").append("\n");
        stats.append("[yellow]🎯 Команда: [#").append(target.team().color).append("]").append(target.team().name).append("\n");
        stats.append("[yellow]📍 ID: [white]").append(target.id).append("\n");
        
        // Дополнительная статистика (если доступна)
        if (target.unit() != null) {
            stats.append("[yellow]🤖 Юнит: [white]").append(target.unit().type.localizedName).append("\n");
            stats.append("[yellow]❤️ HP: [white]").append((int)target.unit().health).append("/").append((int)target.unit().maxHealth).append("\n");
        }
        
        stats.append("[yellow]⏱️ Время на сервере: [white]").append("Неизвестно").append("\n");
        
        Call.sendMessage(stats.toString());
    }
}