package me.tox1que.prison.utils;

import org.bukkit.entity.Player;

public class PlayerUtils{

    public static void sendMessage(Player player, String message, Object... args){
        player.sendMessage(Utils.getPluginMessage(String.format(message, args)));
    }

    public static void sendImportantMessage(Player player, String message, Object... args){
        player.sendMessage("§c§l[!] "+Utils.getPluginMessage(String.format(message, args)));
    }
}
