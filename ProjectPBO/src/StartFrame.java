import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartFrame extends JFrame {
    int width = 1440;
    int height = 900;

    public StartFrame(){
        super("Start");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.getContentPane().setBackground(Color.YELLOW);

        this.setLayout(null);

        JPanel startPanel = new JPanel(){
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int arcSize = 20;
                g2d.setColor(Color.ORANGE);
                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arcSize, arcSize));
            }
        };
        startPanel.setBackground(Color.YELLOW);
        startPanel.setBounds(550, 350, 500, 300);
        startPanel.setOpaque(false); 

        startPanel.setLayout(null);

        JButton startButton = new JButton("START");
        startButton.setBounds(200, 80, 100, 40);
        startButton.setUI(new RoundButtonUI()); 
        startButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        startButton.setOpaque(false); 
        
        JTextField betField = new JTextField();
        betField.setBounds(170, 160, 160, 30);
        betField.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String betAmount = betField.getText(); 
                if (!betAmount.isEmpty()) {
                    System.out.println("Bet Amount: " + betAmount); 
                    new RaceFrame();
                    dispose();  
                } else {
                    System.out.println("No Bett");
                }
            }
        });

        startPanel.add(startButton); 
        startPanel.add(betField);  

        this.add(startPanel); 

        this.setVisible(true); 
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
