package uk.co.samuelzcloud.dev.plugins.ChatTranslator.Executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import uk.co.samuelzcloud.dev.plugins.ChatTranslator.ChatTranslator;

/**
 * Created by Samuel on 29/03/2015.
 * uk.co.samuelzcloud.dev.plugins.ChatTranslator.Executors - LExecutor
 */
public class LExecutor implements CommandExecutor {

    private ChatTranslator plugin;

    public LExecutor(ChatTranslator plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            this.plugin.getLog().sendFormattedMessage(sender, "&cThat command can only be executed in game.");
            return true;
        }

        Player player = (Player) sender;

        if (!(this.plugin.getLog().checkPermission(player, "ChatTranslator.Language"))) {
            this.plugin.getLog().sendFormattedMessage(player, this.plugin.getConfig().getString("NoPermission"));
            return true;
        }

        if (this.plugin.getPlayersViewing().containsKey(player.getUniqueId())) {
            player.closeInventory();
            this.plugin.getPlayersViewing().remove(player.getUniqueId());
        }

        Inventory inv = this.plugin.getPluginServer().createInventory(player, 54, this.plugin.getLog().colour("&bChoose your language..."));

        // Add items.
        inv.setContents(this.plugin.createSet1(inv).getContents());

        player.openInventory(inv);
        this.plugin.getPlayersViewing().put(player.getUniqueId(), inv);

        this.plugin.getLog().sendFormattedMessage(player, "&bOpening...");

        return true;
    }

}
