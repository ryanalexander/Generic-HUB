package net.blockcade.HUB.Common.Static;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;

public enum Game {
    BED_BATTLES(Material.RED_BED,"Bed Battles","Assist your team in taking over the world. Go island to and destroy beds.","BBW",ChatColor.GOLD),
    CAPTURE(Material.RED_WOOL, "Capture The Flag","It's capture the flag, if you don't know what that is, kys.", "CTF",ChatColor.RED);
    Material material;
    String name;
    String description;
    String type;
    ChatColor color;
    Game(Material material, String name, String description, String type, ChatColor color){
        this.material=material;
        this.name=name;
        this.description=description;
        this.type=type;
        this.color=color;
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
