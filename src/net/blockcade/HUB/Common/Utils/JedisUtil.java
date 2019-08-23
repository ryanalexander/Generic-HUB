package net.blockcade.HUB.Common.Utils;

import net.blockcade.HUB.Main;

public class JedisUtil {
    public static void init(redis.clients.jedis.JedisPool pool, Main c) {
        ClassLoader previous = Thread.currentThread().getContextClassLoader();
        System.out.println("[Redis] Connection began and cached.");
        c.pool = new redis.clients.jedis.JedisPool("mc2.stelch.gg");
        Thread.currentThread().setContextClassLoader(previous);
    }

    public static void end(redis.clients.jedis.JedisPool pool) {
        pool.close();
    }
}
