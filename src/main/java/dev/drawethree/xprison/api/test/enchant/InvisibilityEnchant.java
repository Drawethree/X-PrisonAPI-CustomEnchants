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

public class InvisibilityEnchant extends XPrisonEnchantmentBase implements EquipabbleEnchantment {

    public InvisibilityEnchant(String filePath) {
        super(new File(filePath));
    }

    public InvisibilityEnchant(File configFile) {
        super(configFile);
    }

    @Override
    public void onEquip(Player player, ItemStack itemStack, int i) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,-1,1));
        player.sendMessage(ChatColor.GREEN + "You are now Invisible");
    }

    @Override
    public void onUnequip(Player player, ItemStack itemStack, int i) {
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
        player.sendMessage(ChatColor.RED + "You are now Visible");
    }

    @Override
    protected void loadCustomProperties(JsonObject jsonObject) {

    }

    @Override
    public String getAuthor() {
        return "ExampleDev";
    }

    @Override
    public void unload() {

    }
}