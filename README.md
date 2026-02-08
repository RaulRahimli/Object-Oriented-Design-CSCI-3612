Assignment 1 — Random Number Generator Statistics

Name: Raul Rahimli (19808)
Course: Object Oriented Analysis & Design (CRN 20966)
Project: Assignment 1
Date: 09.02.2026


This program generates random double values in the range of [0, 1) using three Java random number generators, then calculates statistics such as:

1. n - sample size
2. mean
3. sample standard deviation
4. minimum
5. maximum

It prints results for 3 n user values * 3 generators = 9 total outputs.


The program uses these approaches:

1. Math.random()
2. java.util.Random
3. java.util.concurrent.ThreadLocalRandom



As 'n' becomes larger, the values approach to:

1. min -> 0
2. max -> 1
3. mean -> 0.5
4. std dev -> 0.29


The program is in a single class 'Generator'.

ArrayList<Double> populate (int n, int randNumGen)
Generates n random values using one of the generators:
1. if randNumGen is 0 -> Math.random()
2. if randNumGen is 1 -> new Random().nextDouble()
3. if randNumGen is 2 -> ThreadLocalRandom.current().nextDouble(0.0, 1.0)


ArrayList<Double> statistics (ArrayList<Double> arr)
Computes and returns results in the order of "[n, mean, stddev, min, max]"


void display (ArrayList<Double> results)
Prints in the format:
n    mean    stddev    min    max


void execute ()
1. Takes three user inputs of n
2. Runs all three generators for each n
3. Prints 9 total result lines (3 per generator)


private void run (int n, int randNumGen)
A separate private method to be accessible only from this class to run the program


private Name (int randNumGen)
Generates in the output the approach it used to calculate for each table
