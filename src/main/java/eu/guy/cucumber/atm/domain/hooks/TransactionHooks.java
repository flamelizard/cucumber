package eu.guy.cucumber.atm.domain.hooks;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import eu.guy.cucumber.atm.transactions.TransactionProcessor;
import eu.guy.cucumber.atm.transactions.TransactionQueue;

import java.io.IOException;

public class TransactionHooks {
    private Thread processor;

    //    before each scenario
    @Before
    public void cleanUp() throws IOException {
        System.out.println("Transaction hooks ... ");
        TransactionQueue.init();
    }

    @Before
    public void startProcessor() {
        processor = TransactionProcessor.getProcessQueueThreaded();
        processor.start();
    }

    @After
    public void stopProcessor() {
//        System.out.println("Stop processor ... ");
        processor.interrupt();
    }

}
