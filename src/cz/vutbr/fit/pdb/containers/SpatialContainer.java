/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb.containers;


import cz.vutbr.fit.pdb.model.SpatialObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Martin Simon
 */
public class SpatialContainer {

    private Map<Integer, ArrayList<SpatialObject>> spatialObjectList;
    private SpatialObject selected;
    private SpatialObject hovered;

    public SpatialContainer(){
        spatialObjectList = new HashMap<Integer, ArrayList<SpatialObject>>();
        selected = null;
        hovered = null;
    }


    public void deselectAll() {
        Set<Integer> keys = spatialObjectList.keySet();
        for (Integer i : keys) {
            ArrayList<SpatialObject> layerItems = spatialObjectList.get(i);
            for (SpatialObject so : layerItems) {
                so.setSelection(false);
            }
        }

    }
}

