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
        MEMBER(0,"&e&l"),
        SUPER(1,"&6&l"),
        MEGA(2,"&d&l"),
        ULTRA(3,"&a&l"),
        YOUTUBE(10,"&c&l"),
        HELPER(50, "&b&l"),
        ADMIN(85,"&4&l");
        private int level;
        private String rank;
        public int getLevel() {return this.level;}
        public String getFormatted() {return this.getColor()+this.name()+"&r";}
        public String getColor() {return this.rank;}
        private Ranks(int level,String rank){this.rank = rank.toUpperCase();this.level=level;}
}