package net.blockcade.HUB.Commands;

import net.blockcade.HUB.Common.Utils.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class spawn implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {

        if(!(sender instanceof Player))return false;

        sender.sendMessage(Text.format("&aWoosh! You have been teleported to the hub!"));
        ((Player) sender).teleport(((Player) sender).getWorld().getSpawnLocation());

        return false;
    }
}
