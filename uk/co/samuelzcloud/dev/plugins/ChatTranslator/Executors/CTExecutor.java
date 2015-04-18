package uk.co.samuelzcloud.dev.plugins.ChatTranslator.Executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import uk.co.samuelzcloud.dev.plugins.ChatTranslator.ChatTranslator;
import uk.co.samuelzcloud.dev.plugins.ChatTranslator.Tasks.ThreadTask;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Samuel on 29/03/2015.
 * uk.co.samuelzcloud.dev.plugins.ChatTranslator.Listeners - CTExecutor
 */
public class CTExecutor implements CommandExecutor {

    private ChatTranslator plugin;
    private List<String> actions;

    public CTExecutor(ChatTranslator plugin) {
        this.plugin = plugin;
        this.actions = Arrays.asList("LOAD", "SAVE", "INFO");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check to make sure the sender provided an action.
        if (args.length == 0) {
            this.plugin.getLog().sendFormattedMessage(sender, "&cYou must provide an action.");
            this.plugin.getLog().sendFormattedMessage(sender, "&3Actions: &6" + this.plugin.getLog().formatList(true, this.actions));
            return true;
        }

        // Get the action which was provided and trim it, then convert it to uppercase.
        String action = args[0].trim().toUpperCase();

        // Check the action provided is valid.
        if (!(this.actions.contains(action))) {
            this.plugin.getLog().sendFormattedMessage(sender, "&cThe action you provided was invalid.");
            this.plugin.getLog().sendFormattedMessage(sender, "&3Actions: &6" + this.plugin.getLog().formatList(true, this.actions));
            return true;
        }

        // Check if the provided action is INFO.
        if (action.equalsIgnoreCase("INFO")) {
            // Check if the sender has the correct permission.
            if (!(this.plugin.getLog().checkPermission(sender, "ChatTranslator.Info"))) {
                this.plugin.getLog().sendFormattedMessage(sender, this.plugin.getConfig().getString("NoPermission"));
                return true;
            }

            // Send the sender the information about plugin.
            this.plugin.getLog().sendPlainMessage(sender, "&3" + this.plugin.getLog().fullLine());
            this.plugin.getLog().sendPlainMessage(sender, "&3-- &6" + this.plugin.getLog().getInfo("Name") + " Information");
            this.plugin.getLog().sendPlainMessage(sender, "&3" + this.plugin.getLog().fullLine());

            this.plugin.getLog().sendPlainMessage(sender, "&3-- &b\u25CF &eVersion: &7" + this.plugin.getLog().getInfo("Version"));
            this.plugin.getLog().sendPlainMessage(sender, "&3-- &b\u25CF &eAuthors: &7" + this.plugin.getLog().getInfo("Author"));
            this.plugin.getLog().sendPlainMessage(sender, "&3-- &b\u25CF &eWebsite: &7" + this.plugin.getLog().getInfo("Website"));
            String desc = this.plugin.getLog().getInfo("Description");
            this.plugin.getLog().sendPlainMessage(sender, "&3-- &b\u25CF &eDescription: &7" + desc.substring(0, (desc.length() > 33) ? 33 : desc.length()) + "...");
            this.plugin.getLog().sendPlainMessage(sender, "&3" + this.plugin.getLog().fullLine());

            // Check to see if logging messages is enabled.
            if (this.plugin.getConfig().getBoolean("LogMessages")) {
                // Check to see if the sender is a player.
                if (sender instanceof Player) {
                    this.plugin.getLog().logInfo(sender.getName() + " (" + ((Player) sender).getUniqueId().toString() + ") just viewed the plugin information.");
                } else {
                    this.plugin.getLog().logInfo(sender.getName() + " just viewed the plugin information.");
                }
            }
            return true;
        }

        // Check if the provided action is SAVE.
        if (action.equalsIgnoreCase("SAVE")) {
            // Check if the sender has the correct permission.
            if (!(this.plugin.getLog().checkPermission(sender, "ChatTranslator.Save"))) {
                this.plugin.getLog().sendFormattedMessage(sender, this.plugin.getConfig().getString("NoPermission"));
                return true;
            }

            try {
                this.plugin.getConfig().save(new File(this.plugin.getDataFolder().getAbsolutePath(), "config.yml"));
            } catch (IOException e) {
                this.plugin.getLog().sendFormattedMessage(sender, "&cAn error occurred saving the config files.");
                //e.printStackTrace();
                return true;
            }
            this.plugin.getPlayers().save();

            // Send the sender a message confirming the files have been saved.
            this.plugin.getLog().sendFormattedMessage(sender, "&aAll files were forcefully saved.");

            // Check to see if logging messages is enabled.
            if (this.plugin.getConfig().getBoolean("LogMessages")) {
                this.plugin.getLog().logInfo(sender.getName() + " has just forced a save of all files.");
            }

            return true;
        }

        // Check if the provided action is LOAD.
        if (action.equalsIgnoreCase("LOAD")) {
            // Check if the sender has the correct permission.
            if (!(this.plugin.getLog().checkPermission(sender, "ChatTranslator.Load"))) {
                this.plugin.getLog().sendFormattedMessage(sender, this.plugin.getConfig().getString("NoPermission"));
                return true;
            }

            try {
                this.plugin.getConfig().load(new File(this.plugin.getDataFolder().getAbsolutePath(), "config.yml"));
            } catch (IOException e) {
                this.plugin.getLog().sendFormattedMessage(sender, "&cAn error occurred loading the config files.");
                //e.printStackTrace();
                return true;
            } catch (InvalidConfigurationException e) {
                this.plugin.getLog().sendFormattedMessage(sender, "&cAn error occurred loading the config files.");
                //e.printStackTrace();
                return true;
            }
            this.plugin.getPlayers().load();

            // Send the sender a message confirming the files have been loaded.
            this.plugin.getLog().sendFormattedMessage(sender, "&aAll files were forcefully loaded.");

            // Check to see if logging messages is enabled.
            if (this.plugin.getConfig().getBoolean("LogMessages")) {
                this.plugin.getLog().logInfo(sender.getName() + " has just forced a load of all files.");
            }

            return true;
        }

        return true;
    }

}
