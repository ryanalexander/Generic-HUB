package net.blockcade.HUB.Common.Static.Inventory.menus.Preferences.Visuals;

import net.blockcade.HUB.Common.GamePlayer;
import net.blockcade.HUB.Common.Static.Inventory.menus.Preferences.Censorship;
import net.blockcade.HUB.Common.Static.Preferances.FilterVisibility;
import net.blockcade.HUB.Common.Static.Preferances.VisualQuality;
import net.blockcade.HUB.Common.Utils.Item;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MediumQuality {

    public static Item getMenuItem(GamePlayer gameplayer) {
        ItemStack is = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);

        Item item = new Item(is,"&6Medium Quality");
        if(gameplayer.getPreferenceSettings().getVisualQuality().equals(VisualQuality.MEDIUM))
            item.setEnchanted(true);
        item.setLore("&7With &6Medium Quality &7objects will be shaped but blurry around the edges");
        item.setOnClick(p -> {
            GamePlayer.getGamePlayer(p).getPreferenceSettings().setVisualQuality(VisualQuality.MEDIUM);
            p.openInventory(Censorship.getMenu(p));
        });
        return item;
    }

}
