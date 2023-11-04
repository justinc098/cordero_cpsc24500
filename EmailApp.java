import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Justin Cordero
 * Emailer
 * Object-Oriented Programming
 * Due: 11/3/23
 */

/**
 * Email Info Setup
 */
class Email {
    /**
     * Create variables
     */
    private List<String> recipient;
    private String subject;
    private String body;
    private String status;

    /**
     * Set up initial email information
     * @param recipient
     * @param subject
     * @param body
     */
    public Email(List<String> recipient, String subject, String body){
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
        this.status = "not sent";
    }

    /**
     * "sent" change
     */
    public void send(){
        this.status = "sent";
    }

    /**
     * Display email
     * @return
     */
    public String toString(){
        return "To: " + String.join(", ", recipient) + "\nSubject: " + subject + "\nStatus: " + status + "\nBody: \n" + body;
    }

    /**
     * .txt display email
     * @return
     */
    public String toRecip(){
        return "To: " + String.join(", ", recipient) + "\tSubject: " + subject + "\tStatus: " + status + "\tBody: \t" + body;
    }
}

/**
 * Print email in program
 */
class EmailPrinter{
    /**
     * Code for displaying emails
     * @param emails
     */
    public static void printEmails(List<Email> emails){
        for (Email email : emails){
            System.out.println(email);
            System.out.println();
        }
    }
}

/**
 * Run the app to do emails
 */
public class EmailApp{
    /**
     * Set up list
     */
    private List<Email> emails;

    /**
     * Create app withe list
     */
    public EmailApp(){
        emails = new ArrayList<>();
    }

    /**
     * Write the emails
     */
    public void writeEmail(){
        Scanner userInput = new Scanner(System.in);
        System.out.println();
        System.out.print("Enter recipients' email(s) (separated by spaces): ");
        List<String> recipient = new ArrayList<>();
        String recipientInput = userInput.nextLine();
        recipient.addAll(List.of(recipientInput.split(" ")));

        System.out.print("Enter Subject: ");
        String subject = userInput.nextLine();

        System.out.print("Enter Body: ");
        String body = userInput.nextLine();

        Email email = new Email(recipient, subject, body);
        emails.add(email);
    }

    /**
     * List the emails
     */
    public void listEmails(){
        System.out.println();
        EmailPrinter.printEmails(emails);
    }

    /**
     * List the emails
     */
    public void sendEmails(){
        for (Email email : emails){
            email.send();
        }
        System.out.println("All emails have been sent.");
    }

    /**
     * Save the emails
     * @param fileName
     */
    public void saveEmails(String fileName){
        System.out.println();
        try {
            FileWriter file = new FileWriter(fileName);
            for (Email email : emails){
                file.write(email.toRecip());
            }
            file.close();
            System.out.println("Emails were saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving emails to file.");
        }
    }

    /**
     * Run the program
     */
    public void run(){
        Scanner userInput = new Scanner(System.in);
        for (int x = 0; x <= 60; x++) {
            System.out.print("*");
        }
        System.out.println();
        for (int x = 0; x <= 16; x++) {
            System.out.print(" ");
        }
        System.out.println("Welcome to Emailer V1.0");
        for (int x = 0; x <= 60; x++) {
            System.out.print("*");
        }
        while (true){
            System.out.println();
            System.out.println("Here are your choices: \n1. Write email \n2. List emails \n3. Send emails \n4. Save emails to file \n5. Exit");
            System.out.print("Enter the number of your choice: ");
            String choice = userInput.nextLine();

            if (choice.equals("1")){
                writeEmail();
            } else if (choice.equals("2")){
                listEmails();
            } else if (choice.equals("3")){
                sendEmails();
            } else if (choice.equals("4")){
                System.out.print("Enter name of file to save: ");
                String fileName = userInput.nextLine();
                saveEmails(fileName);
            } else if (choice.equals("5")){
                System.out.println("Thank you for using this program.");
                break;
            } else {
                System.out.println("Please enter a valid integer (1-5)");
            }
        }
    }

    /**
     * Start the application
     * @param args
     */
    public static void main(String[] args) {
        EmailApp emailApp = new EmailApp();
        emailApp.run();
    }
}