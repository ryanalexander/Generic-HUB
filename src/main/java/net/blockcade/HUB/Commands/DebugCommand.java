/*
 *
 *  *
 *  * © Stelch Software 2019, distribution is strictly prohibited
 *  * Blockcade is a company of Stelch Software
 *  *
 *  * Changes to this file must be documented on push.
 *  * Unauthorised changes to this file are prohibited.
 *  *
 *  * @author Ryan Wood
 *  @since 5/8/2019
 */

package net.blockcade.HUB.Commands;

import net.blockcade.HUB.Common.GamePlayer;
import net.blockcade.HUB.Common.Static.GameServer;
import net.blockcade.HUB.Common.Static.Variables.Game;
import net.blockcade.HUB.Common.Static.Variables.Ranks;
import net.blockcade.HUB.Common.Utils.Servers;
import net.blockcade.HUB.Common.Utils.Text;
import net.blockcade.HUB.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Map;

import static net.blockcade.HUB.Main.GamePlayers;
import static net.blockcade.HUB.Main.network;

public class DebugCommand implements CommandExecutor {

    // TODO Remove this entire file

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if (!sender.isOp()&& GamePlayers.get((Player)sender).getRank().getLevel()< Ranks.HELPER.getLevel()) {
            sender.sendMessage(Text.format("&cYou must be %s&c or higher to execute that command.",Ranks.HELPER.getFormatted()));
            return false;
        }

        sender.sendMessage(Text.format("&a]---------------["));
        sender.sendMessage(Text.format("&a]&e    Server Data    &a["));
        sender.sendMessage(Text.format("&aUUID: &e"+ network.getUuid()));
        sender.sendMessage(Text.format("&aCONTAINER: &e"+ network.getContainer()));
        sender.sendMessage(Text.format("&aNAME: &e"+ network.serverName));
        sender.sendMessage(Text.format("&aJedis: &e"+ (Main.pool.getResource().isConnected()?"connected":"disconnected")));
        if(sender instanceof Player) {
            GamePlayer gamePlayer = GamePlayers.get((Player)sender);
            if(gamePlayer==null){
                sender.sendMessage(Text.format("&cPlayer is null"));
                return false;
            }
            sender.sendMessage(Text.format("&a]---------------["));
            sender.sendMessage(Text.format("&a]&e    Basic Data    &a["));
            sender.sendMessage(Text.format("&aUUID: &e" + gamePlayer.getUuid()));
            sender.sendMessage(Text.format("&aRank: &e" + gamePlayer.getRank()));
            sender.sendMessage(Text.format("&a]&e    Preferences    &a["));
            sender.sendMessage(Text.format("&aPlayer Visibility: &e" + gamePlayer.getPreferenceSettings().getPlayerVisibility()));
            sender.sendMessage(Text.format("&aPet Visibility: &e" + gamePlayer.getPreferenceSettings().getPetVisibility()));
            sender.sendMessage(Text.format("&aChat Visibility: &e" + gamePlayer.getPreferenceSettings().getChatVisibility()));
            sender.sendMessage(Text.format("&a]---------------["));

            if(args.length>=1&&args[0].equalsIgnoreCase("servers")){
                for(Map.Entry<Game, ArrayList<GameServer>> servers : Servers.getAvailableServers().entrySet()){
                    sender.sendMessage("--[ "+servers.getKey().getName()+" ]--");
                    for(GameServer server : servers.getValue()){
                        sender.sendMessage(server.getName()+" / "+server.getState()+" / "+server.getGame()+" / "+server.getPlayercount());
                    }
                }
            }
        }

        return false;
    }
}
