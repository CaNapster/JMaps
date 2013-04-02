import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
* Created with IntelliJ IDEA.
* User: napster
* Date: 4/3/13
* Time: 12:19 AM
* To change this template use File | Settings | File Templates.
*/
public class scrollStateChanged implements ChangeListener {
    @Override
    public void stateChanged(ChangeEvent e) {
            JMaps.scrollPane1.getHorizontalScrollBar().setValue(JMaps.scrollPane2.getHorizontalScrollBar().getValue());
    JMaps.scrollPane1.getVerticalScrollBar().setValue(JMaps.scrollPane2.getVerticalScrollBar().getValue());
    JMaps.frame.repaint();
    }
}