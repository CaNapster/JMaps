import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
 * Created with IntelliJ IDEA.
 * User: napster
 * Date: 4/3/13
 * Time: 2:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class keyListener implements KeyListener {
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode())
        {
            case 37:
               JMaps.scrollBothPaneH("dec", 30);
                break;
            case 39:
                JMaps.scrollBothPaneH("inc", 30);
                break;
            case 38:
                JMaps.scrollBothPaneV("inc", 30);
                break;
            case 40:
                JMaps.scrollBothPaneV("dec", 30);
                break;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
}
