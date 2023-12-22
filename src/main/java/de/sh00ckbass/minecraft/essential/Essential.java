package de.sh00ckbass.minecraft.essential;

import de.sh00ckbass.minecraft.essential.commands.CommandManager;
import de.sh00ckbass.minecraft.essential.data.PlayerProfileManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class Essential extends JavaPlugin {

    private final CommandManager commandManager;

    @Getter
    private final PlayerProfileManager playerProfileManager;

    public Essential() {
        this.commandManager = new CommandManager(this);
        this.playerProfileManager = new PlayerProfileManager(this);
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
