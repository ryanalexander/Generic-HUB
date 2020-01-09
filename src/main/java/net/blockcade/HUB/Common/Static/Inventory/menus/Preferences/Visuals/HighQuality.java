package net.blockcade.HUB.Common.Static.Inventory.menus.Preferences.Visuals;

import net.blockcade.HUB.Common.GamePlayer;
import net.blockcade.HUB.Common.Static.Inventory.menus.Preferences.Censorship;
import net.blockcade.HUB.Common.Static.Inventory.menus.Preferences.Visual;
import net.blockcade.HUB.Common.Static.Preferances.FilterVisibility;
import net.blockcade.HUB.Common.Static.Preferances.VisualQuality;
import net.blockcade.HUB.Common.Utils.Item;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class HighQuality {

    public static Item getMenuItem(GamePlayer gameplayer) {
        ItemStack is = new ItemStack(Material.RED_STAINED_GLASS_PANE);

        Item item = new Item(is,"&cHigh Quality");
        if(gameplayer.getPreferenceSettings().getVisualQuality().equals(VisualQuality.HIGH))
            item.setEnchanted(true);
        item.setLore("&7With &cHigh Quality &7objects will be most precise");
        item.setOnClick(p -> {
            GamePlayer.getGamePlayer(p).getPreferenceSettings().setVisualQuality(VisualQuality.HIGH);
            p.openInventory(Visual.getMenu(p));
        });
        return item;
    }

}
