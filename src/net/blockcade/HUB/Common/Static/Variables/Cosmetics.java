package net.blockcade.HUB.Common.Static.Variables;

import net.blockcade.HUB.Common.Utils.Particles.ParticleManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum Cosmetics {
    RINGS(Material.FIRE_CHARGE,"Rings","&fRing of fire around your head", ParticleManager.ParticleType.FIRE_RINGS, ChatColor.RED);
    String name;
    Material material;
    String description;
    ParticleManager.ParticleType type;
    ChatColor color;
    Cosmetics(Material material, String name, String description, ParticleManager.ParticleType type, ChatColor color){
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

    public ParticleManager.ParticleType getType() {
        return type;
    }

    public ChatColor getColor() {
        return color;
    }
}
