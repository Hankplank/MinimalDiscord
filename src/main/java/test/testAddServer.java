package test;

import delphinadrealms.handlers.SQLManager;

import java.util.HashMap;


/**
 * Created by henry27 on 8/15/2017.
 */
public class testAddServer {

    public static void testAddServer() {
        SQLManager sql = new SQLManager();
        sql.connect();
        sql.addServer(334189774741045249L,334190910852169729L,true,true,true,true);
    }

    public static void main(String[] args) {
        HashMap<String,Integer> taco = new HashMap<>();
        taco.put("taco",5);
        System.out.println(taco.get("taco"));
    }

}
