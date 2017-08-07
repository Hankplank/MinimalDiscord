package delphinadrealms;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.EventListener;
import javax.security.auth.login.LoginException;


public class Main implements EventListener
{
    public static void main(String[] args)

            throws IllegalArgumentException, LoginException, RateLimitedException {
        if (args.length < 1) {
            System.out.println("Please include the Token as a parameter.");
        } else {
            new JDABuilder(AccountType.BOT).setToken(args[0]).addEventListener(new Main()).buildAsync();
        }


    }


    @Override
    public void onEvent(Event event) {
        if (event instanceof GuildMemberJoinEvent) {
            memberJoin member = new memberJoin();
            member.memberJoinEvent(event);
        } else if (event instanceof MessageReceivedEvent && ((MessageReceivedEvent) event).getMessage().toString().contains(Settings.COMMAND_PREFIX)) {
            messageRecievedEvent messageRecieved = new messageRecievedEvent();
            messageRecieved.messageRecieved(event);


        } else if (event instanceof GuildMemberLeaveEvent) {
            ((GuildMemberLeaveEvent) event).getGuild().getTextChannelById(334190910852169729L).sendMessage("Bye, " +
                    ((GuildMemberLeaveEvent) event).getMember().getEffectiveName() + "  has left the server...").queue();
        }
    }


}