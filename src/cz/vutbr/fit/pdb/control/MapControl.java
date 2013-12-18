/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb.control;

import cz.vutbr.fit.pdb.containers.SpatialContainer;
import cz.vutbr.fit.pdb.model.SpatialObject;
import cz.vutbr.fit.pdb.view.MapPanel;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;

/**
 *
 * @author casey
 */
public class MapControl{

    private MapPanel view;
    private SpatialContainer model;

    public MapControl(MapPanel mp, SpatialContainer sc){
        this.view = mp;
        this.model = sc;
        this.view.registerListener(new MapMouseControl());
    }



    class MapMouseControl implements MouseInputListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void mousePressed(MouseEvent e) {
            model.deselectAll();
            SpatialObject so = model.getHovered();
            if (so == null) {
                return;
            }
            so.setSelection(true);
            model.setSelected(so);

            model.checkHovering(e.getPoint(), view.getAffineTransform());
            view.updateUI();
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
            model.checkHovering(e.getPoint(), view.getAffineTransform());
            //System.out.println(e.getPoint());
            view.updateUI();
        }
    }
}
