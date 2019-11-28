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
 *  @since 22/7/2019
 */

package net.blockcade.HUB.Common.Utils;

import net.blockcade.HUB.Common.GamePlayer;
import net.blockcade.HUB.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.Iterator;

public class ScoreboardManager {
    private org.bukkit.scoreboard.ScoreboardManager manager;
    private String name;
    private Scoreboard board;
    private Objective objective;
    private HashMap<Integer, String> lines = new HashMap();
    private HashMap<String, placeholder> placeholders = new HashMap<>();
    public int longest_line = 0;
    private int counter = 32;
    private String payload = " ";
    private int payload_count = 1;
    private GamePlayer gamePlayer;

    public ScoreboardManager(String name) {
        this.name = name;
        this.manager = Bukkit.getServer().getScoreboardManager();
        this.board = this.manager.getNewScoreboard();
        this.objective = this.board.registerNewObjective(Text.format(name), "dummy");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public void setDisplayname(String name) {
        this.objective.setDisplayName(Text.format(name));
        this.name = name;
    }

    public int addLine(String message) {
        if (message.length() > this.longest_line) {
            ++this.longest_line;
        }
        String message_raw = message;
        if(message.length()>=35){
            message=message.substring(0,35);
        }
        this.objective.getScore(Text.format(message)).setScore(this.counter);
        --this.counter;
        this.lines.put(this.counter, Text.format(message_raw));
        this.update();
        return this.counter;
    }

    public int addBlank() {
        String message = "";

        for (int i = this.payload_count; i > 0; --i) {
            message = message + this.payload;
        }

        this.addLine(message.replaceAll(":player_count:", Bukkit.getServer().getOnlinePlayers().size() + ""));
        this.lines.put(this.counter, message);
        ++this.payload_count;
        this.update();
        return this.counter;
    }

    public void editLine(int line, String message) {
        this.lines.put(line, Text.format(message));
        this.update();
    }

    public void enableHealthCounter() {
        Objective obj = this.board.registerNewObjective("healthname", "health");
        obj.setDisplayName(Text.format("&c❤"));
        obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
        Iterator var2 = Bukkit.getOnlinePlayers().iterator();

        while (var2.hasNext()) {
            Player player = (Player) var2.next();
            obj.getScore(player).setScore((int) player.getHealth());
        }

    }

    public void clear() {
        this.lines.clear();
    }

    public void registerPlaceholder(placeholder placeholder, String value){
        this.placeholders.put(value, placeholder);
    }

    public void update() {
        for (String str : this.board.getEntries()) {
            int pid = this.objective.getScore(str).getScore();
            String text = this.lines.get(pid);
            if(text != null) {
                if ((!ChatColor.stripColor(Text.format(text)).equals(ChatColor.stripColor(str)))) {
                    this.board.resetScores(str);
                    this.objective.getScore(Text.format(text
                            .replaceAll(":player_count:", Bukkit.getServer().getOnlinePlayers().size() + "")
                            .replaceAll(":rank:", gamePlayer.getRank().name())
                            .replaceAll(":level:", gamePlayer.getLevel()+"")
                            .replaceAll(":server_name:", Main.network.serverName))
                    ).setScore(pid);
                }
            }
        }
    }

    public void delete() {
        this.clear();
        this.objective.unregister();
    }

    public interface placeholder {
        int Integer(GamePlayer player);
    }

    public void showFor(Player player) {
        player.setScoreboard(this.board);
    }
}