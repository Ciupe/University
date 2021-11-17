public class Producer {
    private final Utils memory;

    public Producer(Utils memory) {
        this.memory = memory;
    }

    private void step(int i) {
        while (true) {
            memory.mutex.lock();
            boolean isCalculatedProduct = memory.calculatedProduct;
            memory.mutex.unlock();

            if (!isCalculatedProduct) {
                memory.productVector[i] = memory.vector1[i] * memory.vector2[i];

                memory.mutex.lock();
                System.out.println("Step " + (i+1) + " product: " + memory.productVector[i]);
                memory.calculatedProduct = true;
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