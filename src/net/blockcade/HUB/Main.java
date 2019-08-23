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
import net.blockcade.HUB.Common.GamePlayer;
import net.blockcade.HUB.Common.Static.GameSearch;
import net.blockcade.HUB.Common.Utils.Item;
import net.blockcade.HUB.Common.Utils.JedisUtil;
import net.blockcade.HUB.Common.Utils.SQL;
import net.blockcade.HUB.Common.Utils.Servers;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;

public class Main extends JavaPlugin {

    // GamePlayer cache
    public static HashMap<Player, GamePlayer> GamePlayers;
    public static HashMap<GamePlayer, GameSearch> Searching;
    public static JedisPool pool;
    private static SQL sqlConnection;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        GamePlayers=new HashMap<>();
        sqlConnection=new SQL(getConfig().getString("sql.host"),3306,getConfig().getString("sql.user"),getConfig().getString("sql.pass"),getConfig().getString("sql.database"));
        GameSearch.StartSearchHandler(this);

        /*
         * Register required events
         */
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new net.blockcade.HUB.Events.Player(),this);
        pm.registerEvents(new Item(), this);

        // BungeeCord Hook
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        // Initialize Jedis
        JedisUtil.init(pool,this);

        /*
         * Register required commands
         */
        getCommand("debug").setExecutor(new debug());
    }

    public static SQL getSqlConnection() {
        return sqlConnection;
    }
}
