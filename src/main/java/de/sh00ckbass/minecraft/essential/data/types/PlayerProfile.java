package de.sh00ckbass.minecraft.essential.data.types;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.*;

@Getter
public class PlayerProfile implements ConfigurationSerializable {

    private final Map<String, Location> homes;
    @Setter
    private UUID playerUUID;
    @Setter
    private String nickname;

    public PlayerProfile(UUID playerUUID, String nickname, Map<String, Location> homes) {
        this.playerUUID = playerUUID;
        this.nickname = nickname;
        this.homes = homes;
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

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();

        data.put("uuid", this.playerUUID.toString());
        data.put("nickname", this.nickname);
        data.put("homes", this.homes);

        return data;
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

    /**
     * Get all home names
     */
    public Set<String> getHomeNames() {
        return this.homes.keySet();
    }

}
