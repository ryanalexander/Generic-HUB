package net.blockcade.HUB.Common.Static.Inventory.menus;

import net.blockcade.HUB.Common.Static.Game;
import net.blockcade.HUB.Common.Utils.Item;
import net.blockcade.HUB.Common.Utils.Text;
import net.blockcade.HUB.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GameMenu {

    public static Item getMenuItem() {
        Item item = new Item(Material.COMPASS,"&eGame Menu &7(Right Click)");

        item.setLore(new String[]{"","&7Click to open Games menu",""});
        item.setOnClick(new Item.click() {
            @Override
            public void run(Player p) {
                p.openInventory(GameMenu.getMenu());
            }
        });

        return item;
    }

    private static Inventory getMenu(){
        Inventory inventory = Bukkit.createInventory(null,27, Text.format("&7Games Menu"));

        int inventory_pos = 10;

        for(Game game : Game.values()){
            Item gameItem = new Item(game.getMaterial(),game.getColor()+game.getName());
            gameItem.setOnClick(new Item.click() {
                @Override
                public void run(Player p) {
                    Main.GamePlayers.get(p).joinQueue(game);
                }
            });
            inventory.setItem(inventory_pos,gameItem.spigot());
            inventory_pos++;
        }

        return inventory;
    }

}
