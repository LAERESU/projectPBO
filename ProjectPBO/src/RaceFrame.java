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
        this.getContentPane().setBackground(Color.GREEN);
        this.remainingBets = coins;

        this.allDogs = allDogs;
        this.startSignal = new CountDownLatch(1);
        this.obstacle = obstacle;

        initializeRace();
        this.setVisible(true);
    }

    private void initializeRace() {
        JPanel racePanel = new JPanel();
        racePanel.setLayout(null);
        racePanel.setBounds(0, 0, width, height);
        racePanel.setBackground(Color.GREEN);
    
        int obstacleX = (int) (width * 0.3); // Lokasi obstacle di jalur balapan
        int obstacleWidth = obstacle.getLength(); // Panjang area hambatan dari obstacle.getLength()
    
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
            int startY = 150 + i * 120;
            dogLabel.setBounds(startX, startY, 100, 100);
    
            racePanel.add(dogLabel);
    
            // Membuat area hambatan sebagai JPanel
            JPanel obstaclePanel = new JPanel();
            obstaclePanel.setBackground(Color.ORANGE);
            obstaclePanel.setBounds(obstacleX, startY, obstacleWidth, 100); // Lebar hambatan sesuai panjang obstacle.getLength()
    
            // Membuat label untuk nama obstacle dan menambahkannya ke dalam area hambatan
            JLabel obstacleLabel = new JLabel(obstacle.getName());
            obstacleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            obstacleLabel.setForeground(Color.BLACK);
            obstacleLabel.setBounds(0, 35, obstacleWidth, 30); // Posisi teks di dalam area hambatan
    
            obstaclePanel.setLayout(null);
            obstaclePanel.add(obstacleLabel);
    
            racePanel.add(obstaclePanel);
    
            Thread dogThread = new Thread(new DogRaceTask(dogLabel, dog.getBaseSpeed(), dog.getName(), obstacleX, obstacleWidth, dog.getPrice()));
            dogThread.start();
        }
    
        this.add(racePanel);
    
        new Timer(2000, e -> startSignal.countDown()).start();
    }
    
    private class DogRaceTask implements Runnable {
        private JLabel dogLabel;
        private int baseSpeed;
        private String dogName;
        private int obstacleX;
        private int obstacleWidth;
        private int originalSpeed;
        private int prize;
        private boolean isSlowed = false;
    
        public DogRaceTask(JLabel dogLabel, int baseSpeed, String dogName, int obstacleX, int obstacleWidth, int prize) {
            this.dogLabel = dogLabel;
            this.baseSpeed = baseSpeed;
            this.dogName = dogName;
            this.obstacleX = obstacleX;
            this.obstacleWidth = obstacleWidth;
            this.prize = prize;
            this.originalSpeed = baseSpeed;
        }
    
        @Override
        public void run() {
            try {
                startSignal.await();
    
                while (!raceFinished.get()) {
                    SwingUtilities.invokeLater(() -> {
                        int x = dogLabel.getX();
    
                        if (x >= obstacleX - obstacleWidth && x <= obstacleX + obstacleWidth) {
                            if (!isSlowed) {
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
