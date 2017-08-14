package delphinadrealms;

import delphinadrealms.commands.SQLManager;
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


public class Main implements EventListener
{
    public static String[] argsGlobal;

    public static SQLManager sqlManager = new SQLManager();

    public static void main(String[] args) throws IllegalArgumentException, LoginException, RateLimitedException {
        argsGlobal = args;
        if (args.length < 1) {
            System.out.println("Please include the Token as a parameter.");
        } else {
            new JDABuilder(AccountType.BOT).setToken(args[0]).addEventListener(new Main()).buildAsync();
        }
        sqlManager = new SQLManager();
        sqlManager.connect();

    }


    @Override
    public void onEvent(Event event) {
        if (event instanceof GuildMemberJoinEvent) {
            memberJoin member = new memberJoin();
            member.memberJoinedEvent(event);

        } else if (event instanceof MessageReceivedEvent && ((MessageReceivedEvent) event).getMessage().toString().contains(Settings.COMMAND_PREFIX)) {
            messageRecievedEvent messageRecieved = new messageRecievedEvent();
            messageRecieved.messageRecieved(event);

        } else if (event instanceof GuildMemberLeaveEvent) {
            memberLeave memberleave = new memberLeave();
            memberleave.memberLeftEvent(event);

        } else if (event instanceof GuildLeaveEvent) {
            if (!sqlManager.isConnectionNull()) {
                sqlManager.removeServer(((GuildLeaveEvent) event).getGuild().getIdLong());
            } else {
                sqlManager.connect();
                sqlManager.removeServer(((GuildLeaveEvent) event).getGuild().getIdLong());
            }

        } else if (event instanceof GuildJoinEvent) {
            if (!sqlManager.isConnectionNull()) {
                long lobbyID = ((GuildJoinEvent) event).getGuild().getTextChannelById("lobby").getIdLong();
                long generalID = ((GuildJoinEvent) event).getGuild().getTextChannelById("general").getIdLong();
                if (Long.toString(lobbyID).length()== 18) {
                    sqlManager.addServer(((GuildJoinEvent) event).getGuild().getIdLong(), lobbyID, false, false, true, true);
                } else if (Long.toString(generalID).length() == 18) {
                    sqlManager.addServer(((GuildJoinEvent) event).getGuild().getIdLong(), generalID, false, false, true, true);
                } else {
                    sqlManager.addServer(((GuildJoinEvent) event).getGuild().getIdLong(),0,false,false,true,true);
                }

            } else {
                sqlManager.connect();
                sqlManager.addServer(((GuildJoinEvent) event).getGuild().getIdLong(),0,false,false,true,true);
            }
        }
    }


}