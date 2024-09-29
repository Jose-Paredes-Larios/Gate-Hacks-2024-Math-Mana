import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.util.Random;


import java.io.IOException;


public class ImageInWindow extends JFrame {

    private JTextField userInputField;  // Text field for user input
    private JLabel resultLabel;         // Label to show the result or feedback
    private BufferedImage image;        // To hold the image
    private BufferedImage correctImage;
    private BufferedImage incorrectImage;
    private BufferedImage backImage;
    private Image scaledImage;
    private int correctAnswer;
    private JLabel mathProblemLabel=new JLabel("", SwingConstants.CENTER);;
    private int wrong=0;



    private void generateMathProblem() {
        Random random = new Random();
        int num1 = random.nextInt(10) + 1;  // Random number between 1 and 10
        int num2 = random.nextInt(10) + 1;  // Random number between 1 and 10
        correctAnswer = num1 + num2;        // Set the correct answer

        // Display the math problem
        mathProblemLabel.setText(num1 + " + " + num2 + " = ?");
        mathProblemLabel.revalidate(); // Ensures layout is updated
        mathProblemLabel.repaint();
    }

    

    private void checkAnswer() {

        try {
            // Get the user input and convert it to an integer
            int userAnswer = Integer.parseInt(userInputField.getText());

            // Check if the answer is correct
            if (userAnswer == correctAnswer) {
                scaledImage=correctImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                resultLabel.setText("Correct! Well done.");
                wrong=0;
                generateMathProblem();
            } else{
                scaledImage=incorrectImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                resultLabel.setText("Incorrect. Try again!");
                wrong++;
            }
            if(wrong>=3)
                scaledImage=backImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        } catch (NumberFormatException ex) {
            // Handle invalid input (e.g., non-integer input)
            resultLabel.setText("Please enter a valid number.");
        }
        repaint();
    }


    public ImageInWindow() {
        // Set up the window
        setTitle("Math Mana");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the window

        // Try to load the image from a file
        try {
            image = ImageIO.read(new File("x.png"));  // Change path if needed
            correctImage = ImageIO.read(new File("correct.png"));    // Image when the answer is correct
            incorrectImage = ImageIO.read(new File("incorrect.png")); // Image when the answer is incorrect
            backImage = ImageIO.read(new File("back.png"));
            scaledImage = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }


        generateMathProblem();

        // Create a panel to hold input components
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());

        // Create a text field for user input
        userInputField = new JTextField(20);

        // Create a button
        JButton submitButton = new JButton("Submit");

        // Create a label to display result or message
        resultLabel = new JLabel("Enter something and press Submit.", SwingConstants.CENTER);

        // Add action listener to the button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the user input from the text field
                String userInput = userInputField.getText();
                
                // Do something with the input
                processInput(userInput);
            }
        });

        // Add the text field and button to the panel
        JPanel topInputPanel = new JPanel();
        topInputPanel.add(mathProblemLabel);
        topInputPanel.add(userInputField);
        topInputPanel.add(submitButton);

        // Add the panels to the window
        inputPanel.add(topInputPanel, BorderLayout.NORTH);   // Input at the top
        inputPanel.add(resultLabel, BorderLayout.CENTER);    // Label in the center

        // Custom panel to draw the image
        ImagePanel imagePanel = new ImagePanel();

        // Add both the input panel and the image panel to the frame
        add(inputPanel, BorderLayout.CENTER);
        add(imagePanel, BorderLayout.SOUTH);  // Image will be displayed at the bottom
    }

    // Function to handle user input
    private void processInput(String input) {
        // Example: You can print it to the console, or display it in the label
        checkAnswer();
        // Update the label with the user input
    }

    // Custom panel to display the image in the bottom-right corner
    private class ImagePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (scaledImage != null) {
                int windowWidth = getWidth();
                int windowHeight = getHeight();
                int imageWidth = scaledImage.getWidth(this);
                int imageHeight = scaledImage.getHeight(this);

                // Draw the image in the bottom right corner of this panel
                g.drawImage(scaledImage, windowWidth - imageWidth - 450, windowHeight - imageHeight - 0, this);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(600, 220);  // Adjust the height of the panel for the image
        }
    }

    public static void main(String[] args) {
        // Create and display the window
        ImageInWindow window = new ImageInWindow();
        window.setVisible(true);
    }
}
