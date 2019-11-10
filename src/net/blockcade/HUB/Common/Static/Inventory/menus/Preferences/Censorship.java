package net.blockcade.HUB.Common.Static.Inventory.menus.Preferences;

import net.blockcade.HUB.Common.Static.Inventory.Manager;
import net.blockcade.HUB.Common.Static.Inventory.menus.CosmeticsMenu;
import net.blockcade.HUB.Common.Utils.Item;
import net.blockcade.HUB.Common.Utils.Particles.ParticleManager;
import net.blockcade.HUB.Common.Utils.Text;
import net.blockcade.HUB.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Censorship {

        public static Item getMenuItem() {
            Item item = new Item(Material.BLAZE_POWDER,"&fCensorship");

            item.setLore(new String[]{"","&7View all Censorship related Preferences",""});
            item.setOnClick(new Item.click() {
                @Override
                public void run(Player p) {
                    p.openInventory(getMenu(p));
                }
            });

            return item;
        }

        private static Inventory getMenu(Player player){
            Inventory inventory = Bukkit.createInventory(null,54, Text.format("&7Particles"));

            Manager.addProfileHeader(inventory, Main.GamePlayers.get(player));

            Item back = new Item(Item.itemWithBase64(new ItemStack(Material.PLAYER_HEAD),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODY1MmUyYjkzNmNhODAyNmJkMjg2NTFkN2M5ZjI4MTlkMmU5MjM2OTc3MzRkMThkZmRiMTM1NTBmOGZkYWQ1ZiJ9fX0="),"&eBack");
            back.setOnClick(new Item.click() {
                @Override
                public void run(Player p) {
                    p.openInventory(CosmeticsMenu.getMenu(p,""));
                }
            });
            inventory.setItem(0,back.spigot());

            Item rings = new Item(Material.FIRE_CHARGE,"&6Rings");
            rings.setOnClick(new Item.click() {
                @Override
                public void run(Player p) {
                    Main.particleManager.setParticle(p, ParticleManager.ParticleType.RINGS);
                }
            });

            inventory.setItem(21,rings.spigot());

            return inventory;
        }

}