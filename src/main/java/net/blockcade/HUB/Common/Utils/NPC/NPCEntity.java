package net.blockcade.HUB.Common.Utils.NPC;

import net.blockcade.HUB.Common.Utils.ReflectionUtil;
import net.blockcade.HUB.Common.Utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class NPCEntity implements Listener {

    private static HashMap<Integer,NPCEntity> entities = new HashMap<>();
    private static HashMap<Integer,Entity> entityIds = new HashMap<>();

    public Integer id;
    public Object entity;
    public EntityType type;
    public Location location;
    public String name;
    public Click click;

    public NPCEntity(JavaPlugin plugin) {

    }

    public NPCEntity(EntityType type, Location location){
        this.type=type;
        this.location=location;
    }

    public void setClick(Click click) {
        this.click = click;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(EntityType type) {
        this.type = type;
    }

    public Click getClick() {
        return click;
    }

    public EntityType getType() {
        return type;
    }

    public LivingEntity getEntity() {
        return (LivingEntity) entityIds.get(id);
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public static HashMap<Integer, NPCEntity> getEntities() {
        return entities;
    }

    public void build() {
        try {
            Constructor<?> PacketPlayOutSpawnEntityLiving = ReflectionUtil.getNMSClass("PacketPlayOutSpawnEntityLiving").getDeclaredConstructor();
            ReflectionUtil.getField(PacketPlayOutSpawnEntityLiving.getClass(), "a").set(int.class, 90 + entities.size());
            ReflectionUtil.getField(PacketPlayOutSpawnEntityLiving.getClass(), "b").set(UUID.class, UUID.randomUUID());
            ReflectionUtil.getField(PacketPlayOutSpawnEntityLiving.getClass(), "c").set(double.class, location.getX());
            ReflectionUtil.getField(PacketPlayOutSpawnEntityLiving.getClass(), "d").set(double.class, location.getY());
            ReflectionUtil.getField(PacketPlayOutSpawnEntityLiving.getClass(), "e").set(double.class, location.getZ());
            ReflectionUtil.getField(PacketPlayOutSpawnEntityLiving.getClass(), "f").set(double.class, 0);
            ReflectionUtil.getField(PacketPlayOutSpawnEntityLiving.getClass(), "g").set(double.class, 0);

            for (Player player : Bukkit.getOnlinePlayers()) {
                ReflectionUtil.sendPacket(player, PacketPlayOutSpawnEntityLiving);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public void PlayerClick(PlayerInteractAtEntityEvent e){
        if(entities.containsKey(e.getRightClicked())){
            NPCEntity entity = entities.get(e.getRightClicked());
            e.setCancelled(true);
            if(entity.getClick()!=null)
                entity.getClick().run(e.getPlayer());
        }
    }
    interface Click {
        void run(Player player);
    }

}
