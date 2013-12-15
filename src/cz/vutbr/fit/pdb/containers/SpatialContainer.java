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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import oracle.spatial.geometry.JGeometry;

/**
 * Container of spatial objects (those who have geometry). For correct function
 * it needs to be initialized together with DataContainer
 *
 * @author Martin Simon
 */
public class SpatialContainer {
    private final DatabaseAPI db;

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
    public void store(SpatialObject _obj) {
        if (_obj.getId() == -1) {
            /* New object without ID */
            ArrayList<SpatialObject> lst = this.spatialObjectList.get(_obj.getLayer());
            Integer lastId = -1;
            for (SpatialObject obj : lst) {
                if (lastId < obj.getId()) {
                    lastId = obj.getId();
                }
            }

            /* Set the new ID */
            _obj.setId(lastId++);

            /* Store the object to the container */
            this.spatialObjectList.get(_obj.getLayer()).add(_obj);

            /* Append SQL query to DB queue */
            if (_obj instanceof BedsObject) {
                this.db.insert((BedsObject) _obj);
            } else if (_obj instanceof FencesObject) {
                this.db.insert((FencesObject) _obj);
            } else if (_obj instanceof PathObject) {
                this.db.insert((PathObject) _obj);
            } else if (_obj instanceof SignObject) {
                this.db.insert((SignObject) _obj);
            } else if (_obj instanceof SoilObject) {
                this.db.insert((SoilObject) _obj);
            } else if (_obj instanceof WaterObject) {
                this.db.insert((WaterObject) _obj);
            }
        } else {
            ArrayList<SpatialObject> lst = this.spatialObjectList.get(_obj.getLayer());
            for (SpatialObject obj : lst) {
                /* Find the correct record */
                if (obj.getId() == _obj.getId()) {
                    /* Update the record - common part*/
                    this.spatialObjectList.get(_obj.getLayer()).get(lst.indexOf(obj)).setGeometry(_obj.getGeometry());

                    /* Update the record - type specific part */
                    if (_obj instanceof BedsObject) {
                        ((BedsObject) this.spatialObjectList.get(_obj.getLayer()).
                                get(lst.indexOf(obj))).setPlant(((BedsObject) _obj).getPlant());
                        this.db.update((BedsObject) _obj);
                    } else if (_obj instanceof FencesObject) {
                        this.db.update((FencesObject) _obj);
                    } else if (_obj instanceof PathObject) {
                        this.db.update((PathObject) _obj);
                    } else if (_obj instanceof SignObject) {
                        ((SignObject) this.spatialObjectList.get(_obj.getLayer()).
                                get(lst.indexOf(obj))).setDescription(((SignObject) _obj).getDescription());
                        ((SignObject) this.spatialObjectList.get(_obj.getLayer()).
                                get(lst.indexOf(obj))).setPlant(((SignObject) _obj).getPlant());
                        this.db.update((SignObject) _obj);
                    } else if (_obj instanceof SoilObject) {
                        ((SoilObject) this.spatialObjectList.get(_obj.getLayer()).
                                get(lst.indexOf(obj))).setSoilType(((SoilObject) _obj).getSoilType());
                        this.db.update((SoilObject) _obj);
                    } else if (_obj instanceof WaterObject) {
                        this.db.update((WaterObject) _obj);
                    }
                    break;
                }
            }
        }
    }


    /**
     * Remove given object from appropriate container and add a record to SQL
     * queue.
     *
     * @param _obj Object to remove
     */
    public void delete(SpatialObject _obj) {
        ArrayList<SpatialObject> lst = this.spatialObjectList.get(_obj.getLayer());
        for (SpatialObject obj : lst) {
            /* Find the correct record */
            if (obj.getId() == _obj.getId()) {
                /* Remove the record */
                this.spatialObjectList.get(_obj.getLayer()).remove(obj);

                /* Append SQL query by type to DB queue */
                if (_obj instanceof BedsObject) {
                    this.db.delete((BedsObject) _obj);
                } else if (_obj instanceof FencesObject) {
                    this.db.delete((FencesObject) _obj);
                } else if (_obj instanceof PathObject) {
                    this.db.delete((PathObject) _obj);
                } else if (_obj instanceof SignObject) {
                    this.db.delete((SignObject) _obj);
                } else if (_obj instanceof SoilObject) {
                    this.db.delete((SoilObject) _obj);
                } else if (_obj instanceof WaterObject) {
                    this.db.delete((WaterObject) _obj);
                }
                break;
            }
        }
    }

    /**
     * Remove object of given type and id from appropriate container and add a
     * record to SQL queue.
     *
     * @param _type Type of object
     * @param _id Id of object
     */
    public void delete(int _type, int _id) {
        SpatialObject _obj = this.spatialObjectList.get(_type).get(_id);
        this.spatialObjectList.get(_obj.getLayer()).remove(_obj);
        if (_obj instanceof BedsObject) {
            this.db.delete((BedsObject) _obj);
        } else if (_obj instanceof FencesObject) {
            this.db.delete((FencesObject) _obj);
        } else if (_obj instanceof PathObject) {
            this.db.delete((PathObject) _obj);
        } else if (_obj instanceof SignObject) {
            this.db.delete((SignObject) _obj);
        } else if (_obj instanceof SoilObject) {
            this.db.delete((SoilObject) _obj);
        } else if (_obj instanceof WaterObject) {
            this.db.delete((WaterObject) _obj);
        }

    }
}

