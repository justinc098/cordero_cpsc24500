import java.text.DecimalFormat;
import java.util.Random;
import java.util.Scanner;

//Objected Oriented Programming
//Justin Cordero
//Payday
//Due 9/22/23
public class Payday {
    public static void main(String[] args){
        //Decimal Format
        DecimalFormat decFormat = new DecimalFormat("#.00");
        
        //Create Variables
        String name;
        double hours;
        double rate;
        String union;
        double dues = 0.0;
        double save;
        double gross;
        double gross2;
        double net;
        double incident;
        double medical;
        double taxes;
        double afterTax;

        //Activate Scanner
        Scanner scan = new Scanner(System.in);

        //Create Header Box
        for (int x = 0; x <= 60; x++){
            System.out.print("*");
        }
        System.out.println();
        System.out.println("                        Payday V1.0");
        for (int x = 0; x <= 60; x++){
            System.out.print("*");
        }
        System.out.println();

        //Gather Information
        System.out.println("Enter Name: ");
        name = scan.nextLine();
        System.out.println("Enter Hours Worked: ");
        hours = scan.nextDouble();
        System.out.println("Enter Hourly Pay Rate: ");
        rate = scan.nextDouble();
        System.out.println("Are You a Union Member (y or n)? ");
        union = scan.next();
        System.out.println("What Percentage do you want to withhold for your medical savings account? ");
        save = scan.nextDouble();

        //Utilize Information
        gross = hours*rate;
        medical = (save/100)*gross;
        if(union.equalsIgnoreCase("y")){
            dues = gross*0.05;
        }
        incident = Math.random() * 200;
        gross2 = gross - medical - dues - incident;

        //Determine Taxes
        if (gross2 >= 2500){
            taxes = 0.25;
        } else if (gross2 >= 1500){
            taxes = 0.15;
        } else if (gross2 >= 500){
            taxes = 0.1;
        } else {
            taxes = 0.05;
        }
        afterTax = gross2*taxes;

        //Calculate Net
        net = gross - medical - dues - incident - afterTax;

        //Display Payday
        System.out.println("---------PAYCHECK---------");
        System.out.printf("Gross Pay $ %.2f%n", gross);
        System.out.printf("Union Dues $ %.2f%n", dues);
        System.out.printf("Med Deduction $ %.2f%n", medical);
        System.out.printf("Incident Pay $ -%.2f%n", incident);
        System.out.printf("Taxes $ %.2f%n", afterTax);
        System.out.printf("Net Pay $ %.2f%n", net);
        System.out.println("--------------------------");
        System.out.println("Prepared for " + name);
        System.out.println();
        System.out.println("Thank You for using this program.");
    }
}
