package net.blockcade.HUB.Common.Static.Inventory.menus.Preferences.Filters;

import net.blockcade.HUB.Common.GamePlayer;
import net.blockcade.HUB.Common.Static.Inventory.Manager;
import net.blockcade.HUB.Common.Static.Preferances.FilterVisibility;
import net.blockcade.HUB.Common.Utils.Item;
import net.blockcade.HUB.Common.Utils.Text;
import net.blockcade.HUB.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class StrictFilter {

    public static Item getMenuItem(GamePlayer gameplayer) {
        ItemStack is = new ItemStack(Material.RED_DYE);

        Item item = new Item(is,"&cSTRICT &bfilter");

        if(gameplayer.getPreferenceSettings().getFilterVisibility().equals(FilterVisibility.STRICT)){
            item.setEnchanted(true);
        }

        item.setLore("","&7a strict filter will be applied","");
        item.setOnClick(new Item.click(){

            @Override
            public void run(Player param1Player) {



            }
        });
        return item;
    }

}
