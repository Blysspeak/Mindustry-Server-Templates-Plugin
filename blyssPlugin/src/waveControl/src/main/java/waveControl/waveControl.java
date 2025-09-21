package waveControl;

import static mindustry.Vars.spawner;
import static mindustry.Vars.state;

import arc.util.CommandHandler;
import mindustry.core.GameState;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.mod.Plugin;

public class waveControl extends Plugin {

    @Override
    public void init() {
        // Красивое уведомление о загрузке плагина
        System.out.println("══════════");
        System.out.println("   [WaveControl] УПРАВЛЕНИЕ ВОЛНАМИ!");
        System.out.println("   Версия: 1.0");
        System.out.println("   Автор: blysspeak");
        System.out.println("   Тип: Серверный плагин");
        System.out.println("════════");
        System.out.println("[WaveControl] Доступные команды:");
        System.out.println("  - skipwave: Пропустить волну");
        System.out.println("  - setwave <n>: Установить волну");
        System.out.println("  - waveinfo: Информация о волне");
        System.out.println("  - speedwave [x]: Ускорить таймер");
        System.out.println("════════════");
    }

    @Override
    public void registerServerCommands(CommandHandler handler) {
        // Команда для пропуска волны (только для администраторов сервера)
        handler.register("skipwave", "Пропустить текущую волну", args -> {
            if (state.is(GameState.State.playing)) {
                if (state.rules.waves) {
                    // Принудительно завершаем текущую волну
                    spawner.spawnEnemies();
                    state.wave++;
                    
                    // Уведомляем всех игроков
                    Call.sendMessage("[orange]Администратор пропустил волну! Текущая волна: " + state.wave);
                    System.out.println("[WaveControl] Волна пропущена. Текущая волна: " + state.wave);
                } else {
                    System.out.println("[WaveControl] Волны отключены на этой карте.");
                }
            } else {
                System.out.println("[WaveControl] Игра не активна.");
            }
        });

        // Команда для установки конкретной волны
        handler.register("setwave", "<номер>", "Установить номер волны", args -> {
            try {
                int waveNumber = Integer.parseInt(args[0]);
                if (waveNumber < 1) {
                    System.out.println("[WaveControl] Номер волны должен быть больше 0.");
                    return;
                }
                
                if (state.is(GameState.State.playing) && state.rules.waves) {
                    state.wave = waveNumber;
                    Call.sendMessage("[orange]Администратор установил волну №" + waveNumber);
                    System.out.println("[WaveControl] Волна установлена на: " + waveNumber);
                } else {
                    System.out.println("[WaveControl] Игра не активна или волны отключены.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[WaveControl] Неверный формат числа: " + args[0]);
            }
        });

        // Команда для получения информации о текущей волне
        handler.register("waveinfo", "Показать информацию о текущей волне", args -> {
            if (state.is(GameState.State.playing)) {
                if (state.rules.waves) {
                    float timeLeft = state.wavetime / 60f;
                    int enemiesAlive = Groups.unit.size();
                    
                    System.out.println("[WaveControl] Текущая волна: " + state.wave);
                    System.out.println("[WaveControl] Время до следующей волны: " + String.format("%.1f", timeLeft) + " сек");
                    System.out.println("[WaveControl] Живых врагов: " + enemiesAlive);
                } else {
                    System.out.println("[WaveControl] Волны отключены на этой карте.");
                }
            } else {
                System.out.println("[WaveControl] Игра не активна.");
            }
        });

        // Команда для ускорения времени волны
        handler.register("speedwave", "[множитель]", "Ускорить таймер волны (по умолчанию x5)", args -> {
            if (state.is(GameState.State.playing) && state.rules.waves) {
                float multiplier = 5f;
                if (args.length > 0) {
                    try {
                        multiplier = Float.parseFloat(args[0]);
                        if (multiplier <= 0) {
                            System.out.println("[WaveControl] Множитель должен быть больше 0.");
                            return;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("[WaveControl] Неверный формат числа: " + args[0]);
                        return;
                    }
                }
                
                state.wavetime = Math.max(0, state.wavetime - (60f * 60f * multiplier));
                Call.sendMessage("[orange]Администратор ускорил таймер волны (x" + multiplier + ")");
                System.out.println("[WaveControl] Таймер волны ускорен с множителем: " + multiplier);
            } else {
                System.out.println("[WaveControl] Игра не активна или волны отключены.");
            }
        });
    }

    @Override
    public void registerClientCommands(CommandHandler handler) {
        // Команда для игроков (только информация)
        handler.register("wave", "Показать информацию о текущей волне", args -> {
            if (state.is(GameState.State.playing) && state.rules.waves) {
                float timeLeft = state.wavetime / 60f;
                int enemiesAlive = Groups.unit.size();
                
                Call.sendMessage("[sky]Текущая волна: [orange]" + state.wave);
                Call.sendMessage("[sky]Время до следующей волны: [orange]" + String.format("%.1f", timeLeft) + " сек");
                Call.sendMessage("[sky]Живых врагов: [orange]" + enemiesAlive);
            }
        });
    }
}