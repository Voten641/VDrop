package me.voten.vdrop.utils;

import com.google.common.collect.Maps;
import me.voten.vdrop.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerClass {

    private static Map<Player, PlayerClass> listPlayers = new ConcurrentHashMap<>();
    private HashMap<ItemsClass, Boolean> drop = Maps.newHashMap();
    private boolean autosell = false;
    private boolean autopickup = true;
    private Player player = null;
    private int cansenddropmessage = 0;
    private int turbodroptime = 0;

    public PlayerClass(Player p){
        player = p;
        for (ItemsClass ic : Main.drops){
            drop.put(ic,true);
        }
        listPlayers.put(player, this);
    }

    public PlayerClass(Player p, boolean as, boolean ac, int ls, int lp, boolean ms){
        player = p;
        autosell = as;
        for (ItemsClass ic : Main.drops){
            drop.put(ic, true);
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

    public boolean getDrop(ItemsClass ic){
        return drop.get(ic);
    }

    public void setDrop(ItemsClass itm, Boolean b) {
        drop.replace(itm, b);
    }

    public void setAutosell(boolean autosell) {
        this.autosell = autosell;
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
