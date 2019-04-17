package me.lxct.bestviewdistance.event;

import me.lxct.bestviewdistance.functions.BVDPlayer;
import me.lxct.bestviewdistance.functions.data.Variable;
import me.lxct.bestviewdistance.functions.sync.IncrementallySetViewDistance;

import static me.lxct.bestviewdistance.functions.data.Variable.teleportUnsetDelay;
import static me.lxct.bestviewdistance.functions.util.Scheduler.scheduleSync;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(final PlayerJoinEvent e) {
        Player p = e.getPlayer();
        
        //Remove the old instance, if they had one
        Variable.onlinePlayers.remove(p);

        //Add the player back to being online
        BVDPlayer data = new BVDPlayer(p);
        Variable.onlinePlayers.put(p, data);

        scheduleSync(new IncrementallySetViewDistance(p, Variable.useLoginView ? Variable.onLoginView : Variable.min), teleportUnsetDelay * 20, data);
    }
}