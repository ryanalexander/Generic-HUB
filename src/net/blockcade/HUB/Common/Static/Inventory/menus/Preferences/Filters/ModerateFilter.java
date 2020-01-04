package net.blockcade.HUB.Common.Static.Inventory.menus.Preferences.Filters;

import net.blockcade.HUB.Common.GamePlayer;
import net.blockcade.HUB.Common.Static.Preferances.FilterVisibility;
import net.blockcade.HUB.Common.Utils.Item;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ModerateFilter {

    public static Item getMenuItem(GamePlayer gameplayer) {
        ItemStack is = new ItemStack(Material.YELLOW_DYE);

        Item item = new Item(is,"&6MODERATE &bfilter");
        if(gameplayer.getPreferenceSettings().getFilterVisibility().equals(FilterVisibility.MODERATE))
            item.setEnchanted(true);
        item.setLore("","&7a moderate filter will be applied","");
        item.setOnClick(p -> {
            GamePlayer.getGamePlayer(p).getPreferenceSettings().setFilterVisibility(FilterVisibility.MODERATE);
        });
        return item;
    }

}
