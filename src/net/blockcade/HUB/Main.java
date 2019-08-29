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

package net.blockcade.HUB;

import net.blockcade.HUB.Commands.debug;
import net.blockcade.HUB.Commands.fly;
import net.blockcade.HUB.Common.GamePlayer;
import net.blockcade.HUB.Common.Static.GameSearch;
import net.blockcade.HUB.Common.Static.GameServer;
import net.blockcade.HUB.Common.Utils.*;
import net.blockcade.HUB.Common.Utils.Particles.ParticleManager;
import net.blockcade.gac.api.Guardian;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;

public class Main extends JavaPlugin {

    // GamePlayer cache
    public static HashMap<Player, GamePlayer> GamePlayers;
    public static HashMap<GamePlayer, GameSearch> Searching;
    public static GameServer server;
    public static ParticleManager particleManager;
    public static JedisPool pool;
    public static Networking network;
    private static SQL sqlConnection;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        GamePlayers=new HashMap<>();
        sqlConnection=new SQL(getConfig().getString("sql.host"),3306,getConfig().getString("sql.user"),getConfig().getString("sql.pass"),getConfig().getString("sql.database"));
        new GameSearch(this);
        particleManager=new ParticleManager(this);

                /*
         * Register required events
         */
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new net.blockcade.HUB.Common.Events.Player(),this);
        pm.registerEvents(new Item(), this);

        // BungeeCord Hook
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        // Initialize Jedis
        JedisUtil.init(pool,this);
        network=new Networking(this);
        network.setGame("HUB");
        network.setGameState("LOBBY");
        network.init();

        /*
         * Register required commands
         */
        getCommand("debug").setExecutor(new debug());
        getCommand("fly").setExecutor(new fly());

        new BukkitRunnable(){
            @Override
            public void run() {
                /*
                 * Disable Guardian recording & playback
                 */
                if(Bukkit.getPluginManager().getPlugin("Guardian").isEnabled()) {
                    System.out.println("Disabled Guardian");
                    Guardian.setAllowRecording(false);
                    Guardian.setAllowSpectating(false);
                }
            }
        }.runTaskLater(this,20L);
    }

    public static SQL getSqlConnection() {
        return sqlConnection;
    }
}
