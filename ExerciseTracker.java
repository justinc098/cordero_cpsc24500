import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Justin Cordero
 * Object-Oriented Programming
 * Exercise Tracker
 * Due: 11/15/23
 */

abstract class Exercise implements Comparable<Exercise>{
    private String name;
    private String comment;
    private Date date;
    private int duration;

    public Exercise(String name, String comment, String dateString, int duration){
        this.name=name;
        this.comment=comment;
        setDateFromString(dateString);
        this.duration=duration;
    }

    public abstract String getType();

    public abstract double caloriesBurned();

    @Override
    public int compareTo(Exercise other){
        return this.date.compareTo(other.date);
    }

    public void setDateFromString(String dateString){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
            this.date=dateFormat.parse(dateString);
        } catch (ParseException e){
            e.printStackTrace();
        }
    }

    public String getDateAsString(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
        return dateFormat.format(date);
    }

    @Override
    public String toString(){
        return getType() + name + getDateAsString() + caloriesBurned();
    }

}

class RunWalk extends Exercise{
    private double distance;

    public RunWalk(String name, String comment, String date, int duration, double distance){
        super(name, comment, date, duration);
        this.distance=distance;
    }

    @Override
    public String getType(){
        return "Run/Walk";
    }

    @Override
    public double caloriesBurned(){
        return distance/duration*9000;
    }
}

class WeightLifting extends Exercise{
    private int weightLifted;

    public WeightLifting(String name, String comment, String date, int duration, int weightLifted){
        super(name, comment, date, duration);
        this.weightLifted=weightLifted;
    }

    @Override
    public String getType(){
        return "WeightLifting";
    }

    @Override
    public double caloriesBurned(){
        return (double) weightLifted/duration*50;
    }
}

class RockClimbing extends Exercise{
    private int wallHeight;
    private int repetitions;

    public RockClimbing(String name, String comment, String date, int duration, int wallHeight, int repetitions){
        super(name, comment, date, duration);
        this.wallHeight=wallHeight;
        this.repetitions=repetitions;
    }

    @Override
    public String getType(){
        return "Rock Climbing";
    }

    @Override
    public double caloriesBurned(){
        return wallHeight*repetitions/duration*100;
    }
}

class CompareByCalories implements Comparator<Exercise>{
    @Override
    public int compare(Exercise e1, Exercise e2){
        return Double.compare(e1.caloriesBurned(), e2.caloriesBurned());
    }
}

class ExerciseWriter{
    public static void writeToFile(List<Exercise> exercises, String fileName){
        try (FileWriter writer = new FileWriter(new File(fileName))){
            for (Exercise exercise : exercises){
                writer.write(exercise.toString());
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void printToScreen(List<Exercise> exercises){
        for (Exercise exercise : exercises){
            System.out.println(exercise.toString());
        }
    }

    public void tabulateSummary(List<Exercise> exercises){
        System.out.printf("%-15s %-20s %-12s %-15s\n", "Type", "Name", "Date", "Calories Burned");
        for (Exercise exercise : exercises){
            System.out.printf("%-15s %-20s %-12s %-15.2f\n",
                    exercise.getType(), exercise.toString(), exercise.getDateAsString(), exercise.caloriesBurned());

        }
    }
}

public class ExerciseTracker {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        List<Exercise> exerciseList = new ArrayList<>();

        while (true){
            System.out.println("Here are your choices:\n" + "1. Add an exercise\n" + "2. Print exercises to file\n" +
                    "3. List sorted by date\n" + "4. List sorted by calories burned\n" + "5. Exit");

            int choice = input.nextInt();

            switch (choice){
                case 1:
                    addExrcise(input, exerciseList);
                    break;
                case 2:
                    System.out.print("Enter the name of the file to save: ");
                    String fileName = input.nextLine();
                    ExerciseWriter.writeToFile(exerciseList, fileName);
                    System
            }
        }
    }
}