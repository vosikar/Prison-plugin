package me.tox1que.prison.utils.users;

import org.bukkit.ChatColor;

public class Rank{

    private final int id;
    private final String name;
    private final int price;

    public Rank(int id, String name, int price){
        this.id = id;
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        this.price = price;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public int getPrice(){
        return price;
    }
}
