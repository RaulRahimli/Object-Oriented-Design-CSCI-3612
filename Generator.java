/* 
    Name: Raul Rahimli 19808
    Project name: Assignment 1
    Class: Object Oriented Analysis & Design CRN 20966
    Date: 09.02.2026
*/

import java.util.ArrayList; // ArrayList for storing values and results
import java.util.Random; // java.util.Random generator
import java.util.concurrent.ThreadLocalRandom; // ThreadLocal Random generator
import java.util.Scanner; // for user input

/*
    Generator Class:
    Generates random number using different Java random number generators
    and caluclates: n, mean, standard deviation, min, max.

    Rules for increasing of n:
    min approaches to 0
    max approaches to 1
    mean approaches to 0.5
    std dev approaches to 0.29
*/


public class Generator{
    //  Scanner for the user input
    private Scanner scanner;

    // Constructor
    public Generator(){
        scanner = new Scanner(System.in);
    }

    /*
        Creates and returns and ArrayList of n random numbers using
        one of the random number generators.
        If randNumGen is 0 use Math.random()
        If randNumGen is 1 use java.util.Random
        If randNumGen is 2 use java.util.concurrent.ThreadLocalRandom
    */

    public ArrayList <Double> populate (int n, int randNumGen){
        ArrayList <Double> nums = new ArrayList <Double> ();

        Random random = null;
        if (randNumGen == 1){
            random = new Random();
        }

        // for loop - generate n random values and add each value to the ArrayList
        for (int i = 0; i < n; i++){
            double ns;
            
            // if-else - checks the value of randNumGen and chooses the approach of random number generator
            if (randNumGen == 0){
                ns = Math.random();
            }else if (randNumGen == 1){
                ns = random.nextDouble();
            }else{
                ns = ThreadLocalRandom.current().nextDouble();
            }

            nums.add (ns);
        }
        return nums;
    }

    /*
        Calculates the number of elements (n), mean, standard deviation, min, and max
        and returns the results in the order [n, mean, stddev, min, max]
    */

    public ArrayList <Double> statistics (ArrayList <Double> arr){
        ArrayList <Double> res = new ArrayList <Double> ();

        int n = arr.size();

        double sum = 0;
        double min = arr.get(0);
        double max = arr.get(0);

        // for-each loop - for each value x in ArrayList arr, add x to sum and update min/max if needed
        for (double x: arr){
            sum += x;
            
            if (x< min) min = x;
            
            if (x > max) max = x;
        }

        double mean = sum / n;

        double sum_pow = 0;

        // for-each loop - for each vlaue x in ArrayList arr, subtract the mean from x, square it, and add it to sum_pow
        for (double x:arr){
            sum_pow += Math.pow(x-mean, 2);
        }

        double stddev = Math.sqrt(sum_pow / (n-1));

        res.add((double) n); // n
        res.add(mean); // mean
        res.add(stddev); // standard deviation
        res.add(min); // min
        res.add(max); // max

        return res;
    }


    /*
        Display the results in a tabular format in the system console.
        Format is "n mean stddev min max"
    */

    public void display (ArrayList <Double> ress){
        System.out.println(
            ress.get(0).intValue() + 
            "\t" +
            ress.get(1) +
            "\t" + 
            ress.get(2) + 
            "\t" +
            ress.get(3) +
            "\t" +
            ress.get(4)
        );
    }


    /*
        Executes everything. Takes 3 values of n, then each table is calculated by the specific random number generator
    */

    public void execute (){
        System.out.println("");
        System.out.print("Enter first n value: ");
        int n1 = scanner.nextInt();

        System.out.print("Enter second n value: ");
        int n2 = scanner.nextInt();

        System.out.print("Enter third n value: ");
        int n3 = scanner.nextInt();

        //3 generators (0,1,2) and 3 n values (n1,n2,n3).  So, 3*3=9, as required in the assignment
        System.out.println("");
        for (int randNumGen = 0; randNumGen < 3; randNumGen++){
            System.out.println(Name(randNumGen));
            run(n1, randNumGen);
            run(n2, randNumGen);
            run(n3, randNumGen);
            System.out.println("");
        }

        scanner.close();

    }


    //helper method - runs populate + statistics + display
    // Accessibility is private - only inside of this class
    private void run (int n, int randNumGen){
        ArrayList <Double> pop = populate (n, randNumGen);
        ArrayList <Double> stat = statistics(pop);
        display(stat);
    }


    // helper method - returns a name for the generator calculated
    private String Name (int randNumGen){
        if (randNumGen == 0) return "Math.random():";
        if (randNumGen == 1) return "java.util.Random:";
        return "java.util.concurrent.ThreadLocalRandom:";
    }

    public static void main(String[] args) {
        Generator g = new Generator();
        g.execute();
    }
}