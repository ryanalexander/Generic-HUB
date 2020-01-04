package net.blockcade.HUB.Common.Static.Inventory.menus.Preferences.Filters;

import net.blockcade.HUB.Common.GamePlayer;
import net.blockcade.HUB.Common.Static.Preferances.FilterVisibility;
import net.blockcade.HUB.Common.Utils.Item;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class StrictFilter {

    public static Item getMenuItem(GamePlayer gameplayer) {
        ItemStack is = new ItemStack(Material.RED_DYE);

        Item item = new Item(is,"&cSTRICT &bfilter");
        if(gameplayer.getPreferenceSettings().getFilterVisibility().equals(FilterVisibility.STRICT))
            item.setEnchanted(true);
        item.setLore("","&7a strict filter will be applied","");
        item.setOnClick(p -> {
            GamePlayer.getGamePlayer(p).getPreferenceSettings().setFilterVisibility(FilterVisibility.STRICT);
        });
        return item;
    }

}
