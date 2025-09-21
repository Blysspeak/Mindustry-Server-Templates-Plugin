package advertisementSystem;

import arc.util.CommandHandler;
import arc.util.Timer;
import mindustry.mod.Plugin;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import static mindustry.Vars.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class advertisementSystem extends Plugin {
    
    private List<String> advertisements = new ArrayList<>();
    private Timer.Task adTask;
    private boolean adsEnabled = false;
    private float adInterval = 300f; // 5 минут по умолчанию
    private boolean randomOrder = false;
    private int currentAdIndex = 0;
    private String adPrefix = "[yellow]《[cyan]РЕКЛАМА[yellow]》[white] ";
    private Random random = new Random();
    
    @Override
    public void init() {
        // Красивое уведомление о загрузке плагина
        System.out.println("═══════════════════════════════════════");
        System.out.println("   [AdSystem] РЕКЛАМНАЯ СИСТЕМА!");
        System.out.println("   Версия: 1.0");
        System.out.println("   Автор: blysspeak");
        System.out.println("═══════════════════════════════════════");
        System.out.println("[AdSystem] Доступные команды:");
        System.out.println("  - ad-add <текст>: Добавить рекламу");
        System.out.println("  - ad-remove <индекс>: Удалить рекламу");
        System.out.println("  - ad-list: Список реклам");
        System.out.println("  - ad-start: Запустить рекламу");
        System.out.println("  - ad-stop: Остановить рекламу");
        System.out.println("  - ad-interval <сек>: Интервал показа");
        System.out.println("  - ad-test: Показать тестовую рекламу");
        System.out.println("  - ad-clear: Очистить все рекламы");
        System.out.println("  - ad-random: Переключить случайный порядок");
        System.out.println("═══════════════════════════════════════");
        
        // Добавляем стандартные рекламные сообщения
        loadDefaultAds();
    }
    
    private void loadDefaultAds() {
        advertisements.add("Присоединяйтесь к нашему Discord серверу!");
        advertisements.add("Поддержите сервер донатом - /donate");
        advertisements.add("Читайте правила сервера - /rules");
        advertisements.add("Нашли баг? Сообщите администрации!");
        advertisements.add("Играйте честно и получайте удовольствие!");
        System.out.println("[AdSystem] Загружено " + advertisements.size() + " стандартных рекламных сообщений");
    }
    
    @Override
    public void registerServerCommands(CommandHandler handler) {
        
        // Добавить рекламное сообщение
        handler.register("ad-add", "<текст...>", "Добавить новое рекламное сообщение", args -> {
            if (args.length == 0) {
                System.out.println("[AdSystem] Укажите текст рекламы!");
                return;
            }
            
            String adText = String.join(" ", args);
            advertisements.add(adText);
            System.out.println("[AdSystem] Реклама добавлена: " + adText);
            System.out.println("[AdSystem] Всего реклам: " + advertisements.size());
        });
        
        // Удалить рекламное сообщение
        handler.register("ad-remove", "<индекс>", "Удалить рекламу по индексу", args -> {
            try {
                int index = Integer.parseInt(args[0]) - 1; // -1 для удобства пользователя
                if (index < 0 || index >= advertisements.size()) {
                    System.out.println("[AdSystem] Неверный индекс! Доступные: 1-" + advertisements.size());
                    return;
                }
                
                String removed = advertisements.remove(index);
                System.out.println("[AdSystem] Реклама удалена: " + removed);
                System.out.println("[AdSystem] Осталось реклам: " + advertisements.size());
                
                // Сбрасываем индекс если он стал недействительным
                if (currentAdIndex >= advertisements.size()) {
                    currentAdIndex = 0;
                }
            } catch (NumberFormatException e) {
                System.out.println("[AdSystem] Неверный формат номера!");
            }
        });
        
        // Показать список всех реклам
        handler.register("ad-list", "Показать список всех рекламных сообщений", args -> {
            if (advertisements.isEmpty()) {
                System.out.println("[AdSystem] Список реклам пуст!");
                return;
            }
            
            System.out.println("═══════ СПИСОК РЕКЛАМ ═══════");
            for (int i = 0; i < advertisements.size(); i++) {
                System.out.println((i + 1) + ". " + advertisements.get(i));
            }
            System.out.println("═══════════════════════════════");
            System.out.println("[AdSystem] Всего: " + advertisements.size() + " реклам");
            System.out.println("[AdSystem] Статус: " + (adsEnabled ? "ВКЛЮЧЕНА" : "ВЫКЛЮЧЕНА"));
            System.out.println("[AdSystem] Интервал: " + (adInterval / 60f) + " мин");
            System.out.println("[AdSystem] Порядок: " + (randomOrder ? "СЛУЧАЙНЫЙ" : "ПОСЛЕДОВАТЕЛЬНЫЙ"));
        });
        
        // Запустить показ рекламы
        handler.register("ad-start", "Запустить автоматический показ рекламы", args -> {
            if (advertisements.isEmpty()) {
                System.out.println("[AdSystem] Нет реклам для показа! Добавьте хотя бы одну.");
                return;
            }
            
            if (adsEnabled) {
                System.out.println("[AdSystem] Реклама уже запущена!");
                return;
            }
            
            adsEnabled = true;
            startAdvertisementTimer();
            System.out.println("[AdSystem] ✅ Реклама ЗАПУЩЕНА!");
            System.out.println("[AdSystem] Интервал: " + (adInterval / 60f) + " мин");
            System.out.println("[AdSystem] Количество реклам: " + advertisements.size());
        });
        
        // Остановить показ рекламы
        handler.register("ad-stop", "Остановить автоматический показ рекламы", args -> {
            if (!adsEnabled) {
                System.out.println("[AdSystem] Реклама уже остановлена!");
                return;
            }
            
            adsEnabled = false;
            if (adTask != null) {
                adTask.cancel();
            }
            System.out.println("[AdSystem] ❌ Реклама ОСТАНОВЛЕНА!");
        });
        
        // Установить интервал показа
        handler.register("ad-interval", "<секунды>", "Установить интервал между рекламами в секундах", args -> {
            try {
                float seconds = Float.parseFloat(args[0]);
                if (seconds < 10) {
                    System.out.println("[AdSystem] Минимальный интервал: 10 секунд");
                    return;
                }
                
                adInterval = seconds;
                System.out.println("[AdSystem] Интервал установлен: " + (adInterval / 60f) + " мин (" + adInterval + " сек)");
                
                // Перезапускаем таймер если реклама активна
                if (adsEnabled) {
                    if (adTask != null) adTask.cancel();
                    startAdvertisementTimer();
                    System.out.println("[AdSystem] Таймер перезапущен с новым интервалом!");
                }
            } catch (NumberFormatException e) {
                System.out.println("[AdSystem] Неверный формат числа!");
            }
        });
        
        // Показать тестовую рекламу
        handler.register("ad-test", "[индекс]", "Показать тестовую рекламу (по индексу или случайную)", args -> {
            if (advertisements.isEmpty()) {
                System.out.println("[AdSystem] Нет реклам для показа!");
                return;
            }
            
            String adText;
            if (args.length > 0) {
                try {
                    int index = Integer.parseInt(args[0]) - 1;
                    if (index < 0 || index >= advertisements.size()) {
                        System.out.println("[AdSystem] Неверный индекс! Доступные: 1-" + advertisements.size());
                        return;
                    }
                    adText = advertisements.get(index);
                    System.out.println("[AdSystem] Показываем рекламу #" + (index + 1));
                } catch (NumberFormatException e) {
                    System.out.println("[AdSystem] Неверный формат номера!");
                    return;
                }
            } else {
                adText = getNextAdvertisement();
                System.out.println("[AdSystem] Показываем случайную рекламу");
            }
            
            showAdvertisement(adText);
        });
        
        // Очистить все рекламы
        handler.register("ad-clear", "Удалить все рекламные сообщения", args -> {
            int count = advertisements.size();
            advertisements.clear();
            currentAdIndex = 0;
            System.out.println("[AdSystem] Все рекламы удалены! Было удалено: " + count);
            
            if (adsEnabled) {
                adsEnabled = false;
                if (adTask != null) adTask.cancel();
                System.out.println("[AdSystem] Автопоказ остановлен (нет реклам)");
            }
        });
        
        // Переключить случайный порядок
        handler.register("ad-random", "Переключить между случайным и последовательным порядком", args -> {
            randomOrder = !randomOrder;
            System.out.println("[AdSystem] Порядок показа: " + (randomOrder ? "СЛУЧАЙНЫЙ" : "ПОСЛЕДОВАТЕЛЬНЫЙ"));
            currentAdIndex = 0; // Сбрасываем индекс
        });
        
        // Изменить префикс рекламы
        handler.register("ad-prefix", "<префикс...>", "Установить префикс для рекламных сообщений", args -> {
            if (args.length == 0) {
                System.out.println("[AdSystem] Текущий префикс: " + adPrefix);
                System.out.println("[AdSystem] Используйте команду с аргументами для изменения");
                return;
            }
            
            adPrefix = String.join(" ", args) + " ";
            System.out.println("[AdSystem] Новый префикс установлен: " + adPrefix);
        });
        
        // Статистика и статус
        handler.register("ad-status", "Показать подробную информацию о рекламной системе", args -> {
            System.out.println("════════ СТАТУС РЕКЛАМНОЙ СИСТЕМЫ ════════");
            System.out.println("Статус: " + (adsEnabled ? "✅ ВКЛЮЧЕНА" : "❌ ВЫКЛЮЧЕНА"));
            System.out.println("Количество реклам: " + advertisements.size());
            System.out.println("Интервал показа: " + (adInterval / 60f) + " мин (" + adInterval + " сек)");
            System.out.println("Порядок показа: " + (randomOrder ? "Случайный" : "Последовательный"));
            System.out.println("Текущий индекс: " + (currentAdIndex + 1));
            System.out.println("Игроков онлайн: " + Groups.player.size());
            System.out.println("Префикс: " + adPrefix);
            System.out.println("═════════════════════════════════════════");
        });
    }
    
    private void startAdvertisementTimer() {
        adTask = Timer.schedule(() -> {
            if (adsEnabled && !advertisements.isEmpty() && Groups.player.size() > 0) {
                String adText = getNextAdvertisement();
                showAdvertisement(adText);
            }
        }, adInterval, adInterval);
    }
    
    private String getNextAdvertisement() {
        if (advertisements.isEmpty()) return "";
        
        if (randomOrder) {
            return advertisements.get(random.nextInt(advertisements.size()));
        } else {
            String ad = advertisements.get(currentAdIndex);
            currentAdIndex = (currentAdIndex + 1) % advertisements.size();
            return ad;
        }
    }
    
    private void showAdvertisement(String adText) {
        if (Groups.player.size() == 0) return;
        
        String fullMessage = adPrefix + adText;
        Call.sendMessage(fullMessage);
        System.out.println("[AdSystem] Показана реклама: " + adText);
    }
    
    @Override
    public void registerClientCommands(CommandHandler handler) {
        // Команда для игроков - информация о сервере
        handler.register("serverinfo", "Информация о сервере", args -> {
            Call.sendMessage("[sky]═══════ ИНФОРМАЦИЯ О СЕРВЕРЕ ═══════");
            Call.sendMessage("[yellow]Сервер: [white]Mindustry Server");
            Call.sendMessage("[yellow]Игроков онлайн: [white]" + Groups.player.size());
            Call.sendMessage("[yellow]Текущая карта: [white]" + state.map.name());
            if (state.rules.waves) {
                Call.sendMessage("[yellow]Текущая волна: [white]" + state.wave);
            }
            Call.sendMessage("[sky]══════════════════════════════════════");
        });
    }
}