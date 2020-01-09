package net.blockcade.HUB.Common.Utils;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

public class WingAPI {

    public static HashMap<Player, WinggedPlayer> WinggedPlayers = new HashMap<>();
    private static HashMap<String, BufferedImage> images = new HashMap<>();

    public WingAPI (JavaPlugin plugin){
        new BukkitRunnable(){
            @Override
            public void run() {
                for(WinggedPlayer winggedPlayer : WinggedPlayers.values()) {
                    if(!winggedPlayer.player.isOnline()){WinggedPlayers.remove(winggedPlayer.player);continue;}
                    winggedPlayer.render();
                }
            }
        }.runTaskTimer(plugin,0L,1L);
    }

    public static class WinggedPlayer {
        private Player player;
        private BufferedImage image;

        public WinggedPlayer(Player player, String wings){
            if(images.containsKey(wings)){this.image=images.get(wings);}
            else {
                try {
                    image = ImageIO.read(new File(String.format("plugins/HUB/wings/%s.png", wings)));
                    images.put(wings,image);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            this.player=player;
            WinggedPlayers.put(player,this);
        }
        public void stop() {
            WinggedPlayers.remove(player);
        }
        private void render(){
            for (int yy = 0; yy < image.getHeight(); yy++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    final int clr = image.getRGB(x, yy);
                    final int y = image.getHeight() - yy;
                    Color color = new Color(clr);

                    if (clr == 16777215) continue;

                    if (x < image.getWidth() / 2) {
                        spawnParticle(x - image.getWidth() / 2, 0, y, player, 0, color);
                    } else if (x > image.getWidth() / 2) {
                        final int off = image.getWidth() - x;
                        final int off2 = image.getWidth() / 2 - off;
                        spawnParticle(0, off2, y, player, 1, color);
                    }
                }
            }
        }
        private void spawnParticle(Location loc, int r, int g, int b) {
            Particle.DustOptions options = new Particle.DustOptions(org.bukkit.Color.fromRGB(r,g,b),0.25f);
            loc.getWorld().spawnParticle(Particle.REDSTONE, loc.getX(), loc.getY(), loc.getZ(), 0, getValue(r) + 0.0000001F, getValue(g), getValue(b), 1, options);
        }

        private float getValue(int input) {
            return (float) (1.0 / 255) * input;
        }

        private void spawnParticle(final int offsetl, int offsetr, final int offsety, final Player p, final int mode, Color color) {
            Location d = p.getLocation().clone();
            d.setPitch(0F);

            final Vector v = d.getDirection();

            final Vector vr = v.clone().setX(-v.getZ()).setZ(v.getX());

            final Vector vl = v.clone().setX(v.getZ()).setZ(-v.getX());

            final double ofr = ++offsetr * 0.04;
            final double ofl = offsetl * 0.04;
            final double ofy = offsety * 0.04;

            vr.multiply(ofr);
            vl.multiply(ofl * -1.0);

            double distance = 0.2d;
            double yawRadians = Math.PI * d.getYaw() / 180;
            d.add(distance * Math.sin(yawRadians), p.isSneaking()?0.2d:0.4d, -distance * Math.cos(yawRadians));

            if (mode == 0) {
                final Location left = d.add(vl.toLocation(p.getWorld()));
                left.add(0.0, ofy, 0.0);
                spawnParticle(left, color.getRed(), color.getGreen(), color.getBlue());
            } else {
                final Location right = d.add(vr.toLocation(p.getWorld()));
                right.add(0.0, ofy, 0.0);
                spawnParticle(right, color.getRed(), color.getGreen(), color.getBlue());
            }

        }
    }
}