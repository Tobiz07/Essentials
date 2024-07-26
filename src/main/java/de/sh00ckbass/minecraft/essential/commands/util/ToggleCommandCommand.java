package de.sh00ckbass.minecraft.essential.commands.util;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import de.sh00ckbass.minecraft.essential.Essential;
import de.sh00ckbass.minecraft.essential.data.types.PluginConfig;
import org.bukkit.command.CommandSender;

@CommandAlias("toggleCommand")
@CommandPermission("op")
public class ToggleCommandCommand extends BaseCommand {

    private final PluginConfig config;

    public ToggleCommandCommand(Essential essential) {
        this.config = essential.getPluginConfigManager().getConfig();
    }

    @Default
    @CommandCompletion("gift|head|home|nick")
    public void toggleCommand(CommandSender commandSender, String commandName) {
        String status;
        switch (commandName) {
            case "gift":
                boolean newGiftCommandStatus = !this.config.isGiftCommandEnabled();
                this.config.setGiftCommandEnabled(newGiftCommandStatus);

                status = newGiftCommandStatus ? "§aaktiviert." : "§cdeaktiviert.";

                commandSender.sendMessage("§7Der Befehl §e/gift§7 ist nun " + status);
                break;
            case "head":
                boolean newHeadCommandStatus = !this.config.isHeadCommandEnabled();
                this.config.setHeadCommandEnabled(newHeadCommandStatus);

                status = newHeadCommandStatus ? "§aaktiviert." : "§cdeaktiviert.";

                commandSender.sendMessage("§7Der Befehl §e/head§7 ist nun " + status);
                break;
            case "home":
                boolean newHomeCommandStatus = !this.config.isHomeCommandEnabled();
                this.config.setHomeCommandEnabled(newHomeCommandStatus);

                status = newHomeCommandStatus ? "§aaktiviert." : "§cdeaktiviert.";

                commandSender.sendMessage("§7Der Befehl §e/home§7 ist nun " + status);
                break;
            case "nick":
                boolean newNickCommandStatus = !this.config.isNickCommandEnabled();
                this.config.setNickCommandEnabled(newNickCommandStatus);

                status = newNickCommandStatus ? "§aaktiviert." : "§cdeaktiviert.";

                commandSender.sendMessage("§7Der Befehl §e/nick§7 ist nun " + status);
                break;
        }

    }

}
