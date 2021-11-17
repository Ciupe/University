import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;

public class Transaction implements Runnable{
    public List<Bill> listOfBills;
    public List<Float> billCosts;

    public List<Product> products;
    public Lock mutex;


    public Transaction(List<Bill> listOfBills, List<Product> products, Lock mutex, List<Float> billCosts) {
        this.listOfBills = listOfBills;
        this.products = products;
        this.mutex = mutex;
        this.billCosts = billCosts;
    }

    @Override
    public void run() {
        Random rand = new Random(System.currentTimeMillis());
        Bill bill = new Bill();

        for(Product product: this.products) {
            Integer randomQuantity = rand.nextInt(100) + 1;
            bill.add(product, randomQuantity, mutex);
        }

        mutex.lock();
        this.billCosts.set(0, this.billCosts.get(0) + bill.totalPrice);
        mutex.unlock();
        this.listOfBills.add(bill);
    }
}
