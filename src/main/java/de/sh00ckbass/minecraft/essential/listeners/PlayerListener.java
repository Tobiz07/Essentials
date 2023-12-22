package de.sh00ckbass.minecraft.essential.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import java.util.List;

public class PlayerListener implements Listener {

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

    private TextComponent getTextComponentOfComponent(Component component) {
        return (TextComponent) component;
    }

}
