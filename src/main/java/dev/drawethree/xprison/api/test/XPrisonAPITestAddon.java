package dev.drawethree.xprison.api.test;

import dev.drawethree.xprison.api.XPrisonAPI;
import dev.drawethree.xprison.api.addons.XPrisonAddon;
import dev.drawethree.xprison.api.enchants.model.XPrisonEnchantment;
import dev.drawethree.xprison.api.test.enchant.DiamondGiverEnchant;
import dev.drawethree.xprison.api.test.enchant.InvisibilityEnchant;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/**
 * A test addon for the XPrisonAPI.
 * <p>
 * This addon demonstrates how to register and manage custom enchantments
 * using the XPrison enchantments API. On plugin enable, it loads configuration
 * files for the custom enchants, registers them with the XPrison API,
 * and unregisters them on plugin disable.
 *
 * IMPORTANT!
 * Jar file should be placed under plugins/X-Prison/addons
 */
public final class XPrisonAPITestAddon implements XPrisonAddon {

    private static XPrisonAPITestAddon INSTANCE;
    private XPrisonAPI api;
    private List<XPrisonEnchantment> customEnchants;
    private File dataFolder;

    /**
     * Gets the singleton instance of this plugin.
     *
     * @return the instance of XPrisonAPITest
     */
    public static XPrisonAPITestAddon getINSTANCE() {
        return INSTANCE;
    }

    /**
     * Called when the addon is enabled.
     * Initializes the API, copies default config files for enchantments,
     * initializes enchantment instances, loads their configuration,
     * and registers them with the API.
     */
    @Override
    public void onEnable() {
        INSTANCE = this;
        api = XPrisonAPI.getInstance();

        this.dataFolder = new File(Bukkit.getPluginManager().getPlugin("X-Prison").getDataFolder(), "addons-data/XPrisonAPITestAddon");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

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

        File diamondGiverConfigFile = new File(dataFolder, "diamondgiver.json");
        File invisibilityConfigFile = new File(dataFolder, "invisibility.json");

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
     * from the plugin's resources to the plugins/X-Prison/addon-data/XPrisonAPITestAddon
     * if they do not already exist.
     */
    private void copyDefaultEnchantConfigurations() {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        String[] enchantFiles = {"diamondgiver.json", "invisibility.json"};

        for (String fileName : enchantFiles) {
            File outFile = new File(dataFolder, fileName);
            URL resource = XPrisonAPITestAddon.class.getResource("/" + fileName);
            System.out.println(resource.toString());
            if (!outFile.exists()) {
                try (InputStream in = XPrisonAPITestAddon.class.getResourceAsStream("/" + fileName)) {
                    if (in != null) {
                        Files.copy(in, outFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        Bukkit.getLogger().info("[XPrisonAPITestAddon] Copied " + fileName);
                    } else {
                        Bukkit.getLogger().warning("[XPrisonAPITestAddon] Could not find " + fileName + " in resources!");
                    }
                } catch (IOException e) {
                    Bukkit.getLogger().warning("[XPrisonAPITestAddon] Failed to copy " + fileName + ": " + e.getMessage());
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
