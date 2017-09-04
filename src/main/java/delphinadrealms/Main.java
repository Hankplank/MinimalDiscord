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
        if (event instanceof MessageReceivedEvent && ((MessageReceivedEvent) event).getMessage().toString().contains(Settings.COMMAND_PREFIX)) {
            messageReceivedEvent messageReceived = new messageReceivedEvent();
            messageReceived.messageReceived(event);

        } else if (event instanceof GuildLeaveEvent) {
            System.out.println("Left guild with the name: " + ((GuildLeaveEvent) event).getGuild().getName());

        } else if (event instanceof GuildJoinEvent) {
            System.out.println("Joined server: " + ((GuildJoinEvent) event).getGuild().getName());

        }
    }


}