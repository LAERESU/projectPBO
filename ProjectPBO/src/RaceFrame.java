import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class RaceFrame extends JFrame {
    private int width = 1440;
    private int height = 900;
    private List<DogClass> allDogs;
    private AtomicBoolean raceFinished = new AtomicBoolean(false);
    private CountDownLatch startSignal;
    private Obstacle obstacle;
    private int remainingBets;

    public RaceFrame(DogClass selectedDog, List<DogClass> allDogs, Obstacle obstacle, int coins) {
        super("Race");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.setLayout(null);
        this.remainingBets = coins;
        this.setResizable(false);

        this.allDogs = allDogs;
        this.startSignal = new CountDownLatch(1);
        this.obstacle = obstacle;

        initializeRace();
        this.setVisible(true);
    }

    private void initializeRace() {
        JPanel racePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                ImageIcon backgroundImageIcon = new ImageIcon("ProjectPBO/res/image/raceBG.jpg");
                Image backgroundImage = backgroundImageIcon.getImage();
                g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);

                int trackHeight = 120;
                int trackMargin = 20; 
    
                for (int i = 0; i < allDogs.size(); i++) {
                    int y = 90 + i * (trackHeight + trackMargin);

                    Color trackColor = new Color(139, 69, 19, 128); 
                    g2d.setColor(trackColor);

                    g2d.fillRect(0, y, width, trackHeight);
                }
            }
        };
        racePanel.setLayout(null);
        racePanel.setBounds(0, 0, width, height);
    
        int obstacleX = (int) (width * 0.3);
        int obstacleWidth = obstacle.getLength();
    
        for (int i = 0; i < allDogs.size(); i++) {
            DogClass dog = allDogs.get(i);
    
            JLabel dogLabel = new JLabel(dog.getName());
            dogLabel.setHorizontalAlignment(SwingConstants.CENTER);
    
            ImageIcon dogIcon = new ImageIcon("ProjectPBO/res/image/dog/" + dog.getImgPath());
            Image scaledDogImage = dogIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            dogLabel.setIcon(new ImageIcon(scaledDogImage));
            dogLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
            dogLabel.setHorizontalTextPosition(SwingConstants.CENTER);
    
            int startX = width - 150;
            int startY = 100 + i * 140;
            dogLabel.setBounds(startX, startY, 100, 100);
    
            racePanel.add(dogLabel);
    
            JLabel obstacleLabel = new JLabel();
            ImageIcon obstacleIcon = new ImageIcon(obstacle.getImgPath());
            Image scaledObstacleImage = obstacleIcon.getImage().getScaledInstance(obstacleWidth, 100, Image.SCALE_SMOOTH);
            obstacleLabel.setIcon(new ImageIcon(scaledObstacleImage));
            obstacleLabel.setBounds(obstacleX, startY, obstacleWidth, 100);
    
            racePanel.add(obstacleLabel);
    
            Thread dogThread = new Thread(new DogRaceTask(dogLabel, dog.getBaseSpeed(), dog.getName(), obstacleX, obstacleWidth, dog.getPrice(), dog.getSkill()));
            dogThread.start();
        }
    
        this.add(racePanel);
    
        new Timer(2000, e -> startSignal.countDown()).start();
    }
        
    
    private class DogRaceTask implements Runnable {
        private JLabel dogLabel;
        private int baseSpeed;
        private String dogName;
        private String dogSkill;
        private int obstacleX;
        private int obstacleWidth;
        private int originalSpeed;
        private int prize;
        private boolean isSlowed = false;
    
        public DogRaceTask(JLabel dogLabel, int baseSpeed, String dogName, int obstacleX, int obstacleWidth, int prize, String dogSkill) {
            this.dogLabel = dogLabel;
            this.baseSpeed = baseSpeed;
            this.dogName = dogName;
            this.dogSkill = dogSkill;
            this.obstacleX = obstacleX;
            this.obstacleWidth = obstacleWidth;
            this.prize = prize;
            this.originalSpeed = baseSpeed;
        }
    
        public void run() {
            try {
                startSignal.await();
    
                while (!raceFinished.get()) {
                    SwingUtilities.invokeLater(() -> {
                        int x = dogLabel.getX();
    
                        if (x >= obstacleX - obstacleWidth && x <= obstacleX + obstacleWidth) {
                            if (!isSlowed && !dogSkill.equals(obstacle.getName())) {
                                isSlowed = true;
                                baseSpeed -= (100 * obstacle.getReduce());
                            }
                        } else {
                            if (isSlowed) {
                                isSlowed = false;
                                baseSpeed = originalSpeed;
                            }
                        }
    
                        x -= baseSpeed;
    
                        if (x <= 0) {
                            x = 0;
                            if (raceFinished.compareAndSet(false, true)) {
                                JOptionPane.showMessageDialog(RaceFrame.this, dogName + " wins the race!\nPrize: " + prize);
                                new FinishFrame(dogLabel, dogName, prize, remainingBets);
                            }
                        }
    
                        dogLabel.setLocation(x, dogLabel.getY());
                    });
    
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }      
}
