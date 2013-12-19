/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb.control;

import cz.vutbr.fit.pdb.containers.DataContainer;
import cz.vutbr.fit.pdb.containers.SpatialContainer;
import cz.vutbr.fit.pdb.model.BedsObject;
import cz.vutbr.fit.pdb.model.FencesObject;
import cz.vutbr.fit.pdb.model.PathObject;
import cz.vutbr.fit.pdb.model.PlantTypeObject;
import cz.vutbr.fit.pdb.model.PlantsObject;
import cz.vutbr.fit.pdb.model.SignObject;
import cz.vutbr.fit.pdb.model.SoilObject;
import cz.vutbr.fit.pdb.model.SpatialObject;
import cz.vutbr.fit.pdb.model.WaterObject;
import cz.vutbr.fit.pdb.view.InfoPanel;
import cz.vutbr.fit.pdb.view.MapPanel;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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
    

    public MapControl(MapPanel mp, InfoPanel ip, SpatialContainer sc, DataContainer dc){
        this.map_view = mp;
        this.map_model = sc;
        this.map_view.registerListener(new MapMouseControl());
        this.info_model = dc;
        this.info_view = ip;
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

    class MapMouseControl implements MouseInputListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void mousePressed(MouseEvent e) {
            map_model.deselectAll();
            SpatialObject o = map_model.getHovered();
            if (o == null) {
                return;
            }
            o.setSelection(true);
            
            
            map_model.setSelected(o);

            map_model.checkHovering(e.getPoint(), map_view.getAffineTransform());
            
             if(editControl != null){
                editControl.setSelected(o);
            }
           
            
            info_view.setNameField("");
            info_view.setTypeField("");
            info_view.setImage(null);
            
            if (o instanceof BedsObject) {
                int plantid = ((BedsObject)o).getPlant();
                PlantsObject po = info_model.getPlants(plantid);
                PlantTypeObject pto = info_model.getPlantType(po.getPlantType());
                info_view.setNameField(po.getName());
                info_view.setTypeField(pto.getName());

                //info_view.setImage(info_model.getImageThumbnail(plantid));

                info_view.setImage(info_model.getImageThumbnail(po));

            } else if (o instanceof FencesObject) {
                info_view.setTypeField("Fence");
            } else if (o instanceof PathObject) {
                info_view.setTypeField("Path");
            } else if (o instanceof SignObject) {
                info_view.setTypeField("Sign");
                
            } else if (o instanceof SoilObject) {
                info_view.setTypeField("Soil");
            } else if (o instanceof WaterObject) {
                info_view.setTypeField("Water");
            }
             
            //map_view.setEditShape(null);
            map_view.updateUI();
            info_view.updateUI();
            

            
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
