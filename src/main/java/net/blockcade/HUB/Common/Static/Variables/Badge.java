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
    STAFF('◈', ChatColor.RED,"Blockcade Staff","eyJ0aW1lc3RhbXAiOjE1Nzk1ODAyNDI4MjIsInByb2ZpbGVJZCI6ImJlY2RkYjI4YTJjODQ5YjRhOWIwOTIyYTU4MDUxNDIwIiwicHJvZmlsZU5hbWUiOiJTdFR2Iiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS81OGU3YjM0OTMwNDRmMGIxZmQzNDNkYTM4NDQ2YzRmOTdmNzllYzE4ZWIwZTU4YTdiM2IxMTQ2OGI4ZDE2Mjc5In19fQ=="),
    BETA('✪', ChatColor.BLUE,"Beta Tester",""),
    SUPPORTER('⛨',ChatColor.GREEN,"Supporter","");

    char badge;
    ChatColor color;
    String name;
    String texture;

    Badge(char badge, ChatColor color, String name, String texture){
        this.badge=badge;
        this.color=color;
        this.name=name;
        this.texture=texture;
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
