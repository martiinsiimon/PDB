/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb.view;

import cz.vutbr.fit.pdb.containers.SpatialContainer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import javax.swing.JPanel;


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
    
    public void registerListeners(){}
    
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
    }
    
    public void calculateResize() {
        Dimension screen = this.getSize();
        this.at = new AffineTransform();
        float a = (float) (screen.width - 10) / most_big_object.width;
        float b = (float) (screen.height - 10) / most_big_object.height;
        float c = (a < b) ? a : b;
        at.translate(5, 20);
        at.scale(c, c);

    }
    
    
}
