/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb.control;

import cz.vutbr.fit.pdb.containers.DataContainer;
import cz.vutbr.fit.pdb.containers.SpatialContainer;
import cz.vutbr.fit.pdb.db.DatabaseAPI;
import cz.vutbr.fit.pdb.model.BedsObject;
import cz.vutbr.fit.pdb.model.DataObject;
import cz.vutbr.fit.pdb.model.PlantsObject;
import cz.vutbr.fit.pdb.model.SpatialObject;
import cz.vutbr.fit.pdb.view.EditPanel;
import cz.vutbr.fit.pdb.view.InfoPanel;
import cz.vutbr.fit.pdb.view.MapPanel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author casey
 */
public class EditControl {
    
   
    SpatialObject selected;
    DataObject selectedData;
    SpatialContainer sc;
    DataContainer dc;
    EditPanel ep;
    MapPanel mp;
    InfoPanel ip;
    AffineTransform at;
    EditMapControl chl;
    DatabaseAPI dapi;
    
    
    public EditControl(EditPanel ep, MapPanel mp, InfoPanel ip, SpatialContainer sc, DataContainer dc, DatabaseAPI api){
        this.ep = ep;
        this.mp = mp;
        this.sc = sc;
        this.dc = dc;
        this.ip = ip;
        this.chl = new EditMapControl();
        this.ep.registerActionListener(chl);
        this.dapi = api;
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
    
    public void setSelected(SpatialObject so, DataObject od){
        this.selected = so;
        this.selectedData = od;
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
            if(this.selected instanceof BedsObject){
                this.ep.nameFieldEnableState(true);
                this.ep.setNameField(((PlantsObject)od).getName());
            }else{
                this.ep.setNameField("");
                this.ep.nameFieldEnableState(false);
            }
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
            if("update".equals(e.getActionCommand())){
                //TODO - store that shit
            }else if("update".equals(e.getActionCommand())){
            
            }else if("load_new_image".equals(e.getActionCommand())){
                JFileChooser fc = new JFileChooser("./");
                int returnVal = fc.showOpenDialog(mp);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    dapi.insertImage((PlantsObject)selectedData, file.getAbsolutePath());
                    ip.setImage(dc.getImageThumbnail((PlantsObject)selectedData));
                    ip.updateUI();
                }
                
            }else if("delete_image".equals(e.getActionCommand())){
                dapi.deleteImage((PlantsObject)selectedData);
                ip.setImage(dc.getImageThumbnail((PlantsObject)selectedData));
                ip.updateUI();
            }
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            updateOnChange();
            //System.out.println("le update");
        }
    
    }
    
}
