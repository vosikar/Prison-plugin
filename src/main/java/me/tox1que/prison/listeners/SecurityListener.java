package me.tox1que.prison.listeners;

import me.tox1que.prison.utils.ItemUtils;
import me.tox1que.prison.utils.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class SecurityListener implements Listener{

    private final Map<String, ItemStack> dropConfirmations = new HashMap<>();

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        Player player = e.getPlayer();
        ItemStack itemStack = e.getItemDrop().getItemStack();
        if(!ItemUtils.isGameItem(itemStack) || dropConfirmations.containsKey(player.getName()) && dropConfirmations.get(player.getName()).equals(itemStack)){
            dropConfirmations.remove(player.getName());
            return;
        }
        PlayerUtils.sendImportantMessage(player, "Chystáš se vyhodit vzácný item. Pro potvrzení a vyhození ho vyhoď znovu.");
        e.setCancelled(true);
        dropConfirmations.put(player.getName(), itemStack);
    }
}
