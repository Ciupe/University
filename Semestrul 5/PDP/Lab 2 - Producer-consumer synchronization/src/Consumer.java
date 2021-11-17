public class Consumer {
    private final Utils memory;

    public Consumer(Utils memory) {
        this.memory = memory;
    }

    private void step(int i) {
        while (true) {
            memory.mutex.lock();
            boolean isProductCalculated = memory.calculatedProduct;
            memory.mutex.unlock();

            if (isProductCalculated) {
                memory.totalSum += memory.productVector[i];

                memory.mutex.lock();
                System.out.println("\tCurrent total: " + memory.totalSum);
                memory.calculatedProduct = false;
                memory.mutex.unlock();

                return;
            }
        }
    }


    public void run() {
        for(int i = 0; i < memory.vector1.length; ++i) {
            step(i);
        }
    }
}