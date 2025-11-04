/*
Thread in java
You are required to build a calculator using concept of multithreading which performs the following arithematic operations:

Sum, difference, product , ratio, power.

Input Format  - two line separated integers

Output Format  

See in sample output

Input 

6

8

Output

ARITHEMATIC OPERATIONS

SUM 14

DIFFERENCE -2

PRODUCT 48

RATIO 0

POWER 1679616.0

END OF A
*/


import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int x = s.nextInt();
        int y = s.nextInt();
        A1 a = new A1(x, y);
        
        Thread t1 = new Thread(a);
        t1.start();
    }
}

// complete the class below and create the thread to achieve desired output
class A1 implements Runnable {
    int i, j;

    A1(int x, int y) {
        i = x;
        j = y;
    }

    public void run() {
        System.out.println("ARITHEMATIC OPERATIONS"); 
        System.out.println("SUM " + (i + j));
        System.out.println("DIFFERENCE " + (i - j));
        System.out.println(" PRODUCT  " + (i * j));  
        System.out.println("RATIO  " + (i / j));     
        System.out.println("POWER  " + Math.pow(i, j)); 
        System.out.println("END OF A");
    }
}
