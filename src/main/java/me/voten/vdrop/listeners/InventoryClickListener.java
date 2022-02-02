package me.voten.vdrop.listeners;

import me.voten.vdrop.Main;
import me.voten.vdrop.utils.GuiConfiguration;
import me.voten.vdrop.utils.GuiDrop;
import me.voten.vdrop.utils.ItemsClass;
import me.voten.vdrop.utils.PlayerClass;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getView().getTitle().equals(Main.color(Main.config.getString("title")))){
            e.setCancelled(true);
            if(e.getCurrentItem() != null){
                ItemsClass itm = findByItemStack(e.getCurrentItem());
                PlayerClass pc = PlayerClass.getByPlayer((Player) e.getWhoClicked());
                if(e.getCurrentItem().containsEnchantment(Enchantment.DURABILITY) && itm != null){
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
                if(e.getClick().isRightClick()){
                     p.closeInventory();
                     p.sendMessage("§eType new name on chat");
                     List<String> configs = Arrays.asList("enable-all", "disable-all", "auto-sell", "drop-to-inv", "enabled", "disabled", "title");
                     for (String s : configs){
                         if(e.getCurrentItem().getItemMeta().getDisplayName().contains(Main.color(Main.config.getString(s)))){
                             Main.namechangeplayer.put(p, s);
                         }
                     }
                }
                /*if(e.getClick().isLeftClick()){
                    p.closeInventory();
                    p.sendMessage("§eType new item on chat");
                    Main.itemchangeplayer.put(p, e.getCurrentItem());

                    List<String> configs = Arrays.asList("enable-all", "disable-all", "auto-sell", "drop-to-inv", "enabled", "disabled", "title");
                    for (String s : configs){
                        if(e.getCurrentItem().getItemMeta().getDisplayName().contains(Main.color(Main.config.getString(s)))){
                            Main.namechangeplayer.put(p, s);
                        }
                    }
                }*/
            }
        }
    }

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent e){
        if(Main.namechangeplayer.containsKey(e.getPlayer())){
            e.setCancelled(true);
            Main.config.set(Main.namechangeplayer.get(e.getPlayer()), e.getMessage());
            e.getPlayer().openInventory(GuiConfiguration.inv());
            Main.namechangeplayer.remove(e.getPlayer());
            Main.getPlugin(Main.class).saveConfig();
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
