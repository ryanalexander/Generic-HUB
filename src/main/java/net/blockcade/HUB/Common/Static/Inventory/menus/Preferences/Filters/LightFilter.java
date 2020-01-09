package net.blockcade.HUB.Common.Static.Inventory.menus.Preferences.Filters;

import net.blockcade.HUB.Common.GamePlayer;
import net.blockcade.HUB.Common.Static.Inventory.menus.Preferences.Censorship;
import net.blockcade.HUB.Common.Static.Preferances.FilterVisibility;
import net.blockcade.HUB.Common.Utils.Item;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class LightFilter {

    public static Item getMenuItem(GamePlayer gameplayer) {
        ItemStack is = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);

        Item item = new Item(is,"&6Light Filter");
        if(gameplayer.getPreferenceSettings().getFilterVisibility().equals(FilterVisibility.LIGHT))
            item.setEnchanted(true);
        item.setLore("&7With &6Light Filter &7a Moderate level of cursing is shown.","&cNetwork wide blacklist still applies.","&b[Ages 15 to 18]");
        item.setOnClick(p -> {
            GamePlayer.getGamePlayer(p).getPreferenceSettings().setFilterVisibility(FilterVisibility.LIGHT);
            p.openInventory(Censorship.getMenu(p));
        });
        return item;
    }

}
