package net.blockcade.HUB.Common.Static.Inventory.menus.Preferences.Filters;

import net.blockcade.HUB.Common.GamePlayer;
import net.blockcade.HUB.Common.Static.Preferances.FilterVisibility;
import net.blockcade.HUB.Common.Utils.Item;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MediumFilter {

    public static Item getMenuItem(GamePlayer gameplayer) {
        ItemStack is = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);

        Item item = new Item(is,"&eMediuim Filter");
        if(gameplayer.getPreferenceSettings().getFilterVisibility().equals(FilterVisibility.MEDIUM))
            item.setEnchanted(true);
        item.setLore("","&7With &eMedium Filter &7No cursing is shown,","&7and suggestive terms are not displayed.","&cNetwork Blacklist still applies.","&b[Ages 10 to 15]");
        item.setOnClick(p -> {
            GamePlayer.getGamePlayer(p).getPreferenceSettings().setFilterVisibility(FilterVisibility.MEDIUM);
        });
        return item;
    }

}
