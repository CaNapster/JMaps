import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class JMaps {
    public static JLayeredPane layeredPane;
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
            setLayout(new MigLayout("nogrid, flowy, btt"));
            }};

            layeredPane = new JLayeredPane();
            layeredPane.setOpaque(true);
            layeredPane.setLocation(0,0);
            layeredPane.setSize(frame.getSize());
            layeredPane.addComponentListener(new componentListener());
            frame.add(layeredPane, "h 80%, w 100%");

            scrollPane1 = new JScrollPane();
            scrollPane1.setLocation(0,0);
            scrollPane1.getVerticalScrollBar().setUnitIncrement(60);
            scrollPane1.getHorizontalScrollBar().setUnitIncrement(60);
            scrollPane1.setOpaque(true);
            scrollPane1.setViewportView(new JLabel(imageIcon));

            scrollPane2 = new JScrollPane();
            scrollPane2.setLocation(0,0);
            scrollPane2.getVerticalScrollBar().setUnitIncrement(60);
            scrollPane2.getHorizontalScrollBar().setUnitIncrement(60);
            scrollPane2.getViewport().setOpaque(false);
            BufferedImage bufferedImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);

            JLabel label = new JLabel(new ImageIcon(bufferedImage));
            scrollPane2.getViewport().setBackground(new Color(0,0,0,0.0f));
            scrollPane2.setBackground(new Color(0,0,0,0.0f));
            scrollPane2.getViewport().setOpaque(true);
            scrollPane2.setViewportView(label);
            scrollPane2.getViewport().addChangeListener(new scrollStateChanged());

            layeredPane.add(scrollPane1,0);
            layeredPane.add(scrollPane2,0);

            frame.setVisible(true);
        } catch (Exception e) {
          System.out.println(e.toString());
        }
    }
}