package me.voten.vdrop.listeners;

import me.voten.vdrop.Main;
import me.voten.vdrop.utils.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getView().getTitle().equals(Main.color(Main.config.getString("title")))){
            e.setCancelled(true);
            if(e.getCurrentItem() != null){
                ItemClass itm = findByItemStack(e.getCurrentItem());
                PlayerClass pc = PlayerClass.getByPlayer((Player) e.getWhoClicked());
                if(e.getCurrentItem().containsEnchantment(Enchantment.DURABILITY) && itm != null){
                    pc.setDrop(Main.drops.get(e.getSlot()), !pc.getDrop(Main.drops.get(e.getSlot())));
                }
                else{
                    if(e.getCurrentItem().getItemMeta().getDisplayName().contains(Main.color(Main.config.getString("disable-all")))){
                        for (ItemClass itms : Main.drops){
                            pc.setDrop(itms, false);
                        }
                    }else if(e.getCurrentItem().getItemMeta().getDisplayName().contains(Main.color(Main.config.getString("enable-all")))){
                        for (ItemClass itms : Main.drops){
                            pc.setDrop(itms, true);
                        }
                    }else if(e.getCurrentItem().getItemMeta().getDisplayName().contains(Main.color(Main.config.getString("auto-sell")))){
                        pc.setAutosell(!pc.isAutosell());
                    }else if(e.getCurrentItem().getItemMeta().getDisplayName().contains(Main.color(Main.config.getString("drop-to-inv")))){
                        pc.setAutopickup(!pc.isAutopickup());
                    }
                }
                e.getWhoClicked().openInventory(GuiDrop.inv((Player)e.getWhoClicked()));
            }
        }

        if(e.getView().getTitle().equals("§aDrop gui configuration")){
            Player p = (Player) e.getWhoClicked();
            if(e.getCurrentItem() != null){
                e.setCancelled(true);
                HashMap<Integer, String> configs = new HashMap<Integer, String>(){{
                    put(0,"enable-all"); put(1,"disable-all"); put(2,"auto-sell"); put(3,"drop-to-inv");
                    put(12,"enabled"); put(13,"disabled"); put(14,"title");
                }};
                if(e.getClick().isRightClick()){
                     p.closeInventory();
                     p.sendMessage("§eType new name on chat");
                     Main.namechangeplayer.put(p, configs.get(e.getSlot()));
                }
                else if(e.getClick().isLeftClick() && e.getSlot() < 9){
                    e.setCancelled(true);
                    p.closeInventory();
                    p.sendMessage("§eType new item on chat");
                    Main.itemchangeplayer.put(p, configs.get(e.getSlot()));
                }
            }
        }
    }

    public ItemClass findByItemStack(ItemStack it){
        ItemClass i = null;
        for (ItemClass itm : Main.drops){
            if(itm.getItem().getType().equals(it.getType())){
                i = itm;
                break;
            }
        }
        return i;
    }

}
