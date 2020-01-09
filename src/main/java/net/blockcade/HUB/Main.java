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
//no u
package net.blockcade.HUB;

import net.blockcade.HUB.Commands.QueueCommand;
import net.blockcade.HUB.Commands.debug;
import net.blockcade.HUB.Commands.fly;
import net.blockcade.HUB.Commands.spawn;
import net.blockcade.HUB.Common.GamePlayer;
import net.blockcade.HUB.Common.Static.GameSearch;
import net.blockcade.HUB.Common.Static.GameServer;
import net.blockcade.HUB.Common.Static.RankManager;
import net.blockcade.HUB.Common.Utils.*;
import net.blockcade.HUB.Common.Utils.Particles.ParticleManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Objects;

public class Main extends JavaPlugin {

    // A comment

    // GamePlayer cache
    public static HashMap<Player, GamePlayer> GamePlayers;
    public static HashMap<GamePlayer, GameSearch> Searching;
    public static WingAPI wingAPI;
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
        wingAPI=new WingAPI(this);
        particleManager=new ParticleManager(this);

        /*
         * Register required events
         */
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new net.blockcade.HUB.Common.Events.Player(),this);
        pm.registerEvents(new Item(), this);

        // Initialize ranks
        new RankManager();

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
        Objects.requireNonNull(getCommand("debug")).setExecutor(new debug());
        Objects.requireNonNull(getCommand("fly")).setExecutor(new fly());
        Objects.requireNonNull(getCommand("spawn")).setExecutor(new spawn());

        Objects.requireNonNull(getCommand("queue")).setExecutor(new QueueCommand());
        Objects.requireNonNull(getCommand("queue")).setTabCompleter(new QueueCommand());
    }

    @Override
    public void onDisable() {
        Main.getSqlConnection().close();
        super.onDisable();
    }

    public static SQL getSqlConnection() {
        return sqlConnection;
    }
}
