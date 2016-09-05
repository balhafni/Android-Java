
import de.yadrone.base.ARDrone;
import de.yadrone.base.command.VideoChannel;
import de.yadrone.base.exception.ARDroneException;
import de.yadrone.base.exception.IExceptionListener;
import de.yadrone.base.video.ImageListener;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javafx.stage.WindowEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author bashar.alhafni
 */
public class VideoManager extends JFrame {

    private BufferedImage image = null;

     public VideoManager(final ARDrone drone) {
        super("Live Streaming");
        setSize(640, 360);
        setLocationRelativeTo(null);
        setVisible(true);
        drone.getVideoManager().addImageListener(new ImageListener() {
            public void imageUpdated(BufferedImage newImage) {
                image = newImage;
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        repaint();
                        
                    }
                });
            }
        });

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                drone.getCommandManager().setVideoChannel(VideoChannel.NEXT);
            }
        });
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                drone.stop();
                System.exit(0);
            }
        });
        drone.addExceptionListener(new IExceptionListener() {
            public void exeptionOccurred(ARDroneException exc) {
                exc.printStackTrace();
            }
        });
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (image != null) {
            g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        }
    }
    
        public BufferedImage picture() throws IOException {
        Calendar cal = new GregorianCalendar();
        String date = (cal.get(GregorianCalendar.MONTH) + 1) + "-" + cal.get(GregorianCalendar.DAY_OF_MONTH) + "-" + cal.get(GregorianCalendar.YEAR);
        String time = (cal.get(GregorianCalendar.HOUR)) + "-" + (cal.get(GregorianCalendar.MINUTE) + "-" + (cal.get(GregorianCalendar.SECOND)));
        return image;
   
    }
}
