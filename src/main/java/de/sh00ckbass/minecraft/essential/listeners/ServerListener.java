package de.sh00ckbass.minecraft.essential.listeners;

import de.sh00ckbass.minecraft.essential.Essential;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;

public class ServerListener implements Listener {

    private final Essential essential;

    public ServerListener(Essential essential) {
        this.essential = essential;
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        if (!event.getPlugin().getName().equals("Essential")) {
            return;
        }
        essential.getPlayerProfileManager().saveAllPlayerProfiles();
    }

}
