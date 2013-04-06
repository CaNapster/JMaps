import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: napster
 * Date: 4/3/13
 * Time: 2:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class GUI {
    private static JLayeredPane layeredPane;
    private static JScrollPane scrollPane1;
    private static JScrollPane scrollPane2;
    private static JFrame frame;
    private static JLabel label;
    private static BufferedImage bufferedImage;
    private static ImageIcon imageIcon;
    private String IMG_PATH = "result.png";

    public GUI(String frameName){
        frame = new JFrame(frameName){{
            setMinimumSize(new Dimension(500, 500));
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setLayout(new MigLayout("nogrid, flowy, btt"));
        }};
        imageIcon = new ImageIcon(IMG_PATH);

        layeredPane = new JLayeredPane();
        layeredPane.setOpaque(true);
        layeredPane.setLocation(0, 0);
        layeredPane.setSize(frame.getSize());
        layeredPane.addComponentListener(new componentListener());
        frame.addKeyListener(new keyListener());
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
        bufferedImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);

        label = new JLabel(new ImageIcon(bufferedImage));
        scrollPane2.getViewport().setBackground(new Color(0,0,0,0.0f));
        scrollPane2.setBackground(new Color(0,0,0,0.0f));
        scrollPane2.getViewport().setOpaque(true);
        scrollPane2.setViewportView(label);
        scrollPane2.getViewport().addChangeListener(new scrollStateChanged());
        label.addMouseListener(new mouseListener());
        label.setBackground(new Color(0,0,0,0.0f));

        layeredPane.add(scrollPane1,0);
        layeredPane.add(scrollPane2,0);

        frame.setVisible(true);
    }
    public static void repaintMainFrame(){
        GUI.frame.repaint();
    }
    public static void repaintScrollPane2() {
        scrollPane2.repaint();
    }
    public static void repaintLabel(){
        GUI.label.repaint();
    }
    public static Graphics2D get2DGraphicsBufferedImage(){
        return GUI.bufferedImage.createGraphics();
    }
    public static void scrollBothPaneH(String str, int delta) {
        if (str =="inc") {
            scrollPane1.getHorizontalScrollBar().setValue(scrollPane1.getHorizontalScrollBar().getValue()+delta);
            scrollPane2.getHorizontalScrollBar().setValue(scrollPane2.getHorizontalScrollBar().getValue()+delta);
        } else
        if (str =="dec") {
            scrollPane1.getHorizontalScrollBar().setValue(scrollPane1.getHorizontalScrollBar().getValue()-delta);
            scrollPane2.getHorizontalScrollBar().setValue(scrollPane2.getHorizontalScrollBar().getValue()-delta);
        }
    }
    public static void scrollBothPaneV(String str, int delta) {
        if (str =="inc") {
            scrollPane1.getVerticalScrollBar().setValue(scrollPane1.getVerticalScrollBar().getValue()-delta);
            scrollPane2.getVerticalScrollBar().setValue(scrollPane2.getVerticalScrollBar().getValue()-delta);
        } else
        if (str =="dec") {
            scrollPane1.getVerticalScrollBar().setValue(scrollPane1.getVerticalScrollBar().getValue()+delta);
            scrollPane2.getVerticalScrollBar().setValue(scrollPane2.getVerticalScrollBar().getValue()+delta);
        }
    }
    public static void setScrollPane1Size(Dimension dimension) {
        scrollPane1.setSize(dimension);
    }
    public static void setScrollPane2Size(Dimension dimension) {
        scrollPane2.setSize(dimension);
    }
    public static Dimension getLayeredPaneSize() {
        return layeredPane.getSize();
    }
    public static void scrollPane1SetValueHScrollBar(Integer value) {
        scrollPane1.getHorizontalScrollBar().setValue(value);
    }
    public static void scrollPane2SetValueHScrollBar(Integer value) {
        scrollPane2.getHorizontalScrollBar().setValue(value);
    }
    public static void scrollPane1SetValueVScrollBar(Integer value) {
        scrollPane1.getVerticalScrollBar().setValue(value);
    }
    public static void scrollPane2SetValueVScrollBar(Integer value) {
        scrollPane2.getVerticalScrollBar().setValue(value);
    }
    public static Integer scrollPane1getValueHScrollBar(){
        return scrollPane1.getHorizontalScrollBar().getValue();
    }
    public static Integer scrollPane2getValueHScrollBar(){
        return scrollPane2.getHorizontalScrollBar().getValue();
    }
    public static Integer scrollPane1getValueVScrollBar(){
        return scrollPane1.getVerticalScrollBar().getValue();
    }
    public static Integer scrollPane2getValueVScrollBar(){
        return scrollPane2.getVerticalScrollBar().getValue();
    }
}
