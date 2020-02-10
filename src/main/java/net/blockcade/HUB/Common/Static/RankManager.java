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

package net.blockcade.HUB.Common.Static;

import net.blockcade.HUB.Common.Static.Variables.Ranks;
import net.blockcade.HUB.Common.Utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public class RankManager implements Listener {

    Scoreboard sb;
    public static HashMap<Ranks, Team> ranks = new HashMap<>();

    public RankManager() {
        sb = Bukkit.getScoreboardManager().getNewScoreboard();
        for(Ranks rank : Ranks.values()){
            Team team = sb.registerNewTeam(rank.getWeight()+rank.name());
            team.setPrefix(Text.format(rank.getFormatted()));
            ranks.put(rank,team);
        }
    }

}
