package delphinadrealms.commands;

import delphinadrealms.Settings;
import net.dv8tion.jda.core.entities.MessageChannel;

/**
 * Created by henry27 on 7/27/2017.
 */
public class commandList {

    public static void printHelpComamnd(MessageChannel channel) {
        channel.sendMessage("Current command list is as follows:\n" +
                "lolnames : check if a name is open on League of Legends. Example command: " + Settings.COMMAND_PREFIX + "lolnames hank,na\n" +
                "Ping : Check the ping of the bot. Example command: " + Settings.COMMAND_PREFIX + "ping\n" +
                "pubg : Check the PUBG Stats of a username. Example command: " + Settings.COMMAND_PREFIX + "pubg almostfamous,na:squad").queue();
    }
}
