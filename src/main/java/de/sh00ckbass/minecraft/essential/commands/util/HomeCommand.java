package de.sh00ckbass.minecraft.essential.commands.util;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import de.sh00ckbass.minecraft.essential.Essential;
import de.sh00ckbass.minecraft.essential.data.PlayerProfileManager;
import de.sh00ckbass.minecraft.essential.data.types.PlayerProfile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Map;

@CommandAlias("home")
public class HomeCommand extends BaseCommand {

    private final PlayerProfileManager profileManager;

    public HomeCommand(Essential essential) {
        this.profileManager = essential.getPlayerProfileManager();
    }

    @Default
    public void home(Player sender) {
        PlayerProfile profile = this.profileManager.getPlayerProfile(sender.getUniqueId());

        this.sendPlayerHomeLocations(sender, profile);
    }

    @Subcommand("set")
    public void setHome(Player sender, String name) {
        PlayerProfile profile = this.profileManager.getPlayerProfile(sender.getUniqueId());

        profile.addHome(name, sender.getLocation());
        sender.sendMessage("§7Du hast dein neues Zuhause §e" + name + "§7 erfolgreich gesetzt.");
    }

    @Subcommand("remove")
    public void removeHome(Player sender, String name) {
        PlayerProfile profile = this.profileManager.getPlayerProfile(sender.getUniqueId());
        Location oldHome = profile.removeHome(name);

        if (oldHome == null) {
            sender.sendMessage("§7Du hast kein Zuhause names §e" + name + "§7.");
            return;
        }

        sender.sendMessage("§7Du hast dein Zuhause §e" + name + "§7 erfolgreich gelöscht.");
    }

    @Subcommand("tp")
    public void teleportHome(Player sender, String name) {
        PlayerProfile profile = this.profileManager.getPlayerProfile(sender.getUniqueId());
        Location home = profile.getHomeByName(name);

        if (home == null) {
            sender.sendMessage("§7Du hast kein Zuhause names §e" + name + "§7.");
            return;
        }

        sender.teleport(home);
        sender.sendMessage("§7Du hast dich zu deinem Zuhause §e" + name + "§7 teleportiert.");
    }

    private void sendPlayerHomeLocations(Player player, PlayerProfile profile) {
        Map<String, Location> homes = profile.getHomes();

        if (homes.isEmpty()) {
            player.sendMessage("§7Du hast kein Homes, setzte welche mit §e/home set <name>");
            return;
        }

        player.sendMessage("\n\n§7Deine Homes sind: ");

        homes.forEach((name, location) -> {
            Component component = Component.text("§7- §e" + name)
                    .clickEvent(ClickEvent.runCommand("/home tp " + name))
                    .hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text("§7Klicke zum teleportieren.")))
                    .asComponent();

            player.sendMessage(component);
        });

        player.sendMessage("\n§7Klicke auf ein Home, um dich zu diesem zu teleportieren");
    }

}
