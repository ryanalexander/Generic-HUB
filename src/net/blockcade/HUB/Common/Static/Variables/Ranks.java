/*
 *
 *  *
 *  * © Stelch Software 2019, distribution is strictly prohibited
 *  * Blockcade is a company of Stelch Software
 *  *
 *  * Changes to this file must be documented on push.
 *  * Unauthorised changes to this file are prohibited.
 *  *
 *  * @author Ryan Wood
 *  @since 5/8/2019
 */

package net.blockcade.HUB.Common.Static.Variables;

public enum Ranks {
        MEMBER(0,"&e"),
        SUPER(1,"&a"),
        MEGA(2,"&b"),
        ULTRA(3,"&d"),
        YOUTUBE(10,"&4"),
        BUILDER(50,"&3"),
        ADMIN(85,"&c"),
        DEV(85,"&6"),
        OWNER(99,"&9");
        private int level;
        private String rank;
        public int getLevel() {return this.level;}
        public String getFormatted() {return this.getColor()+this.name();}
        public String getColor() {return this.rank;}
        private Ranks(int level,String rank){this.rank = rank.toUpperCase();this.level=level;}
}