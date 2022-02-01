package me.voten.vdrop.listeners;

import me.voten.vdrop.Main;
import me.voten.vdrop.utils.GuiDrop;
import me.voten.vdrop.utils.ItemsClass;
import me.voten.vdrop.utils.PlayerClass;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getView().getTitle().equals(Main.color(Main.config.getString("title")))){
            e.setCancelled(true);
            if(e.getCurrentItem() != null){
                ItemsClass itm = findByItemStack(e.getCurrentItem());
                PlayerClass pc = PlayerClass.getByPlayer((Player) e.getWhoClicked());
                if(itm != null){
                    pc.setDrop(itm, !pc.getDrop(itm));
                }
                else{
                    if(e.getCurrentItem().getItemMeta().getDisplayName().contains(Main.color(Main.config.getString("disable-all")))){
                        for (ItemsClass itms : Main.drops){
                            pc.setDrop(itms, false);
                        }
                    }else if(e.getCurrentItem().getItemMeta().getDisplayName().contains(Main.color(Main.config.getString("enable-all")))){
                        for (ItemsClass itms : Main.drops){
                            pc.setDrop(itms, true);
                        }
                    }else if(e.getCurrentItem().getItemMeta().getDisplayName().contains(Main.color(Main.config.getString("auto-sell")))){
                        pc.setAutosell(!pc.isAutosell());
                    }else if(e.getCurrentItem().getItemMeta().getDisplayName().contains(Main.color(Main.config.getString("auto-cobblex")))){
                        pc.setAutocraftcb(!pc.isAutocraftcb());
                    }else if(e.getCurrentItem().getItemMeta().getDisplayName().contains(Main.color(Main.config.getString("drop-to-inv")))){
                        pc.setAutopickup(!pc.isAutopickup());
                    }
                }
                e.getWhoClicked().openInventory(GuiDrop.inv((Player)e.getWhoClicked()));
            }
        }
    }

    public ItemsClass findByItemStack(ItemStack it){
        ItemsClass i = null;
        for (ItemsClass itm : Main.drops){
            if(itm.getItem().getType().equals(it.getType())){
                i = itm;
                break;
            }
        }
        return i;
    }

}
