//Justin Cordero
//Object-Oriented Programming
//Text Analyzer
//Due 10/13/23
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
public class TextAnalyzer {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        for (int x = 0; x <= 60; x++) {
            System.out.print("*");
        }
        System.out.println();
        for (int x = 0; x <= 14; x++) {
            System.out.print(" ");
        }
        System.out.println("Welcome to TextAnalyzer V1.0");
        for (int x = 0; x <= 60; x++) {
            System.out.print("*");
        }
        System.out.println();

        System.out.print("What text file would you like to analyze? ");

        String text = input.nextLine();
        File textFile = new File(text);

        if (!textFile.exists() || !textFile.isFile()) {
            System.out.println("The file does not exist or is not a valid file.");
            return;
        }

        StringBuilder textContent = new StringBuilder();
        try (BufferedReader readText = new BufferedReader(new FileReader(textFile))) {
            String line;
            while ((line = readText.readLine()) != null) {
                textContent.append(line).append(" ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean running = true;
        while (running) {
            System.out.println("Here are your options:");
            System.out.println("1. Count the number of vowels.");
            System.out.println("2. Count the number of consonants.");
            System.out.println("3. Count the number of words");
            System.out.println("4. Print a summary to a file.");
            System.out.println("5. Quit");

            System.out.print("Enter the number of your choice: ");
            String choice = input.nextLine();

            switch (choice) {
                case "1":
                    int vowelCount = countVowels(textContent.toString());
                    System.out.println("There are " + vowelCount + " vowels.");
                    break;
                case "2":
                    int consonantCount = countConsonants(textContent.toString());
                    System.out.println("There are " + consonantCount + " consonants.");
                    break;
                case "3":
                    int wordCount = countWords(textContent.toString());
                    System.out.println("There are " + wordCount + " words.");
                    break;
                case "4":
                    System.out.print("Enter the name of the file to write the summary: ");
                    String summaryFileName = input.nextLine();
                    boolean summaryWritten = writeSummaryToFile(textContent.toString(), summaryFileName);
                    if (summaryWritten) {
                        System.out.println("The summary was written to a file.");
                    } else {
                        System.out.println("Failed to write the summary to a file.");
                    }
                    break;
                case "5":
                    System.out.println("Thank you for using this program.");
                    running = false;
                    break;
                default:
                    System.out.println("That is not a valid choice.");
            }
        }
    }


    private static int countVowels(String text) {
        String lowercaseText = text.toLowerCase();
        int vowelCount = 0;

        for (int i = 0; i < lowercaseText.length(); i++) {
            char c = lowercaseText.charAt(i);
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                vowelCount++;
            }
        }

        return vowelCount;
    }

    private static int countConsonants(String text) {
        String lowercaseText = text.toLowerCase();
        int consonantCount = 0;

        for (int i = 0; i < lowercaseText.length(); i++) {
            char c = lowercaseText.charAt(i);
            if (c >= 'a' && c <= 'z' && "aeiou".indexOf(c) == -1) {
                consonantCount++;
            }
        }

        return consonantCount;
    }

    private static int countWords(String text) {
        String[] words = text.split("\\s+");
        return words.length;
    }

    private static boolean writeSummaryToFile(String text, String textSumm) {
        try {
            FileWriter write = new FileWriter(textSumm);
            write.write("Text Summary:\n");
            write.write("--------------\n");
            write.write("Text Content:\n");
            write.write(text);
            write.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}




