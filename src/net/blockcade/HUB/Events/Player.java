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
import net.blockcade.HUB.Common.PreferenceSettings;
import net.blockcade.HUB.Common.Static.Inventory.menus.Cosmetics;
import net.blockcade.HUB.Common.Static.Inventory.menus.GameMenu;
import net.blockcade.HUB.Common.Static.Inventory.menus.Profile;
import net.blockcade.HUB.Common.Static.Preferances.ChatVisibility;
import net.blockcade.HUB.Common.Utils.Text;
import net.blockcade.HUB.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
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
        player.spigot().getInventory().setItem(1, Profile.getMenuItem().spigot());
        player.spigot().getInventory().setItem(4, Cosmetics.getMenuItem().spigot());
    }

    @EventHandler
    public void PlayerLeave(PlayerQuitEvent event){
        // Remove cachedGamePlayer
        if(Main.GamePlayers.containsKey(event.getPlayer()))Main.GamePlayers.remove(event.getPlayer());
    }

    @EventHandler
    public void PlayerChat(AsyncPlayerChatEvent e){
        GamePlayer player = Main.GamePlayers.get(e.getPlayer());
        if(player==null)return;
        for(org.bukkit.entity.Player p : Bukkit.getOnlinePlayers()){
            boolean shown = false;
            GamePlayer target = Main.GamePlayers.get(p);
            if(target==null)continue;
            PreferenceSettings preferenceSettings = target.getPreferenceSettings();
            if(preferenceSettings.getChatVisibility().equals(ChatVisibility.ALL_SHOWN)){shown=true;}
            if(preferenceSettings.getChatVisibility().equals(ChatVisibility.FRIENDS)){if(target.isFriend(player))shown=true;}
            if(preferenceSettings.getChatVisibility().equals(ChatVisibility.PARTY)){if(target.getParty().hasMember(player))shown=true;}

            if(shown)target.sendMessage(player.getRank().getColor()+player.getRank().name()+" &r&7"+player.getName()+": "+((player.getRank().getLevel()>=2)?Text.format(e.getMessage()):e.getMessage()));
        }
        e.setCancelled(true);
    }

    /*
     * Remove Player damage and Health decline
     */
    @EventHandler
    public void PlayerDamage(EntityDamageEvent e){if(e.getEntity().getType().equals(EntityType.PLAYER))e.setCancelled(true);}
    @EventHandler
    public void PlayerHunger(FoodLevelChangeEvent e){if(e.getEntity().getType().equals(EntityType.PLAYER))e.setCancelled(true);}


}
