package me.lxct.bestviewdistance.functions.sync;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.lxct.bestviewdistance.BestViewDistance;
import me.lxct.bestviewdistance.functions.BVDPlayer;
import me.lxct.bestviewdistance.functions.async.UnsetTeleport;
import me.lxct.bestviewdistance.functions.data.Variable;
import me.lxct.bestviewdistance.functions.util.Scheduler;

public class IncrementallySetViewDistance implements Runnable {
    private int nextDelay;
    private int finalViewDistance;
    private Player p;

    public IncrementallySetViewDistance(Player player, int finalView) {
        this.p = player;
        this.finalViewDistance = finalView;
        this.nextDelay = Variable.incrementalViewIncreaseDelay * 20;
    }

    public void run() {
        if (this.p.getViewDistance() >= this.finalViewDistance) {
            this.p.setViewDistance(this.finalViewDistance);
            this.done();
            return;
        }

        int nextView = this.p.getViewDistance() + 1;
        this.p.setViewDistance(nextView);

        BVDPlayer data = Variable.onlinePlayers.get(p);
        if (data == null) {
            return;
        }

        if (nextView >= this.finalViewDistance) {
            this.done();
            return;
        }

        Scheduler.scheduleSync(this, this.nextDelay, data);
    }

    private void done() {
        Bukkit.getScheduler().runTaskLaterAsynchronously(BestViewDistance.plugin, new UnsetTeleport(this.p), this.nextDelay);
    }
}
