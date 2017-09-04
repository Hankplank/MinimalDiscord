package delphinadrealms;

import delphinadrealms.commands.*;
import delphinadrealms.handlers.SQLManager;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import delphinadrealms.commands.urbanDict;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by henry27 on 7/21/2017.
 */
class messageReceivedEvent {


    public void messageReceived(Event event) {

        String messageFormatted = ((MessageReceivedEvent) event).getMessage().getRawContent().replace(Settings.COMMAND_PREFIX, ""); // removes the command prefix from the command
        MessageChannel channel = ((MessageReceivedEvent) event).getChannel(); //gets the text channel that the mesasge is sent in
        Message message = ((MessageReceivedEvent) event).getMessage(); // creates variable for the message in a easier to see format
        String[] args = messageFormatted.split(" ");
        SQLManager sql = new SQLManager();
        urbanDict ud = new urbanDict();
        switch (args[0].toLowerCase()) {
            case "pubg":
                if (args.length > 3) {
                    String username = args[1];
                    String region = args[2];
                    String mode = args[3];
                    if (mode.equalsIgnoreCase("all")) {
                        PUBG.getAllPUBGStats(channel, username, region);
                    } else if (mode.equalsIgnoreCase("solo") || mode.equalsIgnoreCase("duo") || mode.equalsIgnoreCase("squad")) {
                        PUBG.getPubgStats(channel, username, region, mode);
                    }
                } else {
                    channel.sendMessage("Use the command as: " + Settings.COMMAND_PREFIX + "pubg almostfamous na squad").queue();
                }
                break;


        }
    }
}