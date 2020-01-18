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

import net.blockcade.HUB.Common.GamePlayer;
import net.blockcade.HUB.Common.Static.Inventory.Manager;
import net.blockcade.HUB.Common.Utils.Item;
import net.blockcade.HUB.Common.Utils.Text;
import net.blockcade.HUB.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class FriendsMenu {
    private static int[] avalible_slots = new int[]{20,21,22,23,24,25,29,30,31,32,33,34,38,39,40,41,42,43,47,48,49,50,51,52};

    public static Item getMenuItem() {
        ItemStack friends_item = Item.itemWithBase64(new ItemStack(Material.PLAYER_HEAD),"eyJ0aW1lc3RhbXAiOjE1NjcyMjU0MjEyNjcsInByb2ZpbGVJZCI6IjJkYzc3YWU3OTQ2MzQ4MDI5NDI4MGM4NDIyNzRiNTY3IiwicHJvZmlsZU5hbWUiOiJzYWR5MDYxMCIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDM1YmFiODA1Njc2YjQ1YWNhYTA3ODc0Njc5OGQ3OGFiZmRmMTZlNzQyZTM5YzE4MTFmYTU5MGZhYzYzY2M3OSJ9fX0=");
        Item item = new Item(friends_item,"&eFriends");
        item.setLore(new String[]{"&7This menu contains","&7a list of all your friends"});
        item.setOnClick(new Item.click() {
            @Override
            public void run(Player p) {
                p.openInventory(getMenu(p));
            }
        });

        return item;
    }

    private static Inventory getMenu(Player player){
        Inventory inventory = Bukkit.createInventory(null,45, Text.format("&7My Friends"));
        GamePlayer p = Main.GamePlayers.get(player);

        Manager.addProfileHeader(inventory, Main.GamePlayers.get(player));

        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(Main.class), new Runnable() {
            @Override
            public void run() {
                int inventory_pos = 0;
                ArrayList<GamePlayer> friends = p.getFriends();
                for(GamePlayer friend : friends){
                    ItemStack is = new ItemStack(Material.PLAYER_HEAD);
                    SkullMeta sm = (SkullMeta)is.getItemMeta();
                    sm.setOwningPlayer(Bukkit.getOfflinePlayer(friend.getUuid()));
                    is.setItemMeta(sm);
                    Item item = new Item(is,friend.getName());
                    item.setLore(new String[]{"","&7Level: &e"+friend.getLevel(),"&7Rank: &e"+friend.getRank().getFormatted(),"","&7Connected to &eHUB02&7."});
                    inventory.setItem(avalible_slots[inventory_pos],item.spigot());
                    inventory_pos++;
                }
            }
        });
        return inventory;
    }

}