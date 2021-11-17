public class ItemPair {

    private final Product product;
    private final int  quantity;

    public ItemPair(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct(){
        return this.product;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public String toString() {
        return this.product + " x " + this.quantity + "\n";
    }
}
