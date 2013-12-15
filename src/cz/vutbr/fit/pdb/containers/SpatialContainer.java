/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb.containers;


import cz.vutbr.fit.pdb.db.DatabaseAPI;
import cz.vutbr.fit.pdb.model.BedsObject;
import cz.vutbr.fit.pdb.model.DataObject;
import cz.vutbr.fit.pdb.model.FencesObject;
import cz.vutbr.fit.pdb.model.LayersObject;
import cz.vutbr.fit.pdb.model.PathObject;
import cz.vutbr.fit.pdb.model.SignObject;
import cz.vutbr.fit.pdb.model.SoilObject;
import cz.vutbr.fit.pdb.model.SpatialObject;
import cz.vutbr.fit.pdb.model.WaterObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import oracle.spatial.geometry.JGeometry;

/**
 *
 * @author Martin Simon
 */
public class SpatialContainer {
    private DatabaseAPI db;

    private Map<Integer, ArrayList<SpatialObject>> spatialObjectList;
    private SpatialObject selected;
    private SpatialObject hovered;
    private ArrayList<DataObject> layers;

    public SpatialContainer(String login, String password) {
        this.spatialObjectList = new HashMap<Integer, ArrayList<SpatialObject>>();
        this.layers = null;
        this.selected = null;
        this.hovered = null;

        this.db = new DatabaseAPI(login, password);
    }

    /**
     * Connect to DB and fill appropriate containers
     */
    public void initialize() {
        if (this.layers != null) {
            /* Repeated initialization - need to clear the map and array */
            this.spatialObjectList.clear();
            this.layers.clear();
            this.selected = null;
            this.hovered = null;
        }

        /* Get the layers and store them */
        this.layers = this.db.getLayersAll();

        /* For every type of object get all of occurences from db and store them
         * under appropriate integer into spatialObjectList */
        ArrayList<SpatialObject> tmp = this.db.getBedsAll();
        if (!tmp.isEmpty()) {
            int layer = tmp.get(0).getLayer();
            this.spatialObjectList.put(layer, tmp);
        }
        tmp.clear();

        tmp = this.db.getFencesAll();
        if (!tmp.isEmpty()) {
            int layer = tmp.get(0).getLayer();
            this.spatialObjectList.put(layer, tmp);
        }
        tmp.clear();

        tmp = this.db.getPathAll();
        if (!tmp.isEmpty()) {
            int layer = tmp.get(0).getLayer();
            this.spatialObjectList.put(layer, tmp);
        }
        tmp.clear();

        tmp = this.db.getSignAll();
        if (!tmp.isEmpty()) {
            int layer = tmp.get(0).getLayer();
            this.spatialObjectList.put(layer, tmp);
        }
        tmp.clear();

        tmp = this.db.getSoilAll();
        if (!tmp.isEmpty()) {
            int layer = tmp.get(0).getLayer();
            this.spatialObjectList.put(layer, tmp);
        }
        tmp.clear();

        tmp = this.db.getWaterAll();
        if (!tmp.isEmpty()) {
            int layer = tmp.get(0).getLayer();
            this.spatialObjectList.put(layer, tmp);
        }
        tmp.clear();
    }

    /**
     * Return a list of geometries of all object on the map. The list is sorted
     * in draw order (first object - first to draw)
     *
     * @return List of all geometries in order to draw
     */
    public ArrayList<JGeometry> getGeometries() {
        ArrayList<JGeometry> geometries = new ArrayList<JGeometry>();
        for (DataObject layer : layers) { //TODO is the order unsured?
            ArrayList<SpatialObject> lst = this.spatialObjectList.get(layer.getId());
            for (SpatialObject obj : lst) {
                geometries.add(obj.getGeometry());
            }
        }

        return geometries;
    }

    /**
     * Return a list of geometries in specific layer (e.g. to display only
     * specific layer). The list is sorted in draw order (first object - first
     * to draw)
     *
     * @param _layer Id of layer to draw
     * @return List of geometries in layer determined by param
     */
    public ArrayList<JGeometry> getGeometries(int _layer) {
        ArrayList<JGeometry> geometries = new ArrayList<JGeometry>();

        ArrayList<SpatialObject> lst = this.spatialObjectList.get(_layer);
        for (SpatialObject obj : lst) {
            geometries.add(obj.getGeometry());
        }
        return geometries;
    }

    /**
     * Mark the object on given coordinates as selected and return object type.
     *
     * @return Type of selected object or -1 if no object on given coordinates
     */
    public int selectObject(/*coordinates of object*/) {
        //TODO determine what object is at the given coordinates and set its "selected" flag
        return -1;
    }

    /**
     * Clear the selected flag from all the objects.
     */
    public void deselectAll() {
        for (DataObject layer : layers) {
            ArrayList<SpatialObject> lst = this.spatialObjectList.get(layer.getId());
            for (SpatialObject obj : lst) {
                obj.setSelection(false);
            }
        }
    }

    /**
     * Return object marked as selected. General SpatialObject variant.
     *
     * Note: To determine object type try instanceof operator.
     *
     * @return Selected object or null if no object selected
     */
    public SpatialObject getSelected() {
        return this.selected;
    }


    /**
     * Add given object to appropriate container and add a record to SQL queue.
     *
     * @param _obj Object to add
     */
    public void store(SignObject _obj) {
        //TODO add object to container and to SQL queue
        //TODO the action (insert/update) is determined from id
    }

    /**
     * Add given object to appropriate container and add a record to SQL queue.
     *
     * @param _obj Object to add
     */
    public void store(FencesObject _obj) {
        //TODO add object to container and to SQL queue
        //TODO the action (insert/update) is determined from id
    }

    /**
     * Add given object to appropriate container and add a record to SQL queue.
     *
     * @param _obj Object to add
     */
    public void store(BedsObject _obj) {
        //TODO add object to container and to SQL queue
        //TODO the action (insert/update) is determined from id
    }

    /**
     * Add given object to appropriate container and add a record to SQL queue.
     *
     * @param _obj Object to add
     */
    public void store(SoilObject _obj) {
        //TODO add object to container and to SQL queue
        //TODO the action (insert/update) is determined from id
    }

    /**
     * Add given object to appropriate container and add a record to SQL queue.
     *
     * @param _obj Object to add
     */
    public void store(PathObject _obj) {
        //TODO add object to container and to SQL queue
        //TODO the action (insert/update) is determined from id
    }

    /**
     * Add given object to appropriate container and add a record to SQL queue.
     *
     * @param _obj Object to add
     */
    public void store(WaterObject _obj) {
        //TODO add object to container and to SQL queue
        //TODO the action (insert/update) is determined from id
    }

    /**
     * Remove given object from appropriate container and add a record to SQL
     * queue.
     *
     * @param _obj Object to remove
     */
    public void remove(SignObject _obj) {
        //TODO remove object from container and add a record to SQL queue
    }

    /**
     * Remove given object from appropriate container and add a record to SQL
     * queue.
     *
     * @param _obj Object to remove
     */
    public void remove(FencesObject _obj) {
        //TODO remove object from container and add a record to SQL queue
    }

    /**
     * Remove given object from appropriate container and add a record to SQL
     * queue.
     *
     * @param _obj Object to remove
     */
    public void remove(BedsObject _obj) {
        //TODO remove object from container and add a record to SQL queue
    }

    /**
     * Remove given object from appropriate container and add a record to SQL
     * queue.
     *
     * @param _obj Object to remove
     */
    public void remove(SoilObject _obj) {
        //TODO remove object from container and add a record to SQL queue
    }

    /**
     * Remove given object from appropriate container and add a record to SQL
     * queue.
     *
     * @param _obj Object to remove
     */
    public void remove(PathObject _obj) {
        //TODO remove object from container and add a record to SQL queue
    }

    /**
     * Remove given object from appropriate container and add a record to SQL
     * queue.
     *
     * @param _obj Object to remove
     */
    public void remove(WaterObject _obj) {
        //TODO remove object from container and add a record to SQL queue
    }

    /**
     * Remove object of given type and id from appropriate container and add a
     * record to SQL queue.
     *
     * @param _type Type of object
     * @param _id Id of object
     */
    public void remove(int _type, int _id) {
        //TODO remove object from container and add a record to SQL queue
    }
}

