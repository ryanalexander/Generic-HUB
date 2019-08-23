package net.blockcade.HUB.Common.Utils;

import net.blockcade.HUB.Common.Static.GameServer;
import net.blockcade.HUB.Main;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Servers {
    public static HashMap<String, GameServer> getServers() {
        HashMap<String, GameServer> gameServers = new HashMap<>();

        try (Jedis jedis = Main.pool.getResource()){
            Set<String> servers = jedis.keys("SERVER|*");
            Iterator<String> iterator = servers.iterator();
            while(iterator.hasNext()){
                String argument = iterator.next();
                String[] args = argument.split("[|]");
                String uuid = args[1];
                String field = args[2];
                String data = jedis.get(argument);
                GameServer server;
                if(gameServers.containsKey(uuid)){server=gameServers.get(uuid);}else{server=new GameServer(uuid);gameServers.put(uuid,server);}

                switch(field.toUpperCase()){
                    case "NAME":
                        server.setName(data.toUpperCase());
                        break;
                    case "IPPORT":
                        String[] i = data.split("[:]");
                        server.setIp(i[0]);
                        server.setPort(Integer.parseInt(i[1]));
                        break;
                    case "GAME":
                        server.setGame(data);
                        break;
                    case "STATE":
                        server.setState(data);
                        break;
                    case "TYPE":
                        server.setType(data.toUpperCase());
                        break;
                    case "PLAYERCOUNT":
                        server.setPlayercount(Integer.valueOf(data));
                        break;
                    case "LAST_POLL":
                        server.setLastPoll(data);
                        break;
                }
                gameServers.put(uuid,server);
            }
        }
        return gameServers;
    }
}
