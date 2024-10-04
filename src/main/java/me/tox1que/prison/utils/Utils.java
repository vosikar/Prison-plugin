package me.tox1que.prison.utils;

import me.tox1que.prison.Prison;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Utils{

    private static final Prison main = Prison.getInstance();
    private static final String prefix = main.getPrefix();
    private static final ChatColor primaryColor = main.getPrimaryColor();
    private static final ChatColor secondaryColor = main.getSecondaryColor();

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd. MM. yyyy HH:mm:ss");
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public static void actionBarMessage(Player player, String message){
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    public static String color(@NotNull String text){
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', text);
    }

    public static Location centerLocation(Location location){
        location = location.clone();
        if(Math.abs(location.getZ() / 0.5) % 2 == 0){
            location.add(0, 0, 0.5);
        }
        if(Math.abs(location.getX() / 0.5) % 2 == 0){
            location.add(0.5, 0, 0);
        }
        return location;
    }

    public static ItemStack createEnchantedItem(Material material, String name, List<String> lore){
        ItemStack item = createItem(material, name, lore);
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.SOUL_SPEED, 1, true);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createItem(Material material, String name, List<String> lore, ItemFlag... itemFlags){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        if(itemFlags != null)
            meta.addItemFlags(itemFlags);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack enchantItem(ItemStack item, Enchantment ench, int level){
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(ench, level, true);
        item.setItemMeta(meta);
        return item;
    }

    public static String formatNumber(double number){
        DecimalFormat formatter;
        if(number % 1 == 0){
            formatter = new DecimalFormat("#,##0");
        }else{
            formatter = new DecimalFormat("#,##0.00");
        }
        return formatter.format(number);
    }

    public static Location[] getLocationsBetween(Location loc1, Location loc2){
        double deltaX = loc2.getX() - loc1.getX();
        double deltaY = loc2.getY() - loc1.getY();
        double deltaZ = loc2.getZ() - loc1.getZ();

        int steps = (int) Math.max(Math.abs(deltaX), Math.max(Math.abs(deltaY), Math.abs(deltaZ)));

        double stepX = deltaX / steps;
        double stepY = deltaY / steps;
        double stepZ = deltaZ / steps;

        Location[] locations = new Location[steps + 1];

        for(int i = 0; i <= steps; i++){
            double x = loc1.getX() + stepX * i;
            double y = loc1.getY() + stepY * i;
            double z = loc1.getZ() + stepZ * i;
            locations[i] = new Location(loc1.getWorld(), x, y, z);
        }

        return locations;
    }

    public static String scoreboardTextCenter(String string){
        int maxLength = 24;
        int spacesToAdd = (maxLength - ChatColor.stripColor(string).length())/2;
        String spaces = String.join("", Collections.nCopies(spacesToAdd, " "));
        return spaces+string+spaces;
    }

    //CHAT GPT OP
    public static String getColoredArrow(int current, int total){
        // Check for invalid input
        if (current < 0 || total <= 0 || current > total) {
            return "&cInvalid input";
        }

        // Calculate progress percentage
        double progress = (double) current / total;

        // Calculate number of green arrows based on progress
        int greenArrows = (int) (progress * 10);

        // Construct the arrow with appropriate coloring
        StringBuilder arrow = new StringBuilder();
        for(int i = 0; i < 9; i++){
            if(i < greenArrows){
                arrow.append("§a▮"); // Green arrow
            }else{
                arrow.append("§c▮"); // Red arrow
            }
        }

        // Append the triangle at the end with appropriate coloring
        if(progress >= 1.0){
            arrow.append("§a▶"); // Green triangle
        }else{
            arrow.append("§c▶"); // Red triangle
        }

        return arrow.toString();
    }

    public static String getFormattedTime(int time){
        int minutes = (int) Math.floor(time / 60.0);
        int seconds = time % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    public static String getFormattedTime(Date date){
        return dateFormat.format(date);
    }

    public static String getPluginMessage(String msg){
        return getPluginColoredMessage(ChatColor.translateAlternateColorCodes('&',
                prefix + " &8» [pc]" + msg));
    }

    public static String getPluginColoredMessage(String msg){
        return msg
                .replace("[pc]", primaryColor + "")
                .replace("[sc]", secondaryColor + "");
    }

    public static String getPercentage(double a, double b){
        return Math.max(Math.min(a*100/b, 100), 0)+" %";
    }

    public static String getWordVariant(int amount, String singular, String plural, String secondPlural){
        return amount == 1 ? singular : amount <= 4 && amount >= 2 ? plural : secondPlural;
    }

    public static Location parseLocation(String string){
        String[] split = string.split(";");

        World w = Bukkit.getServer().getWorld(split[0]);
        float x = Float.parseFloat(split[1]);
        float y = Float.parseFloat(split[2]);
        float z = Float.parseFloat(split[3]);

        if(split.length > 4){
            float yaw = Float.parseFloat(split[4]);
            float pitch = Float.parseFloat(split[5]);
            return new Location(w, x, y, z, yaw, pitch);
        }
        return new Location(w, x, y, z);
    }

    public static Location parseLocation(String string, World w){
        String[] split = string.split(";");

        float x = Float.parseFloat(split[0]);
        float y = Float.parseFloat(split[1]);
        float z = Float.parseFloat(split[2]);

        if(split.length > 3){
            float yaw = Float.parseFloat(split[3]);
            float pitch = Float.parseFloat(split[4]);
            return new Location(w, x, y, z, yaw, pitch);
        }
        return new Location(w, x, y, z);
    }

    public static String parseLocation(Location location){
        return String.join(";", location.getWorld().getName(), String.format("%.5f", location.getX()),
                String.format("%.5f", location.getY()), String.format("%.5f", location.getZ()));
    }

    public static void performConsoleCommand(String command){
        Bukkit.dispatchCommand(Prison.getInstance().getServer().getConsoleSender(), command);
    }
}
