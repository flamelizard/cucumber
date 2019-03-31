package eu.guy.cucumber.atm.transactions;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/*
Very simple file-based queue
Every transaction creates a file. Consuming trans will delete the file.
 */
public class TransactionQueue {
    private static String MESSAGE_DIR = ".\\messages";
    private static String MESSAGE_FMT = "%s\\%03d";
    private static Integer nextTrnId = 1;

    public static void init() throws IOException {
        FileUtils.deleteDirectory(new File(MESSAGE_DIR));
        new File(MESSAGE_DIR).mkdirs();
    }

    public synchronized Integer write(Instruction inst) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(String.format(
                    MESSAGE_FMT, MESSAGE_DIR, nextTrnId), "UTF-8");
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
        writer.println(inst.toTransfer());
        writer.close();
        return nextTrnId++;
    }

    //    thread-safe for TransactionProcessor
    public synchronized Instruction read() {
        Instruction inst = null;
        File[] transactions = new File(MESSAGE_DIR).listFiles();
        if (transactions == null || transactions.length == 0)
            return inst;
        Arrays.sort(transactions, new byTransactionID());

        Integer trnId = Integer.valueOf(transactions[0].getName());
        try (Scanner scanner = new Scanner(transactions[0])) {
            if (scanner.hasNextLine())
                inst = Instruction.parseFrom(scanner.nextLine(), trnId);
        } catch (FileNotFoundException e) {
//            TODO replace all prints with sensible logging
            System.out.println(
                    "Error: Cannot read transaction file: " + transactions[0]);
            return inst;
        }
        transactions[0].delete();
        return inst;
    }

    class byTransactionID implements Comparator<File> {
        @Override
        public int compare(File f1, File f2) {
            return Integer.valueOf(f1.getName()) -
                    Integer.valueOf(f2.getName());
        }
    }
}
