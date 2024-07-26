package de.sh00ckbass.minecraft.essential;

import de.sh00ckbass.minecraft.essential.commands.CommandManager;
import de.sh00ckbass.minecraft.essential.data.PlayerProfileManager;
import de.sh00ckbass.minecraft.essential.data.PluginConfigManager;
import de.sh00ckbass.minecraft.essential.util.HeadApi;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class Essential extends JavaPlugin {

    private final CommandManager commandManager;

    @Getter
    private final PlayerProfileManager playerProfileManager;

    @Getter
    private final PluginConfigManager pluginConfigManager;

    @Getter
    private final HeadApi headApi;

    public Essential() {
        this.commandManager = new CommandManager(this);
        this.playerProfileManager = new PlayerProfileManager(this);
        this.pluginConfigManager = new PluginConfigManager(this);
        this.headApi = new HeadApi();
    }

    @Override
    public void onEnable() {
        this.pluginConfigManager.loadConfig();
        this.commandManager.setupCommands();
        this.commandManager.setupListener();
    }

    @Override
    public void onDisable() {
        this.pluginConfigManager.saveConfig();
    }
}
