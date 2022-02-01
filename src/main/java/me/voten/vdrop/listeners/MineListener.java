package me.voten.vdrop.listeners;

import me.voten.vdrop.Main;
import me.voten.vdrop.utils.ItemsClass;
import me.voten.vdrop.utils.PlayerClass;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

public class MineListener implements Listener {

    @EventHandler
    public void onMine(BlockBreakEvent e){
        Player p = e.getPlayer();
        if(Main.blocksdrop.contains(e.getBlock().getType())){
            Random rand = new Random();
            double chance = chance(rand, e.getPlayer());
            PlayerClass pc = PlayerClass.getByPlayer(p);
            Cobblex(pc);
            for (ItemsClass itm : Main.drops){
                if (chance <= addchances(itm.getChance(), pc) && pc.getDrop(itm)){
                    if(e.getBlock().getY() >= itm.getMinY() && e.getBlock().getY() <= itm.getMaxY()) {
                        ItemStack it = itm.getItem();
                        it.setAmount(ammount());
                        e.setExpToDrop(itm.getExp());
                        if(pc.isAutosell())SellItems(pc, it, itm);
                        else GiveItem(e.getBlock(), pc, it);
                        if(!Main.multiitem){
                            return;
                        }
                    }
                }
            }
        }
    }

    public void Cobblex(PlayerClass pc){
        if(pc.isAutocraftcb()){
            if(pc.getPlayer().getInventory().contains(Material.COBBLESTONE, 576)) {
                pc.getPlayer().getInventory().removeItem(new ItemStack(Material.COBBLESTONE, 576));
                pc.getPlayer().updateInventory();
                ItemStack cb = new ItemStack(Material.COBBLESTONE);
                ItemMeta im = cb.getItemMeta();
                im.setDisplayName("§aCobblex");
                im.addEnchant(Enchantment.LUCK, 1, true);
                cb.setItemMeta(im);
                pc.getPlayer().getInventory().addItem(cb);
            }
        }
    }

    public void SellItems(PlayerClass pc, ItemStack it, ItemsClass itm){
        double price = it.getAmount() * itm.getPrice();
        Main.getEconomy().depositPlayer(pc.getPlayer(), price);
    }

    public void GiveItem(Block b, PlayerClass pc, ItemStack it){
        if(pc.isAutopickup()){
            Player p = pc.getPlayer();
            if(p.getInventory().firstEmpty() == -1){
                for(ItemStack itm : p.getInventory()){
                    if(itm != null && itm.getType().equals(it.getType())){
                        if (itm.getMaxStackSize() >= it.getAmount() + itm.getAmount()){
                            int ammount = it.getAmount() + itm.getAmount();
                            itm.setAmount(ammount);
                            break;
                        }
                        else{
                            b.getLocation().getWorld().dropItem(b.getLocation(), it);
                            if(pc.getCansenddropmessage() == 0){
                                p.sendMessage("§cOut of inventory space");
                                pc.setCansenddropmessage(50);
                            }
                            else{
                                pc.minusCansenddropmessage();
                            }
                            break;
                        }
                    }
                }
                p.updateInventory();
            }
            else{
                p.getInventory().addItem(it);
            }
        }else{
            b.getLocation().getWorld().dropItem(b.getLocation(), it);
        }
    }

    public int ammount(){
        int ammount;
        Random rand = new Random();
        int random = rand.nextInt(100);
        if(random < 50) ammount = 1;
        else if(random < 80 && random > 50) ammount = 2;
        else ammount = 2;
        return ammount;
    }

    public double addchances(double ch, PlayerClass pc){
        double chance = ch;

        int fortune = 0;
        Player p = pc.getPlayer();
        if (p.getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.LOOT_BONUS_BLOCKS))
         fortune = p.getInventory().getItemInMainHand().getEnchantments().get(Enchantment.LOOT_BONUS_BLOCKS);
        if(fortune == 1) chance += 0.05;
        if(fortune == 2) chance += 0.1;
        if(fortune == 3) chance += 0.15;

        if(p.hasPermission("vdrop.svip")) chance += 1.2;
        else if(p.hasPermission("vdrop.vip")) chance += 0.5;

        if(pc.getTurbodroptime() > 0) chance += 1;

        return chance;
    }

    public double chance(Random rand, Player p) {
        double rmin = 0.01;
        double rmax = 100.0;
        return rmin + (rmax - rmin) * rand.nextDouble();
    }
}
