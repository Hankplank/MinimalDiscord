package test;

import delphinadrealms.commands.SQLManager;

/**
 * Created by henry27 on 8/15/2017.
 */
public class testAddServer {

    public static void testAddServer() {
        SQLManager sql = new SQLManager();
        sql.connect();
        sql.addServer(334189774741045249L,334190910852169729L,true,true,true,true);
    }
}
