import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class mouseListener implements MouseListener{
    private static int i=0;
    private static RoadGraph vertex1;
    @Override
    public void mouseClicked(MouseEvent e){
        if (GUI.getStateOfHousesCheckBox()){
            CustomPainting.paintCircle(GUI.get2DGraphicsBufferedImage(), Color.orange, e.getPoint(), 10);
            JMaps.getHousesList().add(e.getPoint());
            GUI.refreshControls();
        }
    }
    @Override
    public void mouseReleased(MouseEvent e){
    }
    @Override
    public void mouseExited(MouseEvent e){
    }
    @Override
    public void mousePressed(MouseEvent e){
    }
    @Override
    public void mouseEntered(MouseEvent e){

    }
}