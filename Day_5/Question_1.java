/*
Exception hanlding in java
Write a Java program that takes two integers as input and performs division. The program should handle exceptions for invalid input (non-integer input) and division by zero. The program should continue to prompt the user for valid input until successful division is performed.



Requirements:

Handle InputMismatchException if the user inputs non-integer values.
Handle ArithmeticException if the user attempts to divide by zero.
Use a loop to ensure the program continues to prompt the user until valid input is received and the division is successfully performed.
  */

import java.util.InputMismatchException;
import java.util.Scanner;

public class DivisionProgram {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean success = false;

        while (!success) {
            try {
               
                int num1 = sc.nextInt();

                int num2;
                while (true) {
                    
                    num2 = sc.nextInt();
                    if (num2 == 0) {
                        System.out.println("Cannot divide by zero. Please enter a valid divisor.");
                    } else {
                        break;
                    }
                }

                int result = num1 / num2;
                System.out.println("Result: " + result);
                success = true;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                sc.nextLine(); // clear invalid input
            }
        }

        sc.close();
    }
}
