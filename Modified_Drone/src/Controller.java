
import com.sun.prism.j2d.J2DPipeline;
import de.yadrone.base.ARDrone;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author bashar.alhafni
 */
public class Controller extends JFrame implements KeyListener {
    
    JTextArea area;
    JScrollPane scroll;
    
    public Controller(String t) {
        super(t);
        this.setSize(600, 600);
        area = new JTextArea(10, 10);
        scroll = new JScrollPane(area);
        scroll.setBounds(10, 60, 780, 500);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        area.setEditable(false);
        area.append("You are able to control the drone now, please read the following instructions:\n");
        area.append("Taking off: Press Enter\n");
        
        area.append("Spinning Right: Press A\n");
        area.append("Spinning Left: Press S\n");
        area.append("Going Forward: Press W\n");
        area.append("Going Backward: Press S\n");
        area.append("Going Up: Press the Up Arrow\n");
        area.append("Going Down: Press the Down Arrow\n");
        area.append("Going Right: Press the Right Arrow\n");
        area.append("Going Left: Press the Left Arrow\n");
        area.append("Landing: Press The Space bar\n");
        area.append("Enjoy!!\n\n\n");
        this.add(scroll);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        area.addKeyListener(this);
    }
    
    public void keyTyped(KeyEvent e) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_ENTER:
                GUI.drone.getCommandManager().takeOff();
                area.append("The Drone is Taking off!\n\n");
                break;
            case KeyEvent.VK_SPACE:
                area.append("The Drone is Landing\n\n");
                GUI.drone.getCommandManager().landing();
                break;
            case KeyEvent.VK_UP:
                area.append("The Drone is Going Up\n\n");
                GUI.drone.getCommandManager().up(20);
                break;
            case KeyEvent.VK_DOWN:
                area.append("The Drone is Going Down\n\n");
                GUI.drone.getCommandManager().down(20);
                break;
            case KeyEvent.VK_RIGHT:
                area.append("The Drone is Going Right\n\n");
                GUI.drone.getCommandManager().goRight(20);
                break;
            case KeyEvent.VK_LEFT:
                area.append("The Drone is Going Left\n\n");
                GUI.drone.getCommandManager().goLeft(20);
                break;
            case 65: //A
                area.append("The Drone is Spinning to the Right\n\n");
                GUI.drone.getCommandManager().spinRight(35);
                break;
            case 68://D
                area.append("The Drone is Spinning to the Left\n\n");
                GUI.drone.getCommandManager().spinLeft(35);
                break;
            case 87://W
                area.append("The Drone is Going Forward\n\n");
                GUI.drone.getCommandManager().forward(20);
                break;
            case 53://S
                area.append("The Drone is Going Backward\n\n");
                GUI.drone.getCommandManager().backward(20);
                break;
            
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        GUI.drone.getCommandManager().hover();
    }
    
}
