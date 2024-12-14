import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;
import java.awt.geom.RoundRectangle2D;
import java.util.Random;

public class StartFrame extends JFrame implements StartFrameInterface {
    private static final int WIDTH = 1440;
    private static final int HEIGHT = 900;
    private List<DogClass> dogs;
    private JTextField betField;
    public int remainingBets;
    Obstacle randomArena = null;

    public StartFrame(int coins) {
        super("Start");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        this.setLayout(null);
        this.remainingBets = coins;
        this.setResizable(false);

        ImageIcon backgroundImageIcon = new ImageIcon("ProjectPBO/res/image/startBG.png");
        Image backgroundImage = backgroundImageIcon.getImage();

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
            }
        };

        backgroundPanel.setLayout(null);
        this.setContentPane(backgroundPanel);

        try {
            dogs = DbConnect.getRandomDogs(5);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            randomArena = DbConnect.getRandomArena();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Random random = new Random();
        for (DogClass dog : dogs) { // Modifier jika skill sesuai dengan arena
            boolean isSkillMatched = dog.getSkill().equals(randomArena.getName());

            if (dog.getDogCondition().equals("Super")) {
                if (isSkillMatched) {
                    dog.setBaseSpeed(random.nextInt(3, 5)); // Keunggulan besar jika skill sesuai
                    dog.setPrice(random.nextInt(3, 6)); // Hadiah lebih kecil untuk peluang menang besar
                } else {
                    dog.setBaseSpeed(random.nextInt(2, 4)); // Keunggulan kecil
                    dog.setPrice(random.nextInt(5, 8));
                }
            } else if (dog.getDogCondition().equals("Healthy")) {
                if (isSkillMatched) {
                    dog.setBaseSpeed(random.nextInt(2, 4)); // Sedang jika skill sesuai
                    dog.setPrice(random.nextInt(5, 8)); // Hadiah menengah
                } else {
                    dog.setBaseSpeed(random.nextInt(1, 3)); // Keunggulan rendah
                    dog.setPrice(random.nextInt(7, 12)); // Hadiah lebih besar
                }
            } else if (dog.getDogCondition().equals("Tired")) {
                if (isSkillMatched) {
                    dog.setBaseSpeed(random.nextInt(1, 3)); // Minimal jika skill sesuai
                    dog.setPrice(random.nextInt(7, 12)); // Hadiah menengah ke atas
                } else {
                    dog.setBaseSpeed(random.nextInt(0, 2)); // Hampir tidak ada keuntungan
                    dog.setPrice(random.nextInt(10, 15)); // Hadiah besar untuk peluang kecil
                }
            } else if (dog.getDogCondition().equals("Injured")) {
                if (isSkillMatched) {
                    dog.setBaseSpeed(random.nextInt(0, 2)); // Sangat minimal jika skill sesuai
                    dog.setPrice(random.nextInt(10, 15)); // Hadiah besar
                } else {
                    dog.setBaseSpeed(random.nextInt(0, 1)); // Hampir tidak ada
                    dog.setPrice(random.nextInt(15, 20)); // Hadiah sangat besar untuk peluang kecil
                }
            }
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
                if (remainingBets >= 0) {
                    try {
                        int bet = Integer.parseInt(betAmount);
                        if (bet < 1) {
                            JOptionPane.showMessageDialog(null, "Bet amount must be at least 1!", "Invalid Bet",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            remainingBets += bet;
                            showDogSelection(randomArena);
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

    public void showDogSelection(Obstacle randomArena) {
        JFrame dogSelectionFrame = new JFrame("Choose Your Dog");
        dogSelectionFrame.setSize(WIDTH, HEIGHT);
        dogSelectionFrame.setLayout(null);
        dogSelectionFrame.setResizable(false);

        ImageIcon backgroundImageIcon = new ImageIcon("ProjectPBO/res/image/dogBG.png");
        Image backgroundImage = backgroundImageIcon.getImage();

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null); // Draw the background image
            }
        };

        backgroundPanel.setLayout(null); // Set the layout to null so you can position components manually
        dogSelectionFrame.setContentPane(backgroundPanel);

        // Panel informasi tentang bets
        JPanel betInfoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(new Color(255, 165, 0));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 30, 30);
            }
        };
        betInfoPanel.setLayout(new BorderLayout());
        betInfoPanel.setBounds(10, 10, 220, 60);
        betInfoPanel.setOpaque(false);

        JLabel betInfoLabel = new JLabel("Coins: " + remainingBets);
        betInfoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        betInfoLabel.setForeground(Color.WHITE);
        betInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        betInfoPanel.add(betInfoLabel, BorderLayout.CENTER);

        JPanel obstacleInfoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int arcSize = 30;
                g2d.setColor(new Color(255, 165, 0));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arcSize, arcSize);

                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, arcSize, arcSize);
            }
        };
        obstacleInfoPanel.setLayout(new BorderLayout());
        obstacleInfoPanel.setBounds(10, 80, 220, 60);
        obstacleInfoPanel.setOpaque(false);

        // Create the label for the obstacle information
        JLabel obstacleLabel = new JLabel(randomArena.getName());
        obstacleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        obstacleLabel.setForeground(Color.WHITE); // Text color
        obstacleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        obstacleInfoPanel.add(obstacleLabel, BorderLayout.CENTER);

        // Panel pemilihan anjing
        JPanel dogSelectionPanel = new JPanel();
        dogSelectionPanel.setLayout(null);
        dogSelectionPanel.setOpaque(false);
        dogSelectionPanel.setBounds(0, 0, WIDTH, HEIGHT);

        dogSelectionFrame.add(obstacleInfoPanel);
        dogSelectionFrame.add(betInfoPanel);
        dogSelectionFrame.add(dogSelectionPanel);
        dogSelectionFrame.setVisible(true);

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

            popupPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            Font headerFont = new Font("Arial", Font.BOLD, 16);
            Font labelFont = new Font("Arial", Font.PLAIN, 14);

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

            JSeparator separator1 = new JSeparator();
            JSeparator separator2 = new JSeparator();
            JSeparator separator3 = new JSeparator();
            JSeparator separator4 = new JSeparator();

            popupPanel.add(nameLabel);
            popupPanel.add(separator1);
            popupPanel.add(conditionLabel);
            popupPanel.add(separator2);
            popupPanel.add(skillLabel);
            popupPanel.add(separator3);
            popupPanel.add(speedLabel);
            popupPanel.add(separator4);
            popupPanel.add(priceLabel);

            JScrollPane scrollPane = new JScrollPane(popupPanel);
            scrollPane.setPreferredSize(new Dimension(250, 300));
            scrollPane.setBorder(BorderFactory.createEmptyBorder());

            popupPanel.setBackground(Color.YELLOW);
            popupPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
            popupPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            selectButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    int popupX = selectButton.getX() + (selectButton.getWidth() - scrollPane.getWidth()) / 2;
                    int popupY = selectButton.getY() - scrollPane.getPreferredSize().height - 10;

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

            JLabel clickCountLabel = new JLabel("bet: 0X");
            clickCountLabel.setBounds(buttonX, 610, buttonWidth, 30);
            clickCountLabel.setForeground(Color.BLACK);
            clickCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
            dog.setClickCount(0);

            selectButton.addActionListener(e -> {
                if (remainingBets > 0) {
                    dog.incrementClickCount();
                    remainingBets--;
                    clickCountLabel.setText("Bet: " + dog.getClickCount() + "X");
                    betInfoLabel.setText("Coins: " + remainingBets);
                    if(dog.getClickCount() > 2){
                        dog.setPrice();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No bets remaining!", "Bet Limit Reached",
                            JOptionPane.WARNING_MESSAGE);
                }
            });

            JButton startRaceButton = new JButton("Start Race");
            startRaceButton.setBounds(10, 150, 220, 60);
            startRaceButton.setBackground(Color.GREEN);
            startRaceButton.setForeground(Color.WHITE);
            startRaceButton.setFocusPainted(false);
            startRaceButton.setUI(new RoundButtonUI());

            startRaceButton.addActionListener(e -> {
                for (DogClass d : dogs) {
                    if (d.getClickCount() == 0) {
                        d.setPrice(0);
                    }
                }
                boolean anyDogBetted = dogs.stream().anyMatch(d -> d.getClickCount() > 0);
                if (anyDogBetted) {
                    new RaceFrame(dog, dogs, randomArena, remainingBets);
                    dogSelectionFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "No dog has been betted on!", "No Bets",
                            JOptionPane.WARNING_MESSAGE);
                }
            });

            dogSelectionPanel.add(selectButton);
            dogSelectionPanel.add(clickCountLabel);
            dogSelectionPanel.add(startRaceButton);
        }
        dogSelectionFrame.add(betInfoPanel);
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