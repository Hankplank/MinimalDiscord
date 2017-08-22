package delphinadrealms;

import delphinadrealms.handlers.SQLManager;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;

import java.util.List;

/**
 * Created by henry27 on 8/13/2017.
 */
public class memberLeave {

    public void memberLeftEvent(Event event) {
        long guildID = ((GuildMemberJoinEvent) event).getGuild().getIdLong();
        SQLManager sql = new SQLManager();
        if (sql.getServerJoinMessageEnabled(guildID)) {
            long channelID = sql.getServerLobbyID(guildID);

            if (Long.toString(channelID).equalsIgnoreCase("0")) {
                List<TextChannel> channels = event.getJDA().getTextChannelsByName("lobby",true);
                String channelID1 = channels.toString();
                channelID1 = channelID1.substring(channelID1.indexOf("("),channelID1.indexOf(")"));
                channelID1 = channelID1.replace("(","");
                channelID1 = channelID1.replace(")","");
                channelID = Long.parseLong(channelID1);
                event.getJDA().getTextChannelById(channelID).sendMessage("Hello "
                        + ((GuildMemberJoinEvent) event).getMember().getAsMention() + " Welcome to the server!").queue();
            } else if (Long.toString(channelID).length() == 18) {
                event.getJDA().getTextChannelById(channelID).sendMessage("Hello "
                        + ((GuildMemberJoinEvent) event).getMember().getAsMention() + " Welcome to the server!").queue();
            }


        }


    }
}
