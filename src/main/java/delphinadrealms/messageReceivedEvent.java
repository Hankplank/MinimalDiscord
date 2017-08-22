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
            case "roles":
                channel.sendMessage("The roles this user belongs to are: " + message.getMember().getRoles()).queue();
                break;
            case "channel":
                System.out.println("Channel ID is:" + ((MessageReceivedEvent) event).getTextChannel().toString());
                break;
            case "playing":
                channel.sendMessage("Kys this isn't ready to be used yet").queue();
                break;
            case "ping":
                channel.sendMessage("The ping of the bot is: %d", event.getJDA().getPing()).queue();
                break;
            case "lolname":
                //String[] regions= {"na","euw","eune","br","lan","las","oce","ru","tr"};
                if (!messageFormatted.contains(",")) {
                    channel.sendMessage("Please use the format in this example,using the name hank and the region na: " + Settings.COMMAND_PREFIX + "lolname hank na").queue();
                } else {
                  //  channel.sendMessage(CheckNameLoL.isNameOpen(messageFormatted)).queue();
                    channel.sendMessage("Command is disabled until I feel like fixing it, sorry. Use http://lolnamecheck.jj.ai/ if you wish to check league names.").queue();
                }
                break;
            case "leaguename":
                if (sql.getLeagueEnabled(message.getGuild().getIdLong())) {
                    messageFormatted = messageFormatted.substring(10);
                    if (!getLeagueMatch.doesPlayerExist(message, messageFormatted)) {
                        getLeagueMatch.setPlayerName(message, messageFormatted, channel);
                        System.out.println("user doesn't exist");
                    } else {
                        System.out.println("user exists");
                    }
                    break;
                }
                break;

            case "channelid":
                System.out.println(channel.getIdLong());
                break;
            case "userid":
                long userID = ((MessageReceivedEvent) event).getAuthor().getIdLong();
                channel.sendMessage("Your user id is: " + userID).queue();
                break;
            case "pubg":
                if (args.length > 3) {
                    String username = args[1];
                    String region = args[2];
                    String mode = args[3];
                    if (mode.equalsIgnoreCase("all")) {
                        PUBG.getAllPUBGStats(channel,username,region);
                    } else if (mode.equalsIgnoreCase("solo") || mode.equalsIgnoreCase("duo") || mode.equalsIgnoreCase("squad")) {
                        PUBG.getPubgStats(channel, username, region, mode);
                    }
                } else {
                    channel.sendMessage("Use the command as: " + Settings.COMMAND_PREFIX + "pubg almostfamous na squad").queue();
                }
                break;
            case "help":
                commandList.printHelpComamnd(channel);
                break;
            case "addserver":
                sql.addServer(message.getGuild().getIdLong(),message.getTextChannel().getIdLong(),true,true,true,true);
                channel.sendMessage("Adding server...").queue();
                break;
            case "changelobby":
                if (message.getMember().getPermissions().toString().contains("ADMINISTRATOR")) {
                    //true if it worked, false if it failed
                    boolean result = sql.changeLobbyID(message.getGuild().getIdLong(),args[1]);
                    if (result) {
                        channel.sendMessage("Lobby has been changed for the server " + message.getGuild().getName() + " to: " + args[1]);
                    } else if (!result) {
                        channel.sendMessage("Please type use the lobbyId of the channel you want the leave and join messages to be sent in as the only argument.");
                    }
                } else {
                    channel.sendMessage("You need to have the Administrator permission to use this.").queue();
                }
                break;
            case "disablepubg":
                enableDisablePubg.changePubgEnabled(message,false);
                break;
            case "enablepubg":
                enableDisablePubg.changePubgEnabled(message,true);
                break;
            case "disableleague":
                enableDisablePubg.changeLeagueEnabled(message,false);
                break;
            case "enableleague":
                enableDisablePubg.changeLeagueEnabled(message, true);
                break;
            case "ud":
                messageFormatted = messageFormatted.substring(3);
                channel.sendMessage(ud.findWord(messageFormatted)).queue();
                break;
            case "urbandict":
                messageFormatted = messageFormatted.substring(10);
                channel.sendMessage(ud.findWord(messageFormatted)).queue();
                break;
            case "urbandictionary":
                messageFormatted = messageFormatted.substring(16);
                channel.sendMessage(ud.findWord(messageFormatted)).queue();
                break;
            case "listchannels":
                List<TextChannel> channels = event.getJDA().getTextChannelsByName("lobby",true);
                String channelID = channels.toString();
                channelID = channelID.substring(channelID.indexOf("("),channelID.indexOf(")"));
                channelID = channelID.replace("(","");
                channelID = channelID.replace(")","");
                System.out.println(channels.toString());
                System.out.println(channelID);
                System.out.println(Long.parseLong(channelID));
                break;
            default:
                channel.sendMessage("Command not recognized. Refer to documentation or just type it right next time.").queue();
                break;

        }
        sql.close();
/*
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
                channel.sendMessage("Please use the format in this example,using the name hank and the region na: " + Settings.COMMAND_PREFIX + "lolname hank na").queue();
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
                System.out.println("user doesn't exist");
            } else {
                System.out.println("user exists");
            }

        } else if (messageFormatted.startsWith("channelid")) {
            System.out.println(channel.getIdLong());

        } else if (messageFormatted.startsWith("userid")) {
            long userID = ((MessageReceivedEvent) event).getAuthor().getIdLong();
            channel.sendMessage("Your user id is: " + userID).queue();

        } else if (messageFormatted.startsWith("pubg")) {
            if (args.length > 3) {
                String username = args[1];
                String region = args[2];
                String mode = args[3];
                if (mode.equalsIgnoreCase("all")) {
                    PUBG.getAllPUBGStats(channel,username,region);
                } else if (mode.equalsIgnoreCase("solo") || mode.equalsIgnoreCase("duo") || mode.equalsIgnoreCase("squad")) {
                    PUBG.getPubgStats(channel, username, region, mode);
                }
                } else {
                    channel.sendMessage("Use the command as: " + Settings.COMMAND_PREFIX + "pubg almostfamous na squad").queue();
                }
            } else if (messageFormatted.contains("help")) {
            commandList.printHelpComamnd(channel);
        } else if (messageFormatted.startsWith("changelobby")) {
            if (message.getMember().getPermissions().toString().contains("ADMINISTRATOR")) {
                SQLManager sql = new SQLManager();
                String result = sql.changeLobbyID(message.getGuild().getIdLong(),args[1]);
                if (result == "worked") {
                    channel.sendMessage("Lobby has been changed for the server " + message.getGuild().getName() + " to: " + args[1]);
                } else if (result == "error") {
                    channel.sendMessage("Please type use the lobbyId of the channel you want the leave and join messages to be sent in as the only argument.");
                }
            } else {
                channel.sendMessage("You need to have the Administrator permission to use this.");

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
*/
        }




}
