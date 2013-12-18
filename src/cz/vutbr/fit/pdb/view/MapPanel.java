/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb.view;

import cz.vutbr.fit.pdb.containers.SpatialContainer;
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
 *
 * @author casey
 */
public class MapPanel extends JPanel{
    
    private SpatialContainer sc;
    private AffineTransform at = new AffineTransform();
    private Rectangle most_big_object = new Rectangle(0, 0);
    
    public MapPanel(){
        super();
    
    }
    
    public MapPanel(SpatialContainer sc){
        super();
        this.sc = sc;
        this.most_big_object = sc.getBiggestObjectBoundaries();
    }
    
    public void registerListener(MouseInputListener ml){
        this.addMouseListener(ml);
        this.addMouseMotionListener(ml);
    
    }
    
    public void registerSpatialContainer(SpatialContainer sc){
        this.sc = sc;
        this.most_big_object = sc.getBiggestObjectBoundaries();
    }
    
    public AffineTransform getAffineTransform(){
        return this.at;
    }
    
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
    
    public void calculateResize() {
        Dimension screen = this.getSize();
        System.out.println(screen);
        System.out.println(most_big_object);
        this.at = new AffineTransform();
        float a = (float) (screen.width - 50) / most_big_object.width;
        float b = (float) (screen.height - 50) / most_big_object.height;
        float translate_x = Math.abs((screen.width) - most_big_object.width)/2;
        float translate_y = Math.abs((screen.height) - most_big_object.height)/2;
        float c = (a < b) ? a : b;
        System.out.println(c);
        at.translate(translate_x, 20);
        at.scale(c, c);

    }
    
    private void drawMap(Graphics2D g2){
        int layers = sc.countLayers();
        
        for(int a = 1; a <= layers; a++){
            drawMapLayer(g2, a);
        } 
            
    }
    
    private void drawMapLayer(Graphics2D g2, int layer){
         //SpatialObject list[] = (SpatialObject[])sc.getGeometries();
         ArrayList<SpatialObject> actLayer = sc.getGeometries(layer);
         System.out.println(actLayer.size());
         for(SpatialObject o : actLayer){
                g2.setStroke(new BasicStroke(1));
                switch (layer) {
                    case 2:
                        g2.setColor(Color.DARK_GRAY);
                        break;
                    case 3:
                        g2.setColor(Color.GREEN);
                        break;
                    case 4:
                        g2.setColor(Color.BLUE);
                        break;
                    case 5:
                        g2.setColor(new Color(185, 120, 90));
                        break;
                    case 6:
                        g2.setColor(Color.RED);
                        break;
                    default:
                        g2.setColor(Color.BLACK);
                        break;
                }
                JGeometry geo = o.getGeometry();
                if (o.isSelected()) {
                    g2.setColor(Color.red);
                    g2.setStroke(new BasicStroke(5));

                } else if (o.isHovered()) {
                    g2.setColor(Color.green);
                    g2.setStroke(new BasicStroke(5));
                }
                System.out.println(geo.isPoint());
                if (geo.isPoint()) {
                    Point2D points = geo.getJavaPoint();
                    Shape s = at.createTransformedShape(new Ellipse2D.Double((int) points.getX() - 3, (int) points.getY() - 3, 6, 6));
                    g2.fill(s);

                } else {
                       
                    g2.draw(geo.createShape(at));
                }

            
         
         
         } 
    }
    
    
}
