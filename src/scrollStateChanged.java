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
        GUI.scrollPane1SetValueHScrollBar(GUI.scrollPane2getValueHScrollBar());
        GUI.scrollPane1SetValueVScrollBar(GUI.scrollPane2getValueVScrollBar());
        GUI.repaintMainFrame();
    }
    static public void stateChange() {
        GUI.scrollPane1SetValueHScrollBar(GUI.scrollPane2getValueHScrollBar());
        GUI.scrollPane1SetValueVScrollBar(GUI.scrollPane2getValueVScrollBar());
        GUI.repaintMainFrame();
    }
}