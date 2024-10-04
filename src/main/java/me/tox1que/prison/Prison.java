package me.tox1que.prison;

import me.saves.JARVIS.Jarvis;
import me.tox1que.prison.commands.LevelUpCommand;
import me.tox1que.prison.commands.PrestigeCommand;
import me.tox1que.prison.commands.TestCommand;
import me.tox1que.prison.listeners.PlayerListener;
import me.tox1que.prison.listeners.SecurityListener;
import me.tox1que.prison.managers.*;
import me.tox1que.prison.utils.Logger;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class Prison extends JavaPlugin{

    /**
     * Main variables
     */

    private static Prison main;
    private Economy economy;

    private final ChatColor primaryColor = ChatColor.AQUA;
    private final ChatColor secondaryColor = ChatColor.DARK_AQUA;
    private final String prefix = secondaryColor+"Prison";

    /**
     * Managers
     */

    private DatabaseManager databaseManager = null;
    private FileManager fileManager = null;
    private MinesManager minesManager = null;
    private RankManager rankManager = null;
    private ScoreboardManager scoreboardManager = null;
    private UserManager userManager = null;


    @Override
    public void onEnable(){
        if(!setupEconomy()){
            getLogger().severe("Disabled due to no Vault dependency found!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        main = this;
        Jarvis.getPlugin().getServerManager().disableProxyJoin();

        getCommand("levelup").setExecutor(new LevelUpCommand());
        getCommand("prestige").setExecutor(new PrestigeCommand());
        getCommand("test").setExecutor(new TestCommand());

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new SecurityListener(), this);
    }

    @Override
    public void onDisable(){
    }

    private boolean setupEconomy(){
        if(Bukkit.getPluginManager().getPlugin("Vault") == null){
            Logger.Console.SEVERE("No VAULT dependency found.");
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if(rsp == null){
            Logger.Console.SEVERE("Null registered service provider.");
            return false;
        }
        economy = rsp.getProvider();
        return true;
    }

    /**
     * Managers getters
     */

    public @NotNull DatabaseManager getDatabaseManager(){
        if(databaseManager == null)
            databaseManager = new DatabaseManager();
        return databaseManager;
    }

    public @NotNull FileManager getFileManager(){
        if(fileManager == null)
            fileManager = new FileManager();
        return fileManager;
    }

    public @NotNull MinesManager getMinesManager(){
        if(minesManager == null)
            minesManager = new MinesManager();
        return minesManager;
    }

    public @NotNull RankManager getRankManager(){
        if(rankManager == null)
            rankManager = new RankManager();
        return rankManager;
    }

    public @NotNull ScoreboardManager getScoreboardManager(){
        if(scoreboardManager == null)
            scoreboardManager = new ScoreboardManager();
        return scoreboardManager;
    }

    public @NotNull UserManager getUserManager(){
        if(userManager == null)
            userManager = new UserManager();
        return userManager;
    }

    /**
     * Other getters
     */

    public static Prison getInstance(){
        return main;
    }

    public Economy getEconomy(){
        return economy;
    }

    public ChatColor getPrimaryColor(){
        return primaryColor;
    }

    public ChatColor getSecondaryColor(){
        return secondaryColor;
    }

    public String getPrefix(){
        return prefix;
    }
}
