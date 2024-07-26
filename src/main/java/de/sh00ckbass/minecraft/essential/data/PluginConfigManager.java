package de.sh00ckbass.minecraft.essential.data;

import de.sh00ckbass.minecraft.essential.Essential;
import de.sh00ckbass.minecraft.essential.data.types.PluginConfig;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PluginConfigManager extends BaseConfigManager<PluginConfig> {

    private final Essential essential;

    @Getter
    private PluginConfig config;

    public PluginConfigManager(Essential essential) {
        super(essential);
        this.essential = essential;
    }

    public void saveConfig() {
        try {
            File pluginConfig = new File(this.essential.getDataFolder(), "config.yml");

            if (!pluginConfig.exists()) {
                pluginConfig.createNewFile();
            }

            YamlConfiguration pluginYamlConfig = YamlConfiguration.loadConfiguration(pluginConfig);

            pluginYamlConfig.set("config", this.config);

            pluginYamlConfig.save(pluginConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadConfig() {
        File pluginConfigFile = new File(this.essential.getDataFolder(), "config.yml");

        if (!pluginConfigFile.exists()) {
            this.config = new PluginConfig(true, true, true, true);
            return;
        }

        YamlConfiguration pluginYamlConfig = YamlConfiguration.loadConfiguration(pluginConfigFile);

        this.config = pluginYamlConfig.getSerializable("config", PluginConfig.class);
    }
}
