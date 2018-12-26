package eu.guy.cucumber.atm.server;

import org.javalite.activejdbc.Base;

public class DataStore {
    public static void createConnection() {
        if (Base.hasConnection()) return;
        String db = "bank";
        Base.open("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/" + db,
                "teller", "password");
    }
}
