import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import org.openstreetmap.gui.jmapviewer.*;
import org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent;
import org.openstreetmap.gui.jmapviewer.interfaces.JMapViewerEventListener;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;

/**
 *
 * Demonstrates the usage of {@link JMapViewer}
 *
 * @author Jan Peter Stotz
 *
 */
public class JMaps extends JFrame implements JMapViewerEventListener  {

    private static final long serialVersionUID = 1L;
    private JMapViewer map = null;
    private JLabel zoomLabel=null;
    private JLabel zoomValue=null;
    private JLabel mperpLabelName=null;
    private JLabel mperpLabelValue = null;
    private JLabel tempLabel = null;

    private Double x,y;

    public JMaps() {
        super("First map app");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        map = new JMapViewer();
        map.addJMVListener(this);

        // final JMapViewer map = new JMapViewer(new MemoryTileCache(),4);
        // map.setTileLoader(new OsmFileCacheTileLoader(map));
        // new DefaultMapController(map);

        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        JPanel panelTop = new JPanel();
        JPanel panelBottom = new JPanel();

        mperpLabelName=new JLabel("Meters/Pixels: ");
        mperpLabelValue=new JLabel(String.format("%s",map.getMeterPerPixel()));

        zoomLabel=new JLabel("Zoom: ");
        zoomValue=new JLabel(String.format("%s", map.getZoom()));

        tempLabel=new JLabel("Nothing yet");

        add(panel, BorderLayout.NORTH);
        panel.setLayout(new BorderLayout());
        panel.add(panelTop, BorderLayout.NORTH);
        add(panelBottom, BorderLayout.SOUTH);

        map.setTileSource(new OsmTileSource.Mapnik());
        map.setTileLoader(new OsmTileLoader(map));
        map.getMapMarkersVisible();
        map.getZoomContolsVisible();

        panelTop.add(zoomLabel);
        panelTop.add(zoomValue);
        panelTop.add(mperpLabelName);
        panelTop.add(mperpLabelValue);

        panelBottom.add(tempLabel);

        add(map, BorderLayout.CENTER);

//        map.addMapMarker(new MapMarkerDot(49.814284999, 8.642065999));
//        map.addMapMarker(new MapMarkerDot(49.91, 8.24));
//        map.addMapMarker(new MapMarkerDot(49.71, 8.64));
//        map.addMapMarker(new MapMarkerDot(48.71, -1));
//        map.addMapMarker(new MapMarkerDot(49.8588, 8.643));

        // map.setDisplayPositionByLatLon(49.807, 8.6, 11);
        // map.setTileGridVisible(true);

        map.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    map.getMapPosition(40, 10);
                    // map.setDisplayPositionByLatLon(x, y, map.getZoom());
                    map.getAttribution().handleAttribution(e.getPoint(), true);
                }
            }
        });
        map.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                boolean cursorHand = map.getAttribution().handleAttributionCursor(e.getPoint());
                if (cursorHand) {
                    map.setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    map.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
    }

    public static void main(String[] args) {
        new JMaps().setVisible(true);
    }

    private void updateZoomParameters() {
        if (mperpLabelValue!=null)
            mperpLabelValue.setText(String.format("%s",map.getMeterPerPixel()));
        if (zoomValue!=null)
            zoomValue.setText(String.format("%s", map.getZoom()));
    }
    @Override
    public void processCommand(JMVCommandEvent command) {
        if (command.getCommand().equals(JMVCommandEvent.COMMAND.ZOOM) ||
                command.getCommand().equals(JMVCommandEvent.COMMAND.MOVE)) {
            updateZoomParameters();
        }
    }
}