package de.sh00ckbass.minecraft.essential.data;

import com.google.common.reflect.TypeToken;
import de.sh00ckbass.minecraft.essential.Essential;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

public class BaseConfigManager<TConfig extends ConfigurationSerializable> {

    private final Essential essential;

    public BaseConfigManager(Essential essential) {
        this.essential = essential;

        ConfigurationSerialization.registerClass(getClassOfType());

        this.initFileSystem();
    }

    /**
     * Initializes the base file system of the plugin
     */
    private void initFileSystem() {
        if (!essential.getDataFolder().exists()) {
            essential.getDataFolder().mkdirs();
        }
    }

    private Class<TConfig> getClassOfType() {
        TypeToken<TConfig> typeToken = new TypeToken<>(getClass()) {
        };

        return (Class<TConfig>) typeToken.getRawType();
    }

}
