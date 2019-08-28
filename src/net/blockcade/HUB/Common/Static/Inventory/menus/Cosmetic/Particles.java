package net.blockcade.HUB.Common.Static.Inventory.menus.Cosmetic;


import net.blockcade.HUB.Common.Utils.Item;
import net.blockcade.HUB.Common.Utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Particles {

    public static Item getMenuItem() {
        Item item = new Item(Material.NETHER_STAR,"&aCosmetics &7(Right Click)");

        item.setLore(new String[]{"","&7Click to open Cosmetics menu",""});
        item.setOnClick(new Item.click() {
            @Override
            public void run(Player p) {
                p.openInventory(getMenu(p));
            }
        });

        return item;
    }

    public static Inventory getMenu(Player player){
        Inventory inventory = Bukkit.createInventory(null,54, Text.format("&7Particles"));

        /*
         * Categories for Cosmetics
         */
        Item particles = new Item(Material.BLAZE_POWDER,"&eParticle Effects");
        Item heads = new Item(Material.CREEPER_HEAD,"&eHeads");
        Item costumes = new Item(Material.DIAMOND_CHESTPLATE,"&eCostumes");
        Item drinks = new Item(Material.POTION,"&eDrinks");
        Item pets = new Item(Material.GHAST_SPAWN_EGG,"&ePets");
        Item secondaries = new Item(Material.BOW,"&eSecondary Items");
        Item morphs = new Item(Material.LEATHER,"&eMorphs");

        inventory.setItem(10,particles.spigot());
        inventory.setItem(12,heads.spigot());
        inventory.setItem(14,costumes.spigot());
        inventory.setItem(16,drinks.spigot());
        inventory.setItem(29,pets.spigot());
        inventory.setItem(31,secondaries.spigot());
        inventory.setItem(33,morphs.spigot());

        return inventory;
    }

}
