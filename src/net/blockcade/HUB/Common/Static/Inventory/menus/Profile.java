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

        Manager.addProfileHeader(inventory, Main.GamePlayers.get(player));

        /*
         * Categories for Cosmetics
         */
        ItemStack cosmetics_item = Item.itemWithBase64(new ItemStack(Material.PLAYER_HEAD),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDQzZDRiN2FjMjRhMWQ2NTBkZGY3M2JkMTQwZjQ5ZmMxMmQyNzM2ZmMxNGE4ZGMyNWMwZjNmMjlkODVmOGYifX19");
        Item costumes = new Item(cosmetics_item,"&aCosmetics");
        Item preferences = new Item(Material.COMPARATOR,"&cPreferences");
        Item statistics = new Item(Material.BOOK,"&fStatistics");

        costumes.setLore(new String[]{"&7This menu contains all your","&7lobby related cosmetics","","Sub Menus:","&eParticles&7, &eHeads&7, &eCostumes&7, &ePets&7, &eMorphs"});
        preferences.setLore(new String[]{"&7This menu contains all your","&7network wide preferences","","Sub Menus:","&ePrivacy&7, &eCensorship&7, &eVisuals&7"});
        statistics.setLore(new String[]{"&7This menu contains all your","&7network wide statistics","","Sub Menus:","&ePlayer Search&7"});

        inventory.setItem(30,costumes.spigot());
        inventory.setItem(31,preferences.spigot());
        inventory.setItem(32,statistics.spigot());

        return inventory;
    }

}
