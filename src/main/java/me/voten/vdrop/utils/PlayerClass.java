package me.voten.vdrop.utils;

import com.google.common.collect.Maps;
import me.voten.vdrop.Main;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerClass {

    private static Map<Player, PlayerClass> listPlayers = new ConcurrentHashMap<>();
    private final HashMap<ItemClass, Boolean> drop = Maps.newHashMap();
    private boolean autosell;
    private boolean autopickup;
    private final Player player;
    private int cansenddropmessage;
    private int turbodroptime;

    public PlayerClass(Player p){
        player = p;
        autosell = false;
        autopickup = true;
        cansenddropmessage = 0;
        turbodroptime = 0;
        for (ItemClass ic : Main.drops){
            drop.put(ic,true);
        }
        listPlayers.put(player, this);
    }

    public static PlayerClass getByPlayer(Player p){
        return listPlayers.get(p);
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isAutosell() {
        return autosell;
    }

    public boolean getDrop(ItemClass ic){
        return drop.get(ic);
    }

    public void setDrop(ItemClass itm, Boolean b) {
        drop.replace(itm, b);
    }

    public void setAutosell(boolean autosel) {
        autosell = autosel;
    }

    public boolean isAutopickup() {
        return autopickup;
    }

    public void setAutopickup(boolean autopickup) {
        this.autopickup = autopickup;
    }

    public int getCansenddropmessage() {
        return cansenddropmessage;
    }

    public void setCansenddropmessage(int cansenddropmessage) {
        this.cansenddropmessage = cansenddropmessage;
    }
    public void minusCansenddropmessage() {
        this.cansenddropmessage--;
    }

    public int getTurbodroptime() {
        return turbodroptime;
    }

    public void setTurbodroptime(int turbodroptime) {
        this.turbodroptime = turbodroptime;
    }
}
