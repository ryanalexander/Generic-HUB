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

package net.blockcade.HUB.Common.Static.Inventory.menus.Preferences.Filters;

import net.blockcade.HUB.Common.GamePlayer;
import net.blockcade.HUB.Common.Static.Inventory.menus.Preferences.Censorship;
import net.blockcade.HUB.Common.Static.Preferances.FilterVisibility;
import net.blockcade.HUB.Common.Utils.Item;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class NoFilter {

    public static Item getMenuItem(GamePlayer gameplayer) {
        ItemStack is = new ItemStack(Material.RED_STAINED_GLASS_PANE);

        Item item = new Item(is,"&cNo Filter");
        if(gameplayer.getPreferenceSettings().getFilterVisibility().equals(FilterVisibility.OPEN))
            item.setEnchanted(true);
        item.setLore("&7no filter will be applied to chat messages.","&cNetwork wide blacklist still applies.","&818+");
        item.setOnClick(p -> {
            GamePlayer.getGamePlayer(p).getPreferenceSettings().setFilterVisibility(FilterVisibility.OPEN);
            p.openInventory(Censorship.getMenu(p));
        });
        return item;
    }

}
