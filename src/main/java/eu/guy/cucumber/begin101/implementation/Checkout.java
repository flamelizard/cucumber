package eu.guy.cucumber.begin101.implementation;

/**
 * Created by Tom on 11/17/2017.
 */
public class Checkout {
    private int total = 0;

    public void add(int count, int price) {
        this.total += count * price;
    }

    public int total() {
        return total;
    }
}
