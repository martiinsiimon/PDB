/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb.containers;

import cz.vutbr.fit.pdb.db.DatabaseAPI;
import cz.vutbr.fit.pdb.model.BedsObject;
import cz.vutbr.fit.pdb.model.DataObject;
import cz.vutbr.fit.pdb.model.FencesObject;
import cz.vutbr.fit.pdb.model.PathObject;
import cz.vutbr.fit.pdb.model.SignObject;
import cz.vutbr.fit.pdb.model.SoilObject;
import cz.vutbr.fit.pdb.model.SpatialObject;
import cz.vutbr.fit.pdb.model.WaterObject;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Container of spatial objects (those who have geometry). For correct function
 * it needs to be initialized together with DataContainer
 *
 * @author Martin Simon
 */
public class SpatialContainer {

    private final DatabaseAPI db;

    private final Map<Integer, SpatialObject> spatialBedsList;
    private final Map<Integer, SpatialObject> spatialFencesList;
    private final Map<Integer, SpatialObject> spatialPathsList;
    private final Map<Integer, SpatialObject> spatialSignsList;
    private final Map<Integer, SpatialObject> spatialSoilList;
    private final Map<Integer, SpatialObject> spatialWaterList;
    private SpatialObject selected;
    private SpatialObject hovered;
    private ArrayList<DataObject> layers;
    private Boolean initialized;
    private String date;

    /**
     * Constructor of SpatialContainer.
     *
     * @param login Username to access database
     * @param password Password to access database
     */
    public SpatialContainer(String login, String password) {
        this.spatialBedsList = new HashMap<Integer, SpatialObject>();
        this.spatialFencesList = new HashMap<Integer, SpatialObject>();
        this.spatialPathsList = new HashMap<Integer, SpatialObject>();
        this.spatialSignsList = new HashMap<Integer, SpatialObject>();
        this.spatialSoilList = new HashMap<Integer, SpatialObject>();
        this.spatialWaterList = new HashMap<Integer, SpatialObject>();

        this.layers = null;
        this.selected = null;
        this.hovered = null;
        this.initialized = false;

        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date d = new Date();
        this.date = dateFormat.format(d);
        this.db = new DatabaseAPI(login, password);
    }

    /**
     * Constructor of SpatialContainer.
     *
     * @param _db DatabaseAPI object instance
     */
    public SpatialContainer(DatabaseAPI _db) {
        this.spatialBedsList = new HashMap<Integer, SpatialObject>();
        this.spatialFencesList = new HashMap<Integer, SpatialObject>();
        this.spatialPathsList = new HashMap<Integer, SpatialObject>();
        this.spatialSignsList = new HashMap<Integer, SpatialObject>();
        this.spatialSoilList = new HashMap<Integer, SpatialObject>();
        this.spatialWaterList = new HashMap<Integer, SpatialObject>();

        this.layers = null;
        this.selected = null;
        this.hovered = null;
        this.initialized = false;

        this.db = _db;

        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date d = new Date();
        this.date = dateFormat.format(d);
    }

    /**
     * Set date of od map to new one
     *
     * @param _date Date in format MM-DD-YYYY
     */
    public void setDate(String _date) {
        this.date = _date;
        this.initialize();
    }

    /**
     * Return current date set.
     *
     * @return Date in format MM-DD-YYYY
     */
    public String getDate() {
        return this.date;
    }

    /**
     * Connect to DB and fill appropriate containers.
     */
    public void initialize() {
        if (this.initialized) {
            /* Repeated initialization - need to clear the map and array */
            this.spatialBedsList.clear();
            this.spatialFencesList.clear();
            this.spatialPathsList.clear();
            this.spatialSignsList.clear();
            this.spatialSoilList.clear();
            this.spatialWaterList.clear();
            this.layers.clear();
            this.selected = null;
            this.hovered = null;
        }

        /* Get the layers and store them */
        this.layers = this.db.getLayersAll();

        /* For every type of object get all of occurences from db and store them
         * under appropriate integer into spatialObjectList */
        ArrayList<SpatialObject> tmp = this.db.getBedsAll(this.date);
        for (SpatialObject o : tmp) {
            this.spatialBedsList.put(o.getId(), o);
        }

        tmp = this.db.getFencesAll(this.date);
        for (SpatialObject o : tmp) {
            this.spatialFencesList.put(o.getId(), o);
        }

        tmp = this.db.getPathAll();

        for (SpatialObject o : tmp) {
            this.spatialPathsList.put(o.getId(), o);
        }

        tmp = this.db.getSignAll(this.date);
        for (SpatialObject o : tmp) {
            this.spatialSignsList.put(o.getId(), o);
        }

        tmp = this.db.getSoilAll();
        for (SpatialObject o : tmp) {
            this.spatialSoilList.put(o.getId(), o);
        }

        tmp = this.db.getWaterAll();
        for (SpatialObject o : tmp) {
            this.spatialWaterList.put(o.getId(), o);
        }

        this.initialized = true;
    }

    /**
     * Return BedObject with given id
     *
     * @param id Id (in database) of BedObject
     * @return BedObject with given id
     */
    public BedsObject getBed(Integer id) {
        return (BedsObject) this.spatialBedsList.get(id);
    }

    /**
     * Return FencesObject with given id
     *
     * @param id Id (in database) of FencesObject
     * @return FencesObject with given id
     */
    public FencesObject getFence(Integer id) {
        return (FencesObject) this.spatialFencesList.get(id);
    }

    /**
     * Return PathObject with given id
     *
     * @param id Id (in database) of PathObject
     * @return PathObject with given id
     */
    public PathObject getPath(Integer id) {
        return (PathObject) this.spatialPathsList.get(id);
    }

    /**
     * Return SignObject with given id
     *
     * @param id Id (in database) of SignObject
     * @return SignObject with given id
     */
    public SignObject getSign(Integer id) {
        return (SignObject) this.spatialSignsList.get(id);
    }

    /**
     * Return SoilObject with given id
     *
     * @param id Id (in database) of SoilObject
     * @return SoilObject with given id
     */
    public SoilObject getSoil(Integer id) {
        return (SoilObject) this.spatialSoilList.get(id);
    }

    /**
     * Return WaterObject with given id
     *
     * @param id Id (in database) of WaterObject
     * @return WaterObject with given id
     */
    public WaterObject getWater(Integer id) {
        return (WaterObject) this.spatialWaterList.get(id);
    }

    /**
     * Append layers given in parameter to the stored list of layers.
     *
     * @param _layers ArrayList of layers to append
     */
    public void addLayers(ArrayList<DataObject> _layers) {
        if (this.layers == null) {
            this.layers = _layers;
        } else {
            this.layers.addAll(_layers);
        }
    }

    /**
     * Replace layers with list given in parameter.
     *
     * @param _layers ArrayList of layers to replace with
     */
    public void replaceLayers(ArrayList<DataObject> _layers) {
        if (this.layers == null) {
            this.layers = _layers;
        } else {
            this.layers.clear();
            this.layers.addAll(_layers);
        }
    }

    /**
     * Append (or update) layer given in parameter to the stored list of layers.
     *
     * @param _layer Layer to append (or update, if already exists)
     */
    public void addLayer(DataObject _layer) {
        if (this.layers == null) {
            this.layers = new ArrayList<DataObject>();
            this.layers.add(_layer);
        } else {
            this.layers.add(_layer);
        }
    }

    /**
     * Remove layer given in parameter from the stored list of layers or do
     * nothing if the layer does not exists.
     *
     * @param _layer Layer to remove
     */
    public void removeLayer(DataObject _layer) {
        if (this.layers != null && !this.layers.isEmpty()) {
            for (DataObject l : this.layers) {
                if (l.getName().equals(_layer.getName())) {
                    this.layers.remove(l);
                    break;
                }
            }
        }
    }

    /**
     * Return a list of layers.
     *
     * @return ArrayList of DataObject's
     */
    public ArrayList<DataObject> getLayers() {
        return this.layers;
    }

    /**
     * Return a list of layers size.
     *
     * @return ArrayList size.
     */
    public int countLayers() {
        return this.layers.size();
    }

    public Rectangle getBiggestObjectBoundaries() {

        ArrayList<SpatialObject> allLayers = getGeometries();
        int mostLeft = 0;
        int mostBottom = 0;
        for (SpatialObject o : allLayers) {
            Shape sh = o.getGeometry().createShape();
            Rectangle actual = sh.getBounds();
            if ((actual.width + actual.x) > mostLeft) {
                mostLeft = (actual.width + actual.x);
            }
            if ((actual.height + actual.y) > mostBottom) {
                mostBottom = (actual.height + actual.y);
            }
        }
        return new Rectangle(mostLeft, mostBottom);

    }

    /**
     * Return a list of geometries of all object on the map. The list is sorted
     * in draw order (first object - first to draw)
     *
     *
     * @return List of all geometries in order to draw
     */
    public ArrayList<SpatialObject> getGeometries() {
        Map<Integer, ArrayList<SpatialObject>> geometries = new HashMap<Integer, ArrayList<SpatialObject>>();
        ArrayList<SpatialObject> result = new ArrayList<SpatialObject>();

        for (Integer i : this.spatialBedsList.keySet()) {
            Integer layer = this.spatialBedsList.get(i).getLayer();
            if (geometries.containsKey(layer)) {
                geometries.get(layer).add(this.spatialBedsList.get(i));
            } else {
                geometries.put(layer, new ArrayList<SpatialObject>());
                geometries.get(layer).add(this.spatialBedsList.get(i));
            }
        }

        for (Integer i : this.spatialFencesList.keySet()) {
            Integer layer = this.spatialFencesList.get(i).getLayer();
            if (geometries.containsKey(layer)) {
                geometries.get(layer).add(this.spatialFencesList.get(i));
            } else {
                geometries.put(layer, new ArrayList<SpatialObject>());
                geometries.get(layer).add(this.spatialFencesList.get(i));
            }
        }

        for (Integer i : this.spatialPathsList.keySet()) {
            Integer layer = this.spatialPathsList.get(i).getLayer();
            if (geometries.containsKey(layer)) {
                geometries.get(layer).add(this.spatialPathsList.get(i));
            } else {
                geometries.put(layer, new ArrayList<SpatialObject>());
                geometries.get(layer).add(this.spatialPathsList.get(i));
            }
        }

        for (Integer i : this.spatialSignsList.keySet()) {
            Integer layer = this.spatialSignsList.get(i).getLayer();
            if (geometries.containsKey(layer)) {
                geometries.get(layer).add(this.spatialSignsList.get(i));
            } else {
                geometries.put(layer, new ArrayList<SpatialObject>());
                geometries.get(layer).add(this.spatialSignsList.get(i));
            }
        }

        for (Integer i : this.spatialSoilList.keySet()) {
            Integer layer = this.spatialSoilList.get(i).getLayer();
            if (geometries.containsKey(layer)) {
                geometries.get(layer).add(this.spatialSoilList.get(i));
            } else {
                geometries.put(layer, new ArrayList<SpatialObject>());
                geometries.get(layer).add(this.spatialSoilList.get(i));
            }
        }

        for (Integer i : this.spatialWaterList.keySet()) {
            Integer layer = this.spatialWaterList.get(i).getLayer();
            if (geometries.containsKey(layer)) {
                geometries.get(layer).add(this.spatialWaterList.get(i));
            } else {
                geometries.put(layer, new ArrayList<SpatialObject>());
                geometries.get(layer).add(this.spatialWaterList.get(i));
            }
        }

        /* Reconstruct result array */
        for (Integer i : geometries.keySet()) {
            result.addAll(geometries.get(i));
        }

        /* Return result */
        return result;
    }

    /**
     * Return a list of geometries in specific layer (e.g. to display only
     * specific layer). The list is sorted in draw order (first object - first
     * to draw)
     *
     *
     * @param _layer Id of layer to draw
     * @return List of geometries in layer determined by param
     */
    public ArrayList<SpatialObject> getGeometries(int _layer) {
        Map<Integer, ArrayList<SpatialObject>> geometries = new HashMap<Integer, ArrayList<SpatialObject>>();

        for (Integer i : this.spatialBedsList.keySet()) {
            Integer layer = this.spatialBedsList.get(i).getLayer();
            if (geometries.containsKey(layer)) {
                geometries.get(layer).add(this.spatialBedsList.get(i));
            } else {
                geometries.put(layer, new ArrayList<SpatialObject>());
                geometries.get(layer).add(this.spatialBedsList.get(i));
            }
        }

        for (Integer i : this.spatialFencesList.keySet()) {
            Integer layer = this.spatialFencesList.get(i).getLayer();
            if (geometries.containsKey(layer)) {
                geometries.get(layer).add(this.spatialFencesList.get(i));
            } else {
                geometries.put(layer, new ArrayList<SpatialObject>());
                geometries.get(layer).add(this.spatialFencesList.get(i));
            }
        }

        for (Integer i : this.spatialPathsList.keySet()) {
            Integer layer = this.spatialPathsList.get(i).getLayer();
            if (geometries.containsKey(layer)) {
                geometries.get(layer).add(this.spatialPathsList.get(i));
            } else {
                geometries.put(layer, new ArrayList<SpatialObject>());
                geometries.get(layer).add(this.spatialPathsList.get(i));
            }
        }

        for (Integer i : this.spatialSignsList.keySet()) {
            Integer layer = this.spatialSignsList.get(i).getLayer();
            if (geometries.containsKey(layer)) {
                geometries.get(layer).add(this.spatialSignsList.get(i));
            } else {
                geometries.put(layer, new ArrayList<SpatialObject>());
                geometries.get(layer).add(this.spatialSignsList.get(i));
            }
        }

        for (Integer i : this.spatialSoilList.keySet()) {
            Integer layer = this.spatialSoilList.get(i).getLayer();
            if (geometries.containsKey(layer)) {
                geometries.get(layer).add(this.spatialSoilList.get(i));
            } else {
                geometries.put(layer, new ArrayList<SpatialObject>());
                geometries.get(layer).add(this.spatialSoilList.get(i));
            }
        }

        for (Integer i : this.spatialWaterList.keySet()) {
            Integer layer = this.spatialWaterList.get(i).getLayer();
            if (geometries.containsKey(layer)) {
                geometries.get(layer).add(this.spatialWaterList.get(i));
            } else {
                geometries.put(layer, new ArrayList<SpatialObject>());
                geometries.get(layer).add(this.spatialWaterList.get(i));
            }
        }

        /* Get correct layer and return */
        if (geometries.containsKey(_layer)) {
            return geometries.get(_layer);
        } else {
            return new ArrayList<SpatialObject>();
        }
    }

    /**
     * Mark the object on given coordinates as selected and return object type.
     *
     * @return Type of selected object or -1 if no object on given coordinates
     */
    public Integer selectObject(/*coordinates of object*/) {
        //TODO determine what object is at the given coordinates and set its "selected" flag
        return -1;
    }

    /**
     * Clear the selected flag from all the objects.
     */
    public void deselectAll() {
//      this.selected = null;

        for (Integer i : this.spatialBedsList.keySet()) {
            this.spatialBedsList.get(i).setSelection(false);
        }

        for (Integer i : this.spatialFencesList.keySet()) {
            this.spatialFencesList.get(i).setSelection(false);
        }

        for (Integer i : this.spatialPathsList.keySet()) {
            this.spatialPathsList.get(i).setSelection(false);
        }

        for (Integer i : this.spatialSignsList.keySet()) {
            this.spatialSignsList.get(i).setSelection(false);
        }

        for (Integer i : this.spatialSoilList.keySet()) {
            this.spatialSoilList.get(i).setSelection(false);
        }

        for (Integer i : this.spatialWaterList.keySet()) {
            this.spatialWaterList.get(i).setSelection(false);
        }

    }

    /**
     * Mark objects given in parameter as selected.
     *
     * @param _obj List of SpatialObject to mark as selected
     */
    public void setTheseAsSelected(ArrayList<SpatialObject> _obj) {
        this.deselectAll();

        for (SpatialObject o : _obj) {
            if (o instanceof BedsObject) {
                this.getBed(o.getId()).setSelection(true);
            } else if (o instanceof FencesObject) {
                this.getFence(o.getId()).setSelection(true);
            } else if (o instanceof PathObject) {
                this.getPath(o.getId()).setSelection(true);
            } else if (o instanceof SignObject) {
                this.getSign(o.getId()).setSelection(true);
            } else if (o instanceof SoilObject) {
                this.getSoil(o.getId()).setSelection(true);
            } else if (o instanceof WaterObject) {
                this.getWater(o.getId()).setSelection(true);
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

    public SpatialObject getHovered() {
        return this.hovered;
    }

    public void setSelected(SpatialObject so) {
        this.selected = so;
    }

    public void setHovered(SpatialObject so) {
        this.hovered = so;
    }

    /**
     * Invoke commit all the queries in query queue.
     */
    public void commitToDB() {
        this.db.commit();
    }

    /**
     * Add given object to appropriate container and add a record to SQL queue.
     *
     * @param _obj Object to add
     */
    public void store(BedsObject _obj) {
        if (_obj.getId() != -1) {
            this.db.delete(_obj);
            this.spatialBedsList.remove(_obj.getId());
            _obj.setId(-1);
        }

        this.db.insert(_obj);
        this.spatialBedsList.put(_obj.getId(), _obj);
    }

    /**
     * Add given object to appropriate container and add a record to SQL queue.
     *
     * @param _obj Object to add
     */
    public void store(FencesObject _obj) {
        if (_obj.getId() != -1) {
            this.db.delete(_obj);
            this.spatialFencesList.remove(_obj.getId());
            _obj.setId(-1);
        }

        this.db.insert(_obj);
        this.spatialFencesList.put(_obj.getId(), _obj);
    }

    /**
     * Add given object to appropriate container and add a record to SQL queue.
     *
     * @param _obj Object to add
     */
    public void store(PathObject _obj) {
        if (_obj.getId() != -1) {
            this.db.update(_obj);
        } else {
            this.db.insert(_obj);
        }
        this.spatialPathsList.put(_obj.getId(), _obj);
    }

    /**
     * Add given object to appropriate container and add a record to SQL queue.
     *
     * @param _obj Object to add
     */
    public void store(SignObject _obj) {
        if (_obj.getId() != -1) {
            this.db.delete(_obj);
            this.spatialBedsList.remove(_obj.getId());
            _obj.setId(-1);
        }

        this.db.insert(_obj);
        this.spatialSignsList.put(_obj.getId(), _obj);
    }

    /**
     * Add given object to appropriate container and add a record to SQL queue.
     *
     * @param _obj Object to add
     */
    public void store(SoilObject _obj) {
        if (_obj.getId() != -1) {
            this.db.update(_obj);
        } else {
            this.db.insert(_obj);
        }

        this.spatialSoilList.put(_obj.getId(), _obj);
    }

    /**
     * Add given object to appropriate container and add a record to SQL queue.
     *
     * @param _obj Object to add
     */
    public void store(WaterObject _obj) {
        if (_obj.getId() != -1) {
            this.db.update(_obj);
        } else {
            this.db.insert(_obj);
        }

        this.spatialWaterList.put(_obj.getId(), _obj);
    }

    /**
     * Remove given object from appropriate container and add a record to SQL
     * queue.
     *
     * @param _obj Object to remove
     */
    public void delete(BedsObject _obj) {
        if (this.spatialBedsList.containsKey(_obj.getId())) {
            this.spatialBedsList.remove(_obj.getId());
            this.db.delete(_obj);
        }
    }

    /**
     * Remove given object from appropriate container and add a record to SQL
     * queue.
     *
     * @param _obj Object to remove
     */
    public void delete(FencesObject _obj) {
        if (this.spatialFencesList.containsKey(_obj.getId())) {
            this.spatialFencesList.remove(_obj.getId());
            this.db.delete(_obj);
        }
    }

    /**
     * Remove given object from appropriate container and add a record to SQL
     * queue.
     *
     * @param _obj Object to remove
     */
    public void delete(PathObject _obj) {
        if (this.spatialPathsList.containsKey(_obj.getId())) {
            this.spatialPathsList.remove(_obj.getId());
            this.db.delete(_obj);
        }
    }

    /**
     * Remove given object from appropriate container and add a record to SQL
     * queue.
     *
     * @param _obj Object to remove
     */
    public void delete(SignObject _obj) {
        if (this.spatialSignsList.containsKey(_obj.getId())) {
            this.spatialSignsList.remove(_obj.getId());
            this.db.delete(_obj);
        }
    }

    /**
     * Remove given object from appropriate container and add a record to SQL
     * queue.
     *
     * @param _obj Object to remove
     */
    public void delete(SoilObject _obj) {
        if (this.spatialSoilList.containsKey(_obj.getId())) {
            this.spatialSoilList.remove(_obj.getId());
            this.db.delete(_obj);
        }
    }

    /**
     * Remove given object from appropriate container and add a record to SQL
     * queue.
     *
     * @param _obj Object to remove
     */
    public void delete(WaterObject _obj) {
        if (this.spatialWaterList.containsKey(_obj.getId())) {
            this.spatialWaterList.remove(_obj.getId());
            this.db.delete(_obj);
        }
    }

    public void checkHovering(Point p, AffineTransform at) {
        ArrayList<SpatialObject> allItems = getGeometries();
        boolean haveSelected = false;
        for (int j = allItems.size() - 1; j >= 0; j--) {
            if (haveSelected == false) {
                haveSelected = allItems.get(j).hoverIfMouseOver(p, at);
                if (haveSelected) {
                    this.hovered = allItems.get(j);
                } else {
                    this.hovered = null;
                }
            } else {
                allItems.get(j).setHovering(false);
            }
        }
    }
}
