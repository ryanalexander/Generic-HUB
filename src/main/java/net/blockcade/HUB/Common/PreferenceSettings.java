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

import net.blockcade.HUB.Common.Static.Preferances.*;
import net.blockcade.HUB.Common.Utils.SQL;
import net.blockcade.HUB.Main;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.ResultSet;
import java.sql.SQLException;

import static net.blockcade.HUB.Common.Static.Preferances.FilterVisibility.OPEN;

public class PreferenceSettings {

    GamePlayer player;
    FilterVisibility filterVisibility;
    VisualQuality visualQuality = VisualQuality.LOW;
    PlayerVisibility playerVisibility;
    ChatVisibility chatVisibility;
    PetVisibility petVisibility;
    boolean flight;

    /**
     *
     * @param player Instance of GamePlayer for specific player.
     * @since 5/08/2019
     */
    public PreferenceSettings(GamePlayer player) {
        this.player=player;
        BuildPreferences();
    }

    public FilterVisibility getFilterVisibility() {
        return filterVisibility;
    }

    public ChatVisibility getChatVisibility() {
        return chatVisibility;
    }

    public VisualQuality getVisualQuality() {
        return visualQuality;
    }

    public GamePlayer getPlayer() {
        return player;
    }

    public PetVisibility getPetVisibility() {
        return petVisibility;
    }

    public void setFlight(boolean flight) {
        this.flight = flight;
        SavePreferences();
    }

    public void setChatVisibility(ChatVisibility chatVisibility) {
        this.chatVisibility = chatVisibility;
        SavePreferences();
    }

    public void setPetVisibility(PetVisibility petVisibility) {
        this.petVisibility = petVisibility;
        SavePreferences();
    }

    public void setVisualQuality(VisualQuality visualQuality) {
        this.visualQuality = visualQuality;
    }

    public void setFilterVisibility(FilterVisibility filterVisibility) {
        this.filterVisibility = filterVisibility;
    }

    /**
     *
     * @param playerVisibility enum specifying what players should be shown
     */
    public void setPlayerVisibility(PlayerVisibility playerVisibility) {
        this.playerVisibility = playerVisibility;
        SavePreferences();
    }

    public boolean isFlight() {
        return flight;
    }

    public PlayerVisibility getPlayerVisibility() {
        return playerVisibility;
    }

    public void SavePreferences() {
        FileConfiguration config = Main.getPlugin(Main.class).getConfig();
        SQL sql = new SQL(config.getString("sql.host"),config.getInt("sql.port"),config.getString("sql.user"),config.getString("sql.pass"),config.getString("sql.database"));
        sql.query(String.format("UPDATE `preferences` SET " +
                "`flight`='%s', " +
                "`player_visibility`='%s', " +
                "`pet_visibility`='%s', " +
                "`chat_visibility`='%s', " +
                "`particle_quality`='%s', " +
                "`party_privacy`='%s', " +
                "`chat_filter`='%s' " +
                "WHERE `uuid`='%s';",flight?1:0,playerVisibility,petVisibility,chatVisibility,"MEDIUM","ALL_INVITE",filterVisibility,player.getUuid()),true);
    }

    public void BuildPreferences() {
        FileConfiguration config = Main.getPlugin(Main.class).getConfig();
        SQL sql = new SQL(config.getString("sql.host"),config.getInt("sql.port"),config.getString("sql.user"),config.getString("sql.pass"),config.getString("sql.database"));
        ResultSet results = sql.query(String.format("SELECT * FROM `preferences` WHERE `uuid`='%s' LIMIT 1;",player.getUuid()));
        try {
            while(results.next()){
                this.setPlayerVisibility(PlayerVisibility.valueOf(results.getString("player_visibility")));
                this.setChatVisibility(ChatVisibility.valueOf(results.getString("chat_visibility")));
                this.setFlight(results.getBoolean("flight"));
                this.setPetVisibility(PetVisibility.valueOf(results.getString("pet_visibility")));
                this.setFilterVisibility(FilterVisibility.valueOf(results.getString("chat_filter")));
                return;
            }
            }catch (SQLException e){
                System.out.println("| ---------------------------- |");
                System.out.println("|  This error was posted by PreferenceSettings.java BuildPreferences");
                e.printStackTrace();
                System.out.println("| ---------------------------- |");
            }
            sql.query(String.format("INSERT INTO `preferences` (`uuid`,`flight`,`player_visibility`,`pet_visibility`,`chat_visibility`) VALUES ('%s','0','%s','%s','%s')",player.getUuid(),PlayerVisibility.ALL_SHOWN,PetVisibility.ALL_SHOWN,ChatVisibility.ALL_SHOWN),true);
    }
}
