/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb.control;

import cz.vutbr.fit.pdb.containers.DataContainer;
import cz.vutbr.fit.pdb.containers.SpatialContainer;
import cz.vutbr.fit.pdb.model.BedsObject;
import cz.vutbr.fit.pdb.model.DataObject;
import cz.vutbr.fit.pdb.model.FencesObject;
import cz.vutbr.fit.pdb.model.PathObject;
import cz.vutbr.fit.pdb.model.PlantTypeObject;
import cz.vutbr.fit.pdb.model.PlantsObject;
import cz.vutbr.fit.pdb.model.SignObject;
import cz.vutbr.fit.pdb.model.SoilObject;
import cz.vutbr.fit.pdb.model.SpatialObject;
import cz.vutbr.fit.pdb.model.WaterObject;
import cz.vutbr.fit.pdb.view.ImagePanel;
import cz.vutbr.fit.pdb.view.InfoPanel;
import cz.vutbr.fit.pdb.view.MapPanel;
import java.awt.Dimension;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.event.MouseInputListener;
import oracle.ord.im.OrdImage;

/**
 *
 * @author casey
 */
public class MapControl{

    private MapPanel map_view;
    private SpatialContainer map_model;
    private InfoPanel info_view;
    private DataContainer info_model;
    private EditControl editControl;
    private MapMouseControl mmc;
    private SpatialObject so2;

    public MapControl(MapPanel mp, InfoPanel ip, SpatialContainer sc, DataContainer dc){
        this.mmc = new MapMouseControl();
        this.map_view = mp;
        this.map_model = sc;
        this.map_view.registerListener(this.mmc);
        this.info_model = dc;
        this.info_view = ip;
        this.info_view.registerMouseListener(this.mmc);
        this.so2 = null;
    }
    
    
    public BufferedImage convert(OrdImage image){
        if(image == null){
            return null;
        }
        Blob blob;
        InputStream in;
        BufferedImage bimage = null;
        try {
            blob = image.getBlobContent();
            in = blob.getBinaryStream();
            bimage = ImageIO.read(in);
        } catch (SQLException e) {
            
        }catch (IOException e) {
            
        }
        
        return bimage;
    }

    public void enableEdit(EditControl ec){
        this.editControl = ec;
    
    }
    public void disableEdit(){
        this.editControl = null;
        this.map_view.setEditShape(null);
    
    }
    
    public SpatialObject getSecondSelected() {
        return so2;
    }

    class MapMouseControl implements MouseInputListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if(e.getSource() == map_view){

                SpatialObject o = map_model.getHovered();
                if (o == null) {
                    map_model.setSelected(null);
                    return;
                }
                
                int maskCtrl = InputEvent.CTRL_MASK | InputEvent.BUTTON1_MASK;
                if (map_model.getSelected() != null && (e.getModifiers() & maskCtrl) == maskCtrl) {
                    so2 = o;
                    ArrayList<SpatialObject> lst = new ArrayList<SpatialObject>();
                    lst.add(so2);
                    lst.add(map_model.getSelected());
                    map_model.setTheseAsSelected(lst);
                    map_view.updateUI();
                    return;
                } else {
                    so2 = null;
                }
                
                map_model.deselectAll();
                o.setSelection(true);


                map_model.setSelected(o);

                map_model.checkHovering(e.getPoint(), map_view.getAffineTransform());

                PlantsObject po = new PlantsObject();
                po.setName("");

                info_view.setNameFieldShown();
                info_view.setNameLabel("Name: ");
                info_view.setNameField("");
                info_view.setTypeField("");
                info_view.setDescFieldHidden();
                info_view.setDescLabel("");
                info_view.setImage(null);

                if (o instanceof BedsObject) {
                    int plantid = ((BedsObject)o).getPlant();
                    po = info_model.getPlants(plantid);
                    PlantTypeObject pto = info_model.getPlantType(po.getPlantType());
                    info_view.setNameField(po.getName());
                    String pto_name = pto.getName();
                    String pto_name_cap = (pto_name.substring(0,1).toUpperCase()) + pto_name.substring(1);
                    info_view.setTypeField(pto_name_cap);

                    //info_view.setImage(info_model.getImageThumbnail(plantid));

                    info_view.setImage(info_model.getImageThumbnail(po));

                } else if (o instanceof FencesObject) {
                    info_view.setTypeField("Fence");
                    info_view.setNameLabel("");
                    info_view.setNameFieldHidden();
                } else if (o instanceof PathObject) {
                    info_view.setTypeField("Path");
                    info_view.setNameLabel("");
                    info_view.setNameFieldHidden();
                } else if (o instanceof SignObject) {
                    info_view.setTypeField("Sign");
                    info_view.setNameLabel("Plant: ");
                    int plant_id = ((SignObject)o).getPlant();
                    info_view.setNameField(info_model.getPlants(plant_id).getName());
                    info_view.setDescFieldShown();
                    info_view.setDescLabel("Description: ");
                    info_view.setDescField(((SignObject)o).getDescription());
                } else if (o instanceof SoilObject) {
                    info_view.setTypeField("Soil");
                    int soil_id = ((SoilObject)o).getSoilType();
                    String soil_type = info_model.getSoilType(soil_id).getName();
                    info_view.setNameField(info_model.getSoilType(soil_id).getName());
                } else if (o instanceof WaterObject) {
                    info_view.setTypeField("Water");
                    info_view.setNameLabel("");
                    info_view.setNameFieldHidden(); 
                }
                
                if(editControl != null){
                    editControl.setSelected(o,(DataObject)po);
                }
                //map_view.setEditShape(null);
                map_view.updateUI();
                info_view.updateUI();
            }
            else if(e.getSource() instanceof ImagePanel){
                SpatialObject o = map_model.getSelected();
                if(o != null && o instanceof BedsObject){
                    int plantid = ((BedsObject)o).getPlant();
                    PlantsObject po = info_model.getPlants(plantid);
                    BufferedImage image = info_model.getImage(po);
                    if (image != null) {
                        JFrame f = new JFrame();
                        ImagePanel im = new ImagePanel(image);
                        im.setResize(false);
                        f.add(im);
                        f.setPreferredSize(new Dimension(image.getWidth()+20, image.getHeight() +20));
                        f.pack();
                        f.setVisible(true);
                    }
                }
            }
            
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void mouseExited(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            editControl.moveTo(e.getPoint());
            //System.out.println(e.getPoint());
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            map_model.checkHovering(e.getPoint(), map_view.getAffineTransform());
            //System.out.println(e.getPoint());
            map_view.updateUI();
        }
    }
}
