package me.tox1que.prison.listeners;

import me.tox1que.prison.Prison;
import me.tox1que.prison.managers.UserManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener{

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Prison.getInstance().getUserManager().loadUser(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Prison.getInstance().getUserManager().unloadUser(e.getPlayer());
    }
}
