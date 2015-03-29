package uk.co.samuelzcloud.dev.plugins.ChatTranslator.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import uk.co.samuelzcloud.dev.plugins.ChatTranslator.ChatTranslator;
import uk.co.samuelzcloud.dev.plugins.ChatTranslator.Utilities.Languages;

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
        // 52 Slot - Previous/Next Set
        // 53 Slot - Close Menu

        if (event.getSlot() == event.getRawSlot()) {
            int slot = event.getRawSlot();

            if (slot == this.plugin.getPlayersViewing().get(player.getUniqueId()).getSize() - 1 && this.plugin.getLog().strip(item.getItemMeta().getDisplayName()).equalsIgnoreCase("Close Menu")) {
                // Close Menu
                this.plugin.getLog().sendFormattedMessage(player, "&bYou can always change your preferred language later!");
                player.closeInventory();

            } else if (slot == this.plugin.getPlayersViewing().get(player.getUniqueId()).getSize() - 2 && this.plugin.getLog().strip(item.getItemMeta().getDisplayName()).equalsIgnoreCase("Next Set")) {
                // Next Set
                final Inventory inv = this.plugin.getPlayersViewing().get(player.getUniqueId());
                inv.setContents(this.plugin.createSet2(inv).getContents());

                player.closeInventory();
                this.plugin.getPlayersViewing().remove(player.getUniqueId());

                this.plugin.getScheduler().runTaskLater(this.plugin, new Runnable() {
                    @Override
                    public void run() {
                        plugin.getPlayersViewing().put(player.getUniqueId(), inv);
                        player.openInventory(inv);
                    }
                }, 2L);

            } else if (slot == this.plugin.getPlayersViewing().get(player.getUniqueId()).getSize() - 2 && this.plugin.getLog().strip(item.getItemMeta().getDisplayName()).equalsIgnoreCase("Previous Set")) {
                // Previous Set
                final Inventory inv = this.plugin.getPlayersViewing().get(player.getUniqueId());
                inv.setContents(this.plugin.createSet1(inv).getContents());

                player.closeInventory();
                this.plugin.getPlayersViewing().remove(player.getUniqueId());

                this.plugin.getScheduler().runTaskLater(this.plugin, new Runnable() {
                    @Override
                    public void run() {
                        plugin.getPlayersViewing().put(player.getUniqueId(), inv);
                        player.openInventory(inv);
                    }
                }, 2L);

            } else if (type == Material.ENCHANTED_BOOK) {
                this.plugin.getLog().sendFormattedMessage(player, "&a" + Languages.valueOf(this.plugin.getLog().strip(item.getItemMeta().getDisplayName().toUpperCase())).getLangName() + " Selected..");
                this.plugin.getPlayers().setLanguage(player.getUniqueId(), Languages.valueOf(this.plugin.getLog().strip(item.getItemMeta().getDisplayName().toUpperCase())).getLangName());

                // TODO: Handle Language Item.
                //this.plugin.log.sendFormattedMessage(player, "&cHandle the Language Translation Change.");
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
