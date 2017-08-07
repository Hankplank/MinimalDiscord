package delphinadrealms.commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
/**
 * Created by henry27 on 8/7/2017.
 */
public class SQLManager {

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public void connectToDatabase(String[]data) throws Exception{
        String hostname = data[1];
        String username = data[2];
        String password = data[3];
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jbdc:mysql:://" + hostname + "/feedback?user=" + username + "&password=" + password);
        } catch (Exception e) {

        }

    }

    public void writeData() {

    }
}
