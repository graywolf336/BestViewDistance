package me.lxct.bestviewdistance.functions;

import me.lxct.bestviewdistance.BestViewDistance;
import me.lxct.bestviewdistance.functions.hooks.WorldGuardHook;
import me.lxct.bestviewdistance.functions.sync.IncrementallySetViewDistance;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import static me.lxct.bestviewdistance.functions.data.Variable.*;
import static me.lxct.bestviewdistance.functions.util.Scheduler.scheduleSync;

public class BVDPlayer {

    private Location location; // Location list
    private int supportedViewDistance; // View Distance list
    private int scheduledViewDistance; // Live View Distance list
    private int settingsViewDistance; // 1.12 Settings View Distance list
    private int tpTaskId; // Waiting for teleport unset list with task ID
    private boolean isAfk; // AFK
    private boolean isWaitingForTpUnset;
    private boolean isFlying; // Flying
    private final Player p;

    public BVDPlayer(final Player p) {
        this.p = p;

        this.isAfk = false;
        this.isWaitingForTpUnset = false;
        this.isFlying = false;
        this.tpTaskId = -1;
        this.location = p.getLocation();

        if (useLoginView) {
            this.settingsViewDistance = onLoginView;
            this.supportedViewDistance = onLoginView;
            this.scheduledViewDistance = onLoginView;
        } else {
            this.settingsViewDistance = min;
            this.supportedViewDistance = min;
            this.scheduledViewDistance = min;
        }
    }

    public Player getPlayer() {
        return this.p;
    }

    public boolean isAfk() {
        return this.isAfk;
    }

    public boolean isFlying() {
        return this.isFlying;
    }

    public boolean isWaitingForTpUnset() {
        return this.isWaitingForTpUnset;
    }

    public void setTaskId(final int tpTaskId) {
        this.tpTaskId = tpTaskId;
    }

    public void setAfkLocation(final Location location) {
        this.location = location;
    }

    public void setAfk(final boolean afk) {
        this.isAfk = afk;
    }

    public void setFlying(final boolean flying) {
        this.isFlying = flying;
    }

    public int getTpTaskId() {
        return this.tpTaskId;
    }

    public String getName() {
        return this.p.getName();
    }

    public Location getAfkLocation() {
        return this.location;
    }

    public Location getLocation() {
        return this.p.getLocation();
    }

    public int getCurrentMaxLimit() {
        final FileConfiguration config = BestViewDistance.plugin.getConfig();
        
        if (WorldGuardHook.isEnabled) {
            int tmp = max;

            for (String name : WorldGuardHook.getPlayerRegions(this)) {
                if (config.isInt("Regions." + name + ".Max")) {
                    tmp = Math.max(tmp, config.getInt("Regions." + name + ".Max"));
                }
            }
        }

        final String worldName = this.p.getWorld().getName();
        if (config.isInt("Worlds." + worldName + ".Max")) {
            return config.getInt("Worlds." + worldName + ".Max");
        }

        return max;
    }

    private int getCurrentMinLimit() {
        final FileConfiguration config = BestViewDistance.plugin.getConfig();
        
        if (WorldGuardHook.isEnabled) {
            int tmp = min;

            for (String name : WorldGuardHook.getPlayerRegions(this)) {
                if (config.isInt("Regions." + name + ".Min")) {
                    tmp = Math.min(tmp, config.getInt("Regions." + name + ".Min"));
                }
            }

            return tmp;
        }

        final String worldName = this.p.getWorld().getName();
        if (config.isInt("Worlds." + worldName + ".Min")) {
            return config.getInt("Worlds." + worldName + ".Min");
        }
        return min;
    }

    public int getSupportedViewDistance() {
        return this.supportedViewDistance;
    }

    public void setSupportedViewDistance(int supportedViewDistance) {
        this.supportedViewDistance = Math.max(min, Math.min(supportedViewDistance, max));
    }

    public int getScheduledViewDistance() {
        return this.scheduledViewDistance;
    }

    public void setScheduledViewDistance(int scheduledViewDistance) {
        this.scheduledViewDistance = Math.min(this.getCurrentMaxLimit(), Math.max(scheduledViewDistance, this.getCurrentMinLimit()));
        this.scheduledViewDistance = Math.min(this.scheduledViewDistance, this.getSettingsViewDistance());
        this.scheduledViewDistance = Math.min(this.scheduledViewDistance, this.getSupportedViewDistance());
    }

    public int getCurrentViewDistance() {
        if (!serverVersion.contains("1.8")) {
            return this.p.getViewDistance();
        } else {
            return this.p.getServer().getViewDistance();
        }
    }

    public int getPing() {
        return this.p.spigot().getPing();
    }

    public int getSettingsViewDistance() { // Get View Distance in settings
        if (!serverVersion.contains("1.13")) {
            return this.settingsViewDistance;
        } else {
            return this.p.getClientViewDistance();
        }
    }

    // CHECK AND USE PERMISSIONS
    public int getViewBypassAmount(final int viewDistance) {
        if (usePermissions) {
            for (int i = 32; i >= 3; i--) { // Start at 32, to 3
                if (this.p.hasPermission("view.set." + i)) { // view.set.i is set
                    return i; // If he has permission, then return the number "after" the permission.
                }
            }
        }

        return viewDistance; // If he doesn't have permissions, then return viewDistance.
    }

    public void setWaitingForTpUnset(final boolean waitingForTpUnset) {
        this.isWaitingForTpUnset = waitingForTpUnset;
    }

    // CHECK AND USE PERMISSIONS
    public boolean hasViewBypassPermission() {
        if (usePermissions) {
            for (int i = 32; i >= 3; i--) { // Start at 32, to 3
                if (this.p.hasPermission("view.set." + i)) { // view.set.i is set
                    return true; // If he has permission, then return the number "after" the permission.
                }
            }
        }

        return false;
    }

    public void setViewDistance(final int viewDistance) {
        if (!serverVersion.contains("1.8")) {
            scheduleSync(new IncrementallySetViewDistance(this.p, viewDistance)); // Break Async chain
        }
    }

    public void saveSettingsViewDistance(final int viewDistance) {
        this.settingsViewDistance = viewDistance;
    }

}
