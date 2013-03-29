import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class JMaps {
    public static void main(String args[])
    {
        try
        {
            String IMG_PATH = "result.png";
            JFrame frame = new JFrame("JMaps"){{
            setMinimumSize(new Dimension(900, 600));
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            }};

            BufferedImage img = ImageIO.read(new File(IMG_PATH));
            ScrollPane scrollPane = new ScrollPane();

            frame.getContentPane().add(scrollPane);

            ImageIcon imgIcon = new ImageIcon(img);
            JLabel imgLabel = new JLabel(imgIcon);
            scrollPane.add(imgLabel);

            MouseListener ml = new MouseAdapter ()
            {
                public void mouseClicked (MouseEvent event)
                {
                   System.out.println(event.toString());
                }
            };
            imgLabel.addMouseListener(ml);

            frame.pack();
            frame.setVisible(true);
        } catch (Exception e) {
          System.out.println(e.toString());
        }
    }
}