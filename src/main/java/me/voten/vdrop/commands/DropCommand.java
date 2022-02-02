package me.voten.vdrop.commands;

import me.voten.vdrop.utils.GuiConfiguration;
import me.voten.vdrop.utils.GuiDrop;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DropCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length > 0 && args[0].equals("configure")){
                p.openInventory(GuiConfiguration.inv(p));
                return true;
            }
            p.openInventory(GuiDrop.inv(p));
        }
        return false;
    }
}
