import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Sets up the necessities for the program
 */
public class ExerciseTracker extends JFrame {
    private JTextField nameField, dateField, distanceField, durationField, commentField;
    private boolean loggedIn = false;
    private List<Exercise> exerciseList = new ArrayList<>();
    private DefaultListModel<String> summaryListModel = new DefaultListModel<>();
    private JList<String> summaryList;
    private JLabel caloriesLabel;

    /**
     * Constructs the ExerciseTracker GUI
     */
    public ExerciseTracker() {
        setTitle("Exercise Tracker V2.0");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createMenu();
        createUI();

        setVisible(true);
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu helpMenu = new JMenu("Help");

        JMenuItem loginItem = new JMenuItem("Login");
        JMenuItem summaryItem = new JMenuItem("Summary");
        JMenuItem logOutItem = new JMenuItem("Log Out");
        JMenuItem exitItem = new JMenuItem("Exit");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem aboutItem = new JMenuItem("About");

        loginItem.addActionListener(e -> login());
        summaryItem.addActionListener(e -> showSummary());
        logOutItem.addActionListener(e -> logOut());
        exitItem.addActionListener(e -> exit());
        saveItem.addActionListener(e -> saveToFile());
        aboutItem.addActionListener(e -> showAbout());

        fileMenu.add(loginItem);
        fileMenu.add(summaryItem);
        fileMenu.add(logOutItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        helpMenu.add(aboutItem);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    private void createUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        nameField = createTextField();
        dateField = createTextField();
        distanceField = createTextField();
        durationField = createTextField();

        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Date:"));
        inputPanel.add(dateField);
        inputPanel.add(new JLabel("Distance:"));
        inputPanel.add(distanceField);
        inputPanel.add(new JLabel("Duration:"));
        inputPanel.add(durationField);

        commentField = createTextField();
        JPanel commentPanel = new JPanel(new BorderLayout());
        commentPanel.add(new JLabel("Add Comment:"), BorderLayout.NORTH);
        commentPanel.add(commentField, BorderLayout.CENTER);

        JButton addExerciseButton = new JButton("Add Exercise");
        addExerciseButton.addActionListener(e -> addExercise());

        inputPanel.add(commentPanel);
        inputPanel.add(addExerciseButton);

        mainPanel.add(inputPanel, BorderLayout.WEST);

        // Exercise Summary Tab
        summaryList = new JList<>(summaryListModel);
        JScrollPane summaryScrollPane = new JScrollPane(summaryList);
        summaryScrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Title and Calories Label
        JPanel summaryPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Exercise Summary");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        caloriesLabel = new JLabel("Total Calories Burned: 0");
        caloriesLabel.setHorizontalAlignment(JLabel.CENTER);

        summaryPanel.add(titleLabel, BorderLayout.NORTH);
        summaryPanel.add(summaryScrollPane, BorderLayout.CENTER);
        summaryPanel.add(caloriesLabel, BorderLayout.SOUTH);

        mainPanel.add(summaryPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setColumns(15); // Set the number of columns
        return textField;
    }

    private void addExercise() {
        if (!loggedIn) {
            JOptionPane.showMessageDialog(this, "Please log in first", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String name = nameField.getText();
            String dateString = dateField.getText();
            double distance = Double.parseDouble(distanceField.getText());
            int duration = Integer.parseInt(durationField.getText());
            String comment = commentField.getText();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = dateFormat.parse(dateString);

            Exercise exercise = new Exercise(name, date, duration, comment);
            exerciseList.add(exercise);
            updateSummaryList();

            JOptionPane.showMessageDialog(this, "Exercise added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Clear fields after adding exercise
            clearFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter numeric values for distance and duration.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use dd/MM/yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateSummaryList() {
        // Sort exercises by name, date, and duration
        Collections.sort(exerciseList, new Comparator<Exercise>() {
            @Override
            public int compare(Exercise o1, Exercise o2) {
                int nameComparison = o1.getName().compareTo(o2.getName());
                if (nameComparison != 0) {
                    return nameComparison;
                }

                int dateComparison = o1.getDate().compareTo(o2.getDate());
                if (dateComparison != 0) {
                    return dateComparison;
                }

                return Integer.compare(o1.getDuration(), o2.getDuration());
            }
        });

        summaryListModel.clear();

        // Add exercises to the summary list
        int totalCalories = 0;
        int distance = 0; // You need to set the distance value here or fetch it from some source
        for (Exercise exercise : exerciseList) {
            int calories = exercise.getCalories(distance);
            summaryListModel.addElement(exercise.getSummary());
            totalCalories += calories;
        }

        // Update total calories label
        caloriesLabel.setText("Total Calories Burned: " + totalCalories);
    }

    private void showSummary() {
        updateSummaryList();

        JOptionPane.showMessageDialog(this, summaryList, "Exercise Summary", JOptionPane.PLAIN_MESSAGE);
    }

    private void clearFields() {
        nameField.setText("");
        dateField.setText("");
        distanceField.setText("");
        durationField.setText("");
        commentField.setText("");
    }

    private void login() {
        String username = JOptionPane.showInputDialog(this, "Enter username:");
        String password = JOptionPane.showInputDialog(this, "Enter password:");

        if ("healthy".equals(username) && "donut".equals(password)) {
            loggedIn = true;
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void logOut() {
        loggedIn = false;
        JOptionPane.showMessageDialog(this, "Logged out successfully", "Log Out", JOptionPane.INFORMATION_MESSAGE);
    }

    private void exit() {
        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void saveToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Exercises");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileToSave))) {
                oos.writeObject(exerciseList);
                JOptionPane.showMessageDialog(this, "Exercises saved successfully", "Save", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving exercises", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void showAbout() {
        JOptionPane.showMessageDialog(this, "ExerciseTracker V2.0 - CPSC 24500 FA23", "About", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * The main method
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExerciseTracker());
    }

    /**
     * Creates the exercise
     */
    class Exercise implements Serializable {
        private String name;
        private Date date;
        private int duration;
        private String comment;

        /**
         * @param name
         * @param date
         * @param duration
         * @param comment
         */
        public Exercise(String name, Date date, int duration, String comment) {
            this.name = name;
            this.date = date;
            this.duration = duration;
            this.comment = comment;
        }

        /**
         * @return The name of the exercise.
         */
        public String getName() {
            return name;
        }

        /**
         * @return The formatted date of the exercise.
         */
        public String getDate() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return dateFormat.format(date);
        }

        /**
         * @return The duration of the exercise.
         */
        public int getDuration() {
            return duration;
        }

        /**
         * @return Additional comments about the exercise.
         */
        public String getComment() {
            return comment;
        }

        /**
         * @return The calories burned during the exercise.
         */
        public int getCalories(double distance) {
            // Simple calculation, adjust as needed
            return (int) (duration * 0.1 + distance * 30);
        }

        /**
         * @return A summary string representation of the exercise.
         */
        public String getSummary() {
            return String.format("%s    runwalk      %s      %d      %s",
                    getName(), getDate(), getDuration(), getComment());
        }
    }
}