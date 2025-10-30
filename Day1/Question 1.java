
/*Arithmetic Operators in Java
Instructions:

Write a program that takes two numbers as input from the user and performs arithmetic operations on them using the arithmetic operators (sum, difference, product, quotient, remainder) in Java.
*/

import java.util.Scanner;

public class ArithmeticOperations {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // TODO: Write your code here
        int num1=scanner.nextInt();
        int num2=scanner.nextInt();

        int sum=num1+num2;
        int diff=num1-num2;
        int product=num1*num2;
        System.out.println("Sum: "+sum);
        System.out.println("Difference: "+diff);
        System.out.println("Product: "+product);

        if(num2!=0){
            int quotient=num1/num2;
            int rem=num1%num2;
            System.out.println("Quotient: "+quotient);
            System.out.println("Remainder: "+rem);
        }
        
        scanner.close();
    }
}
