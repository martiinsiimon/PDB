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
import cz.vutbr.fit.pdb.model.FencesObject;
import cz.vutbr.fit.pdb.model.PathObject;
import cz.vutbr.fit.pdb.model.PlantsObject;
import cz.vutbr.fit.pdb.model.SignObject;

import cz.vutbr.fit.pdb.model.SoilObject;
import cz.vutbr.fit.pdb.model.SpatialObject;
import cz.vutbr.fit.pdb.model.WaterObject;
import cz.vutbr.fit.pdb.view.EditPanel;
import cz.vutbr.fit.pdb.view.InfoPanel;
import cz.vutbr.fit.pdb.view.MapPanel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import oracle.spatial.geometry.JGeometry;

/**
 * Class for controlling of editing part of application.
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

    /**
     * Initialization function for EditControl class.
     * @param ep EditPanel object
     * @param mp MapPanel object
     * @param ip InfoPanel object
     * @param sc SpatialContainer object
     * @param dc DataContainer object
     * @param api DatabaseAPI object
     */
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
    
    /**
     * Moves given point to coordinates from GUI.
     * @param p point to be moved
     */
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
    
    /**
     * Sets given object as selected.
     * @param so SpatialObject to hold the selection information
     * @param od DataObject to be selected
     */
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
            //this.mp.setEditShape(at.createTransformedShape(r));
            this.ep.registerChangeListener(this.chl);

        }
        this.mp.updateUI();
    }

    /**
     * Function ensures that the bounding rectangle is updated on change of
     * coordinates in GUI.
     */
    public void updateOnChange(){
        Rectangle r;
        this.ep.removeChangeListener(this.chl);
         if(this.selected.getGeometry().isPoint()){
                Point2D p= this.selected.getGeometry().getJavaPoint();
                r = new Rectangle((int)p.getX()-10, (int)p.getY()-10, 20, 20);
            }else{
                r = this.selected.getGeometry().createShape().getBounds();
            }

        //r.setLocation(new Point(0, 0));
        //AffineTransform af = this.mp.getAffineTransform();
        Point p = this.ep.getXY();
        Point pchange = new Point(p.x - r.x, p.y - r.y);
        Point2D.Double s = this.ep.getScale();
        int rotate = this.ep.getRotate();
        this.at = new AffineTransform();

        try {
        JGeometry  qq= this.selected.getGeometry();
        if(rotate != 0){
            //at.rotate(Math.toRadians(rotate));
            //System.out.println("rotate");
            double[] d = {(double)r.x, (double)r.y};
            qq = qq.affineTransforms(false, 0.0, 0.0, 0.0, false, null, 0, 0, 0, true, JGeometry.createPoint(d,2,0), null, Math.toRadians(rotate), -1, false, 0, 0, 0, 0, 0, 0, false, null, null, 0, false, null, null);
        }

        if(s.x != 1 || s.y != 1){
            //at.scale(s.x, s.y);
            //System.out.println("scale");
            qq = qq.affineTransforms(false, 0, 0, 0, true, new JGeometry((double)r.x+(r.width/2), (double)r.y + (r.height/2), 0), s.x, s.y, 0, false, null, null, 0, 0, false, 0, 0, 0, 0, 0, 0, false, null, null, 0, false, null, null);
        }


        if(p.x - r.x != 0 || p.y - r.y != 0){
            qq = qq.affineTransforms(true, pchange.x, pchange.y, 0.0, false, null, 0, 0, 0, false, null, null, 0, 0, false, 0, 0, 0, 0, 0, 0, false, null, null, 0, false, null, null);

        }
        //editBox = at.createTransformedShape(r);
        //at = new AffineTransform();
        //at.translate(p.x, p.y);

//            editBox = (this.selected.getGeometry().affineTransforms(true, p.x, p.y, 0, false, null, 0, 0, 0, false, null, null, 0, 0, false, 0, 0, 0, 0, 0, 0, false, null, null, 0, true, null, null).createShape());


            this.selected.setGeometry(qq);
            //System.out.println(qq.createShape().getBounds());
        } catch (Exception ex) {
            System.out.println("aa " + ex.toString());
        }


                //editBox.setLocation(p);
        this.ep.resetRest();
        this.ep.registerChangeListener(this.chl);
        this.mp.setEditShape(null);
        this.mp.updateUI();

    }

    /**
     * Enables editing mode.
     */
    public void editEnable(){

    }
    
    /**
     * Disables editing mode.
     */
    public void editDisable(){

    }




    class EditMapControl implements ActionListener, ChangeListener{
        private Integer lastId = -1;
        @Override
        public void actionPerformed(ActionEvent e) {

            if("save_item".equals(e.getActionCommand())){
                if (selected instanceof BedsObject) {
                    sc.store((BedsObject)selected);
                } else if (selected instanceof FencesObject) {
                    sc.store((FencesObject)selected);
                } else if (selected instanceof PathObject) {
                    sc.store((PathObject)selected);
                } else if (selected instanceof SignObject) {
                    sc.store((SignObject)selected);
                } else if (selected instanceof SoilObject) {
                    sc.store((SoilObject)selected);
                } else if (selected instanceof WaterObject) {
                   sc.store((WaterObject)selected);
                }
                sc.commitToDB();
            }else if("delete_item".equals(e.getActionCommand())){

                if (selected instanceof BedsObject) {
                    sc.delete((BedsObject)selected);
                } else if (selected instanceof FencesObject) {
                    sc.delete((FencesObject)selected);
                } else if (selected instanceof PathObject) {
                    sc.delete((PathObject)selected);
                } else if (selected instanceof SignObject) {
                    sc.delete((SignObject)selected);
                } else if (selected instanceof SoilObject) {
                    sc.delete((SoilObject)selected);
                } else if (selected instanceof WaterObject) {
                   sc.delete((WaterObject)selected);
                }
                sc.commitToDB();

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
            } else if ("add_bed_object".equals(e.getActionCommand())) {
                System.out.println("add bed object");
                BedsObject obj = new BedsObject();
                obj.setId(dapi.getHighestId(obj) + 1);
                this.lastId = obj.getId();
                obj.setLayer(4);
                obj.setPlant(1);
                obj.setGeometry(new JGeometry(0.0,0.0,30.0,30,0));
                sc.addObject(obj);
                sc.setSelected(obj);
                setSelected(obj, dc.getPlants(1));
                
            } else if ("add_sign_object".equals(e.getActionCommand())) {
                System.out.println("add sign object");
                SignObject obj = new SignObject();
                obj.setId(dapi.getHighestId(obj) + 1);
                this.lastId = obj.getId();
                obj.setLayer(6);
                obj.setPlant(1);
                obj.setGeometry(new JGeometry(10.0,20.0,0));
                sc.addObject(obj);
                sc.setSelected(obj);
                setSelected(obj, null);
                
            }else if ("add_fence_object".equals(e.getActionCommand())) {
                System.out.println("add fence object");
            }else if ("add_path_object".equals(e.getActionCommand())) {
                System.out.println("add path object");
                PathObject obj = new PathObject();
                obj.setId(dapi.getHighestId(obj) + 1);
                this.lastId = obj.getId();
                obj.setLayer(3);
                obj.setGeometry(new JGeometry(0.0,0.0,200.0,20,0));
                sc.addObject(obj);
                sc.setSelected(obj);
                setSelected(obj, null);
            }else if ("add_water_object".equals(e.getActionCommand())) {
                System.out.println("add water object");
                WaterObject obj = new WaterObject();
                obj.setId(dapi.getHighestId(obj) + 1);
                this.lastId = obj.getId();
                obj.setLayer(2);
                obj.setGeometry(JGeometry.createCircle(-10.0, 30.0, 50.0, 0));
                sc.addObject(obj);
                sc.setSelected(obj);
                setSelected(obj, null);
            }else if ("add_soil_object".equals(e.getActionCommand())) {
                System.out.println("add soil object");
                SoilObject obj = new SoilObject();
                obj.setId(dapi.getHighestId(obj) + 1);
                this.lastId = obj.getId();
                obj.setLayer(1);
                obj.setSoilType(1);
                obj.setGeometry(new JGeometry(0.0,0.0,100.0,100,0));
                sc.addObject(obj);
                sc.setSelected(obj);
                setSelected(obj, null);
            }else if ("add_plant_object".equals(e.getActionCommand())) {
                System.out.println("add plant object");
            }
            
            ip.updateUI();
            mp.updateUI();
            ep.updateUI();
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            updateOnChange();
            //System.out.println("le update");
        }

    }

}
