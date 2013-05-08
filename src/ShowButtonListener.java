import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

/**
 * Created with IntelliJ IDEA.
 * User: napster
 * Date: 4/24/13
 * Time: 2:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShowButtonListener implements ActionListener {
    private static JFrame picFrame = null;
    private static JScrollPane jScrollPane;
    private static JLabel label;
    private static JSlider jSlider;
    private static BufferedImage bufferedImage;

    public static void setScrolls() {
        jScrollPane.getVerticalScrollBar().setUnitIncrement(bufferedImage.getHeight() / 10);
        jScrollPane.getHorizontalScrollBar().setUnitIncrement(bufferedImage.getWidth() / 10);
    }

    public static int getJSliderValue() {
        return ShowButtonListener.jSlider.getValue();
    }

    public void actionPerformed(ActionEvent e) {
        if (picFrame == null || !picFrame.isVisible()) {
            picFrame = new JFrame("JMaps-Viewer");
            picFrame.setLayout(new MigLayout("nogrid, flowy, ttb"));
            picFrame.setSize(600, 600);
            picFrame.setVisible(true);
            picFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            jSlider = new JSlider();
            jSlider.setVisible(true);
            jSlider.setMinimum(30);
            jSlider.setMaximum(100);
            jSlider.setMajorTickSpacing(10);
            Hashtable labelTable = new Hashtable();
            labelTable.put(new Integer(30), new JLabel("30%"));
            labelTable.put(new Integer(100), new JLabel("100%"));
            jSlider.setLabelTable(labelTable);
            jSlider.setPaintTicks(true);
            jSlider.setPaintLabels(true);
            jSlider.setSnapToTicks(true);
            jSlider.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    double x = (jScrollPane.getHorizontalScrollBar().getValue() + jScrollPane.getHorizontalScrollBar().getWidth() / 2.0) / bufferedImage.getWidth();
                    double y = (jScrollPane.getVerticalScrollBar().getValue() + jScrollPane.getVerticalScrollBar().getHeight() / 2.0) / bufferedImage.getHeight();
                    bufferedImage = GUI.getImage(getJSliderValue());
                    setScrolls();
                    jScrollPane.setViewportView(new JLabel(new ImageIcon(bufferedImage)));
                    jScrollPane.getHorizontalScrollBar().setValue((int) Math.round(bufferedImage.getWidth() * x - jScrollPane.getHorizontalScrollBar().getWidth() / 2.0));
                    jScrollPane.getVerticalScrollBar().setValue((int) Math.round(bufferedImage.getHeight() * y - jScrollPane.getVerticalScrollBar().getHeight() / 2.0));
                }
            });

            jScrollPane = new JScrollPane();
            jScrollPane.setVisible(true);
            bufferedImage = GUI.getImage(getJSliderValue());
            label = new JLabel(new ImageIcon(bufferedImage));
            jScrollPane.setViewportView(label);
            setScrolls();

            picFrame.add(jSlider, "h 10%, w 100%");
            picFrame.add(jScrollPane, "h 90%, w 100%");
        }
    }
}
