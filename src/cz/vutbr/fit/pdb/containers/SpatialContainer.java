/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb.containers;


import cz.vutbr.fit.pdb.model.BedsObject;
import cz.vutbr.fit.pdb.model.FencesObject;
import cz.vutbr.fit.pdb.model.PathObject;
import cz.vutbr.fit.pdb.model.SignObject;
import cz.vutbr.fit.pdb.model.SoilObject;
import cz.vutbr.fit.pdb.model.SpatialObject;
import cz.vutbr.fit.pdb.model.WaterObject;

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

    /**
     * Connect to DB and fill appropriate containers
     */
    public void initialize() {
        //TODO get all objects from DB and fill appripriate containers
    }

    /**
     * Return a list of geometries of all object on the map. The list is sorted
     * in draw order (first object - first to draw)
     *
     */
    public void getGeometries() {
        //TODO go through all the list and create a list of all of them
        //TODO return value
    }

    /**
     * Return a list of geometries in specific layer (e.g. to display only
     * specific layer). The list is sorted in draw order (first object - first
     * to draw)
     *
     * @param _layer Id of layer to draw
     */
    public void getGeometries(int _layer) {
        //TODO go through the list determined by argument and create a list
        //TODO return value
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
        //TODO fix it
        Set<Integer> keys = spatialObjectList.keySet();
        for (Integer i : keys) {
            ArrayList<SpatialObject> layerItems = spatialObjectList.get(i);
            for (SpatialObject so : layerItems) {
                so.setSelection(false);
            }
        }
    }

    /**
     * Return object marked as selected. SignObject variant.
     *
     * Note: To determine object type see selectObject method.
     *
     * @return Selected object or null if incorrect type or no object selected
     */
    public SignObject getSelectedSign() {
        //TODO determine selected object and return it
        return new SignObject();
    }

    /**
     * Return object marked as selected. FencesObject variant.
     *
     * Note: To determine object type see selectObject method.
     *
     * @return Selected object or null if incorrect type or no object selected
     */
    public FencesObject getSelectedFences() {
        //TODO determine selected object and return it
        return new FencesObject();
    }

    /**
     * Return object marked as selected. BedsObject variant.
     *
     * Note: To determine object type see selectObject method.
     *
     * @return Selected object or null if incorrect type or no object selected
     */
    public BedsObject getSelectedBeds() {
        //TODO determine selected object and return it
        return new BedsObject();
    }

    /**
     * Return object marked as selected. SoilObject variant.
     *
     * Note: To determine object type see selectObject method.
     *
     * @return Selected object or null if incorrect type or no object selected
     */
    public SoilObject getSelectedSoil() {
        //TODO determine selected object and return it
        return new SoilObject();
    }

    /**
     * Return object marked as selected. PathObject variant.
     *
     * Note: To determine object type see selectObject method.
     *
     * @return Selected object or null if incorrect type or no object selected
     */
    public PathObject getSelectedPath() {
        //TODO determine selected object and return it
        return new PathObject();
    }

    /**
     * Return object marked as selected. WaterObject variant.
     *
     * Note: To determine object type see selectObject method.
     *
     * @return Selected object or null if incorrect type or no object selected
     */
    public WaterObject getSelectedWater() {
        //TODO determine selected object and return it
        return new WaterObject();
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

