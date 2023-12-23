package de.sh00ckbass.minecraft.essential.commands.util;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import de.sh00ckbass.minecraft.essential.Essential;
import de.sh00ckbass.minecraft.essential.util.HeadApi;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandAlias("head")
@CommandPermission("op")
public class HeadCommand extends BaseCommand {

    private final HeadApi headApi;

    public HeadCommand(Essential essential) {
        this.headApi = essential.getHeadApi();
    }

    @Default
    public void head(Player sender, @Optional String headValue) {
        if (headValue == null) {
            TextComponent component = Component.text("§7Klicke um die Head Database zu öffnen")
                    .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, "https://minecraft-heads.com/"));

            sender.sendMessage(component);
            return;
        }

        ItemStack head = this.headApi.getHead(headValue);

        sender.getInventory().addItem(head);
        sender.sendMessage("§7Du hast dir einen Kopf geholt.");
    }

}
