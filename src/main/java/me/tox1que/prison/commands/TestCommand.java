package me.tox1que.prison.commands;

import me.tox1que.prison.utils.Utils;
import me.tox1que.prison.utils.abstracts.BaseCommand;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestCommand extends BaseCommand{

    public TestCommand(){
        super("test");
    }

    @Override
    protected boolean onPlayerCommand(@NotNull Player player, @NotNull Command command, @NotNull String label, String[] args){
        Location a = new Location(player.getWorld(), Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[3]));
        Location b = new Location(player.getWorld(), Double.parseDouble(args[3]), Double.parseDouble(args[4]), Double.parseDouble(args[5]));
        Location[] r = Utils.getLocationsBetween(a, b);
        for(Location location:r){
            System.out.println(location);
        }
        return false;
    }

    @Override
    protected boolean onConsoleCommand(@NotNull ConsoleCommandSender sender, @NotNull Command command, @NotNull String label, String[] args){
        return false;
    }
}
