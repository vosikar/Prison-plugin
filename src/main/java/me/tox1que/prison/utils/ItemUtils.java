package me.tox1que.prison.utils;

import me.tox1que.prison.utils.enums.Pickaxe;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class ItemUtils{

    private static final List<String> gameItemsNames = Arrays.stream(Pickaxe.values())
            .map(Pickaxe::getName)
            .toList();

    public static boolean isGameItem(ItemStack itemStack){
        if(itemStack.getItemMeta() == null)
            return false;
        String name = itemStack.getItemMeta().getDisplayName();
        return gameItemsNames.contains(name);
    }
}
