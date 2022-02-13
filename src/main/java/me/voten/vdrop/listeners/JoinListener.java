package me.voten.vdrop.listeners;

import me.voten.vdrop.Main;
import me.voten.vdrop.utils.GuiConfiguration;
import me.voten.vdrop.utils.PlayerClass;
import me.voten.vdrop.utils.XMaterial;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;

public class JoinListener implements Listener {

    @EventHandler
    public void JoinListener(PlayerJoinEvent e){
        if(!e.getPlayer().hasPlayedBefore()){
            new PlayerClass(e.getPlayer());
        } else{
            PlayerClass pc = new PlayerClass(e.getPlayer());
        }
    }

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent e){
        if(Main.namechangeplayer.containsKey(e.getPlayer())){
            e.setCancelled(true);
            List<String> list = Main.config.getStringList(Main.namechangeplayer.get(e.getPlayer()));
            list.set(0, e.getMessage());
            Main.config.set(Main.namechangeplayer.get(e.getPlayer()), list);
            e.getPlayer().openInventory(GuiConfiguration.inv());
            Main.namechangeplayer.remove(e.getPlayer());
            Main.getPlugin(Main.class).saveConfig();
        }if(Main.itemchangeplayer.containsKey(e.getPlayer())){
            e.setCancelled(true);
            e.setMessage(e.getMessage().replace(" ", "_"));
            if (XMaterial.matchXMaterial(e.getMessage().toUpperCase()).isPresent()){
                List<String> list = Main.config.getStringList(Main.itemchangeplayer.get(e.getPlayer()));
                list.set(1, e.getMessage().toUpperCase());
                Main.config.set(Main.itemchangeplayer.get(e.getPlayer()), list);
                Main.itemchangeplayer.remove(e.getPlayer());
                e.getPlayer().openInventory(GuiConfiguration.inv());
                Main.getPlugin(Main.class).saveConfig();
                return;
            }
            e.getPlayer().sendMessage("§cIncorrect item, try again");
        }
    }
}
