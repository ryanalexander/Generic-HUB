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

public class PreferencesMenu {

    public static Item getMenuItem(Player player) {
        ItemStack is = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta sm = (SkullMeta)is.getItemMeta();
        if(player!=null)sm.setOwningPlayer(player);
        is.setItemMeta(sm);
        Item item = new Item(is,"&cPreferences");

        item.setLore(new String[]{"","&7This menu contains all your","&7network wide preferences.","","&7Sub Menus","&ePrivacy&f, &eCensorship&f, &eVisuals"});
        item.setOnClick(new Item.click() {
            @Override
            public void run(Player p) {
                p.openInventory(getMenu(p));
            }
        });
        return item;
    }

    private static Inventory getMenu(Player player){
        Inventory inventory = Bukkit.createInventory(null,45, Text.format("&7Preferences"));

        Manager.addProfileHeader(inventory, Main.GamePlayers.get(player));

        /*
         * Categories for Cosmetics
         */
        Item cosmetics = new Item(CosmeticsMenu.getMenuItem().spigot(),"&aCosmetics");
        Item preferences = new Item(Material.COMPARATOR,"&cPreferences");
        Item statistics = new Item(Material.BOOK,"&fStatistics");

        cosmetics.setLore(new String[]{"&7This menu contains all your","&7lobby related cosmetics","","Sub Menus:","&eParticles&7, &eHeads&7, &eCostumes&7, &ePets&7, &eMorphs"});
        preferences.setLore(new String[]{"&7This menu contains all your","&7network wide preferences","","Sub Menus:","&ePrivacy&7, &eCensorship&7, &eVisuals&7"});
        statistics.setLore(new String[]{"&7This menu contains all your","&7network wide statistics","","Sub Menus:","&ePlayer Search&7"});

        inventory.setItem(30, CosmeticsMenu.getMenuItem().spigot());
        inventory.setItem(31,preferences.spigot());
        inventory.setItem(32,statistics.spigot());

        return inventory;
    }

}
