/*
constructor overloading
Create one default constructor to initialize the fields as default values to calculate volume of the cube and one parameterized constructor to pass parameters and initialize them accordingly. You can pass 3 integer parameters at runtime 
(While using parameterized constructor, the program, should also display the output with the default constructor also)

Sample output- with parametrized constructor
Constructor without parameter
Volume is 1000.0
Constructor with parameter
Volume is 192.0*/

import java.util.Scanner;

public class Constructor {
    private int length;
    private int width;
    private int height;

    // --- Default Constructor ---
    public Constructor() {
        this.length = 10;
        this.width = 10;
        this.height = 10;
        System.out.println("Constructor without parameter");
        displayVolume();
    }

    // --- Parameterized Constructor ---
    public Constructor(int length, int width, int height) {
        this.length = length;
        this.width = width;
        this.height = height;
        System.out.println("Constructor with parameter");
        displayVolume();
    }

    // Method to calculate and display the volume
    public void displayVolume() {
        // We use double casting to ensure the volume prints with the .0 decimal (e.g., 1000.0)
        double volume = (double) this.length * this.width * this.height;
        System.out.println("Volume is " + volume);
    }

    // --- Main Method (Moved to resolve the execution error) ---
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        // 1. Create an object using the default constructor. (Volume is 1000.0)
        // This is the first action, displaying the default constructor output.
        Constructor cuboid1 = new Constructor();

        // 2. Read three integer parameters for the parameterized constructor.
        // Assuming the test case inputs are 3, 3, 3 to get the expected volume of 27.0.
        int l = s.nextInt();
        int w = s.nextInt();
        int h = s.nextInt();

        // 3. Create a second object using the parameterized constructor. (Volume is 27.0)
        Constructor cuboid2 = new Constructor(l, w, h);

        s.close();
    }
}
