package net.blockcade.HUB.Common.Static.Inventory.menus;

import net.blockcade.HUB.Common.Static.GameSearch;
import net.blockcade.HUB.Common.Static.GameServer;
import net.blockcade.HUB.Common.Static.Variables.Game;
import net.blockcade.HUB.Common.Utils.Item;
import net.blockcade.HUB.Common.Utils.Text;
import net.blockcade.HUB.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class HUB {

    public static Item getMenuItem() {
        Item item = new Item(Game.HUB.getMaterial(),"&fLobby Menu &7(Right Click)");

        item.setLore(new String[]{"","&7Click to open Lobby menu",""});
        item.setOnClick(new Item.click() {
            @Override
            public void run(Player p) {
                p.openInventory(HUB.getMenu(p));
            }
        });

        return item;
    }

    public static Inventory getMenu(Player player){
        Inventory inventory = Bukkit.createInventory(null,54, Text.format("&7Lobby Menu"));

        int inventory_pos = 12;

        Item games = new Item(Material.BOOKSHELF,"&fMain Games");
        games.setOnClick(new Item.click() {
            @Override
            public void run(Player p) {
                p.openInventory(GameMenu.getMenu(p));
            }
        });
        games.setLore(new String[]{"&fOur main games based","off vanilla minecraft."});

        Item lobbies = new Item(Material.IRON_BLOCK,"&aLobbies");
        lobbies.setOnClick(new Item.click() {
            @Override
            public void run(Player p) {
                p.openInventory(HUB.getMenu(p));
            }
        });
        lobbies.setEnchanted(true);
        lobbies.setLore(new String[]{"&fTeleport between the","network lobbies."});

        inventory.setItem(9,games.spigot());
        inventory.setItem(18,lobbies.spigot());

        if(GameSearch.Available_games.containsKey(Game.HUB)) {
            for (GameServer server : GameSearch.Available_games.get(Game.HUB)) {
                Item gameItem = new Item(Game.HUB.getMaterial(), (Main.network.serverName.equals(server.getName()) ? "&a" : "&f") + server.getName());
                if (Main.network.serverName.equals(server.getName())) {
                    gameItem.setEnchanted(true);
                }
                gameItem.setOnClick(new Item.click() {
                    @Override
                    public void run(Player p) {
                        Main.GamePlayers.get(p).sendServer(server);
                        p.closeInventory();
                    }
                });
                gameItem.setLore(new String[]{"", "Players: " + server.getPlayercount()});
                inventory.setItem(inventory_pos, gameItem.spigot());
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
