import sun.org.mozilla.javascript.internal.ast.IfStatement;

import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Created with IntelliJ IDEA.
 * User: napster
 * Date: 4/3/13
 * Time: 2:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class componentListener implements ComponentListener {
    @Override
    public void componentResized(ComponentEvent e)
    {
        JMaps.scrollPane1.setSize(JMaps.layeredPane.getSize());
        JMaps.scrollPane2.setSize(JMaps.layeredPane.getSize());
    }
    @Override
    public void componentMoved(ComponentEvent e)
    {

    }
    @Override
    public void componentShown(ComponentEvent e)
    {

    }
    @Override
    public void componentHidden(ComponentEvent e)
    {

    }
}
