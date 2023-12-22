package de.sh00ckbass.minecraft.essential.data;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerProfile implements ConfigurationSerializable {

    @Getter
    @Setter
    private UUID playerUUID;

    @Getter
    @Setter
    private String nickname;

    private final Map<String, Location> homes;

    public PlayerProfile(UUID playerUUID, String nickname, Map<String, Location> homes) {
        this.playerUUID = playerUUID;
        this.nickname = nickname;
        this.homes = homes;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();

        data.put("uuid", this.playerUUID.toString());
        data.put("nickname", this.nickname);
        data.put("homes", this.homes);

        return data;
    }

    public static PlayerProfile deserialize(Map<String, Object> args) {
        String uuid = (String) args.get("uuid");
        String nickname = (String) args.get("nickname");
        Map<String, Location> homes = (Map<String, Location>) args.get("homes");

        return new PlayerProfile(
                UUID.fromString(uuid),
                nickname,
                homes
        );
    }

    /**
     * Get a home location by name
     *
     * @param homeName The name of a home
     * @return The home or null
     */
    public Location getHomeByName(String homeName) {
        return this.homes.get(homeName);
    }

    /**
     * Add a home to the players homes
     *
     * @param name     The name of the home
     * @param location The location of the home
     */
    public void addHome(String name, Location location) {
        this.homes.put(name, location);
    }

    /**
     * Remove's a player's home
     *
     * @param name The name of the home
     * @return The home which got deleted or null if the home didn't exit
     */
    public Location removeHome(String name) {
        return this.homes.remove(name);
    }

}
