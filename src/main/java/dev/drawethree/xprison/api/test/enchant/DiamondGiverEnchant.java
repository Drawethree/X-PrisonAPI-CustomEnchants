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

/**
 * A custom enchantment that gives players a diamond when they break a block.
 * The enchantment is chance-based and scales with the enchantment level.
 * <p>
 * This class extends {@link XPrisonEnchantmentBase} and implements both
 * {@link BlockBreakEnchant} and {@link ChanceBasedEnchant}.
 */
public class DiamondGiverEnchant extends XPrisonEnchantmentBase implements BlockBreakEnchant, ChanceBasedEnchant {

    /**
     * The base chance of the enchantment triggering, per level.
     */
    private double baseChance;

    /**
     * Constructs a new DiamondGiverEnchant using a {@link File} instance for configuration.
     *
     * @param configFile The JSON configuration file.
     */
    public DiamondGiverEnchant(File configFile) {
        super(configFile);
    }

    /**
     * Called when a block is broken by a player.
     * If triggered, gives the player one diamond and sends a message.
     *
     * @param blockBreakEvent The block break event.
     * @param enchantLevel    The level of the enchantment on the item.
     */
    @Override
    public void onBlockBreak(BlockBreakEvent blockBreakEvent, int enchantLevel) {
        Player player = blockBreakEvent.getPlayer();
        player.getInventory().addItem(new ItemStack(Material.DIAMOND, 1));
        player.sendMessage("Olala! You got a diamond!");
    }

    /**
     * Loads custom properties from the JSON config.
     * Specifically, this reads the {@code baseChance} property.
     *
     * @param jsonObject The JSON object containing the configuration.
     */
    @Override
    protected void loadCustomProperties(JsonObject jsonObject) {
        baseChance = jsonObject.get("baseChance").getAsDouble();
    }

    /**
     * Returns the author of this enchantment.
     *
     * @return The author's name.
     */
    @Override
    public String getAuthor() {
        return "ExampleDev";
    }

    /**
     * Called when the enchantment is unloaded.
     * No specific cleanup is required in this implementation.
     */
    @Override
    public void unload() {
        // No custom unload logic needed
    }

    /**
     * Calculates the chance that this enchantment will trigger.
     * The chance scales linearly with the enchantment level.
     *
     * @param level The level of the enchantment.
     * @return The calculated trigger chance.
     */
    @Override
    public double getChanceToTrigger(int level) {
        return baseChance * level;
    }
}
