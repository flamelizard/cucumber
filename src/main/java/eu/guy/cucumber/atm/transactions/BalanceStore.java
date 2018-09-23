package eu.guy.cucumber.atm.transactions;

import eu.guy.cucumber.atm.domain.Money;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


/**
 * The most simple account balance book.
 * Balance hold as a single string in a text file
 */
public class BalanceStore {
    private static String BALANCE_FILE = "./balance";
    private static Money initialBalance = new Money(0, 0);

    public static void init() throws FileNotFoundException {
        FileUtils.deleteQuietly(new File(BALANCE_FILE));
        setBalance(initialBalance);
    }

    public synchronized static Money getBalance() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(BALANCE_FILE));
        if (scanner.hasNextLine())
            return Money.convert(scanner.nextLine());
        return new Money(0, 0);
    }

    public synchronized static void setBalance(Money money) throws
            FileNotFoundException {
        PrintWriter writer = new PrintWriter(BALANCE_FILE);
        writer.println(money.getAmount().toString());
        writer.close();
    }
}
