package dev.drawethree.xprison.api.test.enchant;

import com.google.gson.JsonObject;
import dev.drawethree.xprison.api.enchants.model.EquipabbleEnchantment;
import dev.drawethree.xprison.api.enchants.model.XPrisonEnchantmentBase;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;

/**
 * A custom enchantment that grants the player invisibility when equipped,
 * and removes it when unequipped.
 * <p>
 * This class extends {@link XPrisonEnchantmentBase} and implements
 * {@link EquipabbleEnchantment}, meaning it activates effects on item equip/unequip.
 */
public class InvisibilityEnchant extends XPrisonEnchantmentBase implements EquipabbleEnchantment {

    /**
     * Constructs a new {@code InvisibilityEnchant} from a {@link File} configuration.
     *
     * @param configFile JSON configuration file.
     */
    public InvisibilityEnchant(File configFile) {
        super(configFile);
    }

    /**
     * Called when a player equips an item with this enchantment.
     * Grants the player permanent invisibility while the item is equipped.
     *
     * @param player    The player who equipped the item.
     * @param itemStack The item that was equipped.
     * @param i         The level of the enchantment (unused in this case).
     */
    @Override
    public void onEquip(Player player, ItemStack itemStack, int i) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, -1, 1));
        player.sendMessage(ChatColor.GREEN + "You are now Invisible");
    }

    /**
     * Called when a player unequips an item with this enchantment.
     * Removes the invisibility effect from the player.
     *
     * @param player    The player who unequipped the item.
     * @param itemStack The item that was unequipped.
     * @param i         The level of the enchantment (unused in this case).
     */
    @Override
    public void onUnequip(Player player, ItemStack itemStack, int i) {
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
        player.sendMessage(ChatColor.RED + "You are now Visible");
    }

    /**
     * Loads custom properties from the configuration file.
     * This enchantment does not use custom config properties.
     *
     * @param jsonObject The JSON configuration object.
     */
    @Override
    protected void loadCustomProperties(JsonObject jsonObject) {
        // No custom properties to load
    }

    /**
     * Returns the author's name for this enchantment.
     *
     * @return The author's name.
     */
    @Override
    public String getAuthor() {
        return "ExampleDev";
    }

    /**
     * Unloads the enchantment.
     * No cleanup logic required in this implementation.
     */
    @Override
    public void unload() {
        // No specific unload logic needed
    }
}
