package me.lxct.bestviewdistance.functions.hooks;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;

import me.lxct.bestviewdistance.functions.BVDPlayer;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class WorldGuardHook {
    public static boolean isEnabled = false;

    public static List<String> getPlayerRegions(BVDPlayer p) {
        if (!isEnabled) {
            return Collections.emptyList();
        }

        final World w = BukkitAdapter.adapt(p.getLocation().getWorld());
        final RegionManager rgm = WorldGuard.getInstance().getPlatform().getRegionContainer().get(w);

        if (rgm == null) {
            return Collections.emptyList();
        }
        
        final ApplicableRegionSet ars = rgm.getApplicableRegions(BukkitAdapter.asBlockVector(p.getLocation()));
        return ars.getRegions().stream().map(r -> r.getId()).collect(Collectors.toList());
    }
}
