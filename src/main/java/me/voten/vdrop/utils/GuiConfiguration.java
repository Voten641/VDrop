package me.voten.vdrop.utils;

import me.voten.vdrop.Main;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

import static me.voten.vdrop.Main.itms;

public class GuiConfiguration {
    public static Inventory inv(){
        Inventory inv = Bukkit.createInventory(null, 45, "§aDrop gui configuration");
        inv.setItem(0, itms(XMaterial.GREEN_WOOL.parseItem(), Main.color(Main.config.getString("enable-all")), Arrays.asList("§7Right click to change name")));
        inv.setItem(1, itms(XMaterial.RED_WOOL.parseItem(), Main.color(Main.config.getString("disable-all")), Arrays.asList("§7Right click to change name")));
        inv.setItem(2, itms(XMaterial.ITEM_FRAME.parseItem(), Main.color(Main.config.getString("auto-sell")), Arrays.asList("§7Right click to change name")));
        inv.setItem(3, itms(XMaterial.CHEST.parseItem(), Main.color(Main.config.getString("drop-to-inv")), Arrays.asList("§7Right click to change name")));

        inv.setItem(36, itms(XMaterial.RED_WOOL.parseItem(), "§aLore " + Main.color(Main.config.getString("enabled")), Arrays.asList("§7Right click to change name")));
        inv.setItem(37, itms(XMaterial.GREEN_WOOL.parseItem(), "§aLore " + Main.color(Main.config.getString("disabled")), Arrays.asList("§7Right click to change name")));
        inv.setItem(38, itms(XMaterial.OAK_SIGN.parseItem(), "§aTitle " + Main.color(Main.config.getString("title")), Arrays.asList("§7Right click to change name")));
        return inv;
    }
}
