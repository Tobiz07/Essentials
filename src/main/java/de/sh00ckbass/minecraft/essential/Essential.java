package de.sh00ckbass.minecraft.essential;

import de.sh00ckbass.minecraft.essential.commands.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Essential extends JavaPlugin {

    private final CommandManager commandManager;

    public Essential(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public void onEnable() {
        this.commandManager.setupCommands();
        this.commandManager.setupListener();
    }

    @Override
    public void onDisable() {
    }
}
