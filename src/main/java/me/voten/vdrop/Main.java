package me.voten.vdrop;

import com.google.common.collect.Maps;
import me.voten.vdrop.commands.DropCommand;
import me.voten.vdrop.commands.TurboCommand;
import me.voten.vdrop.listeners.InventoryClickListener;
import me.voten.vdrop.listeners.JoinListener;
import me.voten.vdrop.listeners.MineListener;
import me.voten.vdrop.utils.ItemsClass;
import me.voten.vdrop.utils.PlayerClass;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.logging.Level;

public final class Main extends JavaPlugin {

    public static List<ItemsClass> drops = new ArrayList<>();
    private static Economy econ = null;
    public static boolean multiitem = false;
    public static boolean customnames = false;
    public static FileConfiguration config;
    public static List<Material> blocksdrop = new ArrayList<>();
    public static HashMap<Player, String> namechangeplayer = Maps.newHashMap();
    public static HashMap<Player, String> itemchangeplayer = Maps.newHashMap();

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "Loading Plugin");
        this.saveDefaultConfig();
        loadConfigs();
        initializeDrops();
        initializePlayer();
        this.getServer().getPluginManager().registerEvents(new MineListener(), this);
        this.getServer().getPluginManager().registerEvents(new JoinListener(), this);
        this.getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        this.getCommand("turbodrop").setExecutor(new TurboCommand());
        this.getCommand("drop").setExecutor(new DropCommand());

        if (!setupEconomy() ) {
            System.out.println(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        getLogger().log(Level.INFO, "Plugin Loaded");
    }

    private void loadConfigs() {
        config = this.getConfig();
        multiitem = this.getConfig().getBoolean("multi-items");
        customnames = this.getConfig().getBoolean("custom-names");
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "Plugin Unloaded");
    }

    public void initializePlayer(){
        for(Player p : Bukkit.getOnlinePlayers()){
            new PlayerClass(p, true, true, 1, 1 ,false);
        }
    }

    public void initializeDrops(){
        List<String> blocks = config.getStringList("blocks");
        for (String s : blocks){
            blocksdrop.add(Material.getMaterial(s.toUpperCase()));
        }
        ConfigurationSection items = this.getConfig().getConfigurationSection("itemlist");
        assert items != null;
        Set<String> itemkeys = items.getKeys(false);
        for(String item : itemkeys){
            ConfigurationSection currentitem = items.getConfigurationSection(item);
            assert currentitem != null;
            String name = currentitem.getString("name");
            assert name != null;
            name  = name.replace("&", "§");
            drops.add(new ItemsClass(Material.getMaterial(currentitem.getString("item").toUpperCase()),
                    currentitem.getDouble("chance"), currentitem.getInt("exp"), currentitem.getInt("min"),
                    currentitem.getInt("max"), currentitem.getDouble("money"), name));
        }
        drops.sort(Comparator.comparing(ItemsClass::getChance));
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    public static Economy getEconomy() {
        return econ;
    }

    public static String replaceBooleans(String s){
        s = s.replace("true", color(config.getString("enabled")));
        s = s.replace("false", color(config.getString("disabled")));
        return s;
    }

    public static String color(String s){
        s = s.replace("&", "§");
        return s;
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

    public static ItemStack itms(ItemStack it, String name, List<String> lore){
        ItemMeta im = it.getItemMeta();
        assert im != null;
        im.setDisplayName(name);
        im.setLore(lore);
        it.setItemMeta(im);
        return it;
    }
    public static ItemStack itms(ItemStack it, String name){
        ItemMeta im = it.getItemMeta();
        assert im != null;
        im.setDisplayName(name);
        it.setItemMeta(im);
        return it;
    }
    public void saveConfigFile(){
        saveConfig();
    }
}
