package me.voten.vdrop.utils;

import me.voten.vdrop.Main;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ItemClass implements ConfigurationSerializable {

    private final Material material;
    private final double chance;
    private final int exp;
    private final int maxY;
    private final int minY;
    private final double price;
    private final String name;

    public ItemClass(Material mat, double ch, int ex, int min, int max, double pric, String nam){
        material = mat;
        chance = ch;
        exp = ex;
        maxY = max;
        minY = min;
        price = pric;
        name = nam;
    }

    public ItemStack getItem(){
        return new ItemStack(material, 1);
    }

    public double getChance(){
        return chance;
    }

    public int getExp(){
        return exp;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getMinY() {
        return minY;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {return name;}

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("mat", material);
        map.put("ch", chance);
        map.put("exp", exp);
        map.put("maxy", maxY);
        map.put("miny", minY);
        map.put("price", price);
        map.put("name", name);
        return map;
    }

    @NotNull
    public static ItemClass deserialize(@NotNull Map<String, Object> args){
        ItemClass icc = new ItemClass(Material.STONE, 1.0,1,1,1,1.0,"WRONG ITEM");
        for (ItemClass ic : Main.drops){
            if (ic.getName().equals(args.get("mat")) && ic.getName().equals(args.get("ch"))
                    && ic.getName().equals(args.get("exp")) && ic.getName().equals(args.get("maxy"))
                    && ic.getName().equals(args.get("miny")) && ic.getName().equals(args.get("price"))
                    && ic.getName().equals(args.get("name"))){
                return ic;
            }
        }
        return icc;
    }
}
