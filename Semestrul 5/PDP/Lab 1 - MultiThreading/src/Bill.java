import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class Bill {
    public List<ItemPair> products;
    public Float totalPrice;

    public Bill() {
        this.products = new ArrayList<>();
        this.totalPrice = 0.0F;
    };

    public void add(Product product, int boughtQuantity, Lock mutex) {
        ItemPair newItem = new ItemPair(product, boughtQuantity);
        this.totalPrice += product.getPrice() * boughtQuantity;

        mutex.lock();
        product.setQuantity(product.getQuantity() - boughtQuantity);
        mutex.unlock();

        products.add(newItem);
    }

    public String toString() {
        StringBuilder soldItems = new StringBuilder();
        for (ItemPair item : products) {
            soldItems.append(item.toString());
        }
        return soldItems + "\nTotal Cost: " + this.totalPrice + "\n";
    }
}
