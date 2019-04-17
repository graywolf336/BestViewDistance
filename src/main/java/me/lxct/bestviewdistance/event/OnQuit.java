package me.lxct.bestviewdistance.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static me.lxct.bestviewdistance.functions.data.Variable.onlinePlayers;


public class OnQuit implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent e) {
        onlinePlayers.remove(e.getPlayer());
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerKicked(PlayerKickEvent e) {
        onlinePlayers.remove(e.getPlayer());
    }
}