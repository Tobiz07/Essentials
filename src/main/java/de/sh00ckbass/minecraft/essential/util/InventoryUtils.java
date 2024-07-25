package de.sh00ckbass.minecraft.essential.util;

import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://gist.github.com/graywolf336/8153678">Source</a>
 */
public abstract class InventoryUtils {

    public static String ConvertItemsToBase64(ItemStack[] items) {
        items = removeNullItems(items);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            DataOutput output = new DataOutputStream(outputStream);
            output.writeInt(items.length);

            for (ItemStack item : items) {
                byte[] bytes = item.serializeAsBytes();
                output.writeInt(bytes.length);
                output.write(bytes);
            }

            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Error while writing itemstack", e);
        }
    }

    public static ItemStack[] ConvertBase64ToItemStackArray(String base64) {
        byte[] bytes = Base64Coder.decodeLines(base64);
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes)) {
            DataInputStream input = new DataInputStream(inputStream);
            int count = input.readInt();
            List<ItemStack> items = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                int length = input.readInt();
                if (length == 0) {
                    continue;
                }

                byte[] itemBytes = new byte[length];
                input.read(itemBytes);
                items.add(ItemStack.deserializeBytes(itemBytes));
            }

            return items.toArray(ItemStack[]::new);
        } catch (IOException e) {
            throw new RuntimeException("Error while reading itemstack", e);
        }
    }

    private static ItemStack[] removeNullItems(ItemStack[] items) {
        List<ItemStack> tempItems = new ArrayList<>();

        for (ItemStack item : items) {
            if (item == null) {
                continue;
            }

            tempItems.add(item);
        }

        return tempItems.toArray(ItemStack[]::new);
    }

}
