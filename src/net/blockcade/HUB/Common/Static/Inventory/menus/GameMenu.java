package net.blockcade.HUB.Common.Static.Inventory.menus;

import net.blockcade.HUB.Common.Static.Variables.Game;
import net.blockcade.HUB.Common.Utils.Item;
import net.blockcade.HUB.Common.Utils.Text;
import net.blockcade.HUB.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GameMenu {

    public static Item getMenuItem() {
        Item item = new Item(Material.COMPASS,"&eGame Menu &7(Right Click)");

        item.setLore(new String[]{"","&7Click to open Games menu",""});
        item.setOnClick(new Item.click() {
            @Override
            public void run(Player p) {
                p.openInventory(GameMenu.getMenu(p));
            }
        });

        return item;
    }

    public static Inventory getMenu(Player player){
        Inventory inventory = Bukkit.createInventory(null,54, Text.format("&7Games Menu"));

        int inventory_pos = 12;

        inventory.setItem(9,GameMenu.getMenuItem().spigot());
        inventory.setItem(18, HubMenu.getMenuItem().spigot());
        for(Game game : Game.values()){
            if(game==Game.HUB)continue;
            Item gameItem = new Item(game.getMaterial(),game.getColor()+game.getName());
            gameItem.setOnClick(new Item.click() {
                @Override
                public void run(Player p) {
                    Main.GamePlayers.get(p).joinQueue(game);
                    p.openInventory(GameMenu.getMenu(p));
                }
            });
            gameItem.setLore(game.getDescription().split("\n"));
            inventory.setItem(inventory_pos,gameItem.spigot());
            inventory_pos++;
        }
        if(Main.Searching.containsKey(Main.GamePlayers.get(player))) {
            Item cancelSearch = new Item(Material.BARRIER, "&cCancel Search");
            cancelSearch.setLore(new String[]{"", "&aClicking this will cancel", String.format("&ayour search for &e%s&a.", (Main.Searching.containsKey(Main.GamePlayers.get(player)) ? Main.Searching.get(Main.GamePlayers.get(player)).getGame().getName() : "null")), ""});
            cancelSearch.setOnClick(new Item.click() {
                @Override
                public void run(Player p) {
                    if (Main.Searching.containsKey(Main.GamePlayers.get(p))) {
                        Main.Searching.get(Main.GamePlayers.get(p)).stop();
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.5f, 0.5f);
                        p.getOpenInventory().close();
                    } else {
                        p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 0.5f);
                        p.getOpenInventory().close();
                    }
                }
            });
            inventory.setItem(inventory.getSize()-1, cancelSearch.spigot());
        }

        return inventory;
    }

}
