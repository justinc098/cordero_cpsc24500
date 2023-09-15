import java.text.DecimalFormat;

//Objected Oriented Progamming
//Justin Cordero
//Apartment Painter
//Due 8/15/23
public class ApartmentPainter {
    public static void main(String[] args){
        //Decimal Format
        DecimalFormat decFormat = new DecimalFormat("#.00");

        //Designate the variables
        double length = 32.75;
        double width = 25.62;
        int height = 10;
        double doorSize = 8*3.25;
        double windSize = 14.4*5.5;
        int paintCover = 400;
        int primerCover = 300;
        double paintCost = 34.99;
        double primerCost = 24.49;

        //Calculate the area of paint needed
        double wallOne = length*height;
        double wallTwo = width*height;
        double wallThree = (length*height) - windSize;
        double wallFour = (width*height) - doorSize;
        double ceilingArea = (length*width);
        double wallArea = wallOne + wallTwo + wallThree + wallFour;
        double totalArea = 8*(wallArea + ceiling);

        //Calculate the amount of gallons needed
        double paintGallons = Math.ceil(totalArea/paintCover);
        double primerGallons = Math.ceil(totalArea/primerCover);

        //Calculate Cost
        double totalPaintCost = paintGallons*paintCost;
        double totalPrimerCost = primerGallons*primerCost;
        double totalCost = totalPrimerCost + totalPaintCost;

        //Generate Display
        for (int x = 0; x <= 60; x++){
            System.out.print("*");
        }
        System.out.println();
        System.out.println("Wall area per unit                         " + decFormat.format(wallArea));
        System.out.println("Ceiling area per unit                       " + decFormat.format(ceilingArea));
        System.out.println("Total area to paint and prime             " + totalArea + "\n");
        System.out.println("You must purchase " + paintGallons + " gallons of paint for $" + decFormat.format(totalPaintCost) + ".");
        System.out.println("You must purchase " + totalPrimerCost + " gallons of paint for $" + totalPrimerCost + "." + "\n");
        System.out.println("Your total cost to paint and prime all units is $" + decFormat.format(totalCost) + ".");
        for (int x = 0; x <= 60; x++){
            System.out.print("*");
        }
    }
}