package net.blockcade.HUB.Commands;

import net.blockcade.HUB.Common.GamePlayer;
import net.blockcade.HUB.Common.Static.Variables.Ranks;
import net.blockcade.HUB.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if(!(sender instanceof Player))return false;
        GamePlayer player = Main.GamePlayers.get(sender);

        if(player.getRank().getLevel()<=85){player.sendMessage("&cYou must be "+ Ranks.ADMIN.getFormatted()+"&c or above to use that command.");return false;}


        return false;
    }
}
