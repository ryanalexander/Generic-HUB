/*
 *
 *
 *  © Stelch Software 2020, distribution is strictly prohibited
 *  Blockcade is a company of Stelch Software
 *
 *  Changes to this file must be documented on push.
 *  Unauthorised changes to this file are prohibited.
 *
 *  @author Ryan W
 * @since (DD/MM/YYYY) 18/1/2020
 */

package net.blockcade.HUB.Common.Static.Inventory.menus.Preferences.Visuals;

import net.blockcade.HUB.Common.GamePlayer;
import net.blockcade.HUB.Common.Static.Inventory.menus.Preferences.Censorship;
import net.blockcade.HUB.Common.Static.Inventory.menus.Preferences.Visual;
import net.blockcade.HUB.Common.Static.Preferances.FilterVisibility;
import net.blockcade.HUB.Common.Static.Preferances.VisualQuality;
import net.blockcade.HUB.Common.Utils.Item;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class LowQuality {

    public static Item getMenuItem(GamePlayer gameplayer) {
        ItemStack is = new ItemStack(Material.LIME_STAINED_GLASS_PANE);

        Item item = new Item(is,"&aLow Quality");
        if(gameplayer.getPreferenceSettings().getVisualQuality().equals(VisualQuality.LOW))
            item.setEnchanted(true);
        item.setLore("&7With &aLow Quality &7objects will have","&7a general shape but no precision");
        item.setOnClick(p -> {
            GamePlayer.getGamePlayer(p).getPreferenceSettings().setVisualQuality(VisualQuality.LOW);
            p.openInventory(Visual.getMenu(p));
        });
        return item;
    }

}
