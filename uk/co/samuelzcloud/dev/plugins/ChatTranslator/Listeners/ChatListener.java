package uk.co.samuelzcloud.dev.plugins.ChatTranslator.Listeners;

import com.dthielke.herochat.ChannelChatEvent;
import com.dthielke.herochat.Chatter;
import com.dthielke.herochat.Herochat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import uk.co.samuelzcloud.dev.plugins.ChatTranslator.ChatTranslator;
import uk.co.samuelzcloud.dev.plugins.ChatTranslator.Utilities.Language;
import uk.co.samuelzcloud.dev.plugins.ChatTranslator.Utilities.Translate;
import uk.co.samuelzcloud.dev.plugins.ChatTranslator.Utilities.YandexDetect;

import java.util.HashMap;

/**
 * Created by Samuel on 29/03/2015.
 * uk.co.samuelzcloud.dev.plugins.ChatTranslator.Listeners - ChatListener
 */
public class ChatListener implements Listener {

    public ChatTranslator plugin;

    public ChatListener(ChatTranslator plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(ChannelChatEvent event) {
        Chatter sender = event.getSender();
        Language sLang;
        HashMap cache = new HashMap();
        String origMess = event.getMessage();

        try {
            sLang = YandexDetect.execute(origMess);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        String format = this.plugin.getLog().colour(event.getChannel().getFormatSupplier().getStandardFormat()
                .replaceAll("\\{lang\\}", (!sLang.getName().equalsIgnoreCase("DISABLED")) ? sLang.getCode().toUpperCase() : "")
                .replaceAll("\\{name\\}", event.getChannel().getName())
                .replaceAll("\\{nick\\}", event.getChannel().getNick())
                .replaceAll("\\{color\\}", event.getChannel().getColor() + "")
                        //.replaceAll("\\{msg\\}", event.getMessage())
                .replaceAll("\\{sender\\}", sender.getPlayer().getDisplayName())
                .replaceAll("\\{prefix\\}", Herochat.getChatService().getPlayerPrefix(sender.getPlayer()))
                .replaceAll("\\{suffix\\}", Herochat.getChatService().getPlayerSuffix(sender.getPlayer())));
        event.setFormat(format);

        for (Player on : Bukkit.getOnlinePlayers()) {
            //if (on.equals(sender.getPlayer())) continue;

            Language rLang = Language.valueOf(this.plugin.getPlayers().getLanguage(on.getUniqueId()).toUpperCase());

            if (rLang != Language.DISABLED) {
                String translation;
                try {
                    translation = Translate.execute(origMess, sLang, rLang) + "";
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }

                if (cache.containsKey(rLang)) {
                    on.sendMessage(format.replaceAll("\\{msg}", (String) cache.get(rLang)));
                } else {
                    on.sendMessage(format.replaceAll("\\{msg}", translation));
                    cache.put(rLang, translation);
                }
            } else {
                on.sendMessage(format.replaceAll("\\{msg}", origMess));
            }
        }

        String msg;
        if (cache.containsKey(Language.ENGLISH)) {
            msg = (String) cache.get(Language.ENGLISH);
        } else {
            try {
                msg = Translate.execute(origMess, sLang, Language.ENGLISH) + "";
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }

        this.plugin.getLog().logInfo("[Chat] " + sender.getName() + " (Translated From " + this.plugin.getLog().capitalizeSingle(sLang.getName()) + "): " + msg);

        event.setResult(Chatter.Result.FAIL);
        return;
    }

}
