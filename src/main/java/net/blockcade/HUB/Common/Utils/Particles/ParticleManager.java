/*
 *
 * *
 *  *
 *  * © Stelch Games 2019, distribution is strictly prohibited
 *  *
 *  * Changes to this file must be documented on push.
 *  * Unauthorised changes to this file are prohibited.
 *  *
 *  * @author Ryan Wood
 *  * @since 22/7/2019
 *
 */

package net.blockcade.HUB.Common.Utils.Particles;

import net.blockcade.HUB.Common.Utils.WingAPI;
import net.blockcade.HUB.Main;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class ParticleManager {

    public enum ParticleType {FIRE_RINGS,WATER_RINGS,WINGS,NONE}

    HashMap<Player, ParticleEffect> particles = new HashMap<>();
    Main plugin;

    public ParticleManager(Main plugin){
        this.plugin = plugin;
    }

    public ParticleEffect setParticle(Player player, ParticleType particle){
        if(particles.containsKey(player)){
            particles.get(player).stop();
            particles.remove(player);
        }
        if(WingAPI.WinggedPlayers.containsKey(player))
            WingAPI.WinggedPlayers.get(player).stop();

        switch(particle){
            case FIRE_RINGS:
                particles.put(player,new Rings(player, Particle.FLAME));
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        if((!player.isOnline())||(!particles.containsKey(player))||particles.get(player).isStopped()){cancel();return;}
                        particles.get(player).doEffect();
                    }
                }.runTaskTimer(Main.getPlugin(Main.class),0L,particles.get(player).getSpeed());
                return particles.get(player);
            case NONE:
                return null;
        }
        return null;
    }
    public boolean hasEffect(Player player){ return particles.containsKey(player); }
    public ParticleEffect getEffect(Player player){ return particles.get(player); }
    public ParticleEffect getEffectType(Player player){ return particles.get(player); }

}
