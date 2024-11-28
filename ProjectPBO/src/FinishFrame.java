import javax.swing.*;
import java.awt.*;

public class FinishFrame extends JFrame{
    int width = 1440;
    int height = 900;

    public FinishFrame(){
        super("Finish");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.getContentPane().setBackground(Color.BLUE);


        this.setVisible(true);
    }
}
