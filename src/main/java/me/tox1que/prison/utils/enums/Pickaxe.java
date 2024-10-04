package me.tox1que.prison.utils.enums;

import me.tox1que.prison.utils.ItemBuilder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public enum Pickaxe{

    STARTER(
            new ItemBuilder(Material.STONE_PICKAXE)
                    .setDisplayName("§aKrumpáč začátečníka")
                    .setLores("", "§8■ §f0 §7vykopaných blocků")
                    .build()
    )
    ;

    private final ItemStack item;

    Pickaxe(ItemStack item){
        this.item = item;
    }

    public ItemStack getItem(){
        return item;
    }

    public @Nullable String getName(){
        return item.getItemMeta() != null ? item.getItemMeta().getDisplayName() : null;
    }
}
