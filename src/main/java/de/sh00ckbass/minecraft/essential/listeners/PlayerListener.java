package de.sh00ckbass.minecraft.essential.listeners;

import de.sh00ckbass.minecraft.essential.Essential;
import de.sh00ckbass.minecraft.essential.data.inventories.DeathItemInventory;
import de.sh00ckbass.minecraft.essential.data.types.PlayerProfile;
import de.sh00ckbass.minecraft.essential.util.InventoryUtils;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PlayerListener implements Listener {

    private final Essential essential;
    private final NamespacedKey deathChestInventoryKey;

    public PlayerListener(Essential essential) {
        this.essential = essential;
        this.deathChestInventoryKey = new NamespacedKey(essential, "player-death-chest");
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        String message = this.getTextComponentOfComponent(event.message()).content();
        TextComponent messageComponent = Component.text(message.replace('&', '§'));

        event.message(messageComponent);
    }

    @EventHandler
    public void onSignWrite(SignChangeEvent event) {
        List<Component> lines = event.lines();
        for (int i = 0; i < lines.size(); i++) {
            String text = this.getTextComponentOfComponent(lines.get(i)).content().replace('&', '§');
            event.line(i, Component.text(text));
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        this.loadPlayerProfile(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        this.savePlayerProfile(player);
    }

    @SuppressWarnings("UnstableApiUsage")
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (playerDiedInVoid(event.getDamageSource())) {
            return;
        }

        Player player = event.getPlayer();

        this.setDeathChest(player);
        event.getDrops().clear();
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Action action = event.getAction();

        if (!action.isRightClick()) {
            return;
        }

        Block clickedBlock = event.getClickedBlock();

        if (clickedBlock == null) {
            return;
        }

        if (!(clickedBlock.getState() instanceof Chest chest)) {
            return;
        }

        Player player = event.getPlayer();

        if (!assertChestIsDeathChest(chest)) {
            return;
        }

        event.setCancelled(true);

        Inventory deathItemsInventory = this.getDeathItemsInventory(chest);

        player.openInventory(deathItemsInventory);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();

        if (!(inventory.getHolder(false) instanceof DeathItemInventory deathItemInventory)) {
            return;
        }

        inventory = deathItemInventory.getInventory();
        Chest chest = deathItemInventory.getDeathChest();
        ItemStack[] contents = inventory.getContents();

        if (Arrays.stream(contents).allMatch(Objects::isNull)) {
            chest.setType(Material.AIR);
            chest.update(true);
            return;
        }

        String deathChestItems = InventoryUtils.ConvertItemsToBase64(contents);
        this.storeDeathChestItems(chest, deathChestItems);
    }

    private Inventory getDeathItemsInventory(Chest chest) {
        PersistentDataContainer chestDataContainer = chest.getPersistentDataContainer();

        String encodedItems = chestDataContainer.get(this.deathChestInventoryKey, PersistentDataType.STRING);
        ItemStack[] deathItems = InventoryUtils.ConvertBase64ToItemStackArray(encodedItems);

        DeathItemInventory deathItemInventory = new DeathItemInventory(this.essential, chest);

        Inventory inventory = deathItemInventory.getInventory();
        inventory.addItem(deathItems);

        return inventory;
    }

    private boolean assertChestIsDeathChest(Chest chest) {
        PersistentDataContainer persistentDataContainer = chest.getPersistentDataContainer();

        return persistentDataContainer.has(this.deathChestInventoryKey);
    }

    private void setDeathChest(Player player) {
        PlayerInventory playerInventory = player.getInventory();

        Location location = player.getLocation();
        Block block = location.getBlock();
        block.setType(Material.CHEST);

        Chest chest = (Chest) block.getState();

        String encodedPlayerItems = this.getPlayerItems(playerInventory);

        this.storeDeathChestItems(chest, encodedPlayerItems);

        player.sendMessage("§eDeathChest §7>> " +
                "Du bist bei X §e" + location.getBlockX() +
                "§7 Y §e" + location.getBlockY() +
                "§7 Z §e" + location.getBlockZ() +
                " §7gestorben.");
    }

    private void storeDeathChestItems(Chest chest, String encodedItems) {
        chest.getPersistentDataContainer().set(this.deathChestInventoryKey, PersistentDataType.STRING, encodedItems);
        chest.update();
    }

    private String getPlayerItems(PlayerInventory playerInventory) {
        ItemStack[] playerItems = playerInventory.getContents();

        return InventoryUtils.ConvertItemsToBase64(playerItems);
    }

    private void savePlayerProfile(Player player) {
        this.essential.getPlayerProfileManager().savePlayerProfile(player.getUniqueId());
    }

    private void loadPlayerProfile(Player player) {
        PlayerProfile profile = this.essential.getPlayerProfileManager().getPlayerProfile(player.getUniqueId());

        if (profile == null) {
            return;
        }

        if (profile.getNickname() != null) {
            String nickname = profile.getNickname().replace('&', '§');

            player.displayName(Component.text(nickname));
            player.playerListName(Component.text(nickname));
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    private boolean playerDiedInVoid(DamageSource damageSource) {
        DamageType damageType = damageSource.getDamageType();

        return damageType == DamageType.OUT_OF_WORLD;
    }

    private TextComponent getTextComponentOfComponent(Component component) {
        return (TextComponent) component;
    }

}
