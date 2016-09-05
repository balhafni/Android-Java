/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import de.yadrone.base.IARDrone;
import de.yadrone.base.command.VideoChannel;
import de.yadrone.base.video.ImageListener;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Juhyun
 */
public class VideoStream extends JFrame {

    IARDrone drone;
    BufferedImage image = null;

    public VideoStream(final IARDrone drone) throws IOException {
        this.drone = drone;
        setSize(640, 360);
        setLocation(1070, 220);
        setVisible(true);

        drone.getVideoManager().addImageListener(new ImageListener() {
            @Override
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

        // Change the camera by a mouse click
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                drone.getCommandManager().setVideoChannel(VideoChannel.NEXT);
            }
        });

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(image!=null){
            g.drawImage(image, 0, 0,image.getWidth(),image.getHeight(), null);
        }
        
    }

    public BufferedImage picture() throws IOException {
        Calendar cal = new GregorianCalendar();
        String date = (cal.get(GregorianCalendar.MONTH) + 1) + "-" + cal.get(GregorianCalendar.DAY_OF_MONTH) + "-" + cal.get(GregorianCalendar.YEAR);
        String time = (cal.get(GregorianCalendar.HOUR)) + "-" + (cal.get(GregorianCalendar.MINUTE) + "-" + (cal.get(GregorianCalendar.SECOND)));
       if (image!=null){
        return image;
       }
       return null;
    }
}
