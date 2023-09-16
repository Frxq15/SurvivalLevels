package net.guildcraft.survivallevels;

import net.guildcraft.survivallevels.command.levelCommand;
import net.guildcraft.survivallevels.data.GPlayer;
import net.guildcraft.survivallevels.data.SQLListeners;
import net.guildcraft.survivallevels.data.SQLManager;
import net.guildcraft.survivallevels.data.SQLSetterGetter;
import net.guildcraft.survivallevels.gui.GUIListeners;
import net.guildcraft.survivallevels.level.LevelUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.NumberFormat;
import java.util.Locale;

public final class SurvivalLevels extends JavaPlugin {
    private static SurvivalLevels instance;
    private final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance(Locale.ENGLISH);
    private SQLManager sqlManager;
    private SQLSetterGetter sqlSetterGetter;
    private LevelUtils levelUtils;
    private int savingTask;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        log("Plugin enabling...");
        initialize();
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTask(savingTask);
        savingTask = 0;
        GPlayer.getAllPlayerData().forEach((uuid, playerData) -> playerData.uploadPlayerData(this));
        log("Plugin disabled.");
        // Plugin shutdown logic
    }
    public void initialize() {
        sqlManager = new SQLManager(this);
        if (!sqlManager.connect()) {
            log("Failed to connect to MySQL, disabling plugin.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        savingTask = startSavingTask();
        levelUtils = new LevelUtils(this);
        sqlSetterGetter = new SQLSetterGetter();
        sqlSetterGetter.createTable("survivallevels_users");
        Bukkit.getPluginManager().registerEvents(new SQLListeners(), this);
        Bukkit.getPluginManager().registerEvents(new GUIListeners(), this);
        getCommand("level").setExecutor(new levelCommand());
    }
    public static SurvivalLevels getInstance() { return instance; }
    public String format(int number) {
        return NUMBER_FORMAT.format(number);
    }
    public void log(String msg) { Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA+"[SurvivalLevels] "+msg); }
    public String colourize(String msg) { return ChatColor.translateAlternateColorCodes('&', msg); }
    public String formatMsg(String msg) { return ChatColor.translateAlternateColorCodes('&', getInstance().getConfig().getString("MESSAGES."+msg)); }

    public SQLManager getSQLManager() { return sqlManager;}

    public SQLSetterGetter getSQLSetterGetter() {
        return sqlSetterGetter;
    }
    public LevelUtils getLevelUtils() { return levelUtils;}

    private int startSavingTask() {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            GPlayer.getAllPlayerData().forEach((uuid, player) -> {
                player.uploadPlayerData(this);
            });
        }, 20L * 60L * 5, 20L * 60L * 10).getTaskId();
    }
}
