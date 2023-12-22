package de.sh00ckbass.minecraft.essential.commands;

import co.aikar.commands.PaperCommandManager;
import de.sh00ckbass.minecraft.essential.Essential;
import de.sh00ckbass.minecraft.essential.commands.util.NickCommand;
import de.sh00ckbass.minecraft.essential.listeners.PlayerListener;
import org.bukkit.plugin.PluginManager;

public class CommandManager {

    private PaperCommandManager commandManager;

    private final Essential essential;

    public CommandManager(Essential essential) {
        this.essential = essential;
    }

    public void setupCommands() {
        this.commandManager = new PaperCommandManager(this.essential);

        this.registerCommands();
    }

    public void setupListener() {
        PluginManager pluginManager = this.essential.getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerListener(this.essential), this.essential);
    }

    private void registerCommands() {
        this.commandManager.registerCommand(new NickCommand(this.essential));
        this.commandManager.registerCommand(new HomeCommand(this.essential));
    }

}
