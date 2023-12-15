import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;

class SpeedrunData implements Comparable<SpeedrunData> {
    private String gameTitle;
    private String runnerName;
    private double completionTime;

    // Constructors
    public SpeedrunData(String gameTitle, String runnerName, double completionTime) {
        this.gameTitle = gameTitle;
        this.runnerName = runnerName;
        this.completionTime = completionTime;
    }

    public SpeedrunData() {
        // Default constructor
    }

    // Getters and setters
    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getRunnerName() {
        return runnerName;
    }

    public void setRunnerName(String runnerName) {
        this.runnerName = runnerName;
    }

    public double getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(double completionTime) {
        this.completionTime = completionTime;
    }

    // toString method
    @Override
    public String toString() {
        return "Game: " + gameTitle + ", Runner: " + runnerName + ", Time: " + completionTime;
    }

    @Override
    public int compareTo(SpeedrunData other) {
        int titleComparison = this.gameTitle.compareToIgnoreCase(other.gameTitle);
        if (titleComparison != 0) {
            return titleComparison;
        }
        return Double.compare(this.completionTime, other.completionTime);
    }
}

class SpeedrunManager {
    private ArrayList<SpeedrunData> speedrunCollection = new ArrayList<>();

    public void addSpeedrunData(SpeedrunData speedrun) {
        speedrunCollection.add(speedrun);
        Collections.sort(speedrunCollection);
    }

    public ArrayList<SpeedrunData> getSpeedrunCollection() {
        return speedrunCollection;
    }

    // Save collection to binary file
    public void saveToBinary(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(speedrunCollection);
            System.out.println("Data saved to binary file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load collection from binary file
    public void loadFromBinary(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            speedrunCollection = (ArrayList<SpeedrunData>) ois.readObject();
            System.out.println("Data loaded from binary file successfully.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class SpeedrunForm extends JFrame {
    private JTextField gameTitleField;
    private JTextField runnerNameField;
    private JTextField completionTimeField;
    private JTextArea gameListArea; // Added JTextArea
    private SpeedrunManager speedrunManager;

    public SpeedrunForm(SpeedrunManager speedrunManager) {
        this.speedrunManager = speedrunManager;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Speedrun Data Form");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gameTitleField = new JTextField(20);
        runnerNameField = new JTextField(20);
        completionTimeField = new JTextField(20);
        gameListArea = new JTextArea(10, 30); // Added JTextArea

        setFixedTextFieldSize(gameTitleField);
        setFixedTextFieldSize(runnerNameField);
        setFixedTextFieldSize(completionTimeField);

        JButton submitButton = new JButton("Submit");
        JButton saveButton = new JButton("Save");

        submitButton.addActionListener(e -> submitForm());
        saveButton.addActionListener(e -> saveData());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        inputPanel.add(new JLabel("Game Title:"));
        inputPanel.add(gameTitleField);

        inputPanel.add(Box.createVerticalStrut(10)); // Add some spacing

        inputPanel.add(new JLabel("Runner Name:"));
        inputPanel.add(runnerNameField);

        inputPanel.add(Box.createVerticalStrut(10)); // Add some spacing

        inputPanel.add(new JLabel("Completion Time:"));
        inputPanel.add(completionTimeField);

        inputPanel.add(Box.createVerticalStrut(10)); // Add some spacing

        inputPanel.add(submitButton);
        inputPanel.add(saveButton);

        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
        displayPanel.add(new JLabel("Game List:"));
        displayPanel.add(new JScrollPane(gameListArea)); // Wrap JTextArea with JScrollPane for scrolling

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        mainPanel.add(inputPanel);
        mainPanel.add(Box.createHorizontalStrut(10)); // Add some spacing
        mainPanel.add(displayPanel);

        setContentPane(mainPanel);
    }

    private void setFixedTextFieldSize(JTextField textField) {
        textField.setMaximumSize(textField.getPreferredSize());
    }

    private void submitForm() {
        String gameTitle = gameTitleField.getText().trim();
        String runnerName = runnerNameField.getText().trim();
        String completionTimeString = completionTimeField.getText().trim();

        if (gameTitle.isEmpty() || runnerName.isEmpty() || completionTimeString.isEmpty()) {
            showError("All fields are required.");
            return;
        }

        double completionTime;
        try {
            completionTime = Double.parseDouble(completionTimeString);
        } catch (NumberFormatException e) {
            showError("Completion Time must be a valid number.");
            return;
        }

        SpeedrunData speedrunData = new SpeedrunData(gameTitle, runnerName, completionTime);
        speedrunManager.addSpeedrunData(speedrunData);

        updateGameList(); // Added to update the game list
        JOptionPane.showMessageDialog(this, "Speedrun data submitted successfully!");
        clearForm();
    }

    private void updateGameList() {
        StringBuilder gameList = new StringBuilder();
        for (SpeedrunData speedrunData : speedrunManager.getSpeedrunCollection()) {
            gameList.append(speedrunData.getGameTitle())
                    .append(" - ")
                    .append(speedrunData.getCompletionTime())
                    .append(" minutes - ")
                    .append(speedrunData.getRunnerName())  // Displaying runner's name
                    .append("\n");
        }
        gameListArea.setText(gameList.toString());
    }

    private void saveData() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getPath();
            speedrunManager.saveToBinary(filePath + ".bin");
        }
    }

    private void clearForm() {
        gameTitleField.setText("");
        runnerNameField.setText("");
        completionTimeField.setText("");
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}

public class SpeedrunApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SpeedrunManager speedrunManager = new SpeedrunManager();
            SpeedrunForm speedrunForm = new SpeedrunForm(speedrunManager);

            JMenuBar menuBar = new JMenuBar();
            JMenu fileMenu = new JMenu("File");
            JMenuItem saveMenuItem = new JMenuItem("Save");
            JMenuItem loadMenuItem = new JMenuItem("Load");
            JMenuItem displayMenuItem = new JMenuItem("Display");

            saveMenuItem.addActionListener(e -> speedrunManager.saveToBinary("speedrunCollection.bin"));
            loadMenuItem.addActionListener(e -> speedrunManager.loadFromBinary("speedrunCollection.bin"));
            displayMenuItem.addActionListener(e -> displayData(speedrunManager.getSpeedrunCollection()));

            fileMenu.add(saveMenuItem);
            fileMenu.add(loadMenuItem);
            fileMenu.add(displayMenuItem);
            menuBar.add(fileMenu);

            speedrunForm.setJMenuBar(menuBar);
            speedrunForm.setVisible(true);
        });
    }

    private static void displayData(ArrayList<SpeedrunData> speedrunCollection) {
        for (SpeedrunData speedrunData : speedrunCollection) {
            System.out.println(speedrunData);
        }
    }
}