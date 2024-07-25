package de.sh00ckbass.minecraft.essential.commands;

import co.aikar.commands.BukkitCommandCompletionContext;
import co.aikar.commands.CommandCompletions;
import co.aikar.commands.PaperCommandManager;
import de.sh00ckbass.minecraft.essential.Essential;
import de.sh00ckbass.minecraft.essential.commands.util.GiftCommand;
import de.sh00ckbass.minecraft.essential.commands.util.HeadCommand;
import de.sh00ckbass.minecraft.essential.commands.util.HomeCommand;
import de.sh00ckbass.minecraft.essential.commands.util.NickCommand;
import de.sh00ckbass.minecraft.essential.data.types.PlayerProfile;
import de.sh00ckbass.minecraft.essential.listeners.GiftListener;
import de.sh00ckbass.minecraft.essential.listeners.PlayerListener;
import de.sh00ckbass.minecraft.essential.listeners.ServerListener;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import java.util.UUID;

public class CommandManager {

    private PaperCommandManager commandManager;

    private final Essential essential;

    public CommandManager(Essential essential) {
        this.essential = essential;
    }

    public void setupCommands() {
        this.commandManager = new PaperCommandManager(this.essential);

        this.registerCommands();
        this.registerCommandCompletions();
    }

    public void setupListener() {
        PluginManager pluginManager = this.essential.getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerListener(this.essential), this.essential);
        pluginManager.registerEvents(new GiftListener(this.essential), this.essential);
        pluginManager.registerEvents(new ServerListener(this.essential), this.essential);
    }

    private void registerCommands() {
        this.commandManager.registerCommand(new NickCommand(this.essential));
        this.commandManager.registerCommand(new HomeCommand(this.essential));
        this.commandManager.registerCommand(new GiftCommand(this.essential));
        this.commandManager.registerCommand(new HeadCommand(this.essential));
    }

    private void registerCommandCompletions() {
        CommandCompletions<BukkitCommandCompletionContext> commandCompletions = this.commandManager.getCommandCompletions();

        commandCompletions.registerAsyncCompletion("homes", c -> {
            CommandSender sender = c.getSender();
            if (sender instanceof Player) {
                UUID uuid = ((Player) sender).getUniqueId();
                PlayerProfile profile = this.essential.getPlayerProfileManager().getPlayerProfile(uuid);
                return profile.getHomeNames();
            }
            return null;
        });
    }

}
