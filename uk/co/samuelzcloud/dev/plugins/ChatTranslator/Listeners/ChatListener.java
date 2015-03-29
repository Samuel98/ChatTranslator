package uk.co.samuelzcloud.dev.plugins.ChatTranslator.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import uk.co.samuelzcloud.dev.plugins.ChatTranslator.ChatTranslator;
import uk.co.samuelzcloud.dev.plugins.ChatTranslator.Utilities.Languages;
import uk.co.samuelzcloud.dev.plugins.ChatTranslator.Utilities.Translate;

import java.util.Set;

/**
 * Created by Samuel on 29/03/2015.
 * uk.co.samuelzcloud.dev.plugins.ChatTranslator.Listeners - ChatListener
 */
public class ChatListener implements Listener {

    private ChatTranslator plugin;

    public ChatListener(ChatTranslator plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent event) {
        if (event.getMessage().startsWith("/")) return;

        if (event.isCancelled()) return;

        Player player = event.getPlayer();
        String message = event.getMessage();
        Set<Player> recipients = event.getRecipients();
        Languages senderLang = Languages.valueOf(this.plugin.getPlayers().getLanguage(player.getUniqueId()).toUpperCase());


        String format = event.getFormat();
        String lformat = this.plugin.getLog().colour("&7[&aFrom " + senderLang.getLangCode().toUpperCase() + "&7]&r ");

        //[From EN] <Samuel98> Message goes Here.
        //event.setMessage(Translate.getTranslation(message, Languages.valueOf(this.plugin.getPlayers().getLanguage(player.getUniqueId())).getLangCode().toUpperCase()));

        event.setCancelled(true);

        String msg = lformat + "<" + player.getDisplayName() + "> ";

        for (Player recipient : recipients) {
            this.plugin.getLog().sendPlainMessage(recipient, msg + Translate.getTranslation(message, Languages.valueOf(this.plugin.getPlayers().getLanguage(recipient.getUniqueId()).toUpperCase()).getLangCode().toUpperCase(), senderLang.getLangCode().toUpperCase()));
        }

        this.plugin.getLog().logInfo(msg + message);

        return;
    }

}
