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

import net.blockcade.HUB.Common.Static.Preferances.ChatVisibility;
import net.blockcade.HUB.Common.Static.Preferances.FilterVisibility;
import net.blockcade.HUB.Common.Static.Preferances.PetVisibility;
import net.blockcade.HUB.Common.Static.Preferances.PlayerVisibility;
import net.blockcade.HUB.Common.Utils.SQL;
import net.blockcade.HUB.Main;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.ResultSet;
import java.sql.SQLException;

import static net.blockcade.HUB.Common.Static.Preferances.FilterVisibility.OPEN;

public class PreferenceSettings {

    GamePlayer player;
    FilterVisibility filterVisibility = OPEN;
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

    public GamePlayer getPlayer() {
        return player;
    }

    public PetVisibility getPetVisibility() {
        return petVisibility;
    }

    public void setFlight(boolean flight) {
        this.flight = flight;
    }

    public void setChatVisibility(ChatVisibility chatVisibility) {
        this.chatVisibility = chatVisibility;
    }

    public void setPetVisibility(PetVisibility petVisibility) {
        this.petVisibility = petVisibility;
    }

    /**
     *
     * @param playerVisibility enum specifying what players should be shown
     */
    public void setPlayerVisibility(PlayerVisibility playerVisibility) {
        this.playerVisibility = playerVisibility;
    }

    public boolean isFlight() {
        return flight;
    }

    public PlayerVisibility getPlayerVisibility() {
        return playerVisibility;
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
