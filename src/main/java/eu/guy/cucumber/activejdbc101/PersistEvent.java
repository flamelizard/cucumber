package eu.guy.cucumber.activejdbc101;

import org.javalite.activejdbc.Base;

import java.util.Random;

/*
ActiveJDBC
https://www.baeldung.com/active-jdbc

-- Tricky handling of primary key column with name "id"
Common practice is to have id auto increment. Based on this
assumption, activeJDBC will generate UPDATE when you supply
value for key "id".
This can be overridden by .setId() and explicit .insert()
 */
public class PersistEvent {
    public static void main(String[] args) {
        Base.open("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/events",
                "root", "password");

//        run maven goal "activejdbc-instrumentation:instrument to create/update model
        Event event = new Event();
        event.set("trnId", new Random().nextInt(100), "type", "transaction");
        event.saveIt();

        Event transRow = Event.findFirst("type = ?", "transaction");
        System.out.println("[first transaction " + transRow);

        Event.findAll().dump();
        Base.close();
    }
}
