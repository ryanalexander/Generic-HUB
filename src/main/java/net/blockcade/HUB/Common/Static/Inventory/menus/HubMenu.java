/*
 *
 *
 *  © Stelch Software 2020, distribution is strictly prohibited
 *  Blockcade is a company of Stelch Software
 *
 *  Changes to this file must be documented on push.
 *  Unauthorised changes to this file are prohibited.
 *
 *  @author Ryan W
 * @since (DD/MM/YYYY) 18/1/2020
 */

package net.blockcade.HUB.Common.Static.Inventory.menus;

import net.blockcade.HUB.Common.Static.GameSearch;
import net.blockcade.HUB.Common.Static.GameServer;
import net.blockcade.HUB.Common.Static.Variables.Game;
import net.blockcade.HUB.Common.Utils.Item;
import net.blockcade.HUB.Common.Utils.Text;
import net.blockcade.HUB.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class HubMenu {

    private static int[] avalible_slots = new int[]{11,12,13,14,15,16,20,21,22,23,24,25,29,30,31,32,33,34,38,39,40,41,42,43,47,48,49,50,51,52};

    public static Item getMenuItem() {
        Item item = new Item(Game.HUB.getMaterial(),"&fLobby Menu &7(Right Click)");

        item.setLore(new String[]{"","&7Click to open Lobby menu",""});
        item.setOnClick(new Item.click() {
            @Override
            public void run(Player p) {
                p.openInventory(HubMenu.getMenu(p));
            }
        });

        return item;
    }

    public static Inventory getMenu(Player player){
        Inventory inventory = Bukkit.createInventory(null,54, Text.format("&7Lobby Menu"));

        int inventory_pos = 0;

        inventory.setItem(9,GameMenu.getMenuItem().spigot());
        inventory.setItem(18, HubMenu.getMenuItem().spigot());

        if(GameSearch.Available_games.containsKey(Game.HUB)) {
            for (GameServer server : GameSearch.Available_games.get(Game.HUB)) {
                if(inventory_pos>avalible_slots.length)continue;
                Item gameItem = new Item((Main.network.serverName.equals(server.getName()) ? Material.LIME_STAINED_GLASS_PANE : Material.WHITE_STAINED_GLASS_PANE), (Main.network.serverName.equals(server.getName()) ? "&a" : "&f") + server.getName());
                gameItem.setOnClick(new Item.click() {
                    @Override
                    public void run(Player p) {
                        Main.GamePlayers.get(p).sendServer(server);
                        p.closeInventory();
                    }
                });
                gameItem.setLore(new String[]{"", "Players: " + server.getPlayercount()});
                inventory.setItem(avalible_slots[inventory_pos], gameItem.spigot());
                inventory_pos++;
            }
        }else {
            Item no_servers = new Item(Material.REDSTONE_BLOCK,"&cNo Servers");
            no_servers.setLore(new String[]{"&fFailed to find any servers","&fwith the filter {serverType:HUB}"});
            no_servers.setOnClick(new Item.click() {
                @Override
                public void run(Player p) {

                }
            });
            inventory.setItem(22,no_servers.spigot());
        }

        return inventory;
    }

}
