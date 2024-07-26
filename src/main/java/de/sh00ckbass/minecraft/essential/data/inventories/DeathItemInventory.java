package de.sh00ckbass.minecraft.essential.data.inventories;

import de.sh00ckbass.minecraft.essential.Essential;
import lombok.Getter;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class DeathItemInventory implements InventoryHolder {

    private final Inventory inventory;

    @Getter
    private final Chest deathChest;

    public DeathItemInventory(Essential essential, Chest deathChest) {
        this.deathChest = deathChest;
        this.inventory = essential.getServer().createInventory(this, 54);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
