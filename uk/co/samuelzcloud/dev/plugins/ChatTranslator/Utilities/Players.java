package uk.co.samuelzcloud.dev.plugins.ChatTranslator.Utilities;

import org.bukkit.configuration.file.YamlConfiguration;
import uk.co.samuelzcloud.dev.plugins.ChatTranslator.ChatTranslator;

import java.io.File;
import java.util.UUID;

/**
 * Created by Samuel on 29/03/2015.
 * uk.co.samuelzcloud.dev.plugins.ChatTranslator.Utilities - Players
 */
public class Players {

     /*
    -- Setup like this --
    10f0461f-f42b-4a54-b5d4-504d5709ad46:
        Joins: 1
        Language: ENGLISH
    85c3f6d8-e79f-480c-a647-98e026bdac5c:
        Joins: 2
        Language: GERMAN
     */

    private ChatTranslator plugin;
    private File playersFile;
    private YamlConfiguration players;

    public Players(ChatTranslator plugin) {
        this.plugin = plugin;
        this.playersFile = this.plugin.getPlayersFile();
        this.players = new YamlConfiguration();

        updateFile();
    }

    /** Methods **/
    private void updateFile() {
        if (this.playersFile.exists()) {
            load();
        } else {
            save();
        }

        // TESTING
        //this.setupPlayer(UUID.fromString("10f0461f-f42b-4a54-b5d4-504d5709ad46"));
        //save();
    }

    public void save() {
        try {
            this.players.save(this.playersFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            this.players.load(this.playersFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean contains(String path) {
        return this.players.contains(path);
    }

    public Integer getJoins(UUID uuid) {
        if (!this.contains(uuid.toString())) {
            return -1;
        }

        return this.players.getInt(uuid.toString() + ".Joins");
    }

    public String getLanguage(UUID uuid) {
        if (!this.contains(uuid.toString())) {
            return null;
        }

        return this.players.getString(uuid.toString() + ".Language").toUpperCase();
    }

    public void setJoins(UUID uuid, Integer value) {
        this.players.set(uuid.toString() + ".Joins", value);
        this.save();
    }

    public void setLanguage(UUID uuid, String value) {
        this.players.set(uuid.toString() + ".Language", value.toUpperCase());
        this.save();
    }

    public void setupPlayer(UUID uuid) {
        if (!this.contains(uuid.toString())) {
            this.setJoins(uuid, 0);
            this.setLanguage(uuid, this.plugin.getConfig().getString("DefaultLanguage").toUpperCase());
            this.save();
        }
    }

}
