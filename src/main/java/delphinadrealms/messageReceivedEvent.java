package delphinadrealms;

import delphinadrealms.commands.*;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by henry27 on 7/21/2017.
 */
class messageReceivedEvent {


    public void messageReceived(Event event) {

        String messageFormatted = ((MessageReceivedEvent) event).getMessage().getRawContent().replace(Settings.COMMAND_PREFIX, ""); // removes the command prefix from the command
        MessageChannel channel = ((MessageReceivedEvent) event).getChannel(); //gets the text channel that the mesasge is sent in
        Message message = ((MessageReceivedEvent) event).getMessage(); // creates variable for the message in a easier to see format

        if (messageFormatted.startsWith("roles")) {
           // System.out.println(((MessageReceivedEvent) event).getAuthor().getJDA().getRoles().toString());
            channel.sendMessage("The roles this user belongs to are: " + message.getMember().getRoles()).queue();
        } else if (messageFormatted.startsWith("channel")) {
            System.out.println("Channel ID is:" + ((MessageReceivedEvent) event).getTextChannel().toString());
        } else if (messageFormatted.startsWith("playing")) {
            channel.sendMessage("Kys this isn't ready to be used yet").queue();
        } else if (messageFormatted.startsWith("ping")) {
            channel.sendMessage("The ping of the bot is: %d", event.getJDA().getPing()).queue();
        } else if (messageFormatted.startsWith("lolname")) {
            //String[] regions= {"na","euw","eune","br","lan","las","oce","ru","tr"};
            if (!messageFormatted.contains(",")) {
                channel.sendMessage("Please use the format in this example,using the name hank and the region na: " + Settings.COMMAND_PREFIX + "lolname hank,na").queue();
            } else {
                channel.sendMessage(CheckNameLoL.isNameOpen(messageFormatted)).queue();
            }
        } else if (messageFormatted.startsWith("getmatch")) {
            messageFormatted = messageFormatted.substring(8);
            if (!getLeagueMatch.doesPlayerExist(message, messageFormatted)) {
                channel.sendMessage("Please use " + Settings.COMMAND_PREFIX + "leaguename to set up your league name with the bot.");
            } else {
                channel.sendMessage(getLeagueMatch.getGameInfo()).queue();
            }
        } else if (messageFormatted.startsWith("leaguename")) {
            messageFormatted = messageFormatted.substring(10);
            if (!getLeagueMatch.doesPlayerExist(message, messageFormatted)) {
                getLeagueMatch.setPlayerName(message, messageFormatted, channel);
                System.out.println("user doesnt exist");
            } else {
                System.out.println("user exists");
            }

        } else if (messageFormatted.startsWith("channelid")) {
            System.out.println(channel.getIdLong());

        } else if (messageFormatted.startsWith("userid")) {
            long userID = ((MessageReceivedEvent) event).getAuthor().getIdLong();
            channel.sendMessage("Your user id is: " + userID).queue();

        } else if (messageFormatted.startsWith("pubg")) {
                messageFormatted = messageFormatted.substring(5);
                String[] args = messageFormatted.split(" ");
                String username = args[0];
                String region = args[1];
                String mode = args[2];
                if (mode.equalsIgnoreCase("all")) {
                    PUBG.getAllPUBGStats(channel,username,region);
                } else if (mode.equalsIgnoreCase("solo") || mode.equalsIgnoreCase("duo") || mode.equalsIgnoreCase("squad")){
                    PUBG.getPubgStats(channel, username, region, mode);
                }
            } else if (messageFormatted.contains("help")) {
            commandList.printHelpComamnd(channel);
        } else if (messageFormatted.startsWith("changelobby")) {
            if (message.getMember().getPermissions().toString().contains("Administrator")) {

            }
        } else if (messageFormatted.startsWith("addserver")) {
            Main.sqlManager.addServer(((MessageReceivedEvent) event).getGuild().getIdLong(),message.getTextChannel().getIdLong(),true,true,true,true);
            channel.sendMessage("Adding server...").queue();
        } else if (messageFormatted.startsWith("disablepubg")) {
            enableDisablePubg.changePubgEnabled(message,false);
        } else if (messageFormatted.startsWith("enablepubg")) {
            enableDisablePubg.changePubgEnabled(message,true);
        } else if (messageFormatted.startsWith("disableleague")) {
            enableDisablePubg.changeLeagueEnabled(message,false);
        } else if (messageFormatted.startsWith("enableleague")) {
            enableDisablePubg.changeLeagueEnabled(message, true);

        }

        }




}
