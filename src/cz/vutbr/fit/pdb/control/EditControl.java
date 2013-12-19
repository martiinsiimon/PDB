/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb.control;

import cz.vutbr.fit.pdb.model.SpatialObject;
import cz.vutbr.fit.pdb.view.EditPanel;
import cz.vutbr.fit.pdb.view.MapPanel;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author casey
 */
public class EditControl {
    
   
    SpatialObject selected;
    EditPanel ep;
    MapPanel mp;
    AffineTransform at;
    ChangeListener chl;
    
    public EditControl(EditPanel ep, MapPanel mp){
        this.ep = ep;
        this.mp = mp;
        this.chl = new EditMapControl();
    }
    
    public void moveTo(Point p){
        if(this.selected != null){
            AffineTransform atmp = this.mp.getAffineTransform();
            Point pa = new Point(0, 0);
            try {
                atmp.inverseTransform(p, pa);
                this.ep.updateXY(pa.x,pa.y);
            } catch (NoninvertibleTransformException ex) {
                
            }
            //System.out.println(pa);
            
        }
    }
    
    public void setSelected(SpatialObject so){
        this.selected = so;
        this.ep.removeChangeListener(this.chl);
        if(this.selected != null){
            Rectangle r;
            this.ep.resetRest();
            if(this.selected.getGeometry().isPoint()){
                Point2D p= this.selected.getGeometry().getJavaPoint();
                r = new Rectangle((int)p.getX()-10, (int)p.getY()-10, 20, 20);
            }else{
                r = this.selected.getGeometry().createShape().getBounds();
            }
            this.at = new AffineTransform();
            this.ep.updateXY(r.x,r.y);
            System.out.println(r);
            this.ep.updateLayer(this.selected.getLayer());
            this.mp.setEditShape(at.createTransformedShape(r));
            this.ep.registerChangeListener(this.chl);
            
        }
        this.mp.updateUI();
    }
    
    public void updateOnChange(){
        Rectangle r;
         if(this.selected.getGeometry().isPoint()){
                Point2D p= this.selected.getGeometry().getJavaPoint();
                r = new Rectangle((int)p.getX()-10, (int)p.getY()-10, 20, 20);
            }else{
                r = this.selected.getGeometry().createShape().getBounds();
            }
        Shape editBox ;
        r.setLocation(new Point(0, 0));
        Point p = this.ep.getXY();
        Point2D.Double s = this.ep.getScale();
        int rotate = this.ep.getRotate();
        this.at = new AffineTransform();
        
        if(rotate != 0){
            at.rotate(Math.toRadians(rotate));
        }
        
        if(s.x != 0 || s.y != 0){
            at.scale(s.x, s.y);
        }
        
        editBox = at.createTransformedShape(r);
        at = new AffineTransform();
        at.translate(p.x, p.y);
        editBox = at.createTransformedShape(editBox);
        //editBox.setLocation(p);
        this.mp.setEditShape(editBox);
        this.mp.updateUI();
        
    }
    
    
    public void editEnable(){
    
    }
    
    public void editDisable(){
    
    }
    
    
    
    
    class EditMapControl implements ActionListener, ChangeListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            updateOnChange();
            //System.out.println("le update");
        }
    
    }
    
}
