package delphinadrealms.commands;

import pro.lukasgorny.dto.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Created by henry27 on 8/7/2017.
 */
public class SQLManager {

    public SQLManager() {
        this.connect = null;

    }

    private Connection connect = null;

    public void connect() {
        try {
            String url = "jdbc:SQLite:/root/discordbot.db";
            connect = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
    //                            SERVER ID          snedJoinMessageChannelID sendLeaveMessage.. sendJoinMessage SendLeaveMessage enablePUBG enableLeague
    //INSERT INTO SERVERS VALUES (334189774741045249,334190910852169729,334190910852169729,"true","true","true","true");


    public void addServer(long serverID,long lobbyChannelID, boolean sendJoinMessage, boolean sendLeaveMessage, boolean enablePUBG, boolean enableLeague) {
        if (!connect.equals(null)) {
            try {
                String command = "INSERT INTO SERVERS VALUES (" + serverID + "," + lobbyChannelID + "," +  sendJoinMessage + "," + sendLeaveMessage + "," + enablePUBG + ","
                        + enableLeague + ");";
                Statement statement = connect.createStatement();
                statement.executeQuery(command);

            } catch (SQLException e) {

            }
        }
    }

    public void removeServer(long serverID) {
        if (!connect.equals(null)) {
            try {
                String command = "DELETE FROM servers WHERE serverid=" + Long.toString(serverID) + ";";
                Statement statement = connect.createStatement();
                statement.executeQuery(command);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public long getServerLobbyID(long serverID) {
        if (!connect.equals(null)) {
            try {
                String command = "SELECT * FROM SERVERS WHERE serverid=" + serverID + ";";
                Statement statement = connect.createStatement();
                ResultSet rs = statement.executeQuery(command);
                return rs.getLong("lobbyChannelID");

            } catch (SQLException e) {
                return 0;
            }
        }
        return 0;
    }

    public boolean getLeagueEnabled(long serverID) {
        try {
            if (!connect.isClosed()) {
                String command = "SELECT * FROM SERVERS WHERE serverid=" + serverID + ";";
                Statement statement = connect.createStatement();
                ResultSet rs = statement.executeQuery(command);
                return rs.getBoolean("enableLeague");
            } else {
                System.out.println("Something was wrong. Just returned false. getLeagueEnabled");
                return false;
            }
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return false;
    }

    public boolean getPUBGEnabled(long serverID) {
        try {
            if (!connect.isClosed()) {
                String command = "SELECT * FROM SERVERS WHERE serverid=" + serverID + ";";
                Statement statement = connect.createStatement();
                ResultSet rs = statement.executeQuery(command);
                return rs.getBoolean("enablePUBG");
            } else {
                System.out.println("Something was wrong. Just returned false. getLeagueEnabled");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isConnectionNull() {
        if (connect.equals(null)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getServerJoinMessageEnabled(long serverID) {
        if (!connect.equals(null)) {
            String command = "SELECT * FROM servers WHERE serverid=" + serverID + ";";
            try {
                Statement statement = connect.createStatement();
                ResultSet rs = statement.executeQuery(command);
                if (rs.getString("sendJoinMessage").equalsIgnoreCase("true")) {
                    return true;
                } else if (rs.getString("sendJoinMessage").equalsIgnoreCase("false")) {
                    return false;
                } else {
                    return false;
                }
            } catch (SQLException e) {
                System.out.println("Error on in SQLManager.java in getServerJoinMessageEnabled.\n" + e.getMessage());
            }
        }
return true;
    }
}
