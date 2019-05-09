package eu.guy.cucumber.atm.server;

import eu.guy.cucumber.atm.domain.Account;
import eu.guy.cucumber.atm.transactions.Transaction;
import org.javalite.activejdbc.Base;

// TODO class should be an object
public class DataStore {
    public static void createConnection() {
        if (Base.hasConnection()) return;
        String db = "bank";
        Base.open("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/" + db,
                "teller", "password");
    }

    public static void deleteAccounts() {
        System.out.println("Delete accounts ...");
        Account.deleteAll();
    }

    public static void deleteTransactions() {
        System.out.println("Delete transactions...");
        Transaction.deleteAll();
    }

    public static void closeConnection() {
        Base.close();
    }
}
