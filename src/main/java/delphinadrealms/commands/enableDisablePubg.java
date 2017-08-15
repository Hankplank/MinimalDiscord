package delphinadrealms.commands;

import delphinadrealms.Main;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.Event;

import java.sql.Statement;

/**
 * Created by henry27 on 8/15/2017.
 */
public class enableDisablePubg {

    public static void changePubgEnabled(Message message, boolean bool) {
        long serverid = message.getGuild().getIdLong();
        if (bool == true) {
            Main.sqlManager.setPUBGEnabled(serverid);
        } else if (bool == false) {
            Main.sqlManager.setPUBGDisabled(serverid);
        }
    }

    public static void changeLeagueEnabled(Message message, boolean bool) {
    long serverid = message.getGuild().getIdLong();
        if (bool == true) {
        Main.sqlManager.setLeagueEnabled(serverid);
    } else if (bool == false) {
        Main.sqlManager.setLeagueDisabled(serverid);
    }
}
}
