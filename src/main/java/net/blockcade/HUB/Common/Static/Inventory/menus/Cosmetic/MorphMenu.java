package net.blockcade.HUB.Common.Static.Inventory.menus.Cosmetic;

import net.blockcade.HUB.Common.GamePlayer;
import net.blockcade.HUB.Common.Static.Inventory.Manager;
import net.blockcade.HUB.Common.Static.Inventory.menus.CosmeticsMenu;
import net.blockcade.HUB.Common.Static.Inventory.menus.Preferences.Filters.LightFilter;
import net.blockcade.HUB.Common.Static.Inventory.menus.Preferences.Filters.MediumFilter;
import net.blockcade.HUB.Common.Static.Inventory.menus.Preferences.Filters.NoFilter;
import net.blockcade.HUB.Common.Static.Inventory.menus.Preferences.Filters.StrongFilter;
import net.blockcade.HUB.Common.Static.Inventory.menus.PreferencesMenu;
import net.blockcade.HUB.Common.Static.Variables.Cosmetics;
import net.blockcade.HUB.Common.Utils.Item;
import net.blockcade.HUB.Common.Utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MorphMenu {
    public static Item getMenuItem() {
        Item item = new Item(Material.CREEPER_HEAD,"&aMorph Menu");

        item.setLore("","&7Click to view all the possible morphs","");
        item.setOnClick(p -> p.openInventory(getMenu(p)));
        return item;
    }



    private static Inventory getMenu(Player player) {

            Inventory inventory = Bukkit.createInventory(null,54, Text.format("&7Morph Menu"));
            GamePlayer gamePlayer = GamePlayer.getGamePlayer(player);

            Manager.addProfileHeader(inventory, gamePlayer);

            Item back = new Item(Item.itemWithBase64(new ItemStack(Material.PLAYER_HEAD),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODY1MmUyYjkzNmNhODAyNmJkMjg2NTFkN2M5ZjI4MTlkMmU5MjM2OTc3MzRkMThkZmRiMTM1NTBmOGZkYWQ1ZiJ9fX0="),"&eBack");
            back.setLore("&7Return to Cosmetics Menu");
            back.setOnClick((p)->{
                p.openInventory(CosmeticsMenu.getMenu(p,null));
            });
            inventory.setItem(0,back.spigot());

            Item zombie_head = new Item(Item.itemWithBase64(new ItemStack(Material.PLAYER_HEAD),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTQzNjc5Njg4ZDYyNjZhOTY3OWIzOGU1YjVlM2M2MGFhYzY0NGY2MWVlZjI3NTBiMDYwNTQxODM0MjY4MmM5In19fQ=="),"&1Zombie");
            zombie_head.setLore("&7With this Morph you will be shown as","a &1Zombie&7 to all players on the Network","","&aClick to Transform");

            inventory.setItem(29, zombie_head.spigot());

            return inventory;
    }
}
