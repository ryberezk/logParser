
import javax.swing.JDialog;
import javax.swing.JFrame;

public class LogParser extends JFrame {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                JDialog.setDefaultLookAndFeelDecorated(true);
                new FrameApplication();
            }
        });
    }
}