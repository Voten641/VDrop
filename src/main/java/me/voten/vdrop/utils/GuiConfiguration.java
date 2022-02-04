package me.voten.vdrop.utils;

import me.voten.vdrop.Main;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Map;

import static me.voten.vdrop.Main.itms;

public class GuiConfiguration {
    public static Inventory inv(){
        Inventory inv = Bukkit.createInventory(null, 18, "§aDrop gui configuration");
        inv.setItem(0, itms(XMaterial.valueOf(Main.config.getStringList("enable-all").get(1).toUpperCase()).parseItem(), Main.color(Main.config.getStringList("enable-all").get(0)), Arrays.asList("§7Right click to change name")));
        inv.setItem(1, itms(XMaterial.valueOf(Main.config.getStringList("disable-all").get(1).toUpperCase()).parseItem(), Main.color(Main.config.getStringList("disable-all").get(0)), Arrays.asList("§7Right click to change name")));
        inv.setItem(2, itms(XMaterial.valueOf(Main.config.getStringList("auto-sell").get(1).toUpperCase()).parseItem(), Main.color(Main.config.getStringList("auto-sell").get(0)), Arrays.asList("§7Right click to change name")));
        inv.setItem(3, itms(XMaterial.valueOf(Main.config.getStringList("drop-to-inv").get(1).toUpperCase()).parseItem(), Main.color(Main.config.getStringList("drop-to-inv").get(0)), Arrays.asList("§7Right click to change name")));

        inv.setItem(12, itms(XMaterial.RED_WOOL.parseItem(), "§aLore " + Main.color(Main.config.getString("enabled")), Arrays.asList("§7Right click to change name")));
        inv.setItem(13, itms(XMaterial.GREEN_WOOL.parseItem(), "§aLore " + Main.color(Main.config.getString("disabled")), Arrays.asList("§7Right click to change name")));
        inv.setItem(14, itms(XMaterial.OAK_SIGN.parseItem(), "§aTitle " + Main.color(Main.config.getString("title")), Arrays.asList("§7Right click to change name")));
        return inv;
    }
}
