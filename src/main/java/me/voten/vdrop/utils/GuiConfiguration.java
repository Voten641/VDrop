package me.voten.vdrop.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import static me.voten.vdrop.Main.itms;

public class GuiConfiguration {
    public static Inventory inv(Player p){
        Inventory inv = Bukkit.createInventory(null, 36, "§aDrop gui configuration");
        inv.setItem(0, itms(Material.GREEN_WOOL, "§aDrag and drop"));
        inv.setItem(1, itms(Material.RED_WOOL, "§aDrag and drop"));
        if(p.hasPermission("vdrop.autosell"))
            inv.setItem(2, itms(Material.ITEM_FRAME, "§aDrag and drop"));
        if(p.hasPermission("vdrop.toinventory"))
            inv.setItem(3, itms(Material.CHEST, "§aDrag and drop"));
        return inv;
    }
}
