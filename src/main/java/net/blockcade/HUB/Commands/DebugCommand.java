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

import net.blockcade.HUB.Common.GamePlayer;
import net.blockcade.HUB.Common.Static.GameServer;
import net.blockcade.HUB.Common.Static.Variables.Game;
import net.blockcade.HUB.Common.Static.Variables.Ranks;
import net.blockcade.HUB.Common.Utils.Item;
import net.blockcade.HUB.Common.Utils.NPC.PlayerNPC;
import net.blockcade.HUB.Common.Utils.Servers;
import net.blockcade.HUB.Common.Utils.Text;
import net.blockcade.HUB.Main;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Map;

import static net.blockcade.HUB.Main.GamePlayers;
import static net.blockcade.HUB.Main.network;

public class DebugCommand implements CommandExecutor {

    // TODO Remove this entire file

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if (!sender.isOp()&& GamePlayers.get(sender).getRank().getLevel()< Ranks.HELPER.getLevel()) {
            sender.sendMessage(Text.format("&cYou must be %s&c or higher to execute that command.",Ranks.HELPER.getFormatted()));
            return false;
        }

        if(args.length>=1) {
            if (args[0].equalsIgnoreCase("servers")) {
                for (Map.Entry<Game, ArrayList<GameServer>> servers : Servers.getAvailableServers().entrySet()) {
                    sender.sendMessage("--[ " + servers.getKey().getName() + " ]--");
                    for (GameServer server : servers.getValue()) {
                        sender.sendMessage(server.getName() + " / " + server.getState() + " / " + server.getGame() + " / " + server.getPlayercount());
                    }
                }
                return false;
            }else if(args[0].equalsIgnoreCase("npc")){
                Player player = (Player)sender;
                new PlayerNPC("&cIt's bob",player.getLocation());
                return false;
            }else if(args[0].equalsIgnoreCase("head")){
                ItemStack cosmetics_item = Item.itemWithBase64(new ItemStack(Material.PLAYER_HEAD),"eyJ0aW1lc3RhbXAiOjE1Nzk1NzkwNzMzNTMsInByb2ZpbGVJZCI6Ijc3MjdkMzU2NjlmOTQxNTE4MDIzZDYyYzY4MTc1OTE4IiwicHJvZmlsZU5hbWUiOiJsaWJyYXJ5ZnJlYWsiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzJlYmY3MjRiNWJkNjI0YWRlNWM1MDI4OTFjNThhNTJiOTFlMjEyMjRjYmM2N2Y2MzdhMDc1ZGM5NTdlMTdhMDYifX19");
                ((Player)sender).getInventory().addItem(cosmetics_item);
                return false;
            }
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
        }

        return false;
    }
}
