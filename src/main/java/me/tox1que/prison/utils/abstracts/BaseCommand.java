package me.tox1que.prison.utils.abstracts;

import me.tox1que.prison.Prison;
import me.tox1que.prison.utils.Logger;
import me.tox1que.prison.utils.Utils;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class BaseCommand implements CommandExecutor, TabCompleter{

    protected final Prison main;

    protected final String name;
    protected final String[] usages;

    public BaseCommand(String name){
        this(name, "/" + name);
    }

    public BaseCommand(String name, String... usages){
        this.name = name;
        this.usages = usages;
        main = Prison.getInstance();

        PluginCommand command = main.getCommand(name);
        if(command != null){
            command.setExecutor(this);
            command.setTabCompleter(this);
        }else{
            Logger.Console.SEVERE("UNABLE TO REGISTER COMMAND " + name);
            Logger.Console.SEVERE("UNABLE TO REGISTER COMMAND " + name);
            Logger.Console.SEVERE("UNABLE TO REGISTER COMMAND " + name);
        }
    }

    protected void sendUsage(CommandSender sender){
        sender.sendMessage(Utils.getPluginMessage("Špatné použití příkazu, příklady použití:"));
        for(String s : usages){
            sender.sendMessage(Utils.getPluginMessage("/" + this.name + " " + s));
        }
    }

    protected abstract boolean onPlayerCommand(@NotNull Player player, @NotNull Command command, @NotNull String label, String[] args);
    protected abstract boolean onConsoleCommand(@NotNull ConsoleCommandSender sender, @NotNull Command command, @NotNull String label, String[] args);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args){
        if(sender instanceof Player)
            return onPlayerCommand((Player) sender, command, label, args);
        if(sender instanceof ConsoleCommandSender)
            return onConsoleCommand((ConsoleCommandSender) sender, command, label, args);
        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args){
        return null;
    }

}
