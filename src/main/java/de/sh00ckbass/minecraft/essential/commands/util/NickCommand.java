package de.sh00ckbass.minecraft.essential.commands.util;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.PreCommand;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import de.sh00ckbass.minecraft.essential.Essential;
import de.sh00ckbass.minecraft.essential.data.PlayerProfileManager;
import de.sh00ckbass.minecraft.essential.data.types.PluginConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("nick")
public class NickCommand extends BaseCommand {

    private final PlayerProfileManager profileManager;
    private final PluginConfig config;

    public NickCommand(Essential essential) {
        this.profileManager = essential.getPlayerProfileManager();
        this.config = essential.getPluginConfigManager().getConfig();
    }

    @PreCommand
    public boolean checkIfCommandIsEnabled(CommandSender commandSender) {
        boolean isDisabled = !config.isNickCommandEnabled();

        if (isDisabled) {
            commandSender.sendMessage("§cDieser Befehl ist deaktiviert.");
        }

        return isDisabled;
    }

    /**
     * Change the display name of executing player
     *
     * @param sender   The sender who executes the command
     * @param nickname The new nickname
     */
    @Default
    public void nick(Player sender, String nickname) {
        TextComponent nicknameComponent = Component.text(nickname.replace('&', '§'));

        sender.sendMessage("§7Du hast deinen Nicknamen auf §e" + nickname + "§7 gesetzt.");

        sender.displayName(nicknameComponent);
        sender.playerListName(nicknameComponent);

        profileManager.getPlayerProfile(sender.getUniqueId()).setNickname(nickname);
    }

    /**
     * Reset's the nickname of the sender
     *
     * @param sender The sender who executes the command
     */
    @CommandAlias("unnick")
    public void unNick(Player sender) {
        sender.displayName(null);
        sender.playerListName(null);

        sender.sendMessage("§7Du hast deinen Nicknamen zurück gesetzt.");

        profileManager.getPlayerProfile(sender.getUniqueId()).setNickname(null);
    }

    /**
     * Change the display name of given player
     *
     * @param sender       The sender who executes the command
     * @param playerToNick The player to get nicked
     * @param nickname     The new nickname
     */
    @CommandAlias("nickother")
    @CommandPermission("op")
    public void nickOther(Player sender, OnlinePlayer playerToNick, String nickname) {
        TextComponent nicknameComponent = Component.text(nickname.replace('&', '§'));

        sender.sendMessage("§7Du hast von §e" + playerToNick.getPlayer().getName() + "§7 den Nicknamen auf §e" + nickname + "§7 gesetzt.");

        playerToNick.getPlayer().displayName(nicknameComponent);
        playerToNick.getPlayer().playerListName(nicknameComponent);

        profileManager.getPlayerProfile(playerToNick.getPlayer().getUniqueId()).setNickname(nickname);
    }

}
