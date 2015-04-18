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
import uk.co.samuelzcloud.dev.plugins.ChatTranslator.Tasks.ThreadTask;
import uk.co.samuelzcloud.dev.plugins.ChatTranslator.Utilities.*;

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
    private FTP ftp;

    private File pluginFolder, playersFile;

    private HashMap<UUID, Inventory> playersViewing;

    @Override
    public void onEnable() {
        if (!(new File(this.pluginFolder, "config.yml").exists())) {
            this.saveResource("config.yml", false);
        }

        // Initiate the pluginFolder, playersFile and messagesFile.
        (this.pluginFolder = new File(this.getDataFolder().getAbsolutePath())).mkdirs();
        this.playersFile = new File(this.pluginFolder, "players.yml");

        // Initiate the Server and Plugin Manager variables.
        this.server = this.getServer();
        this.manager = this.getServer().getPluginManager();
        this.scheduler = this.getServer().getScheduler();

        // Initiate the Custom Logger and Players variables.
        this.log = new CustomLogger(this);
        this.players = new Players(this);
        this.ftp = new FTP();

        // Initiate the players viewing hash map.
        this.playersViewing = new HashMap<>();

        if (this.getConfig().getString("API_Key").equalsIgnoreCase("NEED_TO_SET")) {
            this.getLog().logInfo("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
            this.getLog().logWarning("You need to obtain an API KEY.");
            this.getLog().logInfo("Check config file for more information.");
            this.getLog().logInfo("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

            this.getManager().disablePlugin(this);
            return;
        } else {
            Translate.setKey(this.getConfig().getString("API_Key"));
        }

        // Define the ChatTranslator command executor.
        this.getCommand("ChatTranslator").setExecutor(new CTExecutor(this));
        this.getCommand("Language").setExecutor(new LExecutor(this));

        // Register the Listeners.
        this.getManager().registerEvents(new ClickListener(this), this);
        this.getManager().registerEvents(new JoinListener(this), this);

        if (this.getManager().getPlugin("Herochat") != null && this.getManager().getPlugin("Vault") != null) {
            this.getManager().registerEvents(new ChatListener(this), this);
        }

        // Check dependencies
        if (this.getManager().getPlugin("Herochat") == null || this.getManager().getPlugin("Vault") == null) {
            this.getLog().logInfo("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
            this.getLog().logWarning("Herochat or Vault dependency not found, please install it.");
            this.getLog().logInfo("ChatTranslator will be disabled until such times.");
            this.getLog().logInfo("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

            this.getManager().disablePlugin(this);
            return;
        }
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
    public FTP getFTP() {
        return this.ftp;
    }

    // Method to get the Players File variable.
    public File getPlayersFile() {
        return this.playersFile;
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

    // Method which creates the inventory set.
    public Inventory createSet(Inventory inv) {
        inv.clear();

        for (int i = 0; i < Language.values().length-1; i++) {
            if (!Language.values()[i].getName().equalsIgnoreCase("DISABLED")) {
                inv.setItem(i, this.createItem(Material.ENCHANTED_BOOK, 1, 0, "&b" + Language.values()[i].getName(), new String[]{"&bAliases: " + Language.values()[i].getCode().toUpperCase(), "&fIs this your preferred language?", "&fClick to change to it."}));
            }
        }

        inv.setItem(52, this.createItem(Material.BLAZE_ROD, 1, 0, "&bDisable", new String[]{ "&fIs your preferred language not here?", "&fThen don\'t worry, you do not have to use me!" }));
        inv.setItem(53, this.createItem(Material.CARROT_STICK, 1, 0, "&bClose Menu", new String[]{ "&fCan't decide what language to use?", "&fNever mind. You can change it later!" }));
        return inv;
    }

}
