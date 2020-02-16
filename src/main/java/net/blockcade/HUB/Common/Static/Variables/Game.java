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

package net.blockcade.HUB.Common.Static.Variables;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum Game {
    BEDBATTLES(Material.RED_BED,"Bed Battles","&fFight to defend your bed\nand be the last player standing.","BBW",ChatColor.RED,16),
    ARENA(Material.GOLDEN_SWORD, "Arena","Free for all PvP\nwith upgrades & abilities", "AR",ChatColor.RED,100),
    FACTIONS(Material.GRASS,"Factions","The Factions you know and love.","FAC",ChatColor.GREEN,500),
    TESTING(Material.BOW,"Testing","testing gamemode","TEST",ChatColor.WHITE,100),
    POKE(Material.AIR,"Pokemon","","POKE",ChatColor.YELLOW,100),
    HUB(Material.CLOCK,"HUB","","HUB",ChatColor.WHITE,25);
    Material material;
    String name;
    String description;
    String type;
    ChatColor color;
    int MaxPlayers;
    Game(Material material, String name, String description, String type, ChatColor color, int MaxPlayers){
        this.material=material;
        this.name=name;
        this.description=description;
        this.type=type;
        this.color=color;
        this.MaxPlayers=MaxPlayers;
    }

    public int getMaxPlayers() {
        return MaxPlayers;
    }

    public Material getMaterial() {
        return material;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public ChatColor getColor() {
        return color;
    }
}
