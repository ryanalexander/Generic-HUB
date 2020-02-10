package net.blockcade.HUB.Common.Utils;

import net.blockcade.HUB.Main;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.JedisPool;

public class JedisUtil {
    private static boolean closed=true;

    public static void init(redis.clients.jedis.JedisPool pool, Main c) {
        ClassLoader previous = Thread.currentThread().getContextClassLoader();
        System.out.println("[Redis] Connection began and cached.");
        c.pool = new redis.clients.jedis.JedisPool("mc2.stelch.gg");
        Thread.currentThread().setContextClassLoader(previous);
        closed=false;
        new BukkitRunnable(){
            @Override
            public void run() {
                isOpen(c);
            }
        }.runTaskTimerAsynchronously(c,0L,100);
    }
    public static JedisPool init() {
        ClassLoader previous = Thread.currentThread().getContextClassLoader();
        System.out.println("[Redis] Connection began and cached.");
        Thread.currentThread().setContextClassLoader(previous);
        closed=false;
        return new JedisPool("mc2.stelch.gg");
    }

    private static void isOpen(Main main){
        if(!closed){
            // Check if Jedis Pool is open
            if(Main.pool.isClosed()){
                // Pool has closed
                init(Main.pool,main);
            }
        }
    }

    public static void end(redis.clients.jedis.JedisPool pool) {
        pool.destroy();
    }
}
