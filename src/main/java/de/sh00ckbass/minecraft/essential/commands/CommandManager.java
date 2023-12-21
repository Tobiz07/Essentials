package de.sh00ckbass.minecraft.essential.commands;

import co.aikar.commands.PaperCommandManager;
import de.sh00ckbass.minecraft.essential.Essential;
import org.bukkit.plugin.PluginManager;

public class CommandManager {

    private final PaperCommandManager commandManager;

    private final Essential essential;

    public CommandManager(Essential essential) {
        this.essential = essential;

        this.commandManager = new PaperCommandManager(essential);
    }

    public void setupCommands() {
        this.registerCommands();
    }

    public void setupListener() {
        PluginManager pluginManager = this.essential.getServer().getPluginManager();
    }

    private void registerCommands() {

    }

}
