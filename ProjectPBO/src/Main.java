import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        DbConnect.checkConnection();
        SwingUtilities.invokeLater(() -> {
            new StartFrame(0);
        });
    }
}
