package net.blockcade.HUB.Common.Static.Inventory.menus;

import net.blockcade.HUB.Common.Static.Inventory.Manager;
import net.blockcade.HUB.Common.Static.Inventory.menus.Cosmetic.Particles;
import net.blockcade.HUB.Common.Utils.Item;
import net.blockcade.HUB.Common.Utils.Text;
import net.blockcade.HUB.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CosmeticsMenu {

    private static Item item;

    public static Item getMenuItem() {
        ItemStack cosmetics_item = Item.itemWithBase64(new ItemStack(Material.PLAYER_HEAD),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjg1OWUyZjAyOGIyOTU2ZGFlNzBhZDAxODE1NjhmYjBlY2ZkNzRkYjJiZTBmOWE1ZWYzNGQyODY3YzM3Zjc2OSJ9fX0=");
        Item item = new Item(cosmetics_item,"&aCosmetics &7(Right Click)");

        item.setLore("","&7Click to open Cosmetics menu","");
        item.setOnClick(p -> p.openInventory(CosmeticsMenu.getMenu(p,null)));
        return item;
    }

    public static Inventory getMenu(Player player, String referer){
        Inventory inventory = Bukkit.createInventory(null,45, Text.format("&7Cosmetics"));
        Manager.addProfileHeader(inventory,Main.GamePlayers.get(player));

        /*
         * Categories for Cosmetics
         */
        Item heads = new Item(Material.CREEPER_HEAD,"&eHeads");
        Item costumes = new Item(Material.DIAMOND_CHESTPLATE,"&eCostumes");
        Item pets = new Item(Material.GHAST_SPAWN_EGG,"&ePets");
        Item morphs = new Item(Material.LEATHER,"&eMorphs");

        heads.setLore("Coming Soon");
        costumes.setLore("Coming Soon");
        pets.setLore("Coming Soon");
        morphs.setLore("Coming Soon");

        inventory.setItem(29,Particles.getMenuItem().spigot());
        inventory.setItem(30,heads.spigot());
        inventory.setItem(31,costumes.spigot());
        inventory.setItem(32,pets.spigot());
        inventory.setItem(33,morphs.spigot());

        return inventory;
    }

}
