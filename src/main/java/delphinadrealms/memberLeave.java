package delphinadrealms;

import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;

/**
 * Created by henry27 on 8/13/2017.
 */
public class memberLeave {

    public void memberLeftEvent(Event event) {
        long guildID = ((GuildMemberJoinEvent) event).getGuild().getIdLong();

        if (Main.sqlManager.getServerJoinMessageEnabled(guildID)) {
            long channelID = Main.sqlManager.getServerLobbyID(guildID);
            if (Long.toString(channelID).equalsIgnoreCase("0")) {
                event.getJDA().getTextChannelById("lobby").sendMessage("Hello "
                        + ((GuildMemberJoinEvent) event).getMember().getAsMention() + " Welcome to the server!").queue();
            } else if (Long.toString(channelID).length() == 18) {
                event.getJDA().getTextChannelById(channelID).sendMessage("Hello "
                        + ((GuildMemberJoinEvent) event).getMember().getAsMention() + " Welcome to the server!").queue();
            }


        }


    }
}
