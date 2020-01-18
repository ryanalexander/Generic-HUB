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

import net.blockcade.HUB.Common.GamePlayer;
import net.blockcade.HUB.Common.Static.Variables.Game;
import net.blockcade.HUB.Common.Utils.JavaUtils;
import net.blockcade.HUB.Common.Utils.Servers;
import org.bukkit.Sound;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

import static net.blockcade.HUB.Main.Searching;

public class GameSearch {

    public static HashMap<Game, ArrayList<GameServer>> Available_games = new HashMap<>();

    private long searchTime;
    private boolean stopped=false;
    private GamePlayer player;

    private Game game;
    private JavaPlugin main;

    public GameSearch(JavaPlugin main){
        this.main=main;
        StartSearchHandler(main);
    }

    public GameSearch(GamePlayer player, Game game){
        this.player=player;
        this.game=game;
        this.searchTime=1;
        player.sendMessage(String.format("&aNow searching for &e%s&a!",game.getName()));
    }

    public void stop(){
        Searching.remove(player);
        this.stopped=true;
        player.sendMessage(String.format("&cCancelled search for &e%s&c!",game.getName()));
        player.sendActionBar(String.format("&cCancelled search for &e%s&c!",game.getName()));
    }

    public Game getGame() {
        return game;
    }

    public void poll() {
        if(!player.spigot().isOnline()){this.stopped=true;Searching.remove(player);return;}

        searchTime=searchTime+50;
        long MINUTES = JavaUtils.FormatMS(searchTime, JavaUtils.TimeUnit.MINUTE);
        long SECONDS = JavaUtils.FormatMS(searchTime, JavaUtils.TimeUnit.SECOND);
        long MILLISECONDS = JavaUtils.FormatMS(searchTime, JavaUtils.TimeUnit.MILI);
        String MINUTES_FORMATTED = (MINUTES<10?"0"+MINUTES:MINUTES+"");
        String SECONDS_FORMATTED = (SECONDS-(MINUTES*60)<10?("0"+(SECONDS-(MINUTES*60))):SECONDS-(MINUTES*60)+"");
        // MILLISECONDS-(SECONDS*1000)
        String MILLISECOND_FORMATTED = (MILLISECONDS-(SECONDS*1000)<10?"0"+(MILLISECONDS-(SECONDS*1000)):MILLISECONDS-(SECONDS*1000)+"").substring(0,2);

        if(Available_games.containsKey(game)&&Available_games.get(game).size()>0){
            GameServer server = Available_games.get(game).get(0);
            player.spigot().playSound(player.spigot().getLocation(), Sound.ENTITY_VILLAGER_YES,1f,1f);
            server.setPlayercount(server.getPlayercount()+1);
            player.sendActionBar("&aFound game &e- Now Connecting");
            player.sendServer(server);
            Searching.remove(player);
            this.stopped=true;
        }else {
            player.sendActionBar(String.format("&aSearching for &e%s&a - &e&l%s&a:&e&l%s&a:&e&l%s",game.getName(), MINUTES_FORMATTED,SECONDS_FORMATTED,MILLISECOND_FORMATTED));
        }
    }


    public void StartSearchHandler(JavaPlugin plugin) {
        Searching=new HashMap<>();
        new BukkitRunnable(){
            int i = 0;
            @Override
            public void run() {
                i++;
                if(i==40){
                    i=0;
                    // Update server list
                    Available_games= Servers.getAvailableServers();
                }
                for(GameSearch search : Searching.values()){
                    if(search.player.spigot().isOnline()&&(!search.stopped)){search.poll();}else{Searching.remove(player);}
                }
            }
        }.runTaskTimerAsynchronously(plugin,0,0);
    }
}
