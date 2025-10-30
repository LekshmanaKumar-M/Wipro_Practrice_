/*Logical Operators in Java
Instructions:

Write a program that takes two boolean values as input and performs logical operations (AND, OR) on them using the logical operators& print the result.
  */



import java.util.Scanner;

public class LogicalOperatorsExample {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean input1 = scanner.nextBoolean();
        boolean input2 = scanner.nextBoolean();
        
        // TODO: Write your code here

        boolean a=input1 && input2;
        boolean b=input1 || input2;

        System.out.println(a);
        System.out.println(b);

        scanner.close();
    }
}
