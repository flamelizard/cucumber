package eu.guy.cucumber.atm.transactions.events;

import eu.guy.cucumber.atm.AppSetup;
import eu.guy.cucumber.atm.domain.BusinessException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static eu.guy.cucumber.atm.utils.Utils.sleep;

/*
Event handling package is a support for E2E testing of async system.

The test waits for a specific event to occur and then checks the assumption.
Obviously, event-driven testing needs to have a support built-in the program.
 */
public class EventLogger {
    private static String LOGFILE = AppSetup.getProjectProperty("event.log.file");
    private static Logger log = LogManager.getLogger(EventLogger.class);

    //    log to an event log file
    public static void logEvent(Event event) throws
            BusinessException {
        if (!log.isInfoEnabled())
            throw new BusinessException("Cannot log transactional event");
        log.info(event);
    }

    private static Map<String, String> getParsedEvent(String event) {
        Pattern field = Pattern.compile("(\\w+)=<([^>]+?)>",
                Pattern.CASE_INSENSITIVE);
        Map<String, String> fields = new HashMap<>();
        Matcher match = field.matcher(event);
        while (match.find()) {
            fields.put(match.group(1), match.group(2));
        }
        return fields;
    }

    private static List<String> searchForEvent(String wildcard) {
        List<String> events = new ArrayList<>();
        try (Scanner reader = new Scanner(new File(LOGFILE))) {
            String line;
            while (reader.hasNextLine()) {
                line = reader.nextLine();
//                TODO log.debug here will overload log file, why?
//                System.out.println("[search " + wildcard + ",," + line);
                if (line.contains(wildcard))
                    events.add(line);
            }
        } catch (FileNotFoundException ex) {
            log.error(ex.getMessage());
        }
        return events;
    }

    /**
     * Regularly search an event log for a transaction to occur.
     * This is must to sync test cases that verify transaction status in async
     * system.
     *
     * @param trnId transaction id from transaction queue
     * @return event key-value pairs
     */
    public static Map<String, String> waitForEvent(
            Integer trnId, Integer timeout) {
        List<String> events;
        String wildcard = String.format("id=<%d>", trnId);
        while (timeout > 0) {
            events = searchForEvent(wildcard);
            if (events.size() > 0)
                return getParsedEvent(events.get(0));
            timeout = timeout - 1;
            sleep(1);
        }
        log.warn(String.format("Event did not occur: trnId=<%d>", trnId));
        return null;

    }
}
