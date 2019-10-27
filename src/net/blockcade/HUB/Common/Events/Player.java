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

package net.blockcade.HUB.Common.Events;

import net.blockcade.HUB.Common.GamePlayer;
import net.blockcade.HUB.Common.PreferenceSettings;
import net.blockcade.HUB.Common.Static.Inventory.menus.CosmeticsMenu;
import net.blockcade.HUB.Common.Static.Inventory.menus.GameMenu;
import net.blockcade.HUB.Common.Static.Inventory.menus.HubMenu;
import net.blockcade.HUB.Common.Static.Inventory.menus.ProfileMenu;
import net.blockcade.HUB.Common.Static.Preferances.ChatVisibility;
import net.blockcade.HUB.Common.Utils.Text;
import net.blockcade.HUB.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * This class was created for the soul purpose of having a single place for all Player related events, instead of creating multiple classes.
 */
public class Player implements Listener {

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event){
        event.getPlayer().teleport(event.getPlayer().getLocation().getWorld().getSpawnLocation());
        event.getPlayer().getActivePotionEffects().clear();
        event.setJoinMessage(null);

        GamePlayer player;

        // Check if GamePlayer already exists (If it does, something went wrong!)
        if(Main.GamePlayers.containsKey(event.getPlayer()))Main.GamePlayers.remove(event.getPlayer());
        event.getPlayer().setPlayerListHeaderFooter(Text.format("&fWelcome to &d&lBlockcade Games"),Text.format(String.format("&fConnected to &a%s&f!",Main.network.serverName)));

        player=new GamePlayer(event.getPlayer());
        new Thread() {
            @Override
            public void run() {
                player.BuildPlayer();
                if(!player.isBuilt()){
                    player.spigot().kickPlayer(Text.format("&cAn error has occurred in the connection."));
                    return;
                }
                if(player.getRank().getLevel()>=2){
                    event.setJoinMessage(Text.format(String.format("&e>&6>&c> &fWelcome %s &c<&6<&e<",player.getName())));
                }
                player.spigot().setAllowFlight((player.getRank().getLevel()>1&&player.getPreferenceSettings().isFlight()));
            }
        }.start();

        player.spigot().addPotionEffect(new PotionEffect(PotionEffectType.SPEED,Integer.MAX_VALUE,1),false);

        player.spigot().playSound(player.spigot().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1,1);

        event.getPlayer().setLevel(player.getLevel());

        event.getPlayer().setFoodLevel(20);
        event.getPlayer().setHealth(20);
        event.getPlayer().setGameMode(GameMode.ADVENTURE);


        Main.GamePlayers.put(event.getPlayer(),player);

        player.spigot().getInventory().clear();

        player.spigot().getInventory().setItem(0, GameMenu.getMenuItem().spigot());
        player.spigot().getInventory().setItem(1, ProfileMenu.getMenuItem(player.spigot()).spigot());
        player.spigot().getInventory().setItem(4, CosmeticsMenu.getMenuItem().spigot());
        player.spigot().getInventory().setItem(8, HubMenu.getMenuItem().spigot());
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

            if(shown)p.sendMessage(Text.format(player.getRank().getColor()+player.getRank().name()+" &r&7"+player.getName()+": ")+((player.getRank().getLevel()>=2)?Text.format(e.getMessage()):e.getMessage()));
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
