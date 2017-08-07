package delphinadrealms.commands;

import delphinadrealms.Settings;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * Created by henry27 on 7/20/2017.
 */
public class getLeagueMatch {

    public static void main(String[] args) {

    }

    public static String getGameInfo() {
        StringBuffer content = new StringBuffer();
        URLConnection connection = null;
        try {
            connection = new URL("/lol/spectator/v3/active-games/by-summoner/" + getSummonerID()).openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Game info:" + content);
        return "e";
    }

    public static boolean setPlayerName(Message message, String messageFormatted, MessageChannel channel) {
        messageFormatted = messageFormatted.substring(1);
        if (!messageFormatted.contains(",")) {
            channel.sendMessage("Please use the format: " + Settings.COMMAND_PREFIX + "leaguename examplename,exampleregion").queue();
        } else {
            String userDiscordName = message.getAuthor().getAsMention();
            String userLeagueName = messageFormatted.substring(0, messageFormatted.indexOf(","));
            String userRegion = messageFormatted.substring(messageFormatted.indexOf(",") + 1);
            userRegion = userRegion.replace(",", "");
            try {
                PrintWriter out = new PrintWriter("users.txt");
                out.append(userDiscordName + ":" + userLeagueName + ":" + userRegion);
                out.close();
                System.out.println(messageFormatted);
            } catch (FileNotFoundException e) {

            }
            return true;
        }
        return false;
    }

    public static boolean doesPlayerExist(Message message,String messageFormatted) {
    try {
        File file = new File("users.txt");
        Scanner scanner = new Scanner(file);
        int lineNum=0;
        boolean found = false;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lineNum++;
            if (line.contains(message.getAuthor().getAsMention())) {
                found= true;
            }

        }
        scanner.close();
        if (found) {
            return false;
        } else {
            return true;
        }
    } catch (FileNotFoundException e) {
            return false;
        }
    }

    public static long getSummonerID() {

        return 51;
    }
}
