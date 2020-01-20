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
 * @since (DD/MM/YYYY) 20/1/2020
 */

package net.blockcade.HUB.Common.Static.Variables;

import org.bukkit.ChatColor;

public enum Badge {
    STAFF('۞', ChatColor.RED,"Blockcade Staff"),
    BETA('✪', ChatColor.BLUE,"Beta Tester"),
    SUPPORTER('⛨',ChatColor.GREEN,"Supporter");

    char badge;
    ChatColor color;
    String name;

    Badge(char badge, ChatColor color, String name){
        this.badge=badge;
        this.color=color;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public char getBadge() {
        return badge;
    }

    public ChatColor getColor() {
        return color;
    }
}
