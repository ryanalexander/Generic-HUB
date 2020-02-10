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

package net.blockcade.HUB.Common.Utils;

public class JavaUtils {

    public static enum TimeUnit {
        MILI("Milliseconds ",1),
        SECOND("Seconds",1000),
        MINUTE("Minutes",60000);
        private String translated;
        private int division;
        TimeUnit(String translated, int division){
            this.translated=translated;this.division=division;
        }
    }

    public static long FormatMS(Long time, TimeUnit timeUnit){
        /*
        TimeUnit timeUnit = TimeUnit.MILI;
        if(time>60000){timeUnit=TimeUnit.MINUTE;}
        if((time<60000)){timeUnit=TimeUnit.SECOND;}
        if((time<1000)){timeUnit=TimeUnit.MILI;}
        */

        return ((time==0?1:time)/timeUnit.division);
    }

}
