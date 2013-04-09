import java.awt.*;
import java.awt.event.KeyEvent;

public class MyKeyDispatcher implements KeyEventDispatcher {
    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        switch (e.getKeyCode())
        {
            case 37:
                GUI.scrollBothPaneH("dec", 30);
                break;
            case 39:
                GUI.scrollBothPaneH("inc", 30);
                break;
            case 38:
                GUI.scrollBothPaneV("inc", 30);
                break;
            case 40:
                GUI.scrollBothPaneV("dec", 30);
                break;
        }
        return false;
    }
}
