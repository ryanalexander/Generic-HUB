package net.blockcade.HUB.Common.Static.Inventory.menus;

import net.blockcade.HUB.Common.Static.Inventory.Manager;
import net.blockcade.HUB.Common.Static.Inventory.menus.Preferences.Censorship;
import net.blockcade.HUB.Common.Static.Inventory.menus.Preferences.Visual;
import net.blockcade.HUB.Common.Utils.Item;
import net.blockcade.HUB.Common.Utils.Text;
import net.blockcade.HUB.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PreferencesMenu {

    public static Item getMenuItem(Player player) {
        Item item = new Item(Material.COMPARATOR,"&cPreferences");

        item.setLore("","&7This menu contains all your","&7network wide preferences.","","&7Sub Menus","&ePrivacy&f, &eCensorship&f, &eVisuals");
        item.setOnClick((p)->{
            p.openInventory(getMenu(p));
        });
        return item;
    }

    public static Inventory getMenu(Player player){
        Inventory inventory = Bukkit.createInventory(null,45, Text.format("&7Preferences"));

        Manager.addProfileHeader(inventory, Main.GamePlayers.get(player));

        /*
         Privacy
         Censorship
         Visuals
         */

        /*
         * Categories for Cosmetics
         */
        Item privacy = new Item(Material.PAPER,"&fPrivacy");

        privacy.setLore("&fThis menu is coming soon");

        privacy.setOnClick((p)->{p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO,1,1);});

        inventory.setItem(29, privacy.spigot());
        inventory.setItem(31, Censorship.getMenuItem().spigot());
        inventory.setItem(33, Visual.getMenuItem().spigot());


        return inventory;
    }

}
