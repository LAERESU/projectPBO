import javax.swing.*;
import java.awt.*;

public class RaceFrame extends JFrame {
    int width = 1440;
    int height = 900;

    public RaceFrame(){
        super("Race");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.getContentPane().setBackground(Color.GREEN);


        this.setVisible(true);
    }
}
