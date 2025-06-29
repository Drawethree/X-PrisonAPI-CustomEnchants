package dev.drawethree.xprison.api.test.enchant;

import com.google.gson.JsonObject;
import dev.drawethree.xprison.api.enchants.model.BlockBreakEnchant;
import dev.drawethree.xprison.api.enchants.model.ChanceBasedEnchant;
import dev.drawethree.xprison.api.enchants.model.XPrisonEnchantmentBase;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;

public class DiamondGiverEnchant extends XPrisonEnchantmentBase implements BlockBreakEnchant, ChanceBasedEnchant {

    private double baseChance;

    public DiamondGiverEnchant(String filePath) {
        super(new File(filePath));
    }

    public DiamondGiverEnchant(File configFile) {
        super(configFile);
    }

    @Override
    public void onBlockBreak(BlockBreakEvent blockBreakEvent, int enchantLevel) {
        Player player = blockBreakEvent.getPlayer();
        player.getInventory().addItem(new ItemStack(Material.DIAMOND,1));
        player.sendMessage("Olala! You got a diamond!");
    }

    @Override
    protected void loadCustomProperties(JsonObject jsonObject) {
        baseChance = jsonObject.get("baseChance").getAsDouble();
    }

    @Override
    public String getAuthor() {
        return "ExampleDev";
    }

    @Override
    public void unload() {

    }

    @Override
    public double getChanceToTrigger(int level) {
        return baseChance * level;
    }
}