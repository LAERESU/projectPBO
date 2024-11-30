import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;
import java.awt.geom.RoundRectangle2D;

public class StartFrame extends JFrame {
    private static final int WIDTH = 1440;
    private static final int HEIGHT = 900;
    private List<DogClass> dogs;
    private JTextField betField;

    public StartFrame() {
        super("Start");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        this.setLayout(null);

        this.getContentPane().setBackground(Color.CYAN);

        try {
            dogs = DbConnect.getRandomDogs(5);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JPanel betPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int arcSize = 20;
                g2d.setColor(Color.ORANGE);
                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arcSize, arcSize));
            }
        };
        betPanel.setBackground(Color.YELLOW);
        betPanel.setBounds(550, 350, 500, 300);
        betPanel.setOpaque(false);
        betPanel.setLayout(null);

        betField = new JTextField();
        betField.setBounds(170, 160, 160, 30);
        betField.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));

        JButton startButton = new JButton("START");
        startButton.setBounds(200, 80, 100, 40);
        startButton.setUI(new RoundButtonUI());
        startButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        startButton.setOpaque(false);

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String betAmount = betField.getText();
                if (!betAmount.isEmpty()) {
                    try {
                        // int bet = Integer.parseInt(betAmount);
                        showDogSelection();
                        dispose();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid bet amount!", "Invalid Bet",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter your bet!", "Empty Bet",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        betPanel.add(startButton);
        betPanel.add(betField);

        this.add(betPanel);
        this.setVisible(true);
    }

    private void showDogSelection() {
        JFrame dogSelectionFrame = new JFrame("Choose Your Dog");
        dogSelectionFrame.setSize(WIDTH, HEIGHT);
        dogSelectionFrame.setLayout(new BorderLayout());

        JPanel dogSelectionPanel = new JPanel();
        dogSelectionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        dogSelectionPanel.setOpaque(false);

        for (int i = 0; i < dogs.size(); i++) {
            DogClass dog = dogs.get(i);

            JButton selectButton = new JButton();
            ImageIcon originalDogIcon = new ImageIcon("ProjectPBO/res/image/dog/" + dog.getImgPath());

            Image dogImage = originalDogIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            ImageIcon resizedDogIcon = new ImageIcon(dogImage);

            selectButton.setIcon(resizedDogIcon);
            selectButton.setPreferredSize(new Dimension(120, 120));
            selectButton.setBorder(BorderFactory.createEmptyBorder());
            selectButton.setFocusPainted(false);

            selectButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    selectButton.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    selectButton.setBorder(BorderFactory.createEmptyBorder());
                }
            });

            selectButton.addActionListener(e -> {
                new RaceFrame(dog);
                dogSelectionFrame.dispose();
            });

            dogSelectionPanel.add(selectButton);
        }

        dogSelectionFrame.add(dogSelectionPanel, BorderLayout.SOUTH);
        dogSelectionFrame.setVisible(true);
    }

    static class RoundButtonUI extends javax.swing.plaf.basic.BasicButtonUI {
        public void paint(Graphics g, JComponent c) {
            AbstractButton b = (AbstractButton) c;
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(b.getBackground());
            g2.fill(new RoundRectangle2D.Double(0, 0, b.getWidth(), b.getHeight(), 20, 20));

            super.paint(g, c);
        }
    }
}
