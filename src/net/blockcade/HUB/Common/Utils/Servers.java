package net.blockcade.HUB.Common.Utils;

import net.blockcade.HUB.Common.Static.GameServer;
import net.blockcade.HUB.Common.Static.Variables.Game;
import net.blockcade.HUB.Main;
import redis.clients.jedis.Jedis;

import java.util.*;

public class Servers {

    public static HashMap<Game, ArrayList<GameServer>> getAvailableServers() {

        HashMap<Game, ArrayList<GameServer>> servers = new HashMap<>();

        for(GameServer server : getServers().values()){
            Game game;
            try {game=Game.valueOf(server.getGame().toUpperCase());}catch(Exception e){continue;}
            if(!(server.getState().contains("LOBBY"))){continue;}
            if(server.getPlayercount()>=Game.valueOf(server.getGame().toUpperCase()).getMaxPlayers()){continue;}
            if(!(servers.containsKey(game)))servers.put(game,new ArrayList<>());
            servers.get(game).add(server);
        }

        return servers;

    }

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
                if(data==null||data.equals("null")){continue;}
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
