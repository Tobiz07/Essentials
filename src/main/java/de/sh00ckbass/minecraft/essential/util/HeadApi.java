package de.sh00ckbass.minecraft.essential.util;

import com.destroystokyo.paper.profile.PlayerProfile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HeadApi {

    private final Map<String, ItemStack> headCache = new HashMap<>();

    /**
     * Get a head from <a href="https://minecraft-heads.com">Heads Database</a> with Minecraft URL
     *
     * @param skinValue The minecraft url (e.g. 21bc9d42b0041e8f95cb9b26628fdaf50cd0e36f7bb9d6b3a4d2af3949da97d6)
     * @return The requested head as a {@link ItemStack}
     */
    public ItemStack getHead(String skinValue) {
        ItemStack cachedHead = this.headCache.get(skinValue);
        if (cachedHead != null) {
            return cachedHead;
        }

        try {
            String baseURL = "http://textures.minecraft.net/texture/";
            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta) head.getItemMeta();

            UUID uuid = UUID.randomUUID();

            PlayerProfile profile = Bukkit.createProfile(uuid, null);
            PlayerTextures textures = profile.getTextures();

            URL url = new URL(baseURL + skinValue);

            textures.setSkin(url);

            profile.setTextures(textures);
            skullMeta.setPlayerProfile(profile);
            head.setItemMeta(skullMeta);

            this.headCache.put(skinValue, head);

            return head;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}
