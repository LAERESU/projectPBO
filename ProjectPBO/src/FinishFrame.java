import javax.swing.*;
import java.awt.*;

public class FinishFrame extends JFrame {
    int width = 1440;
    int height = 900;
    private int remainingBets;

    public FinishFrame(JLabel dogLabel, String dogName, int koinWin, int coins) {
        super("Finish");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.remainingBets = coins;
        this.setResizable(false);

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon podiumImage = new ImageIcon("ProjectPBO/res/image/podium.jpeg");
                g.drawImage(podiumImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(new BorderLayout());
        this.setContentPane(mainPanel);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        dogLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dogLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel("Winner: " + dogName);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 36));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel koinLabel = new JLabel("Koin yang didapat: " + koinWin);
        koinLabel.setFont(new Font("Arial", Font.BOLD, 36));
        koinLabel.setForeground(Color.WHITE);
        koinLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalStrut(200));
        centerPanel.add(dogLabel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(nameLabel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(koinLabel);

        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setFont(new Font("Arial", Font.BOLD, 24));
        playAgainButton.setForeground(Color.WHITE);
        playAgainButton.setBackground(new Color(0, 123, 255));
        playAgainButton.setFocusPainted(false);
        playAgainButton.setBorderPainted(false);
        playAgainButton.setOpaque(true);
        playAgainButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playAgainButton.addActionListener(e -> {
            new StartFrame(remainingBets);
            this.dispose();
        });

        centerPanel.add(Box.createVerticalStrut(50));
        centerPanel.add(playAgainButton);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        this.setVisible(true);
    }
}
