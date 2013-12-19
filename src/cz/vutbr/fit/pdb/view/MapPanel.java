/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb.view;

import cz.vutbr.fit.pdb.containers.SpatialContainer;
import cz.vutbr.fit.pdb.model.SoilObject;
import cz.vutbr.fit.pdb.model.SpatialObject;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
import oracle.spatial.geometry.JGeometry;


/**
 * MapPanel class.
 * Displays the panel showing map.
 * @author casey
 */
public class MapPanel extends JPanel{

    private SpatialContainer sc;
    private AffineTransform at = new AffineTransform();
    private AffineTransform ate;
    private Rectangle most_big_object = new Rectangle(0, 0);

    private Shape editBox;

    /**
     * Initialization function for MapPanel class.
     */
    public MapPanel(){
        super();

    }

    /**
     * Initialization function for MapPanel class.
     * @param sc SpatialContainer object
     */
    public MapPanel(SpatialContainer sc){
        super();
        this.sc = sc;
        this.most_big_object = sc.getBiggestObjectBoundaries();
    }

    /**
     * MouseInputListener register.
     * @param ml MouseInputListener object
     */
    public void registerListener(MouseInputListener ml){
        this.addMouseListener(ml);
        this.addMouseMotionListener(ml);

    }

    /**
     * EditShape setter.
     * @param r shape
     */
    public void setEditShape(Shape r){
        this.editBox = r;
    }

    /**
     * SpatialContainer register.
     * @param sc SpatialContainer object
     */
    public void registerSpatialContainer(SpatialContainer sc){
        this.sc = sc;
        this.most_big_object = sc.getBiggestObjectBoundaries();
    }

    /**
     * SpatialContainer getter.
     * @return
     */
    public SpatialContainer getSpatialContainer(){
        return this.sc;
    }

    /**
     * AffineTransform getter.
     * @return
     */
    public AffineTransform getAffineTransform(){
        return this.at;
    }

    /**
     * Painting function.
     * @param g Graphics object
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        this.setBackground(Color.WHITE);

        this.calculateResize();
        if(sc != null){
            drawMap(g2);
        }
    }

    /**
     * Function calculating the resize of objects.
     */
    public void calculateResize() {
        Dimension screen = this.getSize();
        this.at = new AffineTransform();
        float a = (float) (screen.width - 50) / most_big_object.width;
        float b = (float) (screen.height - 50) / most_big_object.height;
        float translate_x = Math.abs((screen.width) - most_big_object.width)/2;
        float translate_y = Math.abs((screen.height) - most_big_object.height)/2;
        float c = (a < b) ? a : b;

        at.translate(translate_x, 20);
        at.scale(c, c);
    }

    private void drawMap(Graphics2D g2){
        int layers = sc.countLayers();

        for(int a = 1; a <= layers; a++){
            drawMapLayer(g2, a);

        }

        if(editBox != null){
            g2.setStroke(new BasicStroke(3));
            g2.setColor(new Color(135, 249, 255));
            g2.draw(at.createTransformedShape(editBox));
            //g2.draw(editBox);
        }
    }

    private void drawMapLayer(Graphics2D g2, int layer){
         //SpatialObject list[] = (SpatialObject[])sc.getGeometries();
         ArrayList<SpatialObject> actLayer = sc.getGeometries(layer);

         for(SpatialObject o : actLayer){
                g2.setStroke(new BasicStroke(1));
                boolean to_be_filled = true;
                boolean to_draw_black_border = true;
                switch (layer) {
                    case 1: // soil
                        switch (((SoilObject)o).getSoilType()) {
                            case 1:
                                g2.setColor(new Color(123, 69, 33));
                                break;
                            case 2:
                                g2.setColor(new Color(175, 105, 59));
                                break;
                            case 3:
                                g2.setColor(new Color(255, 207, 13));
                                break;
                        }
                        break;
                    case 2: // water
                        g2.setColor(new Color(128, 179, 255));
                        break;
                    case 3: // paths
                        g2.setColor(new Color(80, 68, 22));
                        break;
                    case 4: // beds
                        g2.setColor(new Color(171, 200, 55));
                        break;
                    case 5: // fences
                        g2.setStroke(new BasicStroke(3));
                        g2.setColor(new Color(193, 34, 0));
                        to_be_filled = false;
                        to_draw_black_border = false;
                        break;
                    case 6: // signs
                        g2.setColor(new Color(193, 34, 0));
                        break;
                }
                JGeometry geo = o.getGeometry();
                if (o.isSelected()) {
                    g2.setStroke(new BasicStroke(6));

                } else if (o.isHovered()) {
                    g2.setColor(g2.getColor().darker());
                }

                if (geo.isPoint()) {
                    Point2D points = geo.getJavaPoint();
                    Shape s = at.createTransformedShape(new Ellipse2D.Double((int) points.getX() - 5, (int) points.getY() - 5, 10, 10));
                    g2.fill(s);
                    g2.setColor(Color.BLACK);
                    g2.draw(s);
                }else{

                    if (to_be_filled) {
                        g2.fill(geo.createShape(at));
                    }
                    if (to_draw_black_border) {
                        g2.setColor(Color.BLACK);
                    }
                    g2.draw(geo.createShape(at));
                }

         }
    }
}
