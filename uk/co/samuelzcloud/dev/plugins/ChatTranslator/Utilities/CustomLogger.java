package uk.co.samuelzcloud.dev.plugins.ChatTranslator.Utilities;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import uk.co.samuelzcloud.dev.plugins.ChatTranslator.ChatTranslator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Samuel on 29/03/2015.
 * uk.co.samuelzcloud.dev.plugins.ChatTranslator.Utilities - CustomLogger
 */
public class CustomLogger {

    private ChatTranslator plugin;
    private Logger log;
    private PluginDescriptionFile pdFile;

    public CustomLogger(ChatTranslator plugin) {
        this.plugin = plugin;
        this.log = Logger.getLogger("Minecraft");
        this.pdFile = this.plugin.getDescription();
    }

    public String colour(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public String strip(String str) {
        return ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', str));
    }

    public String format(Boolean useColours, String str) {
        StringBuilder builder = new StringBuilder();

        builder.append(ChatColor.GRAY).append("[");
        builder.append(ChatColor.DARK_AQUA).append(this.getInfo("Name"));
        builder.append(ChatColor.GRAY).append("] ");
        builder.append(ChatColor.GREEN).append(str);

        return (useColours) ? (this.colour(builder.toString())) : (this.strip(builder.toString()));
    }

    public String getInfo(String key) {
        switch (key.toUpperCase()) {
            default:
            case "NAME":
                return (this.pdFile.getName() != null) ? this.pdFile.getName() : "This has not been defined";
            case "VERSION":
                return (this.pdFile.getVersion() != null) ? this.pdFile.getVersion() : "This has not been defined";
            case "DESCRIPTION":
                return (this.pdFile.getDescription() != null) ? this.pdFile.getDescription() : "This has not been defined";
            case "WEBSITE":
                return (this.pdFile.getWebsite() != null) ? this.pdFile.getWebsite() : "This has not been defined";
            case "AUTHOR":
                return (this.pdFile.getAuthors() != null) ? this.pdFile.getAuthors().toString().substring(1, this.pdFile.getAuthors().toString().length() - 1) : "This has not been defined";
        }
    }

    public void logInfo(String str) {
        this.log.log(Level.INFO, this.format(false, this.strip(str)));
    }

    public void logSevere(String str) {
        this.log.log(Level.SEVERE, this.format(false, this.strip(str)));
    }

    public void logWarning(String str) {
        this.log.log(Level.WARNING, this.format(false, this.strip(str)));
    }

    public void sendFormattedMessage(CommandSender sender, String str) {
        sender.sendMessage(this.format(true, this.colour(str)));
    }

    public void sendPlainMessage(CommandSender sender, String str) {
        sender.sendMessage(this.colour(str));
    }

    public String formatList(Boolean useColours, List<String> list) {
        StringBuilder builder = new StringBuilder();

        for (String str : list) {
            builder.append("&b\u25CF &e" + this.capitalizeSingle(str) + " ");
        }

        return (useColours) ? (this.colour(builder.toString().trim())) : (this.strip(builder.toString().trim()));
    }

    public List<String> colourList(List<String> list) {
        List<String> coloured = new ArrayList<>();

        for (String str : list) {
            coloured.add(this.colour(str));
        }

        return coloured;
    }

    public String capitalizeSingle(String message) {
        return Character.toUpperCase(message.charAt(0)) + message.substring(1).toLowerCase();
    }

    public void broadcast(List<UUID> excludedPlayers, String str, String permission) {
        for (Player player : this.plugin.getServer().getOnlinePlayers()) {
            if (!excludedPlayers.contains(player.getUniqueId())) {
                if (permission == null || permission.equals(" ")) {
                    this.sendFormattedMessage(player, str);
                } else {
                    if (this.checkPermission(player, permission)) {
                        this.sendFormattedMessage(player, str);
                    }
                }
            }
        }
    }

    public Boolean checkPermission(CommandSender sender, String permission) {
        if (sender.hasPermission(permission)) {
            return true;
        }

        return false;
    }

    public String fullLine() {
        int len = 52;
        String line = "";

        for (int i = 0; i < len; i++) {
            line += "-";
        }
        return line;
    }

    public String halfLine() {
        int len = 26;
        String line = "";

        for (int i = 0; i < len; i++) {
            line += "-";
        }
        return line;
    }

    public String quarterLine() {
        int len = 13;
        String line = "";

        for (int i = 0; i < len; i++) {
            line += "-";
        }
        return line;
    }

}
