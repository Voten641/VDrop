package me.voten.vdrop.utils;

import me.voten.vdrop.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuiDrop {

    public static Inventory inv(Player p){
        Inventory inv = Bukkit.createInventory(null, 36, Main.color(Main.config.getString("title")));
        PlayerClass pc = PlayerClass.getByPlayer(p);
        for (ItemsClass itm : Main.drops){
            String status = Boolean.toString(PlayerClass.getByPlayer(p).getDrop(itm));
            status = Main.replaceBooleans(status);
            ItemStack it = itm.getItem();
            ItemMeta im = it.getItemMeta();
            List<String> lore2 = Main.config.getStringList("itemlore");
            List<String> lore = new ArrayList<>();
            for(String l : lore2){
                l = l.replace("%status", status);
                l = l.replace("%chance", String.valueOf(itm.getChance()));
                l = l.replace("%min", String.valueOf(itm.getMinY()));
                l = l.replace("%max", String.valueOf(itm.getMaxY()));
                l = l.replace("%exp", String.valueOf(itm.getExp()));
                l = l.replace("%money", String.valueOf(itm.getPrice()));
                l = l.replace("&", "§");
                lore.add(l);
            }
            im.setLore(lore);
            if(Main.customnames) im.setDisplayName(itm.getName());
            it.setItemMeta(im);
            inv.addItem(it);
        }
        inv.setItem(27, itms(Material.GREEN_BANNER, Main.color(Main.config.getString("enable-all"))));
        inv.setItem(28, itms(Material.RED_BANNER, Main.color(Main.config.getString("disable-all"))));
        if(p.hasPermission("vdrop.autosell"))
        inv.setItem(33, itms(Material.ITEM_FRAME, Main.color(Main.config.getString("auto-sell")), Arrays.asList(Main.replaceBooleans(""+pc.isAutosell()))));
        if(p.hasPermission("vdrop.autocobblex"))
        inv.setItem(34, itms(Material.COBBLESTONE, Main.color(Main.config.getString("auto-cobblex")), Arrays.asList(Main.replaceBooleans(""+pc.isAutocraftcb()))));
        if(p.hasPermission("vdrop.to inventory"))
        inv.setItem(35, itms(Material.CHEST, Main.color(Main.config.getString("drop-to-inv")), Arrays.asList(Main.replaceBooleans(""+pc.isAutopickup()))));
        return inv;
    }

    public static ItemStack itms(Material mat, String name, List<String> lore){
        ItemStack it = new ItemStack(mat);
        ItemMeta im = it.getItemMeta();
        assert im != null;
        im.setDisplayName(name);
        im.setLore(lore);
        it.setItemMeta(im);
        return it;
    }
    public static ItemStack itms(Material mat, String name){
        ItemStack it = new ItemStack(mat);
        ItemMeta im = it.getItemMeta();
        assert im != null;
        im.setDisplayName(name);
        it.setItemMeta(im);
        return it;
    }
}
