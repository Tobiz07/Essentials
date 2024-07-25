package de.sh00ckbass.minecraft.essential.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GiftListener implements Listener {

    private final Map<Integer, ItemStack> lootTable = new HashMap<>();

    public GiftListener() {
        this.initLootTable();
    }

    @EventHandler
    public void onPlayerInteractWithGift(PlayerInteractEvent event) {
        ItemStack handItem = event.getItem();

        if (handItem == null) {
            return;
        }

        if (!this.isGift(handItem)) {
            return;
        }
        event.setCancelled(true);
        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();

        handItem.setAmount(handItem.getAmount() - 1);

        inventory.addItem(this.getRandomGift());
    }

    /**
     * Check if item stack is a gift
     *
     * @param itemStack The item stack to be checked
     * @return true if item stack is a gift
     */
    private boolean isGift(ItemStack itemStack) {
        if (!itemStack.hasItemMeta()) {
            return false;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();

        TextComponent displayName = ((TextComponent) itemMeta.displayName());
        List<Component> lore = itemMeta.lore();

        if (displayName == null || lore == null) {
            return false;
        }

        boolean isGiftDisplayname = displayName.content().equalsIgnoreCase("§f§lWeihnachtsgeschenk");
        boolean hasCorrectLore = lore.stream().anyMatch((tc) -> ((TextComponent) tc).content().equals("§r§aEin besonderes Geschenk"));

        return hasCorrectLore && isGiftDisplayname;
    }

    /**
     * Get a random gift
     */
    private ItemStack getRandomGift() {
        Random random = new Random();

        int randomNumber = random.nextInt(9) + 1;

        if (randomNumber <= 5) {
            randomNumber = random.nextInt(5) + 1;
        } else {
            randomNumber = random.nextInt(4) + 6;
        }

        return this.lootTable.get(randomNumber);
    }

    /**
     * Initialize the loot table
     */
    private void initLootTable() {
        // Looting X Sword
        ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta diamondSwordMeta = diamondSword.getItemMeta();

        diamondSwordMeta.displayName(Component.text("§b§lMICHO"));
        diamondSwordMeta.addEnchant(Enchantment.LOOTING, 10, true);
        diamondSwordMeta.addEnchant(Enchantment.UNBREAKING, 5, true);

        diamondSword.setItemMeta(diamondSwordMeta);

        this.lootTable.put(9, diamondSword);

        // Fortune X Pickaxe
        ItemStack diamondPickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta diamondMeta = diamondPickaxe.getItemMeta();

        diamondMeta.displayName(Component.text("§b§lTorben"));
        diamondMeta.addEnchant(Enchantment.LOOTING, 10, true);
        diamondMeta.addEnchant(Enchantment.UNBREAKING, 5, true);

        diamondPickaxe.setItemMeta(diamondMeta);

        this.lootTable.put(8, diamondPickaxe);

        // Beacon
        ItemStack beacon = new ItemStack(Material.BEACON);

        this.lootTable.put(7, beacon);

        // NetherStar
        ItemStack netherStar = new ItemStack(Material.NETHER_STAR);

        this.lootTable.put(6, netherStar);

        // Wither skeleton skull 3
        ItemStack witherSkull = new ItemStack(Material.WITHER_SKELETON_SKULL, 3);

        this.lootTable.put(5, witherSkull);

        // End crystal 16
        ItemStack endCrystal = new ItemStack(Material.END_CRYSTAL, 16);

        this.lootTable.put(4, endCrystal);

        // Slime blocks 64
        ItemStack slimeBlock = new ItemStack(Material.SLIME_BLOCK, 64);

        this.lootTable.put(3, slimeBlock);

        // Diamonds 10
        ItemStack diamonds = new ItemStack(Material.DIAMOND, 10);

        this.lootTable.put(2, diamonds);

        // Grass block 32
        ItemStack grass = new ItemStack(Material.GRASS_BLOCK, 32);

        this.lootTable.put(1, grass);
    }

}
