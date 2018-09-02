package eu.guy.cucumber.atm.transactions;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class TransactionQueue {
    private static String MESSAGE_DIR = ".\\messages";
    private static String MESSAGE_FMT = "%s\\%03d";
    private Integer nextId = 1;

    public static void init() throws IOException {
        FileUtils.deleteDirectory(new File(MESSAGE_DIR));
        new File(MESSAGE_DIR).mkdirs();
    }

    public void write(String instruction) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(String.format(
                    MESSAGE_FMT, MESSAGE_DIR, nextId++), "UTF-8");
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        writer.println(instruction);
        writer.close();
    }

    public String read() {
        String message = null;
        File[] transactions = new File(MESSAGE_DIR).listFiles();
        if (transactions == null || transactions.length == 0)
            return message;
        Arrays.sort(transactions, new byTransactionID());

        Scanner scanner = null;
        try {
            scanner = new Scanner(transactions[0]);
        } catch (FileNotFoundException e) {
            System.out.println("Error: Cannot read transaction file: " +
                    transactions[0]);
            return message;
        }
        if (scanner.hasNextLine())
            message = scanner.nextLine();
        scanner.close();
        transactions[0].delete();
        return message;
    }

    class byTransactionID implements Comparator<File> {
        @Override
        public int compare(File f1, File f2) {
            return Integer.valueOf(f1.getName()) -
                    Integer.valueOf(f2.getName());
        }
    }
}
