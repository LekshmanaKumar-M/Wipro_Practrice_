/*
Classes & Objects in Java
Instructions:
  Write a program that takes input from the user and creates an object of a class named 'Person'. 
  The 'Person' class should have two member variables: 'name' and 'age'.
  The program should prompt the user to enter their name and age, create a 'Person' object with the entered values,
  and then display the name and age of the person.

  */

import java.util.Scanner;

public class Person {

    
    String name;
    int age;
    
    
    public Person() {
        
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        
        String inputName = sc.nextLine();

        
        int inputAge = sc.nextInt();

        
        Person p = new Person();

        
        p.name = inputName;
        p.age = inputAge;

       
        System.out.println("Name: " + p.name);
        System.out.println("Age: " + p.age);

        sc.close();
    }
}
