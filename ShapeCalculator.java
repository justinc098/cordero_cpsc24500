//Justin Cordero
//Object-Oriented Programming
//Shape Calculator
//Due: 10/2/23
import java.util.Scanner;
public class ShapeCalculator {
    //Create user input settings and heading
    public static void main(String[] args) {
        //Heading
        for (int x = 0; x <= 60; x++){
            System.out.print("*");
        }
        System.out.println();
        for (int x = 0; x <= 22; x++){
            System.out.print(" ");
        }
        System.out.println("Shape Calculator");
        for (int x = 0; x <= 60; x++){
            System.out.print("*");
        }
        System.out.println();

        //Set shape determination
        System.out.print("Enter C for circle, R for rectangle, or T for triangle: ");
        Scanner input = new Scanner(System.in);
        String shape = input.nextLine();

        //Set responses
        while (true) {
            if (shape.equalsIgnoreCase("C")) {
                circleDims(input);
            } else if (shape.equalsIgnoreCase("R")) {
                rectangleDims(input);
            } else if (shape.equalsIgnoreCase("T")) {
                triDims(input);
            } else if (shape.equalsIgnoreCase("Q")) {
                break;
            } else {
                System.out.print("Shape not Recognized.");
            }
        }
        //Closing Message
        System.out.print("Thank You for Using Shape Calculator!!!");
        input.close();
    }
        // Set up the circle calculator
        public static void circleDims (Scanner input){
            System.out.print("Enter the Radius: ");
            double rad = input.nextDouble();
            double area = Math.PI * (rad * rad);
            double circ = 2 * rad * Math.PI;

            //Display Measurements
            System.out.println("The area of the circle is " + area + ", and the circumference is " + circ + ".");
        }
        // Set up the rectangle calculator
        public static void rectangleDims (Scanner input){
            System.out.print("Enter the length and width: ");
            String sides = input.nextLine();
            String[] lw = sides.split(" ");

            //Calculate rectangle
            double height = Double.parseDouble(lw[0]);
            double width = Double.parseDouble(lw[1]);
            double area = height * width;
            double peri = (height * 2) + (width * 2);

            //Display Measurements
            System.out.println("The area of the rectangle is " + area + ", and the perimeter is " + peri + ".");
        }
        //Set up the triangle calculator
        public static void triDims (Scanner input){
            System.out.print("Enter the lengths of the three sides: ");
            String threeSides = input.nextLine();
            String[] sides = threeSides.split(" ");

            //Calculate Triangle (Finally)
            double side1 = Double.parseDouble(sides[0]);
            double side2 = Double.parseDouble(sides[1]);
            double side3 = Double.parseDouble(sides[2]);

            double semi = (side1 + side2 + side3) / 2;
            double area = Math.sqrt(semi * (semi - side1) * (semi - side2) * (semi - side3));
            double peri = side1 + side2 + side3;

            //Display measurements
            System.out.println("The area of the triangle is " + area + ", and the perimeter is " + peri + ".");
        }

}