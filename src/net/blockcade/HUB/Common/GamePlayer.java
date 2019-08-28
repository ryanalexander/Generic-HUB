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

package net.blockcade.HUB.Common;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.blockcade.HUB.Common.Static.GameServer;
import net.blockcade.HUB.Common.Static.Variables.Game;
import net.blockcade.HUB.Common.Static.GameSearch;
import net.blockcade.HUB.Common.Static.Variables.Ranks;
import net.blockcade.HUB.Common.Utils.SQL;
import net.blockcade.HUB.Common.Utils.Text;
import net.blockcade.HUB.Main;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class GamePlayer {

    private Player player;
    private UUID uuid;

    private PreferenceSettings preferenceSettings;
    private boolean isBuilt=false;
    private String name;
    private Ranks rank;
    private GameParty party=null;

    /**
     *
     * @param player Bukkit Player to be resolved from SQL Database.
     */
    public GamePlayer(Player player){this.player=player;}

    /**
     * This function will send the GamePlayer a formatted message, &( [0-9] [a-f] [i-0] )
     * sent by Text Chat
     * @param message non formatted message to be sent to player
     * @since 5/08/2019
     */
    public void sendMessage(String message) { Text.sendMessage(player,message, Text.MessageType.TEXT_CHAT); }

    /**
     * This function will send the GamePlayer a formatted message, &( [0-9] [a-f] [i-0] )
     * sent by Title Screen
     * @param message non formatted message to be sent to player
     * @since 5/08/2019
     */
    public void sendTitle(String message) { Text.sendMessage(player,message, Text.MessageType.TITLE); }

    /**
     * This function will send the GamePlayer a formatted message, &( [0-9] [a-f] [i-0] )
     * sent by Action Bar
     * @param message non formatted message to be sent to player
     * @since 5/08/2019
     */
    public void sendActionBar(String message) { Text.sendMessage(player,message, Text.MessageType.ACTION_BAR); }

    /**
     *
     * @param server BungeeCord server in which to send the player to
     */
    public void sendServer(GameServer server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server.getName());
        player.sendPluginMessage(Main.getPlugin(Main.class), "BungeeCord", out.toByteArray());
    }

    public void joinQueue(Game game){
        if(Main.Searching.containsKey(this)&&Main.Searching.get(this).getGame().equals(game)){return;}
        GameSearch search = new GameSearch(this,game);
        if(Main.Searching.containsKey(player))Main.Searching.remove(this);
        Main.Searching.put(this,search);
    }

    /**
     *
     * @return GamePlayer UUID (Online Version)
     * @since 5/08/2019
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     *
     * @return Get GamePlayer rank
     * @since 5/08/2019
     */
    public Ranks getRank() {
        if(!isBuilt)throw new Error("Failed getting rank, the specified GamePlayer has not been built.");
        return rank;
    }

    /**
     *
     * @return Player display name (Nickname)
     */
    public String getName() {
        return name;
    }
    /**
     * TODO BungeeCord implementation for parties
     * @return Player party, fetched by BungeeCord if updated.
     * @since 5/08/2019
     */
    public GameParty getParty() {
        if(!isBuilt)throw new Error("Failed getting party, the specified GamePlayer has not been built.");
        return party;
    }

    /**
     *
     * TODO Add database integration
     * @param player Target player to check relationship to
     * @return Weather the two players are friends
     */
    public boolean isFriend(GamePlayer player){
        return true;
    }

    /**
     *
     * @param rank set a player rank, use with caution!
     * @since 5/08/2019
     */
    public void setRank(Ranks rank) {
        if(!isBuilt)throw new Error("Failed setting rank, the specified GamePlayer has not been built.");
        this.rank = rank;
        Main.getSqlConnection().query(String.format("UPDATE `players` SET `rank`='%s' WHERE `uuid`='%s'",rank.name().toUpperCase(),uuid),true);
    }

    /**
     *
     * @param party Override existing GameParty, use of this function can cause issues!
     * @since 5/08/2019
     */
    public void setParty(GameParty party) {
        if(!isBuilt)throw new Error("Failed setting party, the specified GamePlayer has not been built.");
        this.party = party;
    }

    public PreferenceSettings getPreferenceSettings() {
        if(!isBuilt)throw new Error("Failed getting preferences, the specified GamePlayer has not been built.");
        return preferenceSettings;
    }

    public void BuildPlayer() {
        SQL sql = Main.getSqlConnection();
        if(sql.isInitilized()){
            ResultSet results = sql.query(String.format("SELECT * FROM `players` WHERE `username`='%s' LIMIT 1;",this.player.getName()));
            try {
                while(results.next()){
                    this.name=this.player.getName();
                    this.rank=(Ranks.valueOf(results.getString("rank").toUpperCase()));
                    this.uuid=(UUID.fromString(results.getString("uuid")));
                    this.preferenceSettings=new PreferenceSettings(this);
                }
                this.isBuilt=true;
                return;
            }catch (SQLException e){
                System.out.println("| ---------------------------- |");
                System.out.println("|  This error was posted by GamePlayer.java BuildPlayer");
                e.printStackTrace();
                System.out.println("| ---------------------------- |");
            }
            player.kickPlayer(Text.format("&cAn error has occurred in the connection, try again later."));
        }else {
            throw new Error("SQL server is not initialized, failed to build GamePlayer");
        }
    }

    public Player spigot() {return this.player;}

}
