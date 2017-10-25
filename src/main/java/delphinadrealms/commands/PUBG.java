package delphinadrealms.commands;

import delphinadrealms.Main;
import net.dv8tion.jda.core.entities.MessageChannel;
import pro.lukasgorny.core.JPubg;
import pro.lukasgorny.dto.Player;
import pro.lukasgorny.dto.Stat;
import pro.lukasgorny.enums.PUBGMode;
import pro.lukasgorny.enums.PUBGRegion;
import pro.lukasgorny.enums.PUBGStat;
import pro.lukasgorny.exceptions.ApiException;
import pro.lukasgorny.factory.JPubgFactory;

/**
 * Created by henry27 on 7/23/2017.
 */
public class PUBG {

    public static void getPubgStats(MessageChannel channel, String username, String region, String mode) {

        PUBGMode mode1 = getMode(mode,channel);
        PUBGRegion region1 = getRegion(region,channel);
        JPubg jPubg = JPubgFactory.getWrapper(Main.argsGlobal[1]);

      //  System.out.println(region1.toString() + " " + mode1.toString() + " "  + username);
        if (!mode1.equals(null) && !region1.equals(null)) {
            try {
                Player player = jPubg.getByNickname(username,mode1,region1);
                Stat rating = jPubg.getPlayerMatchStatByStatName(player, PUBGStat.RATING);
                Stat kills = jPubg.getPlayerMatchStatByStatName(player, PUBGStat.KILLS);
                Stat KDR = jPubg.getPlayerMatchStatByStatName(player, PUBGStat.KILL_DEATH_RATIO);
                Stat KillsPerGame = jPubg.getPlayerMatchStatByStatName(player, PUBGStat.KILLS_PER_GAME);
                Stat AvgDmgPerRound = jPubg.getPlayerMatchStatByStatName(player,PUBGStat.DAMAGE_PER_GAME);
                Stat wins = jPubg.getPlayerMatchStatByStatName(player,PUBGStat.WINS);
                Stat mostKills = jPubg.getPlayerMatchStatByStatName(player,PUBGStat.ROUND_MOST_KILLS);
                Stat roundsPlayed =jPubg.getPlayerMatchStatByStatName(player,PUBGStat.ROUNDS_PLAYED);
                channel.sendMessage("```Stats for %s are as follows in the queue type: %s. \nRating: %s. \nKills: %s \nKDR: %s \nKills Per Game: %s \nAverage Damage Per Game: %s \nWins: %s \nRounds Played: %s \nMost Kills: %s```",username,mode1,rating.getStringValue(),
                        kills.getStringValue(),KDR.getStringValue(), KillsPerGame.getStringValue(),AvgDmgPerRound.getStringValue(),wins.getStringValue(),roundsPlayed.getStringValue(), mostKills.getStringValue()).queue();

            } catch (ApiException e) {
                if (e.getMessage().contains("no matches played")) {
                    channel.sendMessage("User %s doesn't exist or has no matches played.",username).queue();
                } else {
                    e.printStackTrace();
                }


            }
        } else {
            System.out.printf("One of the values was null! Mode: %s Region:%s\n",mode1,region1);

        }
    }

    public static  void getAllPUBGStats(MessageChannel channel, String username, String region) {
        JPubg pubg = JPubgFactory.getWrapper(Main.argsGlobal[1]);
        PUBGRegion pubgRegion = getRegion(region,channel);
        if (!pubgRegion.equals(null)) {
            try {
                Player solo = pubg.getByNickname(username,PUBGMode.solo,pubgRegion);
                Player duo = pubg.getByNickname(username,PUBGMode.duo,pubgRegion);
                Player squad = pubg.getByNickname(username,PUBGMode.squad,pubgRegion);

                String[] soloStats = getStats(solo);
                if (soloStats[0].equalsIgnoreCase("error")) {
                    channel.sendMessage("No stats for that user").queue();
                } else {
                    channel.sendMessage("```Stats for Player: %s in: Solos\nRating: %s\nKills: %s\nKDR: %s\nKills Per Game: %s\nAverage Damage Per round: %s\nWins: %s\nRounds Played: %s\nMost Kills: %s```", username, soloStats[0],soloStats[1],soloStats[2],soloStats[3],soloStats[4],soloStats[5],soloStats[6],soloStats[7]).queue();
                }
                String[] duoStats = getStats(duo);
                if (duoStats[0].equalsIgnoreCase("error")) {
                    channel.sendMessage("No stats for that user").queue();
                } else {
                    channel.sendMessage("```Stats for Player: %s in: Duos\nRating: %s\nKills: %s\nKDR: %s\nKills Per Game: %s\nAverage Damage Per round: %s\nWins: %s\nRounds Played: %s\nMost Kills: %s```", username, duoStats[0],duoStats[1],duoStats[2],duoStats[3],duoStats[4],duoStats[5],duoStats[6],duoStats[7]).queue();
                }
                String[] squadStats = getStats(squad);
                if (squadStats[0].equalsIgnoreCase("error")) {
                    channel.sendMessage("No stats for that user").queue();
                } else {
                    channel.sendMessage("```Stats for Player: %s in: Squads\nRating: %s\nKills: %s\nKDR: %s\nKills Per Game: %s\nAverage Damage Per round: %s\nWins: %s\nRounds Played: %s\nMost Kills: %s```", username, squadStats[0],squadStats[1],squadStats[2],squadStats[3],squadStats[4],squadStats[5],squadStats[6],squadStats[7]).queue();

                }
            } catch (ApiException e) {
                if (e.getMessage().contains("no matches played")) {
                    channel.sendMessage("User %s doesn't exist or has no matches played.",username).queue();
                } else {
                    e.printStackTrace();
                }
            }
        } else {
        }
    }

    private static String[] getStats(Player player) {
        JPubg pubg = JPubgFactory.getWrapper(Main.argsGlobal[1]);
        Stat rating;
        Stat kills;
        Stat kdr;
        Stat KillsPerGame;
        Stat AvgDmgPerRound;
        Stat wins;
        Stat roundsPlayed;
        Stat mostKills;
         try {
             rating = pubg.getPlayerMatchStatByStatName(player, PUBGStat.RATING);
             kills = pubg.getPlayerMatchStatByStatName(player, PUBGStat.KILLS);
             kdr = pubg.getPlayerMatchStatByStatName(player, PUBGStat.KILL_DEATH_RATIO);
             KillsPerGame = pubg.getPlayerMatchStatByStatName(player, PUBGStat.KILLS_PER_GAME);
             AvgDmgPerRound = pubg.getPlayerMatchStatByStatName(player,PUBGStat.DAMAGE_PER_GAME);
             wins = pubg.getPlayerMatchStatByStatName(player,PUBGStat.WINS);
             roundsPlayed = pubg.getPlayerMatchStatByStatName(player, PUBGStat.ROUNDS_PLAYED);
             mostKills = pubg.getPlayerMatchStatByStatName(player, PUBGStat.ROUND_MOST_KILLS);
             String[] Stats = {rating.getStringValue(),kills.getStringValue(),kdr.getStringValue(), KillsPerGame.getStringValue(),AvgDmgPerRound.getStringValue(),wins.getStringValue(),roundsPlayed.getStringValue(),mostKills.getStringValue()};
             return Stats;
         } catch (ApiException e) {
             if (e.getMessage().contains("no matches played")) {

             } else {
                 e.printStackTrace();
             }

             String[] Stats = {"error"};
             return Stats;
         }

    }

    private static PUBGMode getMode(String modeString, MessageChannel channel) {
        switch (modeString) {
            case "solo":
                return PUBGMode.solo;
            case "duo":
                return PUBGMode.duo;
            case "squad":
                return PUBGMode.squad;
            default:
                System.out.println("User did not specify a valid mode: " + modeString);
                channel.sendMessage("Please use a valid mode of: solo,duo,squad").queue();
                return null;
        }
    }
    private static PUBGRegion getRegion(String regionString,MessageChannel channel) {
        switch (regionString) {
            case "na":
                return PUBGRegion.na;
            case "eu":
                return PUBGRegion.eu;
            case "sa":
                return PUBGRegion.sa;
            case "as":
                return PUBGRegion.as;
            default:
                System.out.println("User did not specify a valid region: " + regionString);
                channel.sendMessage("Please use regions, na, eu, sa, as").queue();
                return null;
        }
    }
}
