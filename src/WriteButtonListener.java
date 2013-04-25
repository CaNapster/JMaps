import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: napster
 * Date: 4/25/13
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class WriteButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            ImageIO.write(GUI.getImage(100), "PNG", new File("Optimized.png"));
        } catch (IOException ex) {System.out.println(ex.toString());}
    }
}
