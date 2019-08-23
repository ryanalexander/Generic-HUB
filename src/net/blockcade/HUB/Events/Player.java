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

package net.blockcade.HUB.Events;

import net.blockcade.HUB.Common.GamePlayer;
import net.blockcade.HUB.Common.Static.Inventory.menus.GameMenu;
import net.blockcade.HUB.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * This class was created for the soul purpose of having a single place for all Player related events, instead of creating multiple classes.
 */
public class Player implements Listener {

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event){

        GamePlayer player;

        // Check if GamePlayer already exists (If it does, something went wrong!)
        if(Main.GamePlayers.containsKey(event.getPlayer()))Main.GamePlayers.remove(event.getPlayer());

        player=new GamePlayer(event.getPlayer());
        player.BuildPlayer();

        Main.GamePlayers.put(event.getPlayer(),player);

        player.spigot().getInventory().setItem(0, GameMenu.getMenuItem().spigot());
    }

    @EventHandler
    public void PlayerLeave(PlayerQuitEvent event){
        // Remove cachedGamePlayer
        if(Main.GamePlayers.containsKey(event.getPlayer()))Main.GamePlayers.remove(event.getPlayer());
    }

}
