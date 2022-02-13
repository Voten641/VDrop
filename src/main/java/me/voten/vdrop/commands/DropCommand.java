package me.voten.vdrop.commands;

import me.voten.vdrop.Main;
import me.voten.vdrop.utils.GuiConfiguration;
import me.voten.vdrop.utils.GuiDrop;
import me.voten.vdrop.utils.GuiDropNewVer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class DropCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length > 0 && p.hasPermission("vdrop.admin")){
                if(args[0].equals("configure")) p.openInventory(GuiConfiguration.inv());
                else if(args[0].equals("reload")) {
                    Main.getPlugin(Main.class).reloadConfig();
                    try {
                        Main.config.load(new File(Main.getPlugin(Main.class).getDataFolder(), "config.yml"));
                    } catch (IOException | InvalidConfigurationException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    if (Main.usenewmethod) p.openInventory(GuiDropNewVer.inv(p));
                    else p.openInventory(GuiDrop.inv(p));
                }
                return true;
            }else{
                if (Main.usenewmethod) p.openInventory(GuiDropNewVer.inv(p));
                else p.openInventory(GuiDrop.inv(p));
            }
        }
        return false;
    }
}
