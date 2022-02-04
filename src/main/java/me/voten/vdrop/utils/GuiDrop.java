package me.voten.vdrop.utils;

import me.voten.vdrop.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.voten.vdrop.Main.itms;

public class GuiDrop {

    public static Inventory inv(Player p){
        List<Integer> size = Arrays.asList(9,18,27,36,45);
        Integer invsize = 0;
        if(Main.drops.size()>45){
            p.sendMessage("§cToo many items, support max 45");
            return null;
        }
        for(int s : size){
            if(s >= Main.drops.size()){
                invsize = s+9;
                break;
            }
        }
        Inventory inv = Bukkit.createInventory(null, invsize, Main.color(Main.config.getString("title")));
        PlayerClass pc = PlayerClass.getByPlayer(p);
        for (ItemsClass itm : Main.drops){
            String status = Boolean.toString(PlayerClass.getByPlayer(p).getDrop(itm));
            status = Main.replaceBooleans(status);
            ItemStack it = itm.getItem();
            ItemMeta im = it.getItemMeta();
            im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            im.addEnchant(Enchantment.DURABILITY, 1, true);
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
        inv.setItem(invsize-9, itms(XMaterial.valueOf(Main.config.getStringList("enable-all").get(1).toUpperCase()).parseItem(), Main.color(Main.config.getStringList("enable-all").get(0))));
        inv.setItem(invsize-8, itms(XMaterial.valueOf(Main.config.getStringList("disable-all").get(1).toUpperCase()).parseItem(), Main.color(Main.config.getStringList("disable-all").get(0))));
        if(p.hasPermission("vdrop.autosell"))
        inv.setItem(invsize-2, itms(XMaterial.valueOf(Main.config.getStringList("auto-sell").get(1).toUpperCase()).parseItem(), Main.color(Main.config.getStringList("auto-sell").get(0)), Arrays.asList(Main.replaceBooleans(""+pc.isAutosell()))));
        if(p.hasPermission("vdrop.toinventory"))
        inv.setItem(invsize-1, itms(XMaterial.valueOf(Main.config.getStringList("drop-to-inv").get(1).toUpperCase()).parseItem(), Main.color(Main.config.getStringList("drop-to-inv").get(0)), Arrays.asList(Main.replaceBooleans(""+pc.isAutopickup()))));
        return inv;
    }
}
