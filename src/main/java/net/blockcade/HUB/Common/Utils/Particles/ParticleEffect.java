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
 *  * @since 23/7/2019
 *
 */

package net.blockcade.HUB.Common.Utils.Particles;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;

public class ParticleEffect {

    private boolean isStopped;
    private long effect_speed;
    private Particle particleType;

    public Entity target;

    public ParticleEffect() {
        // Do nothing
    }

    public ParticleEffect(long speed, Particle particleType, Entity target) {
        this.isStopped=false;
        this.effect_speed=speed;
        this.particleType=particleType;
        ParticleEffect.this.target=target;
    }

    public void doEffect() {
        Location loc = target.getLocation();
        double y;
        double z;
        for (int i = 0; i < 180; i++) {
            loc.add(loc.getDirection().multiply(i));
            y = loc.getY() + Math.cos(i);
            z = loc.getZ() + Math.sin(i);
            loc.getWorld().spawnParticle(Particle.FLAME, new Location(loc.getWorld(), loc.getX(), y, z, Float.valueOf(loc.getYaw() + ""), Float.valueOf(loc.getPitch() + "")), 10, 1, 1, 1, 0);
        }
    }

    public Entity getTarget() {
        return target;
    }

    public boolean isStopped() {
        return isStopped;
    }

    public void stop() {
        this.isStopped=true;
    }

    public long getSpeed() {
        return effect_speed;
    }

    public Particle getParticleType() {
        return particleType;
    }

    public void setSpeed(long effect_speed) {
        this.effect_speed = effect_speed;
    }

    public void setParticleType(Particle particleType) {
        this.particleType = particleType;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
