package dev.drawethree.xprison.api.test;

import dev.drawethree.xprison.api.XPrisonAPI;
import dev.drawethree.xprison.api.enchants.model.XPrisonEnchantment;
import dev.drawethree.xprison.api.test.enchant.DiamondGiverEnchant;
import dev.drawethree.xprison.api.test.enchant.InvisibilityEnchant;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/**
 * A test plugin for the XPrisonAPI.
 * <p>
 * This plugin demonstrates how to register and manage custom enchantments
 * using the XPrison enchantments API. On plugin enable, it loads configuration
 * files for the custom enchants, registers them with the XPrison API,
 * and unregisters them on plugin disable.
 */
public final class XPrisonAPITest extends JavaPlugin {

    private static XPrisonAPITest INSTANCE;
    private XPrisonAPI api;
    private List<XPrisonEnchantment> customEnchants;

    /**
     * Gets the singleton instance of this plugin.
     *
     * @return the instance of XPrisonAPITest
     */
    public static XPrisonAPITest getINSTANCE() {
        return INSTANCE;
    }

    /**
     * Called when the plugin is enabled.
     * Initializes the API, copies default config files for enchantments,
     * initializes enchantment instances, loads their configuration,
     * and registers them with the API.
     */
    @Override
    public void onEnable() {
        INSTANCE = this;
        api = XPrisonAPI.getInstance();

        copyDefaultEnchantConfigurations();

        initEnchants();
        loadEnchants();
        registerEnchants();
    }

    /**
     * Initializes the custom enchantments and their configuration files.
     */
    private void initEnchants() {
        customEnchants = new ArrayList<>();

        File diamondGiverConfigFile = new File(getDataFolder(), "diamondgiver.json");
        File invisibilityConfigFile = new File(getDataFolder(), "invisibility.json");

        customEnchants.add(new DiamondGiverEnchant(diamondGiverConfigFile));
        customEnchants.add(new InvisibilityEnchant(invisibilityConfigFile));
    }

    /**
     * Loads each custom enchantment from its corresponding configuration file.
     */
    private void loadEnchants() {
        customEnchants.forEach(XPrisonEnchantment::load);
    }

    /**
     * Registers each custom enchantment with the XPrison enchantment API.
     */
    private void registerEnchants() {
        customEnchants.forEach(enchantment -> api.getEnchantsApi().registerEnchant(enchantment));
    }

    /**
     * Copies the default configuration files for the enchantments
     * from the plugin's resources to the plugin data folder,
     * if they do not already exist.
     */
    private void copyDefaultEnchantConfigurations() {
        File pluginFolder = this.getDataFolder();
        if (!pluginFolder.exists()) {
            pluginFolder.mkdirs();
        }

        String[] enchantFiles = {"diamondgiver.json", "invisibility.json"};

        for (String fileName : enchantFiles) {
            File outFile = new File(pluginFolder, fileName);
            if (!outFile.exists()) {
                try (InputStream in = getResource(fileName)) {
                    if (in != null) {
                        Files.copy(in, outFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        getLogger().info("Copied default " + fileName);
                    } else {
                        getLogger().warning("Resource not found: " + fileName);
                    }
                } catch (IOException e) {
                    getLogger().warning("Failed to copy " + fileName + ": " + e.getMessage());
                }
            }
        }
    }

    /**
     * Called when the plugin is disabled.
     * Unregisters all custom enchantments from the XPrison API.
     */
    @Override
    public void onDisable() {
        customEnchants.forEach(enchantment -> api.getEnchantsApi().unregisterEnchant(enchantment));
    }

    /**
     * Gets the instance of the XPrison API used by this plugin.
     *
     * @return the XPrisonAPI instance
     */
    public XPrisonAPI getApi() {
        return api;
    }
}
