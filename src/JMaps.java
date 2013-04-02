import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

class stateChanged implements ChangeListener {
    @Override
    public void stateChanged(ChangeEvent e) {
        JMaps.scrollPane1.getHorizontalScrollBar().setValue(JMaps.scrollPane2.getHorizontalScrollBar().getValue());
        JMaps.scrollPane1.getVerticalScrollBar().setValue(JMaps.scrollPane2.getVerticalScrollBar().getValue());
        JMaps.frame.repaint();
    }
}

public class JMaps {

    public static JScrollPane scrollPane1;
    public static JScrollPane scrollPane2;
    public static JFrame frame;

    public static void main(String args[])
    {
        try
        {
            String IMG_PATH = "result.png";
            ImageIcon imageIcon = new ImageIcon(IMG_PATH);
            frame = new JFrame("JMaps"){{
            setMinimumSize(new Dimension(500, 500));
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            }};

            JLayeredPane layeredPane = new JLayeredPane();
            layeredPane.setOpaque(true);
            layeredPane.setLocation(0,0);
            layeredPane.setSize(400,400);
            layeredPane.setBackground(Color.CYAN);
            frame.add(layeredPane);

            scrollPane1 = new JScrollPane();
            scrollPane1.setLocation(10,10);
            scrollPane1.setSize(new Dimension(400, 400));
            scrollPane1.getVerticalScrollBar().setUnitIncrement(60);
            scrollPane1.getHorizontalScrollBar().setUnitIncrement(60);
            scrollPane1.setOpaque(true);
            scrollPane1.setViewportView(new JLabel(imageIcon));



            scrollPane2 = new JScrollPane();
            scrollPane2.setLocation(5,5);
            scrollPane2.setSize(300, 300);
            scrollPane2.getVerticalScrollBar().setUnitIncrement(60);
            scrollPane2.getHorizontalScrollBar().setUnitIncrement(60);
            scrollPane2.getViewport().setOpaque(false);
            BufferedImage bufferedImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);

            Graphics g2 = bufferedImage.getGraphics();
            g2.setColor(new Color(0,0,0, 0.1f));
            g2.fillRect(0,0,600,600);

            JLabel label = new JLabel(new ImageIcon(bufferedImage));
            scrollPane2.getViewport().setBackground(new Color(0,0,0,0.1f));
            scrollPane2.getViewport().setOpaque(true);
            scrollPane2.setViewportView(label);
            scrollPane2.setBackground(new Color(0,0,0,0.1f));
            scrollPane2.getViewport().addChangeListener(new stateChanged());

            layeredPane.add(scrollPane1,0);
            layeredPane.add(scrollPane2,0);

            //frame.add(layeredPane1);
           // frame.add(layeredPane2);
//            panel.add(scrollPane2);

            frame.setVisible(true);
        } catch (Exception e) {
          System.out.println(e.toString());
        }
    }

    public void changeScrollBar(Point e)
    {
        //scrollPane1.getHorizontalScrollBar().setValue();
    }
}