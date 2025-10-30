/*
Generic Data Analysis using JAVA
Description:
You are working on a data analysis module that needs to process different types of data. Implement a class named DataAnalyzer with the following requirements:


Constructor:

The class should have a constructor that takes an array of type T as its parameter and initializes an internal data structure to store the provided array.

Calculate Average Method:

Implement a method named calculateAverage that calculates and returns the average of the elements in the array.
Ensure that the method works for numeric types (e.g., Integer, Double) and does not require a separate implementation for each type.
Find Maximum Method:

Implement a method named findMaximum that finds and returns the maximum element in the array.
Ensure that the method works for comparable types (e.g., Integer, String) and does not require a separate implementation for each type.
Data Summary Method: (Pre-Implemented as a Boiler Plate Code)

Implement a method named dataSummary that prints a summary of the data, including the average and maximum values.
  */

import java.util.*;

public class DataAnalyzer<T extends Number & Comparable<T>> {

    private T[] data;

    // Constructor
    public DataAnalyzer(T[] data) {
        this.data = data;
    }

    // Method to calculate average (works for numeric types)
    public double calculateAverage() {
        double sum = 0.0;
        for (T value : data) {
            sum += value.doubleValue();  // convert to double for all numeric types
        }
        return sum / data.length;
    }

    // Method to find maximum (works for comparable types)
    public T findMaximum() {
        T max = data[0];
        for (T value : data) {
            if (value.compareTo(max) > 0) {
                max = value;
            }
        }
        return max;
    }

    // Method to print summary
    public void dataSummary() {
        System.out.println("Data Summary:");
        System.out.println("Average: " + String.format("%.1f", calculateAverage()));
        System.out.println("Maximum: " + findMaximum());
    }

    // Main method for testing
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int ch = sc.nextInt();   // choice: 1=Integer, 2=Double, 3=Short, 4=Float
        int size = sc.nextInt(); // array size

        if (ch == 1) {
            Integer[] intData = new Integer[size];
            for (int i = 0; i < size; i++) {
                intData[i] = sc.nextInt();
            }
            DataAnalyzer<Integer> analyzer = new DataAnalyzer<>(intData);
            analyzer.dataSummary();

        } else if (ch == 2) {
            Double[] doubleData = new Double[size];
            for (int i = 0; i < size; i++) {
                doubleData[i] = sc.nextDouble();
            }
            DataAnalyzer<Double> analyzer = new DataAnalyzer<>(doubleData);
            analyzer.dataSummary();

        } else if (ch == 3) {
            Short[] shortData = new Short[size];
            for (int i = 0; i < size; i++) {
                shortData[i] = sc.nextShort();
            }
            DataAnalyzer<Short> analyzer = new DataAnalyzer<>(shortData);
            analyzer.dataSummary();

        } else if (ch == 4) {
            Float[] floatData = new Float[size];
            for (int i = 0; i < size; i++) {
                floatData[i] = sc.nextFloat();
            }
            DataAnalyzer<Float> analyzer = new DataAnalyzer<>(floatData);
            analyzer.dataSummary();
        }
    }
}
