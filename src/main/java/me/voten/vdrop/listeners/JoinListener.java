package me.voten.vdrop.listeners;

import me.voten.vdrop.utils.PlayerClass;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void JoinListener(PlayerJoinEvent e){
        if(!e.getPlayer().hasPlayedBefore()){
            new PlayerClass(e.getPlayer());
        } else{
            PlayerClass pc = new PlayerClass(e.getPlayer(), true, true, 1, 1 ,true);
        }
    }
}
