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
        MEMBER(0,"&f&l",99),
        SUPER(1,"&a&l",90),
        MEGA(2,"&b&l",80),
        ULTRA(3,"&d&l",70),
        MEDIA(10,"&6&l",60),
        HELPER(50, "&9&l",40),
        ADMIN(85,"&c&l",1);
        private int level;
        private int weight;
        private String rank;
        public int getLevel() {return this.level;}
        public String getFormatted() {return this.getColor()+this.name()+"&r";}
        public String getColor() {return this.rank;}
        public int getWeight() {
                return weight;
        }

        private Ranks(int level, String rank, int weight){this.rank = rank.toUpperCase();this.level=level;}
}