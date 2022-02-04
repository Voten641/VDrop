package me.voten.vdrop.utils;

import me.voten.vdrop.Main;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemsClass {

    private final Material material;
    private final double chance;
    private final int exp;
    private final int maxY;
    private final int minY;
    private final double price;
    private final String name;

    public ItemsClass(Material mat, double ch, int ex, int min, int max, double pric, String nam){
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

}
