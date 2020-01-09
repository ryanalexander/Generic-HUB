package net.blockcade.HUB.Commands;

import net.blockcade.HUB.Common.GamePlayer;
import net.blockcade.HUB.Common.Static.Variables.Ranks;
import net.blockcade.HUB.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if(!(sender instanceof Player))return false;
        GamePlayer player = Main.GamePlayers.get(sender);

        if(player.getRank().getLevel()<=1){player.sendMessage("&cYou must be "+ Ranks.MEGA.getFormatted()+"&c or above to use that command.");return false;}

        if(player.getPreferenceSettings().isFlight()){
            player.sendMessage("&cYou have now disabled flying.");
        }else {
            player.sendMessage("&aYou have now enabled flying.");
        }

        player.getPreferenceSettings().setFlight(!player.getPreferenceSettings().isFlight());
        player.spigot().setAllowFlight(player.getPreferenceSettings().isFlight());
        return false;
    }
}
