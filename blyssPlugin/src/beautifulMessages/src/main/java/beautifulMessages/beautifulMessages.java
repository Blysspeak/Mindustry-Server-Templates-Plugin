package beautifulMessages;

import arc.Events;
import arc.util.CommandHandler;
import mindustry.game.EventType.*;
import mindustry.gen.Player;
import mindustry.mod.Plugin;
import mindustry.gen.Call;

public class beautifulMessages extends Plugin {
    
    @Override
    public void init() {
        // Красивое уведомление о загрузке плагина
        System.out.println("══════════");
        System.out.println("   [BeautifulMessages] КРАСИВЫЕ СООБЩЕНИЯ!");
        System.out.println("   Версия: 1.0");
        System.out.println("   Автор: blysspeak");
        System.out.println("   Тип: Серверный плагин");
        System.out.println("══════════");
        
        // Регистрируем обработчики событий для входа и выхода игроков
        Events.on(PlayerJoin.class, event -> {
            Player player = event.player;
            // Отправляем красивое сообщение в чат при входе игрока
            sendBeautifulJoinMessage(player.name);
        });
        
        Events.on(PlayerLeave.class, event -> {
            Player player = event.player;
            // Отправляем красивое сообщение в чат при выходе игрока
            sendBeautifulLeaveMessage(player.name);
        });
    }
    
    private void sendBeautifulJoinMessage(String playerName) {
        // Создаем красивое цветное сообщение для входа игрока
        String message = "[#FFA500]✨ Добро пожаловать на сервер! ✨\n" +
                        "[#00FF00]Игрок [#" + generatePlayerColor(playerName) + "]" + playerName + "[] [#00FF00]присоединился к игре!\n" +
                        "[#FF69B4]Пусть удача будет на вашей стороне! 🍀";
        
        // Отправляем сообщение в чат сервера
        Call.sendMessage(message);
    }
    
    private void sendBeautifulLeaveMessage(String playerName) {
        // Создаем красивое цветное сообщение для выхода игрока
        String message = "[#FF0000]🚪 Игрок [#" + generatePlayerColor(playerName) + "]" + playerName + "[] [#FF0000]покинул сервер.\n" +
                        "[#8A2BE2]Спасибо за игру! До скорой встречи! 👋";
        
        // Отправляем сообщение в чат сервера
        Call.sendMessage(message);
    }
    
    private String generatePlayerColor(String playerName) {
        // Генерируем уникальный цвет для имени игрока на основе его имени
        int hash = playerName.hashCode();
        int red = (hash & 0xFF0000) >> 16;
        int green = (hash & 0x00FF00) >> 8;
        int blue = hash & 0x0000FF;
        
        return String.format("%02X%02X", red, green, blue);
    }
}
