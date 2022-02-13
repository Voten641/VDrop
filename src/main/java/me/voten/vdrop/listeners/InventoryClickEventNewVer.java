package me.voten.vdrop.listeners;

import com.jeff_media.morepersistentdatatypes.ConfigurationSerializableDataType;
import me.voten.vdrop.Main;
import me.voten.vdrop.utils.GuiDrop;
import me.voten.vdrop.utils.ItemClass;
import me.voten.vdrop.utils.PlayerClass;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;

public class InventoryClickEventNewVer implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getView().getTitle().equals(Main.color(Main.config.getString("title")))){
            e.setCancelled(true);
            if(e.getCurrentItem() != null){
                NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "itemclass");
                PersistentDataType<byte[], ItemClass> itemclasstype = new ConfigurationSerializableDataType<>(ItemClass.class);
                PersistentDataContainer pdc = e.getCurrentItem().getItemMeta().getPersistentDataContainer();
                ItemClass itm = pdc.get(key, itemclasstype);
                ItemClass itm2 = new ItemClass(Material.STONE, 1.0,1,1,1,1.0,"WRONG ITEM");
                PlayerClass pc = PlayerClass.getByPlayer((Player) e.getWhoClicked());
                if(e.getCurrentItem().containsEnchantment(Enchantment.DURABILITY) && itm != itm2){
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

}
