package net.blockcade.HUB.Common.Static.Inventory.menus.Preferences;

import net.blockcade.HUB.Common.GamePlayer;
import net.blockcade.HUB.Common.Static.Inventory.Manager;
import net.blockcade.HUB.Common.Static.Inventory.menus.Preferences.Filters.LightFilter;
import net.blockcade.HUB.Common.Static.Inventory.menus.Preferences.Filters.MediumFilter;
import net.blockcade.HUB.Common.Static.Inventory.menus.Preferences.Filters.NoFilter;
import net.blockcade.HUB.Common.Static.Inventory.menus.Preferences.Filters.StrongFilter;
import net.blockcade.HUB.Common.Static.Inventory.menus.Preferences.Visuals.HighQuality;
import net.blockcade.HUB.Common.Static.Inventory.menus.Preferences.Visuals.LowQuality;
import net.blockcade.HUB.Common.Static.Inventory.menus.Preferences.Visuals.MediumQuality;
import net.blockcade.HUB.Common.Static.Inventory.menus.PreferencesMenu;
import net.blockcade.HUB.Common.Utils.Item;
import net.blockcade.HUB.Common.Utils.Text;
import net.blockcade.HUB.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Visual {

    public static Item getMenuItem() {
        Item item = new Item(Material.HOPPER,"Quality Settings");

        item.setLore("","&7View all Visual Settings here","");
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
        GamePlayer gamePlayer = GamePlayer.getGamePlayer(player);

        Manager.addProfileHeader(inventory, gamePlayer);

        Item back = new Item(Item.itemWithBase64(new ItemStack(Material.PLAYER_HEAD),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODY1MmUyYjkzNmNhODAyNmJkMjg2NTFkN2M5ZjI4MTlkMmU5MjM2OTc3MzRkMThkZmRiMTM1NTBmOGZkYWQ1ZiJ9fX0="),"&eBack");
        back.setLore("&7Return to Preferences Menu");
        back.setOnClick((p)->{
            p.openInventory(PreferencesMenu.getMenu(p));
        });
        inventory.setItem(0,back.spigot());

        inventory.setItem(29, LowQuality.getMenuItem(gamePlayer).spigot());
        inventory.setItem(31, MediumQuality.getMenuItem(gamePlayer).spigot());
        inventory.setItem(33, HighQuality.getMenuItem(gamePlayer).spigot());




        return inventory;
    }


}
