package de.sh00ckbass.minecraft.essential.commands.util;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import de.sh00ckbass.minecraft.essential.Essential;
import de.sh00ckbass.minecraft.essential.util.HeadApi;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@CommandAlias("gift")
@CommandPermission("op")
public class GiftCommand extends BaseCommand {

    private final HeadApi headApi;

    public GiftCommand(Essential essential) {
        this.headApi = essential.getHeadApi();
    }

    @Default
    @CommandCompletion("1|10|20")
    public void gift(Player sender, int amount) {
        if (amount == 0) {
            amount = 1;
        }
        ItemStack gifts = this.getGift(amount);

        sender.getInventory().addItem(gifts);
        sender.sendMessage("§7Du hast §e" + amount + "§7 Geschenk(e) erhalten.");
    }

    /**
     * Get gift items
     *
     * @param amount Amount of the Gifts
     * @return a item stack of gifts
     */
    private ItemStack getGift(int amount) {
        ItemStack gift = this.headApi.getHead("21bc9d42b0041e8f95cb9b26628fdaf50cd0e36f7bb9d6b3a4d2af3949da97d6");
        ItemMeta giftMeta = gift.getItemMeta();
        List<TextComponent> lore = new ArrayList<>();

        lore.add(Component.text("§r§aEin besonderes Geschenk"));

        giftMeta.displayName(Component.text("§f§lWeihnachtsgeschenk"));
        giftMeta.lore(lore);

        gift.setItemMeta(giftMeta);
        gift.setAmount(amount);
        return gift;
    }

}
