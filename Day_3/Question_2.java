/*
Sum of All Numbers
Find the sum of the digits using recursion
  */
import java.util.*;

public class Main {

    static int sum(int n) {
       
        if (n == 0) {
            return 0;
        }
        
        return (n % 10) + sum(n / 10);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        int res = sum(num);
        System.out.println(res);
        sc.close();
    }
}
