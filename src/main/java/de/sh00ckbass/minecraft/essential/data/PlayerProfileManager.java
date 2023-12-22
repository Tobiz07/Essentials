package de.sh00ckbass.minecraft.essential.data;

import de.sh00ckbass.minecraft.essential.Essential;
import de.sh00ckbass.minecraft.essential.data.types.PlayerProfile;
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

    /**
     * Get the profile of a player
     *
     * @param uuid The UUID of the player
     * @return The player profile
     */
    public PlayerProfile getPlayerProfile(UUID uuid) {
        return this.loadProfile(uuid);
    }

    /**
     * Save the profile of a player
     *
     * @param uuid The UUID of the player
     */
    public void savePlayerProfile(UUID uuid) {
        this.saveProfile(uuid);
    }

    /**
     * Initializes the base file system of the plugin
     */
    private void initFileSystem() {
        if (!essential.getDataFolder().exists()) {
            essential.getDataFolder().mkdirs();
        }
    }

    /**
     * Save's the player profile to the player config file (uuid.yml)
     *
     * @param uuid The UUID of the player
     */
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

    /**
     * Loads the player profile of the player config file (uuid.yml)
     *
     * @param uuid The UUID of the player
     * @return The player profile
     */
    private PlayerProfile loadProfile(UUID uuid) {
        PlayerProfile playerProfile = this.playerProfiles.get(uuid);

        if (playerProfile != null) {
            return playerProfile;
        }

        File playerProfileFile = new File(this.essential.getDataFolder(), uuid.toString() + ".yml");

        // if the player has no profile a new one get returned
        if (!playerProfileFile.exists()) {
            return new PlayerProfile(uuid, null, new HashMap<>());
        }

        YamlConfiguration profileConfig = YamlConfiguration.loadConfiguration(playerProfileFile);

        playerProfile = profileConfig.getSerializable("profile", PlayerProfile.class);

        this.playerProfiles.put(uuid, playerProfile);
        return playerProfile;
    }

}
