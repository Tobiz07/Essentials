package de.sh00ckbass.minecraft.essential.data;

import de.sh00ckbass.minecraft.essential.Essential;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerProfileManager {

    private final Essential essential;

    private final Map<UUID, PlayerProfile> playerProfiles = new HashMap<>();


    public PlayerProfileManager(Essential essential) {
        this.essential = essential;

        ConfigurationSerialization.registerClass(PlayerProfile.class);

        this.initFileSystem();
    }

    public PlayerProfile getPlayerProfile(UUID uuid) {
        return this.loadProfile(uuid);
    }

    public void savePlayerProfile(UUID uuid) {
        this.saveProfile(uuid);
    }

    private void initFileSystem() {
        if (!essential.getDataFolder().exists()) {
            essential.getDataFolder().mkdirs();
        }
    }

    private void saveProfile(UUID uuid) {
        try {
            PlayerProfile playerProfile = this.loadProfile(uuid);

            File playerProfileFile = new File(this.essential.getDataFolder(), uuid.toString() + ".yml");

            if (!playerProfileFile.exists()) {
                playerProfileFile.createNewFile();
            }

            YamlConfiguration profileConfig = YamlConfiguration.loadConfiguration(playerProfileFile);

            profileConfig.set("profile", playerProfile);

            profileConfig.save(playerProfileFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private PlayerProfile loadProfile(UUID uuid) {
        PlayerProfile playerProfile = this.playerProfiles.get(uuid);

        if (playerProfile != null) {
            return playerProfile;
        }

        File playerProfileFile = new File(this.essential.getDataFolder(), uuid.toString() + ".yml");

        if (!playerProfileFile.exists()) {
            return new PlayerProfile(uuid, null, new HashMap<>());
        }

        YamlConfiguration profileConfig = YamlConfiguration.loadConfiguration(playerProfileFile);

        playerProfile = profileConfig.getSerializable("profile", PlayerProfile.class);

        this.playerProfiles.put(uuid, playerProfile);
        return playerProfile;
    }

}
