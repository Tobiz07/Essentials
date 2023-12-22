package de.sh00ckbass.minecraft.essential.listeners;

import de.sh00ckbass.minecraft.essential.Essential;
import de.sh00ckbass.minecraft.essential.data.PlayerProfile;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class PlayerListener implements Listener {

    private final Essential essential;

    public PlayerListener(Essential essential) {
        this.essential = essential;
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        String message = this.getTextComponentOfComponent(event.message()).content();
        TextComponent messageComponent = Component.text(message.replace('&', '§'));

        event.message(messageComponent);
    }

    @EventHandler
    public void onSignWrite(SignChangeEvent event) {
        List<Component> lines = event.lines();
        for (int i = 0; i < lines.size(); i++) {
            String text = this.getTextComponentOfComponent(lines.get(i)).content().replace('&', '§');
            event.line(i, Component.text(text));
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        this.loadPlayerProfile(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        this.savePlayerProfile(player);
    }

    private void savePlayerProfile(Player player) {
        this.essential.getPlayerProfileManager().savePlayerProfile(player.getUniqueId());
    }

    private void loadPlayerProfile(Player player) {
        PlayerProfile profile = this.essential.getPlayerProfileManager().getPlayerProfile(player.getUniqueId());

        if (profile == null) {
            return;
        }

        if (profile.getNickname() != null) {
            String nickname = profile.getNickname().replace('&', '§');

            player.displayName(Component.text(nickname));
            player.playerListName(Component.text(nickname));
        }
    }

    private TextComponent getTextComponentOfComponent(Component component) {
        return (TextComponent) component;
    }

}
