package de.sh00ckbass.minecraft.essential.data.types;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class PluginConfig implements ConfigurationSerializable {

    private boolean isGiftCommandEnabled;
    private boolean isHeadCommandEnabled;
    private boolean isHomeCommandEnabled;
    private boolean isNickCommandEnabled;

    public PluginConfig(boolean isGiftCommandEnabled, boolean isHeadCommandEnabled, boolean isHomeCommandEnabled, boolean isNickCommandEnabled) {
        this.isGiftCommandEnabled = isGiftCommandEnabled;
        this.isHeadCommandEnabled = isHeadCommandEnabled;
        this.isHomeCommandEnabled = isHomeCommandEnabled;
        this.isNickCommandEnabled = isNickCommandEnabled;
    }

    public static PluginConfig deserialize(Map<String, Object> args) {
        Boolean isGiftCommandEnabled = (Boolean) args.get("giftCommandEnabled");
        Boolean isHeadCommandEnabled = (Boolean) args.get("headCommandEnabled");
        Boolean isHomeCommandEnabled = (Boolean) args.get("homeCommandEnabled");
        Boolean isNickCommandEnabled = (Boolean) args.get("nickCommandEnabled");

        return new PluginConfig(
                isGiftCommandEnabled,
                isHeadCommandEnabled,
                isHomeCommandEnabled,
                isNickCommandEnabled
        );
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();

        data.put("giftCommandEnabled", this.isGiftCommandEnabled);
        data.put("headCommandEnabled", this.isHeadCommandEnabled);
        data.put("homeCommandEnabled", this.isHomeCommandEnabled);
        data.put("nickCommandEnabled", this.isNickCommandEnabled);

        return data;
    }

}
