package net.blockcade.HUB.Common.Static;

import net.blockcade.HUB.Common.GamePlayer;
import net.blockcade.HUB.Common.Utils.JavaUtils;
import net.blockcade.HUB.Main;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static net.blockcade.HUB.Main.Searching;

public class GameSearch {

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
    }

    public void stop(){this.stopped=true;}

    public Game getGame() {
        return game;
    }

    public void poll() {
        searchTime=searchTime+50;
        long MINUTES = JavaUtils.FormatMS(searchTime, JavaUtils.TimeUnit.MINUTE);
        long SECONDS = JavaUtils.FormatMS(searchTime, JavaUtils.TimeUnit.SECOND);
        long MILLISECONDS = JavaUtils.FormatMS(searchTime, JavaUtils.TimeUnit.MILI);
        String MINUTES_FORMATTED = (MINUTES<10?"0"+MINUTES:MINUTES+"");
        String SECONDS_FORMATTED = (SECONDS-(MINUTES*60)<10?("0"+(SECONDS-(MINUTES*60))):SECONDS-(MINUTES*60)+"");
        // MILLISECONDS-(SECONDS*1000)
        String MILLISECOND_FORMATTED = (MILLISECONDS-(SECONDS*1000)<10?"0"+(MILLISECONDS-(SECONDS*1000)):MILLISECONDS-(SECONDS*1000)+"").substring(0,2);
        player.sendActionBar(String.format("&aSearching for &e%s&a - &e&l%s&a:&e&l%s&a:&e&l%s",game.getName(), MINUTES_FORMATTED,SECONDS_FORMATTED,MILLISECOND_FORMATTED));
    }


    public void StartSearchHandler(JavaPlugin plugin) {
        Searching=new HashMap<>();
        new BukkitRunnable(){
            @Override
            public void run() {
                for(GameSearch search : Searching.values()){
                    if(search.player.spigot().isOnline()&&(GameSearch.this.stopped)){search.poll();}else{cancel();} }
            }
        }.runTaskTimer(plugin,0,0);
    }
}
