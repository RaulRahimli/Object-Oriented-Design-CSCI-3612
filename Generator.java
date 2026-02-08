/*
    Name: Raul Rahimli 19808
    Project name: Assignment 1
    Class: Object Oriented Analysis & Design CRN 20966
    Date: 09.02.2026
*/

import java.util.ArrayList;                      // ArrayList for storing values and results
import java.util.Random;                        // java.util.Random generator
import java.util.concurrent.ThreadLocalRandom; // ThreadLocalRandom generator
import java.util.Scanner;                     // for user input

/*
    Generator Class:
    Generates random numbers using different Java random number generators
    and calculates : n, mean, standard deviation, min, max.

    Rules for increasing of n:
    min approaches 0
    max approaches 1
    mean approaches 0.5
    sample std dev approaches 0.29)
*/

public class Generator {
    // Scanner for the user input
    private Scanner scanner;

    // Constructor
    public Generator() {
        scanner = new Scanner(System.in);
    }

    /*
        Creates and returns an ArrayList of n random numbers using one of the random number generators.
        if randNumGen is 0 use Math.random(), if it is 1 use java.util.Random, if it is 2 use ThreadLocalRandom
    */
    public ArrayList<Double> populate(int n, int randNumGen) {
        ArrayList<Double> values = new ArrayList<Double>();

        Random random = null;
        if (randNumGen == 1) {
            random = new Random();
        }

        // for loop - generate n random values and add each value to the ArrayList
        for (int i = 0; i < n; i++) {
            double v;

            // if-else - checks the value of randNumGen and chooses the approach of random number generator
            if (randNumGen == 0) {
                v = Math.random();
            } else if (randNumGen == 1) {
                v = random.nextDouble();
            } else {
                v = ThreadLocalRandom.current().nextDouble(0.0, 1.0);
            }

            values.add(v);
        }

        return values;
    }

    /*
        Calculates the number of elements (n), mean, sample standard deviation, minimum, and maximum,
        and returns the results in the order [n, mean, stddev, min, max]
    */
    public ArrayList<Double> statistics(ArrayList<Double> arr) {
        ArrayList<Double> result = new ArrayList<Double>();

        int n = arr.size();

        double sum = 0.0;
        double min = arr.get(0);
        double max = arr.get(0);

        // for-each loop - for each value x in ArrayList arr, add x to sum and update min/max if needed
        for (double x : arr) {
            sum += x;
            if (x < min) min = x;
            if (x > max) max = x;
        }

        double mean = sum / n;

        double sum_pow = 0.0;
        // for-each loop - for each value x in ArrayList arr, subtract the mean from x, square it, and add it to sum_pow
        for (double x : arr) {
            sum_pow += Math.pow(x - mean, 2);
        }

        // if n is 0 or 1, sample standard deviation cannot be calculated, so we set it to 0.0
        double stddev = (n <= 1) ? 0.0 : Math.sqrt(sum_pow / (n - 1));


        result.add((double) n);     // n
        result.add(mean);          // mean
        result.add(stddev);       // stddev
        result.add(min);         // min
        result.add(max);        // max

        return result;
    }

    /*
        Displays the results in a tabular format in the system console.
        format is "n mean stddev min max"
    */
    public void display(ArrayList<Double> results) {
    System.out.println(
            results.get(0).intValue() + "\t" +
            results.get(1) + "\t" +
            results.get(2) + "\t" +
            results.get(3) + "\t" +
            results.get(4)
    );
}

    /*
        Executes everything. Takes 3 values of n, then each table is calculated by the specific random number generator
     */
    public void execute() {
        System.out.println("");
        System.out.print("Enter first n value: ");
        int n1 = scanner.nextInt();

        System.out.print("Enter second n value: ");
        int n2 = scanner.nextInt();

        System.out.print("Enter third n value: ");
        int n3 = scanner.nextInt();

        // 3 generators (0, 1, 2) and 3 n values (n1, n2, n3). So, 3*3=9, as required in the assignment
        System.out.println("");
        for (int randNumGen = 0; randNumGen < 3; randNumGen++) {
            System.out.println(Name(randNumGen));
            run(n1, randNumGen);
            run(n2, randNumGen);
            run(n3, randNumGen);
            System.out.println("");
        }

        scanner.close();
    }

    // helper method - runs populate + statistics + display
    // Accessibility is private - only inside of this class
    private void run(int n, int randNumGen) {
        ArrayList<Double> values = populate(n, randNumGen);
        ArrayList<Double> stats = statistics(values);
        display(stats);
    }

    // helper method - returns a name for the generator calculated
    private String Name(int randNumGen) {
        if (randNumGen == 0) return "Math.random()";
        if (randNumGen == 1) return "java.util.Random";
        return "java.util.concurrent.ThreadLocalRandom";
    }

    public static void main(String[] args) {
        Generator g = new Generator();
        g.execute();
    }
}
