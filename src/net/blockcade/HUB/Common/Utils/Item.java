/*
 *
 *  *
 *  * © Stelch Software 2019, distribution is strictly prohibited
 *  * Blockcade is a company of Stelch Software
 *  *
 *  * Changes to this file must be documented on push.
 *  * Unauthorised changes to this file are prohibited.
 *  *
 *  * @author Ryan Wood
 *  @since 22/7/2019
 */

package net.blockcade.HUB.Common.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.apache.commons.lang.Validate.notNull;

public class Item implements Listener {
    public static HashMap<Item, click> actions = new HashMap<>();

    //Game GameCommand;
    ItemStack is;
    ItemMeta im;

    public Item() {

    }

    /*public Item(Game GameCommand){
        this.GameCommand=GameCommand;
    }*/

    public Item (ItemStack item, String name){
        this.is=item;
        this.im=item.getItemMeta();
        this.im.setDisplayName(Text.format(name));
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    }

    public Item(Material material, String name) {
        this.is = new ItemStack(material);
        im = this.is.getItemMeta();
        im.setDisplayName(Text.format(name));
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    }

    /**
     * This function is only applicable to Items that color can be changed on
     */
    public Item setLeatherColor(Color color) {
        LeatherArmorMeta meta = (LeatherArmorMeta) this.im;
        meta.setColor(color);
        this.im = meta;
        return this;
    }

    public void setName(String name) {
        this.im.setDisplayName(Text.format(name));
    }

    public void setTexture(String base64){this.is=itemWithBase64(this.is,base64);}

    public void setMaterial(Material mat) {
        this.is.setType(mat);
    }

    public void setAmount(int amount) {
        is.setAmount(amount);
    }

    public void setEnchanted(boolean enchanted) {
        im.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    }

    public void setLore(String... lines) {
        ArrayList<String> lines2 = new ArrayList<>();
        for (String line : lines) {
            lines2.add(Text.format(line));
        }
        im.setLore(lines2);
    }

    public Item setOnClick(click onClick) {
        if (onClick != null)
            actions.put(this, onClick);
        return this;
    }

    public ItemStack spigot() {
        this.is.setItemMeta(this.im);
        return this.is;
    }


    public interface click {
        void run(Player param1Player);
    }


    @EventHandler
    public void EntityInteract(PlayerInteractEvent e){
        boolean done = false;
        if(!(e.getHand().equals(EquipmentSlot.HAND)))return;
        try {
            for (Map.Entry<Item, Item.click> is : actions.entrySet()) {
                if(!(done)&&is.getKey().spigot().getType().equals(Material.PLAYER_HEAD)){
                    if(e.getItem()==null)continue;
                    if(isSimilarHead(is.getKey().spigot(),e.getItem())){
                        if(is.getValue()!=null)
                            is.getValue().run((Player)e.getPlayer());
                        e.setCancelled(true);
                        done=true;
                    }
                }else if(!(done)&&is.getKey().spigot().equals(e.getItem())){
                    if(is.getValue()!=null)
                        is.getValue().run((Player)e.getPlayer());
                    e.setCancelled(true);
                    done=true;
                }
            }
        }catch (Exception ex){
            return;
        }
    }
    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent e) {
        boolean done = false;
        try {
            for(Item item : ((HashMap<Item, click>)actions.clone()).keySet()){
                click c = actions.get(item);
                if(done)return;
                if(item.spigot().getType().equals(Material.PLAYER_HEAD)){
                    if(e.getCurrentItem()==null)continue;
                    if(!(e.getClick().equals(ClickType.LEFT)))return;
                    if(isSimilarHead(item.spigot(),e.getCurrentItem())){
                        e.setCancelled(true);
                        if(c!=null)
                            c.run((Player)e.getWhoClicked());
                        done=true;
                    }
                }else if(item.spigot().equals(e.getCurrentItem())){
                    e.setCancelled(true);
                    if(c!=null)
                        c.run((Player)e.getWhoClicked());
                    done=true;
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public enum WoolColor {
        WHITE(0, Color.WHITE), ORANGE(1, Color.ORANGE), MAGENTA(2, null), BLUE(3, Color.BLUE), YELLOW(4, Color.YELLOW), GREEN(5, Color.LIME), PINK(6, Color.FUCHSIA), DARK_GREY(7, Color.GRAY), LIGHT_GREY(8, Color.GRAY), CYAN(9, Color.BLUE), PURPLE(10, Color.PURPLE), DARK_BLUE(11, Color.BLUE), BROWN(12, null), DARK_GREEN(13, Color.GREEN), RED(14, Color.RED), BLACK(15, Color.BLACK);
        byte color;
        Color jColor;

        public Color getjColor() {
            return this.jColor;
        }

        public byte getColor() {
            return this.color;
        }

        WoolColor(int color, Color jColor) {
            this.jColor = jColor;
            this.color = (byte) color;
        }
    }


    public static ItemStack itemWithBase64(ItemStack item, String base64) {
        notNull(item, "item");
        notNull(base64, "base64");

        UUID hashAsId = new UUID(base64.hashCode(), base64.hashCode());
        return Bukkit.getUnsafe().modifyItemStack(item,
                "{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + base64 + "\"}]}}}"
        );
    }
    public static boolean isSimilarHead(ItemStack is1, ItemStack is2) {
        if (is1.getType() == is2.getType() &&
                is1.getDurability() == is2.getDurability() &&
                is1.hasItemMeta() == is2.hasItemMeta()) {
            SkullMeta im1 = (SkullMeta) is1.getItemMeta();
            SkullMeta im2 = (SkullMeta) is2.getItemMeta();
            if (im1.getDisplayName().equals(im2.getDisplayName())) {
                if (im1.hasLore() == im2.hasLore()&&im1.getLore().equals(im2.getLore())) {
                    return true;
                }
            }
        }
        return false;
    }
}