package de.sh00ckbass.minecraft.essential.commands;

import co.aikar.commands.PaperCommandManager;
import de.sh00ckbass.minecraft.essential.Essential;
import org.bukkit.plugin.PluginManager;

public class CommandManager {

    private PaperCommandManager commandManager;

    private final Essential essential;

    public CommandManager(Essential essential) {
        this.essential = essential;
    }

    public void setupCommands() {
        this.commandManager = new PaperCommandManager(essential);

        this.registerCommands();
    }

    public void setupListener() {
        PluginManager pluginManager = this.essential.getServer().getPluginManager();
    }

    private void registerCommands() {

    }

}
