package me.voten.vdrop.commands;

import me.voten.vdrop.utils.PlayerClass;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TurboCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length != 2 || !isNumeric(args[1])){
            sender.sendMessage("§cError");
            sender.sendMessage("/turbodrop [.Player/@] [Time in minutes]");
        } else{
            if(args[0].startsWith(".")){
                for (Player p : Bukkit.getOnlinePlayers()){
                    String nick = args[0].substring(1);
                    if(p.getName().equals(nick)){
                        PlayerClass.getByPlayer(p).setTurbodroptime(Integer.parseInt(args[1]));
                    }
                }
            }
            if(args[0].equals("@")){
                for (Player p : Bukkit.getOnlinePlayers()){
                    PlayerClass.getByPlayer(p).setTurbodroptime(Integer.parseInt(args[1]));
                }
            }
        }
        return false;
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
