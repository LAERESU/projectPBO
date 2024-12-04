import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class RaceFrame extends JFrame {
    private int width = 1440;
    private int height = 900;
    private List<DogClass> allDogs;
    private AtomicBoolean raceFinished = new AtomicBoolean(false);

    public RaceFrame(DogClass selectedDog, List<DogClass> allDogs) {
        super("Race");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.setLayout(null);
        this.getContentPane().setBackground(Color.GREEN);

        this.allDogs = allDogs;

        initializeRace();
        this.setVisible(true);
    }

    private void initializeRace() {
        JPanel racePanel = new JPanel();
        racePanel.setLayout(null);
        racePanel.setBounds(0, 0, width, height);
        racePanel.setBackground(Color.GREEN);

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

            startDogRace(dogLabel, dog.getBaseSpeed(), dog.getName());
        }

        this.add(racePanel);
    }

    private void startDogRace(JLabel dogLabel, int baseSpeed, String dogName) {
        Timer timer = new Timer(50, e -> {
            if (raceFinished.get()) {
                ((Timer) e.getSource()).stop();
                return;
            }

            int x = dogLabel.getX();
            x -= baseSpeed;
            if (x <= 0) {
                x = 0;
                if (raceFinished.compareAndSet(false, true)) {
                    JOptionPane.showMessageDialog(this, dogName + " wins the race!");
                }
                ((Timer) e.getSource()).stop();
            }
            dogLabel.setLocation(x, dogLabel.getY());
        });

        timer.start();
    }
}