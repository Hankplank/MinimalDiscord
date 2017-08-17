package delphinadrealms;

import delphinadrealms.handlers.SQLManager;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;

/**
 * Created by henry27 on 7/21/2017.
 */
public class memberJoin {

    public void memberJoinedEvent(Event event) {
        long guildID = ((GuildMemberJoinEvent) event).getGuild().getIdLong();
        SQLManager sql = new SQLManager();
        if (sql.getServerJoinMessageEnabled(guildID)) {
            long channelID = sql.getServerLobbyID(guildID);
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
