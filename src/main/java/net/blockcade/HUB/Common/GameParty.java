
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

package net.blockcade.HUB.Common;

import java.util.ArrayList;
import java.util.HashMap;

public class GameParty {

    private ArrayList<GamePlayer> players=new ArrayList<>();
    private GamePlayer leader;

    public boolean hasMember(GamePlayer player){return players.contains(player);}

}
