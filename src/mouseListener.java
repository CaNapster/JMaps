import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created with IntelliJ IDEA.
 * User: napster
 * Date: 4/3/13
 * Time: 3:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class mouseListener implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e)
    {
        System.out.println(e.toString());
        JMaps.getLabelGraphics().setColor(Color.RED);
        JMaps.getLabelGraphics().fillOval(e.getX(),e.getY(),10,10);
    }
    @Override
    public void mouseReleased(MouseEvent e)
    {

    }
    @Override
    public void mouseExited(MouseEvent e)
    {

    }
    @Override
    public void mousePressed(MouseEvent e)
    {

    }
    @Override
    public void mouseEntered(MouseEvent e)
    {

    }
}
