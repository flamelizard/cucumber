package eu.guy.cucumber.atm.transactions;

public enum Status {
    Waiting(0), Completed(1), Fail(2), Cancel(3);
    int id;

    Status(int id) {
        this.id = id;
    }

    int getId() {
        return id;
    }
}