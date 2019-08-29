package net.blockcade.HUB.Common.Static.Inventory;

import net.blockcade.HUB.Common.GamePlayer;
import net.blockcade.HUB.Common.Utils.Item;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Manager {

    public static enum Menus {
        Games,
        Lobbies
    }



    public static void addProfileHeader(Inventory inventory, GamePlayer player){

        ItemStack friends_item = Item.itemWithBase64(new ItemStack(Material.PLAYER_HEAD),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjE4M2JhYjUwYTMyMjQwMjQ4ODZmMjUyNTFkMjRiNmRiOTNkNzNjMjQzMjU1OWZmNDllNDU5YjRjZDZhIn19fQ==");
        ItemStack party_item = Item.itemWithBase64(new ItemStack(Material.PLAYER_HEAD),"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTBhNzk4OWI1ZDZlNjIxYTEyMWVlZGFlNmY0NzZkMzUxOTNjOTdjMWE3Y2I4ZWNkNDM2MjJhNDg1ZGMyZTkxMiJ9fX0=");
        Item friends = new Item(friends_item,"&eFriends");
        friends.setLore(new String[]{"&7This menu contains","&7a list of all your friends"});

        Item profile = new Item(Material.PLAYER_HEAD,"&eYour Profile");
        profile.setLore(new String[]{"&7Rank: &e"+player.getRank().getFormatted(),"&7Level: &e0"});

        Item party = new Item(party_item,"&eParty");
        party.setLore(new String[]{"&7This menu contains","&7Party Management Tools"});


        Item placeholder = new Item(Material.BLACK_STAINED_GLASS_PANE,"&r");
        placeholder.setOnClick(new Item.click() {
            @Override
            public void run(Player param1Player) {}
        });
        for(int i=9;i<18;i++){ inventory.setItem(i,placeholder.spigot()); }

        inventory.setItem(3,friends.spigot());
        inventory.setItem(4,profile.spigot());
        inventory.setItem(5,party.spigot());

    }

}
