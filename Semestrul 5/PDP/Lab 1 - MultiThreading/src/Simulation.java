import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Simulation {
    public List<Bill> bills;
    public List<Float> billCosts;

    public List<Product> products;
    public Lock mutex;

    public Integer threadsCount;

    public Simulation() {
        Random rand = new Random();
        this.products = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            String productName = "Product" + i;
            Float productPrice = rand.nextInt(100) / 100.0F;
            Integer productQuantity = rand.nextInt(100);
            this.products.add(new Product(productName, productPrice, productQuantity));
        }

        this.bills = new ArrayList<>();
        this.billCosts = new ArrayList<>();
        this.billCosts.add(0.0F);
        this.mutex = new ReentrantLock();
        this.threadsCount = 100;
    }

    // inventory check -> returns true if quantity is ok and total cost matches the transaction, false otherwise
    public boolean inventoryCheck() {
        HashMap<Product, Integer> productsSold = new HashMap<Product, Integer>();
        for (Product product: this.products) {
            productsSold.put(product, 0);
        }

        Float totalCost = 0.0F;
        for (Bill bill: this.bills) {
            for (ItemPair itemBill: bill.products) {
                productsSold.put(itemBill.getProduct(), productsSold.get(itemBill.getProduct()) + itemBill.getQuantity());
            }
            totalCost += bill.totalPrice;
        }

        for (Map.Entry<Product, Integer> entry : productsSold.entrySet()) {
            Product product = entry.getKey();
            Integer soldQuantity = entry.getValue();
            if (product.getQuantity() + soldQuantity != product.getInitialQuantity()) {
                return false;
            }
        }

        return (totalCost.equals(this.billCosts.get(0)));
    }

    public void run() {
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < this.threadsCount; i++) {
            List<Product> chosenProducts = new ArrayList<>();

            for(Product product: this.products) {
                if(product.getQuantity() > 0) {
                    chosenProducts.add(product);
                }
            }

            Transaction newTransaction = new Transaction(this.bills, chosenProducts, this.mutex, this.billCosts);
            threads.add(new Thread(newTransaction));
        }

        float start = System.nanoTime() / 1000000;

        for(Thread thread: threads) {
            thread.start();
        }

        for (Thread thread: threads) {
            try {
                thread.join();
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }

        float end = System.nanoTime() / 1000000;

        if (inventoryCheck()) {
            System.out.println("Inventory check successful!");
        } else {
            System.out.println("Error! Inventory check unsuccessful...");
        }

        System.out.println("All threads completed in " + String.format("%.3f", (end - start) / 1000) + " seconds");
    }
}
