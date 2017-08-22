package delphinadrealms;

import delphinadrealms.handlers.SQLManager;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.EventListener;
import javax.security.auth.login.LoginException;
import delphinadrealms.commands.urbanDict;


public class Main implements EventListener
{
    public static String[] argsGlobal;


    public static void main(String[] args) throws IllegalArgumentException, LoginException, RateLimitedException {
        argsGlobal = args;
        if (args.length < 2) {
            System.out.println("Please include the Bot Token as a parameter, and the PUBG token as a parameter.");
        } else {
            new JDABuilder(AccountType.BOT).setToken(args[0]).addEventListener(new Main()).buildAsync();
        }
        /*
        if (sqlManager.isConnectionNull()) {
            sqlManager.connect();
        }
        */
        //testAddServer.testAddServer();
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof GuildMemberJoinEvent && !((GuildMemberJoinEvent) event).getMember().getUser().isBot()) {
            System.out.println("Member joined with the name:" + ((GuildMemberJoinEvent) event).getMember().getEffectiveName());
            memberJoin member = new memberJoin();
            member.memberJoinedEvent(event);

        } else if (event instanceof GuildMemberLeaveEvent && !((GuildMemberLeaveEvent) event).getMember().getUser().isBot()) {
            System.out.println("User left with the name: " + ((GuildMemberLeaveEvent) event).getMember().getEffectiveName());
            memberLeave memberleave = new memberLeave();
            memberleave.memberLeftEvent(event);

        } else if (event instanceof MessageReceivedEvent && ((MessageReceivedEvent) event).getMessage().toString().contains(Settings.COMMAND_PREFIX)) {
            messageReceivedEvent messageReceived = new messageReceivedEvent();
            messageReceived.messageReceived(event);

        } else if (event instanceof GuildLeaveEvent) {
            System.out.println("Left guild with the name: " + ((GuildLeaveEvent) event).getGuild().getName());
            SQLManager sql = new SQLManager();
            if (!sql.isConnectionNull()) {
                sql.removeServer(((GuildLeaveEvent) event).getGuild().getIdLong());
            } else if (sql.isConnectionNull()) {
                sql.connect();
                sql.removeServer(((GuildLeaveEvent) event).getGuild().getIdLong());
            }
            sql.close();

        } else if (event instanceof GuildJoinEvent) {
            System.out.println("Joined server: " + ((GuildJoinEvent) event).getGuild().getId());
            SQLManager sql = new SQLManager();
            if (!sql.isConnectionNull()) {
                long lobbyID;
                if (((GuildJoinEvent) event).getGuild().getTextChannelsByName("lobby",true).get(0).getName().equalsIgnoreCase("lobby")) {
                    lobbyID = ((GuildJoinEvent) event).getGuild().getTextChannelsByName("lobby",true).get(0).getIdLong();
                } else {
                    lobbyID = ((GuildJoinEvent) event).getGuild().getTextChannelsByName("general",true).get(0).getIdLong();
                }
                long guildID = ((GuildJoinEvent) event).getGuild().getIdLong();
                if (Long.toString(lobbyID).length()== 18) {
                    System.out.println(guildID + " " + lobbyID);
                    sql.addServer(guildID,lobbyID, true, true, true, true);
                } else {
                    sql.addServer(((GuildJoinEvent) event).getGuild().getIdLong(),0,false,false,true,true);
                }

            } else {
                System.out.println("sqlManager wasn't connected, trying to connect then trying again.");
                sql.connect();

                sql.addServer(((GuildJoinEvent) event).getGuild().getIdLong(),0,false,false,true,true);
            }
        }
    }


}