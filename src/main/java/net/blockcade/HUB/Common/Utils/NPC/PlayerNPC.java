package net.blockcade.HUB.Common.Utils.NPC;

import net.blockcade.HUB.Common.Utils.ReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.util.UUID;

public class PlayerNPC extends NPCEntity {

    public PlayerNPC(String name, Location location) {
        super(EntityType.PLAYER, location);
        setName(name);
        build();
    }

    @Override
    public void build() {
        try {
            Constructor<?> PacketPlayOutNamedEntitySpawn = ReflectionUtil.getNMSClass("PacketPlayOutNamedEntitySpawn").getDeclaredConstructor();
            ReflectionUtil.getField(PacketPlayOutNamedEntitySpawn.getClass(), "a").set(int.class, 95 + getEntities().size());
            ReflectionUtil.getField(PacketPlayOutNamedEntitySpawn.getClass(), "b").set(UUID.class, UUID.randomUUID());
            ReflectionUtil.getField(PacketPlayOutNamedEntitySpawn.getClass(), "c").set(double.class, location.getX());
            ReflectionUtil.getField(PacketPlayOutNamedEntitySpawn.getClass(), "d").set(double.class, location.getY());
            ReflectionUtil.getField(PacketPlayOutNamedEntitySpawn.getClass(), "e").set(double.class, location.getZ());
            ReflectionUtil.getField(PacketPlayOutNamedEntitySpawn.getClass(), "f").set(double.class, location.getYaw());
            ReflectionUtil.getField(PacketPlayOutNamedEntitySpawn.getClass(), "g").set(double.class, location.getPitch());

            for (Player player : Bukkit.getOnlinePlayers()) {
                ReflectionUtil.sendPacket(player, PacketPlayOutNamedEntitySpawn);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
