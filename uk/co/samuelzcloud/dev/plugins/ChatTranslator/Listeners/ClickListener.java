package uk.co.samuelzcloud.dev.plugins.ChatTranslator.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import uk.co.samuelzcloud.dev.plugins.ChatTranslator.ChatTranslator;
import uk.co.samuelzcloud.dev.plugins.ChatTranslator.Utilities.Language;

/**
 * Created by Samuel on 29/03/2015.
 * uk.co.samuelzcloud.dev.plugins.ChatTranslator.Listeners - ClickListener
 */
public class ClickListener implements Listener {

    private ChatTranslator plugin;

    public ClickListener(ChatTranslator plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        final Player player = (Player) event.getWhoClicked();

        if (!(this.plugin.getPlayersViewing().containsKey(player.getUniqueId()))) return;

        if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) return;

        if (!(this.plugin.getLog().strip(event.getInventory().getName()).equalsIgnoreCase("Choose your language...")) || !(this.plugin.getLog().strip(event.getInventory().getTitle()).equalsIgnoreCase("Choose your language..."))) return;

        event.setCancelled(true);

        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;

        if (!(event.getCurrentItem().getItemMeta().hasDisplayName())) return;

        ItemStack item = event.getCurrentItem();
        Material type = item.getType();

        // 0-45 Slot - Language Item
        // 52 Slot - Disable
        // 53 Slot - Close Menu

        if (event.getSlot() == event.getRawSlot()) {
            int slot = event.getRawSlot();

            if (slot == this.plugin.getPlayersViewing().get(player.getUniqueId()).getSize() - 1 && this.plugin.getLog().strip(item.getItemMeta().getDisplayName()).equalsIgnoreCase("Close Menu")) {
                // Close Menu
                this.plugin.getLog().sendFormattedMessage(player, "&bYou can always change your preferred language later!");

            } else if (slot == this.plugin.getPlayersViewing().get(player.getUniqueId()).getSize() - 2 && this.plugin.getLog().strip(item.getItemMeta().getDisplayName()).equalsIgnoreCase("Disable")) {
                // Set to None.
                this.plugin.getLog().sendFormattedMessage(player, "&bYou can always check again for your language later!");
                this.plugin.getPlayers().setLanguage(player.getUniqueId(), "DISABLED");

            } else if (type == Material.ENCHANTED_BOOK) {
                Language lang = Language.valueOf(this.plugin.getLog().strip(item.getItemMeta().getDisplayName().toUpperCase()));

                this.plugin.getLog().sendFormattedMessage(player, "&aYou have successfully selected &6" + lang.getName() + " &a(&6" + lang.getCode().toUpperCase() + "&a).");

                if (this.plugin.getConfig().getBoolean("LogMessages")) {
                    this.plugin.getLog().logInfo(player.getDisplayName() + " has chosen " + lang.getName() + " (" + lang.getCode().toUpperCase() + ") as their language.");
                }

                this.plugin.getPlayers().setLanguage(player.getUniqueId(), lang.getName().toUpperCase());
            }

            player.closeInventory();
            this.plugin.getPlayersViewing().remove(player.getUniqueId());
        }
        return;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClose(InventoryCloseEvent event) {
        this.plugin.getPlayersViewing().remove(event.getPlayer().getUniqueId());
    }

}
