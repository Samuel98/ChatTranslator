package uk.co.samuelzcloud.dev.plugins.ChatTranslator;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import uk.co.samuelzcloud.dev.plugins.ChatTranslator.Executors.CTExecutor;
import uk.co.samuelzcloud.dev.plugins.ChatTranslator.Executors.LExecutor;
import uk.co.samuelzcloud.dev.plugins.ChatTranslator.Listeners.ChatListener;
import uk.co.samuelzcloud.dev.plugins.ChatTranslator.Listeners.ClickListener;
import uk.co.samuelzcloud.dev.plugins.ChatTranslator.Listeners.JoinListener;
import uk.co.samuelzcloud.dev.plugins.ChatTranslator.Utilities.CustomLogger;
import uk.co.samuelzcloud.dev.plugins.ChatTranslator.Utilities.Languages;
import uk.co.samuelzcloud.dev.plugins.ChatTranslator.Utilities.Players;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Samuel on 29/03/2015.
 * uk.co.samuelzcloud.dev.plugins.ChatTranslator - ChatTranslator
 */
public class ChatTranslator extends JavaPlugin {

    private Server server;
    private PluginManager manager;
    private BukkitScheduler scheduler;

    private CustomLogger log;
    private Players players;

    private File pluginFolder, playersFile, messagesFile;

    private HashMap<UUID, Inventory> playersViewing;

    @Override
    public void onEnable() {
        // Copy the default configuration file to plugins folder and save it.
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        // Initiate the pluginFolder, playersFile and messagesFile.
        (this.pluginFolder = new File(this.getDataFolder().getAbsolutePath())).mkdirs();
        this.playersFile = new File(this.pluginFolder, "players.yml");
        this.messagesFile = new File(this.pluginFolder, "messages.yml");

        // Initiate the Server and Plugin Manager variables.
        this.server = this.getServer();
        this.manager = this.getServer().getPluginManager();
        this.scheduler = this.getServer().getScheduler();

        // Initiate the Custom Logger and Players variables.
        this.log = new CustomLogger(this);
        this.players = new Players(this);

        // Initiate the players viewing hash map.
        this.playersViewing = new HashMap<>();

        // Define the ChatTranslator command executor.
        this.getCommand("ChatTranslator").setExecutor(new CTExecutor(this));
        this.getCommand("Language").setExecutor(new LExecutor(this));

        // Register the Listeners.
        this.getManager().registerEvents(new ClickListener(this), this);
        this.getManager().registerEvents(new JoinListener(this), this);
        this.getManager().registerEvents(new ChatListener(this), this);

    }

    @Override
    public void onDisable() {
        this.getPlayersViewing().clear();
    }

    /** Getters **/
    // Method to get the Server variable.
    public Server getPluginServer() {
        return this.server;
    }

    // Method to get the Plugin Manager variable.
    public PluginManager getManager() {
        return this.manager;
    }

    // Method to get the Bukkit Scheduler variable.
    public BukkitScheduler getScheduler() {
        return this.scheduler;
    }

    // Method to get the Custom Logger variable.
    public CustomLogger getLog() {
        return this.log;
    }

    // Method to get the Players variable.
    public Players getPlayers() {
        return this.players;
    }

    // Method to get the Players File variable.
    public File getPlayersFile() {
        return this.playersFile;
    }

    // Method to get the Messages Files variable.
    public File getMessagesFile() {
        return this.messagesFile;
    }

    // Method to get the players viewing hash map.
    public HashMap<UUID, Inventory> getPlayersViewing() {
        return this.playersViewing;
    }

    /** Methods **/
    // Method to return a created item stack from the provided material (using amount, data and item meta provided)
    public ItemStack createItem(Material type, int amount, int data, String displayName, String[] lore) {
        ItemStack item  = new ItemStack(type, amount, (short) data);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(this.getLog().colour(displayName));
        meta.setLore(this.getLog().colourList(Arrays.asList(lore)));

        item.setItemMeta(meta);
        return item;
    }

    // Method which creates the first inventory set.
    public Inventory createSet1(Inventory inv) {
        inv.clear();

        for (int i = 0; i < 45; i++) {
            inv.setItem(i, this.createItem(Material.ENCHANTED_BOOK, 1, 0, "&b" + Languages.values()[i].getLangName(), new String[]{"&fIs this your preferred language?", "&fClick to change to it."}));
        }

        inv.setItem(52, this.createItem(Material.BLAZE_ROD, 1, 0, "&bNext Set", new String[]{ "&fIs your preferred language not here?", "&fThen try the next set of languages!" }));
        inv.setItem(53, this.createItem(Material.CARROT_STICK, 1, 0, "&bClose Menu", new String[]{ "&fCan't decide what language to use?", "&fNever mind. You can change it later!" }));
        return inv;
    }

    // Method which creates the second inventory set.
    public Inventory createSet2(Inventory inv) {
        inv.clear();

        for (int i = 0; i < 38; i++) {
            inv.setItem(i, this.createItem(Material.ENCHANTED_BOOK, 1, 0, "&b" + Languages.values()[i+45].getLangName(), new String[]{"&fIs this your preferred language?", "&fClick to change to it."}));
        }

        inv.setItem(52, this.createItem(Material.BLAZE_ROD, 1, 0, "&bPrevious Set", new String[]{ "&fIs your preferred language not here?", "&fThen try the previous set of languages!" }));
        inv.setItem(53, this.createItem(Material.CARROT_STICK, 1, 0, "&bClose Menu", new String[]{ "&fCan't decide what language to use?", "&fNever mind. You can change it later!" }));
        return inv;
    }

}
