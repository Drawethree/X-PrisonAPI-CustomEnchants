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

public final class XPrisonAPITest extends JavaPlugin {

    private static XPrisonAPITest INSTANCE;
    private XPrisonAPI api;

    private List<XPrisonEnchantment> customEnchants;

    public static XPrisonAPITest getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void onEnable() {
        INSTANCE = this;
        api = XPrisonAPI.getInstance();

        copyDefaultEnchantConfigurations();

        initEnchants();
        loadEnchants();
        registerEnchants();
    }

    private void initEnchants() {
        customEnchants = new ArrayList<>();

        File diamondGiverConfigFile = new File(getDataFolder(), "diamondgiver.json");
        File invisibilityConfigFile = new File(getDataFolder(), "invisibility.json");
        customEnchants.add(new DiamondGiverEnchant(diamondGiverConfigFile));
        customEnchants.add(new InvisibilityEnchant(invisibilityConfigFile));
    }

    private void loadEnchants() {
        customEnchants.forEach(XPrisonEnchantment::load);
    }

    private void registerEnchants() {
        customEnchants.forEach(enchantment -> api.getEnchantsApi().registerEnchant(enchantment));
    }

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

    @Override
    public void onDisable() {
        customEnchants.forEach(enchantment -> api.getEnchantsApi().unregisterEnchant(enchantment));
    }

    public XPrisonAPI getApi() {
        return api;
    }
}
