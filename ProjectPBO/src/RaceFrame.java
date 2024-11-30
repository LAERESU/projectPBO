import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class RaceFrame extends JFrame {
    int width = 1440;
    int height = 900;
    DogClass selectedDog;
    List<Arena> arenas;

    public RaceFrame(DogClass selectedDog) {
        super("Race");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.getContentPane().setBackground(Color.GREEN);
        this.selectedDog = selectedDog;

        try {
            arenas = DbConnect.getRandomArenas(1); // Memilih 1 arena acak untuk balapan
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.setVisible(true);
    }
}
