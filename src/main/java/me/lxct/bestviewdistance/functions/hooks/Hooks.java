package me.lxct.bestviewdistance.functions.hooks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import static me.lxct.bestviewdistance.functions.data.Variable.serverVersion;
import static me.lxct.bestviewdistance.functions.hooks.ProtocolLibHook.protocolLibHook;

public class Hooks {
    public static void checkHooks(Plugin plugin) {

        // PROTOCOLLIB
        if (Bukkit.getPluginManager().getPlugin("ProtocolLib") != null && !serverVersion.contains("1.13")) { // Add !=1.13 Support for Client View Distance
            try {
                protocolLibHook(plugin);
                Bukkit.getLogger().info("[BestViewDistance] Successfully hooked into ProtocolLib!");
            } catch (NoClassDefFoundError ignored) {
            }
        }

        // PLACEHOLDERAPI
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            try {
            	new PlaceholderAPIHook().register();
                Bukkit.getLogger().info("[BestViewDistance] Successfully hooked into PlaceholderAPI!");
            } catch (NoClassDefFoundError ignored) {
            }
        }

        // WORLDGUARD
        if (Bukkit.getPluginManager().getPlugin("WorldGuard") != null) {
            try {
                WorldGuardHook.isEnabled = true;
                Bukkit.getLogger().info("[BestViewDistance] Successfully hooked into WorldGuard!");
            } catch (NoClassDefFoundError ignored) {
            }
        }
    }
}
