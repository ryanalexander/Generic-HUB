package net.blockcade.HUB.Common.Static.Inventory.menus;

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

public class ProfileMenu {

    public static Item getMenuItem(Player player) {
        ItemStack is = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta sm = (SkullMeta)is.getItemMeta();
        if(player!=null)sm.setOwningPlayer(player);
        is.setItemMeta(sm);
        Item item = new Item(is,"&bProfile &7(Right Click)");

        item.setLore("","&7Click to open your Profile","");
        item.setOnClick(new Item.click() {
            @Override
            public void run(Player p) {
                p.openInventory(ProfileMenu.getMenu(p));
            }
        });
        return item;
    }

    private static Inventory getMenu(Player player){
        Inventory inventory = Bukkit.createInventory(null,45, Text.format("&7My Profile"));

        Manager.addProfileHeader(inventory, Main.GamePlayers.get(player));

        /*
         * Categories for Cosmetics
         */
        Item statistics = new Item(Material.BOOK,"&fStatistics");

        statistics.setLore("&7This menu contains all your","&7network wide statistics","","Sub Menus:","&ePlayer Search&7");
        statistics.setOnClick((p)->{
            return;
        });

        inventory.setItem(30, CosmeticsMenu.getMenuItem().spigot());
        inventory.setItem(31,PreferencesMenu.getMenuItem(player).spigot());
        inventory.setItem(32,statistics.spigot());

        return inventory;
    }

}
