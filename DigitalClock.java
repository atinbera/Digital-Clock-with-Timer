// Java Library 
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DigitalClock extends JFrame {
    private JLabel timeLabel;
    private JLabel dateLabel;
    private JTextField timerInputField;
    private JButton startTimerButton;
    private JButton stopTimerButton;
    private Timer clockTimer; // Timer for updating the clock
    private Timer countdownTimer; // Timer for the countdown
    private int countdownSeconds;

    public DigitalClock() {
        // Set up the JFrame
        setTitle("Digital Clock with Timer");
        setSize(600, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(true);
        setVisible(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage("clock.png"));

        // Create and style the time label
        timeLabel = new JLabel("", JLabel.CENTER);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 40));
        timeLabel.setOpaque(true);
        timeLabel.setBackground(Color.BLACK);
        timeLabel.setForeground(Color.CYAN);
        add(timeLabel, BorderLayout.NORTH);

        // Create and style the date label
        dateLabel = new JLabel("", JLabel.CENTER);
        dateLabel.setFont(new Font("Arial", Font.ITALIC, 20));
        dateLabel.setOpaque(true);
        dateLabel.setBackground(Color.DARK_GRAY);
        dateLabel.setForeground(Color.yellow);
        add(dateLabel, BorderLayout.CENTER);

        // Create a panel for the timer input and buttons
        JPanel timerPanel = new JPanel();
        timerPanel.setLayout(new GridLayout(1, 3));

        // Create and style the timer input field
        timerInputField = new JTextField("Enter seconds", 10);
        timerInputField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                timerInputField.setText(""); // Clear the default text on focus
            }
        });
        timerPanel.add(timerInputField);

        // Create and style the start timer button
        startTimerButton = new JButton("Start Timer");
        startTimerButton.addActionListener(e -> {
            try {
                int seconds = Integer.parseInt(timerInputField.getText().trim());
                if (seconds > 0) {
                    startTimer(seconds);
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter a positive number of seconds.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number of seconds.");
            }
        });
        timerPanel.add(startTimerButton);

        // Create and style the stop timer button
        stopTimerButton = new JButton("Stop Timer");
        stopTimerButton.addActionListener(e -> stopTimer());
        timerPanel.add(stopTimerButton);

        // Add the timer panel to the frame
        add(timerPanel, BorderLayout.SOUTH);

        // Start updating the clock
        startClock();
    }

    /**
     * Starts the digital clock display and updates it every second.
     */
    private void startClock() {
        clockTimer = new Timer(1000, e -> {
            // Format the current time in 12-hour format with AM/PM
            String currentTime = new SimpleDateFormat("hh:mm:ss a").format(new Date());

            // Combine the current time and timer display
            String timerText = countdownSeconds > 0
                ? String.format(" Timer: %02d:%02d", countdownSeconds / 60, countdownSeconds % 60)
                : "| Timer: 00:00";

            // Update the time label
            timeLabel.setText(currentTime + timerText);

            // Format the current date
            String currentDate = new SimpleDateFormat("EEEE, MMMM dd, yyyy").format(new Date());
            dateLabel.setText(currentDate);
        });
        clockTimer.start();
    }

    /**
     * Starts a countdown timer for the specified number of seconds.
     *
     * @param seconds The number of seconds for the countdown.
     */
    private void startTimer(int seconds) {
        countdownSeconds = seconds;

        // If a previous timer exists, stop it
        if (countdownTimer != null) {
            countdownTimer.stop();
        }

        // Create a new countdown timer
        countdownTimer = new Timer(1000, e -> {
            if (countdownSeconds > 0) {
                countdownSeconds--;
            } else {
                countdownTimer.stop();
                JOptionPane.showMessageDialog(this, "Time's up!");
            }
        });

        // Start the countdown timer
        countdownTimer.start();
    }

    /**
     * Stops the currently running countdown timer.
     */
    private void stopTimer() {
        if (countdownTimer != null) {
            countdownTimer.stop();
            countdownSeconds = 0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DigitalClock clock = new DigitalClock();
            clock.setVisible(true);
        });
    }
}
