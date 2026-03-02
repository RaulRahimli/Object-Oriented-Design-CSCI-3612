public class Main {
    public static void main(String[] args) {
        RingBuffer rb = new RingBuffer(4);

        rb.regR("R1");
        rb.regR("R2");

        rb.write(10);
        rb.write(20);
        rb.write(30);

        System.out.println("Initial state:");
        rb.printState();

        System.out.println("R1 reads: " + rb.read("R1"));
        System.out.println("R2 reads: " + rb.read("R2"));

        System.out.println("\nSame first item:");
        rb.printState();

        System.out.println("R1 reads: " + rb.read("R1"));
        System.out.println("R1 reads: " + rb.read("R1"));

        rb.write(40);
        rb.write(50);
        rb.write(60);
        rb.write(70);
        rb.write(80);

        System.out.println("\nOverflow:");
        rb.printState();

        System.out.println("R2 reads: " + rb.read("R2"));
        System.out.println("R2 reads: " + rb.read("R2"));

        System.out.println("\nSlow reader catches up:");
        rb.printState();

        Thread anotherWriter = new Thread(() -> {
            try {
                rb.write(999);
                System.out.println("Second writer succeeded (Error).");
            } catch (IllegalStateException e) {
                System.out.println("Second writer blocked: " + e.getMessage());
            }
        });

        anotherWriter.start();

        try {
            anotherWriter.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\nFinal state:");
        rb.printState();
    }
}