package eu.guy.cucumber.activejdbc101;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

// model ( = table spec) is inferred from DB scheme
// table name is assumed model name in plural, override with @Table
@Table("event")
public class Event extends Model {
    static {
        validatePresenceOf("type", "trnid");
    }
}
