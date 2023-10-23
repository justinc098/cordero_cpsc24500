import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.File;

/**
 * Madlibs
 * Object-Oriented Programming
 * Justin Cordero
 * Due: 10/23/23
 */
public class Madlibs {
    public static void  main(String[] args) {
        /**
         * Initiate Scanner
         */
        Scanner input = new Scanner(System.in);

        for (int x = 0; x <= 60; x++) {
            System.out.print("*");
        }
        System.out.println();
        for (int x = 0; x <= 16; x++) {
            System.out.print(" ");
        }
        System.out.println("Welcome to Madlibs V1.0");
        for (int x = 0; x <= 60; x++) {
            System.out.print("*");
        }
        System.out.println();

        /**
         * Describe Program and get information
         */
        System.out.println("This program generates random stories using wordlists you supply. \n");
        System.out.println("");

        System.out.print("Enter the name of the folder where the stories and wordlists are.\n" +
                "Or just press Enter to accept the default location: ");
        String folderLoc = input.nextLine();
        System.out.println("");

        /**
         * Determine Story Number and Display Madlib
         */
        while (true) {
            System.out.print("Enter a story number or q to quit: ");
            String story = input.nextLine();
            System.out.println("");
            if (story.equalsIgnoreCase("q")) {
                /**
                 * Close Program
                 */
                System.out.println("Thank you for using this program.");
                break;
            }

            int storyNumber;
            try {
                storyNumber = Integer.parseInt(story);
            } catch (NumberFormatException e) {
                System.out.println("This story does not exist. Choose again. \n");
                continue;
            }

            String fileName = (folderLoc + "/story" + storyNumber + ".txt");
            try {
                String madlib = generateMadlib(fileName, folderLoc);
                System.out.println("Here is your Madlib: \n" + madlib);
            } catch (IOException e) {
                System.out.println("This story does not exist. Choose again.\n");
            }
        }
        input.close();
    }

    /**
     * Read story/determine the words that need to be changed
     * @param fileName
     * @param folderLoc
     * @return
     * @throws IOException
     */
    private static String generateMadlib(String fileName, String folderLoc) throws IOException {
        BufferedReader storyReader = new BufferedReader(new FileReader(new File(fileName.strip())));
        StringBuilder madlib = new StringBuilder();

        String line;
        while ((line = storyReader.readLine()) !=null){
            String[] words = line.split(" ");
            for (String word : words) {
                if (word.startsWith("<") && word.endsWith(">")) {
                    String placeHolder = word.substring(1, word.length() - 1);
                    String wordList = folderLoc + "/" + placeHolder + ".txt";
                    String replace = getRandomWord(wordList);
                    madlib.append(replace).append(" ");
                } else if (word.startsWith("<") && word.endsWith(".")) {
                    String placeHolder = word.substring(1, word.length() - 2);
                    String wordList = folderLoc + "/" + placeHolder + ".txt";
                    String replace = getRandomWord(wordList);
                    madlib.append(replace).append(".");
                } else if (word.startsWith("<") && word.endsWith(",")) {
                    String placeHolder = word.substring(1, word.length() - 2);
                    String wordList = folderLoc + "/" + placeHolder + ".txt";
                    String replace = getRandomWord(wordList);
                    madlib.append(replace).append(",");
                } else {
                    madlib.append(word).append(" ");
                }
            }
            madlib.append("\n");
        }

        storyReader.close();
        return madlib.toString();
    }

    /**
     * Read story/determine the words that need to be changed
     * @param wordList
     * @return
     * @throws IOException
     */
    private static String getRandomWord(String wordList) throws IOException {
        BufferedReader wordListReader = new BufferedReader(new FileReader(wordList));
        ArrayList<String> words = new ArrayList<>();

        String word;
        while ((word = wordListReader.readLine()) != null) {
            words.add(word);
        }

        wordListReader.close();

        if (words.isEmpty()) {
            return "";
        }

        Random randWord = new Random();
        int randIndex = randWord.nextInt(words.size());
        return words.get(randIndex);
    }
}