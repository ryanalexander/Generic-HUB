package net.blockcade.HUB.Common.Static.Inventory.menus;

import net.blockcade.HUB.Common.Static.Inventory.Manager;
import net.blockcade.HUB.Common.Static.Inventory.menus.Cosmetic.Particles;
import net.blockcade.HUB.Common.Static.Variables.Game;
import net.blockcade.HUB.Common.Utils.Item;
import net.blockcade.HUB.Common.Utils.Particles.ParticleManager;
import net.blockcade.HUB.Common.Utils.Text;
import net.blockcade.HUB.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Cosmetics {

    public static Item getMenuItem() {
        Item item = new Item(Material.NETHER_STAR,"&aCosmetics &7(Right Click)");

        item.setLore(new String[]{"","&7Click to open Cosmetics menu",""});
        item.setOnClick(new Item.click() {
            @Override
            public void run(Player p) {
                p.openInventory(Cosmetics.getMenu(p,null));
            }
        });

        return item;
    }

    private static Inventory getMenu(Player player, String referer){
        Inventory inventory = Bukkit.createInventory(null,45, Text.format("&7Cosmetics"));

        Manager.addProfileHeader(inventory,Main.GamePlayers.get(player));

        /*
         * Categories for Cosmetics
         */
        Item particles = new Item(Material.BLAZE_POWDER,"&eParticle Effects");
        Item heads = new Item(Material.CREEPER_HEAD,"&eHeads");
        Item costumes = new Item(Material.DIAMOND_CHESTPLATE,"&eCostumes");
        Item pets = new Item(Material.GHAST_SPAWN_EGG,"&ePets");
        Item morphs = new Item(Material.LEATHER,"&eMorphs");

        inventory.setItem(29,particles.spigot());
        inventory.setItem(30,heads.spigot());
        inventory.setItem(31,costumes.spigot());
        inventory.setItem(32,pets.spigot());
        inventory.setItem(33,morphs.spigot());

        return inventory;
    }

}
