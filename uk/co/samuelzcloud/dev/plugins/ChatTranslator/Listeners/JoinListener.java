package uk.co.samuelzcloud.dev.plugins.ChatTranslator.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import uk.co.samuelzcloud.dev.plugins.ChatTranslator.ChatTranslator;

/**
 * Created by Samuel on 29/03/2015.
 * uk.co.samuelzcloud.dev.plugins.ChatTranslator.Listeners - JoinListener
 */
public class JoinListener implements Listener {

    private ChatTranslator plugin;

    public JoinListener(ChatTranslator plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        this.plugin.getPlayers().setupPlayer(player.getUniqueId());

        if (this.plugin.getPlayers().getJoins(player.getUniqueId()) <= 3) {
            this.plugin.getLog().sendFormattedMessage(player, "&bChange your preferred language using /language!");
        }

        this.plugin.getPlayers().setJoins(player.getUniqueId(), this.plugin.getPlayers().getJoins(player.getUniqueId()) + 1);
    }

}
