package delphinadrealms;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import com.google.gson.*;
import org.json.JSONArray;


/**
 * Created by henry27 on 7/18/2017.
 */
public class LolNameChecker {


    public static void main(String[] args) {
        //System.out.println(CheckNameLoL.isNameOpen("lolname CLGFLAME,na"));
        getData();
    }

    public static String getData() {
        String content = null;
        URLConnection connection = null;
        try {
            connection =  new URL("http://lolnamecheck.jj.ai/upcoming/na").openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            content = scanner.next();
        }catch ( Exception ex ) {
            ex.printStackTrace();
        }

        content = content.substring(content.indexOf("globalData.names =",content.indexOf("<div class=fb like")));
        content = content.substring(content.indexOf("globalData.names = "),content.indexOf("];"));
        content = content.substring(content.indexOf("="));
        content = content.replace("=","");
        content = content.substring(1);
        content = content + "]";
        //System.out.println(content);
        formatContent(content);
    return content;
    }

    public static void formatContent(String x) {
        Gson gson = new Gson();
        ArrayList<String> names = new ArrayList<>();
        //System.out.println(x);
        names.add(x);
        String json = gson.toJson(x);

        //System.out.println(json);
        JsonStreamParser parser = new JsonStreamParser(x);
        System.out.println(parser.next());
        while (parser.hasNext()) {
            JsonElement object = parser.next();
            System.out.println(object.getAsJsonArray().size());
            names.add("qwe");
        }


        //for content.
    }
}

