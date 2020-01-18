/*
 *
 *
 *  © Stelch Software 2020, distribution is strictly prohibited
 *  Blockcade is a company of Stelch Software
 *
 *  Changes to this file must be documented on push.
 *  Unauthorised changes to this file are prohibited.
 *
 *  @author Ryan W
 * @since (DD/MM/YYYY) 18/1/2020
 */

package net.blockcade.HUB.Commands;

import net.blockcade.HUB.Common.Static.Variables.Game;
import net.blockcade.HUB.Common.Utils.Text;
import net.blockcade.HUB.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class QueueCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage("That command may only be executed by players.");
            return false;
        }

        if(args.length<=0){
            // No data provided
            sender.sendMessage(Text.format("&cUsage: /queue {gamemode} - https://blockcade.net/games"));
            return true;
        }else {
            if(Arrays.asList(Arrays.stream(Game.class.getEnumConstants()).map(Enum::name).toArray(String[]::new)).contains(args[0].toUpperCase())){
                Main.GamePlayers.get((Player)sender).joinQueue(Game.valueOf(args[0].toUpperCase()));
            }else {
                // Invalid game provided
                sender.sendMessage(Text.format("&cInvalid game requested"));
                sender.sendMessage(Text.format("&cUsage: /queue {gamemode} - https://blockcade.net/games"));
                return true;
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length<=0)
            return Arrays.asList(Arrays.stream(Game.class.getEnumConstants()).map(Enum::name).toArray(String[]::new));
        return null;
    }
}
