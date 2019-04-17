package me.lxct.bestviewdistance.event;

import me.lxct.bestviewdistance.BestViewDistance;
import me.lxct.bestviewdistance.functions.BVDPlayer;
import me.lxct.bestviewdistance.functions.async.TeleportData;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import static me.lxct.bestviewdistance.functions.data.Variable.*;

public class OnTeleport implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        if (p.hasMetadata("NPC")) {
            return;
        }

        TeleportCause c = e.getCause();
        if (c == TeleportCause.CHORUS_FRUIT || c == TeleportCause.UNKNOWN || c == TeleportCause.ENDER_PEARL) {
            return;
        }
        
        BVDPlayer player = onlinePlayers.get(p);
        if (!player.isWaitingForTpUnset()) { // If he's not waiting for tp unset
            p.setViewDistance(onTeleportView); // Set on teleport view
        }

        Bukkit.getScheduler().runTaskAsynchronously(BestViewDistance.plugin, new TeleportData(p)); // Process teleport data with async method
    }
}