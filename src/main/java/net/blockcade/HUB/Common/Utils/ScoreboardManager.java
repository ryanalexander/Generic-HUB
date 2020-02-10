
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

package net.blockcade.HUB.Common.Utils;

import net.blockcade.HUB.Common.GamePlayer;
import net.blockcade.HUB.Common.Static.Variables.Game;
import net.blockcade.HUB.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ScoreboardManager {
    private static int sbs = 0;
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
        this.objective = this.board.registerNewObjective("SB"+sbs, "dummy",Text.format(name));
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        new BukkitRunnable(){
            @Override
            public void run() {
                try {
                    ScoreboardManager.this.update();
                }catch (Exception e){
                    cancel();
                }
            }
        }.runTaskTimer(Main.getPlugin(Main.class),0L,2L);
        sbs++;
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

        this.addLine(message);
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
                if(gamePlayer!=null) {
                    int experience = gamePlayer.getExperience();
                    int experience_required = gamePlayer.getExpToNextLevel();
                    int e = (experience) * (experience_required / 25);
                    String level = "&a&l";
                    for(int i=0;i<10;i++){
                        level += (i>(e/10)?"-":"◆");
                        if(i==(e/10))
                            level+="&7&l";
                    }

                    text=text.replaceAll(":player_count:", Bukkit.getServer().getOnlinePlayers().size() + "")
                             .replaceAll(":rank:", gamePlayer.getRank().name())
                             .replaceAll(":level:", gamePlayer.getLevelRound()+" &7["+level+"&7]")
                             .replaceAll(":tokens:", (new DecimalFormat("###,###,###").format(gamePlayer.getTokens()))+"")
                             .replaceAll(":server_name:", Main.network.serverName);
                }
                for(Map.Entry<String, placeholder> replacements : placeholders.entrySet()){
                    text=text.replaceAll(replacements.getKey(), (replacements.getValue().String(gamePlayer)));
                }
                if ((!ChatColor.stripColor(Text.format(text)).equals(ChatColor.stripColor(str)))) {
                    this.board.resetScores(str);
                    this.objective.getScore(Text.format(text)
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
        String String(GamePlayer player);
    }

    public void showFor(Player player) {
        player.setScoreboard(this.board);
    }
}