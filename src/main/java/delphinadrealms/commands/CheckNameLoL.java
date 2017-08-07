package delphinadrealms.commands;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * Created by henry27 on 7/19/2017.
 */
public class CheckNameLoL {

    public static String isNameOpen(String commandargs) {
        commandargs = commandargs.substring(8);
        String name = commandargs.substring(0,commandargs.indexOf(","));
        String region = commandargs.substring(commandargs.indexOf(","));
        region = region.replace(",","");
       // System.out.println(name+region);
        if (name.contains(" ")) {
           name = name.replace(" ","%");
        }
        String urlString = "http://lolnamecheck.jj.ai/main/check/check?username=" + name + "&region_name=" + region + "&g=a&_=1500448450925";
        URLConnection connection = null;
        try {
            connection =  new URL(urlString).openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            //System.out.println(content.toString());
            if (content.toString().contains("{\"available\":false")) {
                String contentString = content.toString();
                String availableWhen = contentString.substring(contentString.lastIndexOf(":"));
                availableWhen = availableWhen.replace(":","");
                availableWhen = availableWhen.replace("\"","");
                availableWhen = availableWhen.replace("}","");
                return "The name is not open, but will be on: " + availableWhen;
            } else if(content.toString().contains("{\"available\":true")) {

                return "The name is open";
            } else {
                return "Uhhh something broke";//else if ()
            }
        }catch (MalformedURLException e) {
            e.printStackTrace();
            return "Something went wrong.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Something went wrong";
        }

    }

}
