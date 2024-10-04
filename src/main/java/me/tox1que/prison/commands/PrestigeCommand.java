package me.tox1que.prison.commands;

import me.tox1que.prison.utils.abstracts.BaseCommand;
import me.tox1que.prison.utils.users.User;
import me.tox1que.prison.managers.UserManager;
import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PrestigeCommand extends BaseCommand{

    public PrestigeCommand(){
        super("prestige");
    }

    @Override
    protected boolean onPlayerCommand(@NotNull Player player, @NotNull Command command, @NotNull String label, String[] args){
        User user = main.getUserManager().getUser(player);
        user.prestigeUp();
        return false;
    }

    @Override
    protected boolean onConsoleCommand(@NotNull ConsoleCommandSender sender, @NotNull Command command, @NotNull String label, String[] args){
        return false;
    }
}
