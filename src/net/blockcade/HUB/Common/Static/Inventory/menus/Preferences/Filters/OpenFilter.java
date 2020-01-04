package net.blockcade.HUB.Common.Static.Inventory.menus.Preferences.Filters;

import net.blockcade.HUB.Common.GamePlayer;
import net.blockcade.HUB.Common.Static.Preferances.FilterVisibility;
import net.blockcade.HUB.Common.Utils.Item;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class OpenFilter {

    public static Item getMenuItem(GamePlayer gameplayer) {
        ItemStack is = new ItemStack(Material.LIME_DYE);

        Item item = new Item(is,"&2OPEN &bfilter");
        if(gameplayer.getPreferenceSettings().getFilterVisibility().equals(FilterVisibility.OPEN))
            item.setEnchanted(true);
        item.setLore("","&7no filter will be applied","");
        item.setOnClick(p -> {
            GamePlayer.getGamePlayer(p).getPreferenceSettings().setFilterVisibility(FilterVisibility.OPEN);
        });
        return item;
    }

}
