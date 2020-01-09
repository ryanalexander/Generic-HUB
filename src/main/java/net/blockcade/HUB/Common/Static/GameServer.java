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

package net.blockcade.HUB.Common.Static;

public class GameServer {
    private int id;
    private String uuid;
    private String name = null;
    private String state;
    private String type;
    private String game;
    private String ip;
    private long lastPoll=0;
    private int port;
    private int playercount;

    public GameServer(String uuid) {
        this.uuid = uuid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setLastPoll(String pollTime){
        this.lastPoll=Long.valueOf(pollTime);
    }

    public void setGame(String game) {
        this.game = game;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type != null ? type : "undefined";
    }

    public void setPlayercount(int playercount) {
        this.playercount = playercount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public long getLastPoll() {
        return lastPoll;
    }

    public String getIp() {
        return ip;
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }

    public int getPort() {
        return port;
    }

    public int getPlayercount() {
        return playercount;
    }

    public String getGame() {
        return game;
    }
}