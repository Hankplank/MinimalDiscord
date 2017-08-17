package delphinadrealms.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;



/**
 * Created by henry27 on 7/14/2017.
 */
public class urbanDict {

    public String findWord(String query) {
        try {
            query = query.replace(" ", "+");
            URL url = new URL("http://api.urbandictionary.com/v0/define?term=" + query);
            InputStream in = url.openStream();
            Scanner scanner = new Scanner(in);
            String jsonstring = "";
            while (scanner.hasNext()) {
                jsonstring += scanner.next() + " ";
            }
            scanner.close();
            Gson gson = new GsonBuilder().create();
            JsonObject json = gson.fromJson(jsonstring, JsonElement.class).getAsJsonObject();
            if (json.get("result_type").getAsString().equalsIgnoreCase("no_results")) {
                return "No Results";
            }
            JsonObject result = json.get("list").getAsJsonArray().get(0).getAsJsonObject();
            String word = result.get("word").getAsString();
            String permalink = result.get("permalink").getAsString();
            String definition = result.get("definition").getAsString();
            return String.format("%s: %s (%s)",word, definition, permalink);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
