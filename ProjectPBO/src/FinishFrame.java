import javax.swing.*;
import java.awt.*;
import javax.swing.ImageIcon;

public class FinishFrame extends JFrame {
    int width = 1440;
    int height = 900;

    public FinishFrame(JLabel dogLabel, String dogName, int koinWin) {
        super("Finish");

        // Set up window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.getContentPane().setBackground(Color.BLUE);

        // Use BorderLayout for main layout
        this.setLayout(new BorderLayout());

        // Panel to center the content
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.BLUE);
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the content horizontally
        centerPanel.setAlignmentY(Component.CENTER_ALIGNMENT); // Center the content vertically

        dogLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the image horizontally

        // Name label
        JLabel nameLabel = new JLabel("Winner: " + dogName);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 36)); // Set a large bold font
        nameLabel.setForeground(Color.WHITE); // Set the text color to white
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the name horizontally

        JLabel koinLabel = new JLabel("Koin yang didapat: " + koinWin);
        koinLabel.setFont(new Font("Arial", Font.BOLD, 36)); // Set a large bold font
        koinLabel.setForeground(Color.WHITE); // Set the text color to white
        koinLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the name horizontally
        

        // Add image and name to the center panel
        centerPanel.add(dogLabel);
        centerPanel.add(Box.createVerticalStrut(20)); // Add vertical space between image and name
        centerPanel.add(nameLabel);

        // Add vertical space between name and koin
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(koinLabel);

        // Add center panel to the frame
        this.add(centerPanel, BorderLayout.CENTER);

        // Set the frame visible
        this.setVisible(true);
    }


    // public static void main(String[] args) {
    //     DogClass winner = new DogClass(1, "Doggo", "Good", "Jump", 10, "D:\\KULI AH\\SEMESTER 5\\PBO\\Praktikum\\projectPBO\\ProjectPBO\\res\\image\\dog\\1.png");
    //     SwingUtilities.invokeLater(() -> {
    //         new FinishFrame(winner, 12);
    //     });
    // }
}