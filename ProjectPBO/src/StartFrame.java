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
        betPanel.setOpaque(false);
        betPanel.setLayout(null);

        int panelWidth = 500;
        int panelHeight = 300;

        int x = (400);
        int y = (200);
        betPanel.setBounds(x, y, panelWidth, panelHeight);

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
                        int bet = Integer.parseInt(betAmount);
                        if (bet < 50) {
                            JOptionPane.showMessageDialog(null, "Bet amount must be at least 50!", "Invalid Bet",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            showDogSelection();
                            dispose();
                        }
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
        dogSelectionFrame.setLayout(null);

        JPanel dogSelectionPanel = new JPanel();
        dogSelectionPanel.setLayout(null);
        dogSelectionPanel.setOpaque(false);
        dogSelectionPanel.setBounds(0, 0, WIDTH, HEIGHT);

        int buttonWidth = 100;
        int buttonHeight = 100;
        int buttonSpacing = 50;

        int centerX = WIDTH / 2;
        int thirdButtonIndex = 2;
        int thirdButtonX = thirdButtonIndex * (buttonWidth + buttonSpacing);
        int offset = centerX - thirdButtonX - (buttonWidth / 2);
        int startX = offset;

        for (int i = 0; i < dogs.size(); i++) {
            DogClass dog = dogs.get(i);

            JButton selectButton = new JButton();
            ImageIcon originalDogIcon = new ImageIcon("ProjectPBO/res/image/dog/" + dog.getImgPath());

            Image dogImage = originalDogIcon.getImage().getScaledInstance(buttonWidth, buttonHeight,
                    Image.SCALE_SMOOTH);
            ImageIcon resizedDogIcon = new ImageIcon(dogImage);

            selectButton.setIcon(resizedDogIcon);
            selectButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
            selectButton.setBorder(BorderFactory.createEmptyBorder());
            selectButton.setFocusPainted(false);
            selectButton.setContentAreaFilled(false);

            int buttonX = startX + i * (buttonWidth + buttonSpacing);
            selectButton.setBounds(buttonX, 500, buttonWidth, buttonHeight);

            // Panel yang menampilkan informasi anjing (popup)
            JPanel popupPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    int arcSize = 20;
                    g2d.setColor(Color.YELLOW);
                    g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arcSize, arcSize));
                }
            };
            popupPanel.setLayout(new BoxLayout(popupPanel, BoxLayout.Y_AXIS));
            popupPanel.setOpaque(false);
            popupPanel.setPreferredSize(new Dimension(250, 300));

            // Menambahkan padding agar teks tidak terlalu rapat dengan batas
            popupPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            Font headerFont = new Font("Arial", Font.BOLD, 16);
            Font labelFont = new Font("Arial", Font.PLAIN, 14);

            // Label dengan teks anjing
            JLabel nameLabel = new JLabel(dog.getName());
            nameLabel.setFont(headerFont);
            nameLabel.setForeground(Color.DARK_GRAY);
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel conditionLabel = new JLabel("Condition: " + dog.getDogCondition());
            conditionLabel.setFont(labelFont);
            conditionLabel.setForeground(Color.DARK_GRAY);
            conditionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel skillLabel = new JLabel("Skill: " + dog.getSkill());
            skillLabel.setFont(labelFont);
            skillLabel.setForeground(Color.DARK_GRAY);
            skillLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel speedLabel = new JLabel("Base Speed: " + dog.getBaseSpeed());
            speedLabel.setFont(labelFont);
            speedLabel.setForeground(Color.DARK_GRAY);
            speedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel priceLabel = new JLabel("Price: " + dog.getPrice());
            priceLabel.setFont(labelFont);
            priceLabel.setForeground(Color.DARK_GRAY);
            priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Menambahkan pemisah antar informasi
            JSeparator separator1 = new JSeparator();
            JSeparator separator2 = new JSeparator();
            JSeparator separator3 = new JSeparator();
            JSeparator separator4 = new JSeparator();

            // Menambahkan label dan pemisah ke panel popup
            popupPanel.add(nameLabel);
            popupPanel.add(separator1);
            popupPanel.add(conditionLabel);
            popupPanel.add(separator2);
            popupPanel.add(skillLabel);
            popupPanel.add(separator3);
            popupPanel.add(speedLabel);
            popupPanel.add(separator4);
            popupPanel.add(priceLabel);

            // Membuat scroll pane untuk panel popup
            JScrollPane scrollPane = new JScrollPane(popupPanel);
            scrollPane.setPreferredSize(new Dimension(250, 300));
            scrollPane.setBorder(BorderFactory.createEmptyBorder());

            // Menambahkan efek bayangan
            popupPanel.setBackground(Color.YELLOW); // Pastikan panel kuning terlihat
            popupPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
            popupPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            selectButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    // Hitung posisi popup agar berada di tengah tombol
                    int popupX = selectButton.getX() + (selectButton.getWidth() - scrollPane.getWidth()) / 2;
                    int popupY = selectButton.getY() - scrollPane.getPreferredSize().height - 10;

                    // Mengatur posisi dan visibilitas popup
                    scrollPane.setBounds(popupX, popupY, scrollPane.getPreferredSize().width,
                            scrollPane.getPreferredSize().height);
                    scrollPane.setVisible(true);

                    dogSelectionPanel.add(scrollPane);
                    dogSelectionPanel.revalidate();
                    dogSelectionPanel.repaint();
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    scrollPane.setVisible(false);
                    dogSelectionPanel.remove(scrollPane);
                    dogSelectionPanel.revalidate();
                    dogSelectionPanel.repaint();
                }
            });

            dogSelectionPanel.add(selectButton);
        }
        dogSelectionFrame.add(dogSelectionPanel);
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