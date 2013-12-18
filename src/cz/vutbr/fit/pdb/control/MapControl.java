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
import javax.swing.event.MouseInputListener;
import sun.awt.SunToolkit.InfiniteLoop;

/**
 *
 * @author casey
 */
public class MapControl{

    private MapPanel map_view;
    private SpatialContainer map_model;
    private InfoPanel info_view;
    private DataContainer info_model;

    public MapControl(MapPanel mp, InfoPanel ip, SpatialContainer sc, DataContainer dc){
        this.map_view = mp;
        this.map_model = sc;
        this.map_view.registerListener(new MapMouseControl());
        this.info_model = dc;
        this.info_view = ip;
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
           
            
            info_view.setNameField("");
            info_view.setTypeField("");
            
            if (o instanceof BedsObject) {
                int plantid = ((BedsObject)o).getPlant();
                PlantsObject po = info_model.getPlants(plantid);
                PlantTypeObject pto = info_model.getPlantType(po.getPlantType());
                info_view.setNameField(po.getName());
                info_view.setTypeField(pto.getName());
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
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            map_model.checkHovering(e.getPoint(), map_view.getAffineTransform());
            //System.out.println(e.getPoint());
            map_view.updateUI();
        }
    }
}
