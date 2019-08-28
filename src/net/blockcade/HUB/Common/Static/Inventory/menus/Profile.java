package net.blockcade.HUB.Common.Static.Inventory.menus;

import net.blockcade.HUB.Common.Utils.Item;
import net.blockcade.HUB.Common.Utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Profile {

    public static Item getMenuItem() {
        Item item = new Item(Material.PLAYER_HEAD,"&bProfile &7(Right Click)");

        item.setLore(new String[]{"","&7Click to open your Profile",""});
        item.setOnClick(new Item.click() {
            @Override
            public void run(Player p) {
                p.openInventory(Profile.getMenu(p));
            }
        });

        return item;
    }

    private static Inventory getMenu(Player player){
        Inventory inventory = Bukkit.createInventory(null,45, Text.format("&7My Profile"));

        Item placeholder = new Item(Material.BLACK_STAINED_GLASS_PANE,"&r");
        placeholder.setOnClick(new Item.click() {
            @Override
            public void run(Player param1Player) {}
        });
        for(int i=9;i<17;i++){ inventory.setItem(i,placeholder.spigot()); }

        /*
         * Categories for Cosmetics
         */
        Item costumes = new Item(Material.PLAYER_HEAD,"&aCostumes");
        Item preferences = new Item(Material.COMPARATOR,"&cPreferences");
        Item statistics = new Item(Material.BOOK,"&fStatistics");

        costumes.setLore(new String[]{"&7This menu contains all your","&7lobby related cosmetics","","Sub Menus:","&eParticles&7, Heads&7, &eCostumes&7, &ePets&7, &eMorphs"});
        preferences.setLore(new String[]{"&7This menu contains all your","&7network wide preferences","","Sub Menus:","&ePrivacy&7, &eCensorship&7, &eVisuals&7"});
        statistics.setLore(new String[]{"&7This menu contains all your","&7network wide statistics","","Sub Menus:","&ePlayer Search&7"});

        inventory.setItem(29,costumes.spigot());
        inventory.setItem(30,preferences.spigot());
        inventory.setItem(31,statistics.spigot());

        return inventory;
    }

}
