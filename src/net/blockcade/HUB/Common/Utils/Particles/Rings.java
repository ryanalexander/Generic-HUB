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
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Rings extends ParticleEffect {

    int frame = 0;

    // Changes nothing rn
    public Rings(Player player){
        target=player;new ParticleEffect(5L, Particle.FLAME,player);
    }

    public void doEffect() {
        if(frame>=360)frame=0;
        Location loc = getTarget().getLocation();
        double x;
        double z;
        x = loc.getX() + Math.cos(frame);
        z = loc.getZ() + Math.sin(frame);
        loc.getWorld().spawnParticle(Particle.FLAME, new Location(loc.getWorld(), x,loc.getY(), z, Float.valueOf(loc.getYaw() + ""), Float.valueOf(loc.getPitch() + "")), 1, 0, 0, 0, 0);
    }

    private Vector rotateAroundAxisX(Vector v, double cos, double sin) {
        double y = v.getY() * cos - v.getZ() * sin;
        double z = v.getY() * sin + v.getZ() * cos;
        return v.setY(y).setZ(z);
    }

    private Vector rotateAroundAxisY(Vector v, double cos, double sin) {
        double x = v.getX() * cos + v.getZ() * sin;
        double z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(x).setZ(z);
    }

    private Vector rotateAroundAxisZ(Vector v, double cos, double sin) {
        double x = v.getX() * cos - v.getY() * sin;
        double y = v.getX() * sin + v.getY() * cos;
        return v.setX(x).setY(y);
    }

}