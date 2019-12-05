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
import net.blockcade.HUB.Common.Utils.ScoreboardManager;
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
import org.bukkit.scheduler.BukkitRunnable;

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

                new BukkitRunnable(){
                    @Override
                    public void run() {
                        ScoreboardManager sm = new ScoreboardManager(player.getName());
                        sm.setGamePlayer(player);
                        sm.enableHealthCounter();
                        String name = "  BLOCKCADE  ";
                        sm.addBlank();
                        sm.addLine("&6&lRank");
                        sm.addLine(" :rank:");
                        sm.addBlank();
                        sm.addLine("&b&lLevel");
                        sm.addLine(" :level:");
                        sm.addBlank();
                        sm.addLine("&7:server_name: &8- &dblockcade.net");
                        sm.addBlank();
                        sm.showFor(player.spigot());

                        new BukkitRunnable() {
                            int offset = 1;
                            int expected_offset = name.length();
                            boolean left = false;

                            @Override
                            public void run() {
                                if (!(player.spigot().isOnline())) {
                                    cancel();
                                    return;
                                }
                                if (offset <= expected_offset) {
                                    left = false;
                                    expected_offset = name.length() - 1;
                                }
                                if (offset >= expected_offset) {
                                    left = true;
                                    expected_offset = 1;
                                }

                                String var = "&9&l" +
                                        name.substring(0, offset) +
                                        "&7&l" + name.substring(offset, offset + 1) + "&f&l" +
                                        (offset + 2 <= name.length() ? name.substring(offset + 1) : "");
                                sm.setDisplayname(var);
                                if (left) {
                                    offset--;
                                } else {
                                    offset++;
                                }
                            }
                        }.runTaskTimer(Main.getPlugin(Main.class), 0L, 1L);
                    }
                }.runTask(Main.getPlugin(Main.class));
                if(player.getRank().getLevel()>=2){
                    Text.sendAll(String.format("&e>&6>&c> &fWelcome %s &c<&6<&e<",player.getRank().getFormatted()+" "+player.getName()), Text.MessageType.TEXT_CHAT);
                }else {
                    player.sendMessage("&fWelcome back!");
                    player.sendMessage("&dDid you know, if you purchase a rank your arrival will be announced!");
                }
                player.spigot().setAllowFlight((player.getRank().getLevel()>1&&player.getPreferenceSettings().isFlight()));
                player.spigot().setPlayerListName(Text.format(player.getRank().getFormatted()+"&r "+player.getName()));
                event.getPlayer().setLevel(player.getLevel());
            }
        }.start();

        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED,Integer.MAX_VALUE,1),false);

        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1,1);


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
        event.setQuitMessage(null);
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
