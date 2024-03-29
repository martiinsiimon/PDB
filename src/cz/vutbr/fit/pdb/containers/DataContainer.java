package cz.vutbr.fit.pdb.containers;

import cz.vutbr.fit.pdb.db.DatabaseAPI;
import cz.vutbr.fit.pdb.model.DataObject;
import cz.vutbr.fit.pdb.model.LayersObject;
import cz.vutbr.fit.pdb.model.PlantTypeObject;
import cz.vutbr.fit.pdb.model.PlantsObject;
import cz.vutbr.fit.pdb.model.SoilTypeObject;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Container of data objects
 *
 * @author Martin Simon <martiin.siimon@gmail.com>
 */
public class DataContainer {

    private final DatabaseAPI db;
    private final Map<Integer, ArrayList<DataObject>> dataObjectList;
    private final Map<String, Integer> str2id;
    private final HashMap<Integer, String> id2str;

    /**
     * Constructor of DataContainer. Variant with DatabaseAPI given in
     * parameter.
     *
     * @param _db DatabaseAPI reference to access database objects
     */
    public DataContainer(DatabaseAPI _db) {
        this.dataObjectList = new HashMap<Integer, ArrayList<DataObject>>();
        this.str2id = new HashMap<String, Integer>();
        this.id2str = new HashMap<Integer, String>();

        this.db = _db;
    }

    /**
     * Constructor of DataContainer. Variant with username and password in
     * parameter.
     *
     * @param login Username to access database
     * @param password Password to access database
     */
    public DataContainer(String login, String password) {
        this.dataObjectList = new HashMap<Integer, ArrayList<DataObject>>();
        this.str2id = new HashMap<String, Integer>();
        this.id2str = new HashMap<Integer, String>();

        this.db = new DatabaseAPI(login, password);
    }

    /**
     * Connect to DB and fill appropriate containers.
     */
    public void initialize() {
        if (!this.dataObjectList.isEmpty()) {
            /* Repeated initialization - need to clear the maps */
            this.dataObjectList.clear();
            this.str2id.clear();
            this.id2str.clear();
        }

        ArrayList<DataObject> tmp = this.db.getLayersAll();
        this.str2id.put("layers", 0);
        this.id2str.put(0, "layers");
        this.dataObjectList.put(0, tmp);

        tmp = this.db.getPlantTypeAll();
        this.str2id.put("plant_type", 1);
        this.id2str.put(1, "plant_type");
        this.dataObjectList.put(1, tmp);

        tmp = this.db.getPlantsAll();
        this.str2id.put("plants", 2);
        this.id2str.put(2, "plants");
        this.dataObjectList.put(2, tmp);

        tmp = this.db.getSoilTypeAll();
        this.str2id.put("soil_type", 3);
        this.id2str.put(3, "soil_type");
        this.dataObjectList.put(3, tmp);
    }

    /**
     * Get a list of layers data object
     *
     * @return List of DataObjects
     */
    public ArrayList<DataObject> getLayers() {
        return this.dataObjectList.get(this.str2id.get("layers"));
    }

    /**
     * Get DataObject specified by id (not id in array, but id in db).
     *
     * @param id Id of object in DB
     * @return Object with id given in parameter or null, if such an object does
     * not exists
     */
    public DataObject getLayers(Integer id) {
        for (DataObject o : this.dataObjectList.get(this.str2id.get("layers"))) {
            if (o.getId() == id) {
                return o;
            }
        }
        return null;
    }

    /**
     * Get DataObject specified by name.
     *
     * @param str Name of object in DB
     * @return Object with name given in parameter or null, if such an object
     * does * not exists
     */
    public DataObject getLayers(String str) {
        for (DataObject o : this.dataObjectList.get(this.str2id.get("layers"))) {
            if (o.getName().equals(str)) {
                return o;
            }
        }
        return null;
    }

    /**
     * Get a list of plant type data object
     *
     * @return List of DataObjects
     */
    public ArrayList<DataObject> getPlantType() {
        return this.dataObjectList.get(this.str2id.get("plant_type"));
    }

    /**
     * Get DataObject specified by id (not id in array, but id in db).
     *
     * @param id Id of object in DB
     * @return Object with name given in parameter or null, if such an object
     * does * not exists
     */
    public PlantTypeObject getPlantType(Integer id) {
        for (DataObject o : this.dataObjectList.get(this.str2id.get("plant_type"))) {
            if (o.getId() == id) {
                return (PlantTypeObject) o;
            }
        }
        return null;
    }

    /**
     * Get DataObject specified by name.
     *
     * @param str Name of object in DB
     * @return Object with name given in parameter or null, if such an object
     * does * not exists
     */
    public PlantTypeObject getPlantType(String str) {
        for (DataObject o : this.dataObjectList.get(this.str2id.get("plant_type"))) {
            if (o.getName().equals(str)) {
                return (PlantTypeObject) o;
            }
        }
        return null;
    }

    /**
     * Get a list of plants data object
     *
     * @return List of DataObjects
     */
    public ArrayList<DataObject> getPlants() {
        return this.dataObjectList.get(this.str2id.get("plants"));
    }

    /**
     * Get DataObject specified by id (not id in array, but id in db).
     *
     * @param id Id of object in DB
     * @return Object with id given in parameter or null, if such an object does
     * not exists
     */
    public PlantsObject getPlants(Integer id) {
        for (DataObject o : this.dataObjectList.get(this.str2id.get("plants"))) {
            if (o.getId() == id) {
                return (PlantsObject) o;
            }
        }
        return null;
    }

    /**
     * Get DataObject specified by name.
     *
     * @param str Name of object in DB
     * @return Object with name given in parameter or null, if such an object
     * does * not exists
     */
    public PlantsObject getPlants(String str) {
        for (DataObject o : this.dataObjectList.get(this.str2id.get("plants"))) {
            if (o.getName().equals(str)) {
                return (PlantsObject) o;
            }
        }
        return null;
    }

    /**
     * Get a list of soil type data object
     *
     * @return List of DataObjects
     */
    public ArrayList<DataObject> getSoilType() {
        return this.dataObjectList.get(this.str2id.get("soil_type"));
    }

    /**
     * Get DataObject specified by id (not id in array, but id in db).
     *
     * @param id Id of object in DB
     * @return Object with id given in parameter or null, if such an object does
     * not exists
     */
    public DataObject getSoilType(Integer id) {
        for (DataObject o : this.dataObjectList.get(this.str2id.get("soil_type"))) {
            if (o.getId() == id) {
                return o;
            }
        }
        return null;
    }

    /**
     * Get DataObject specified by name.
     *
     * @param str Name of object in DB
     * @return Object with id given in parameter or null, if such an object does
     * not exists
     */
    public DataObject getSoilType(String str) {
        for (DataObject o : this.dataObjectList.get(this.str2id.get("soil_type"))) {
            if (o.getName().equals(str)) {
                return o;
            }
        }
        return null;
    }

    /**
     * Get image (full resolution) of given object. The object has to be
     * initialized as PlansObject type.
     *
     * @param obj PlantObject which is looking for image
     * @return OrdImage of given object or null if fail
     */
    public BufferedImage getImage(PlantsObject obj) {
        if (obj == null) {
            return null;
        } else {
            return this.getImage(obj.getId());
        }
    }

    /**
     * Get image (full resolution) of given object. The object has to be
     * initialized as PlansObject type. Object is determined by its ID.
     *
     * @param id ID of PlantsObject
     * @return OrdImage of given object or null if fail
     */
    public BufferedImage getImage(Integer id) {
        DataObject obj = this.getPlants(id);
        if (obj == null || !(obj instanceof PlantsObject)) {
            /* There's no such and object */
            return null;
        }

        /* Return locally cashed image */
        if (((PlantsObject) obj).getImage() != null) {
            return ((PlantsObject) obj).getImage();
        }

        /* Get the object from db */
        BufferedImage img = db.getPlantsImage((PlantsObject) obj);

        /* Return result */
        return img;
    }

    /**
     * Get image (thumbnail) of given object. The object has to be initialized
     * as PlansObject type.
     *
     * @param obj PlantObject which is looking for image
     * @return OrdImage of given object or null if fail
     */
    public BufferedImage getImageThumbnail(PlantsObject obj) {
        if (obj == null) {
            return null;
        } else {
            return this.getImageThumbnail(obj.getId());
        }
    }

    /**
     * Get image (thumbnail) of given object. The object has to be initialized
     * as PlansObject type. Object is determined by its ID.
     *
     * @param id ID of PlantsObject
     * @return OrdImage of given object or null if fail
     */
    public BufferedImage getImageThumbnail(Integer id) {
        DataObject obj = this.getPlants(id);
        if (obj == null || !(obj instanceof PlantsObject)) {
            /* There's no such and object */
            return null;
        }

        /* Get the object from db */
        BufferedImage img = this.db.getPlantsImageThumb((PlantsObject) obj);

        /* Return result */
        return img;
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
    public void store(DataObject _obj) {
        Integer id;
        if (_obj instanceof LayersObject) {
            id = this.str2id.get("layers");
        } else if (_obj instanceof SoilTypeObject) {
            id = this.str2id.get("soil_type");
        } else if (_obj instanceof PlantsObject) {
            id = this.str2id.get("plants");
        } else if (_obj instanceof PlantTypeObject) {
            id = this.str2id.get("plant_type");
        } else {
            return;
        }
        if (_obj.getId() == -1) {
            /* Append SQL query to DB queue */
            if (_obj instanceof LayersObject) {
                this.db.insert((LayersObject) _obj);
            } else if (_obj instanceof SoilTypeObject) {
                this.db.insert((SoilTypeObject) _obj);
            } else if (_obj instanceof PlantsObject) {
                this.db.insert((PlantsObject) _obj);
            } else if (_obj instanceof PlantTypeObject) {
                this.db.insert((PlantTypeObject) _obj);
            }

            /* Store the object to the container */
            this.dataObjectList.get(id).add(_obj);
        } else {
            ArrayList<DataObject> lst = this.dataObjectList.get(id);
            for (DataObject obj : lst) {
                /* Find the correct record */
                if (obj.getId() == _obj.getId()) {
                    /* Update the record - common part*/
                    this.dataObjectList.get(id).get(lst.indexOf(obj)).setName(_obj.getName());

                    /* Update the record - type specific part */
                    if (_obj instanceof LayersObject) {
                        this.db.update((LayersObject) _obj);
                    } else if (_obj instanceof SoilTypeObject) {
                        this.db.update((SoilTypeObject) _obj);
                    } else if (_obj instanceof PlantsObject) {
                        ((PlantsObject) this.dataObjectList.get(id).get(lst.indexOf(obj))).setPlantType(((PlantsObject) _obj).getPlantType());
                        this.db.update((PlantsObject) _obj);
                    } else if (_obj instanceof PlantTypeObject) {
                        this.db.update((PlantTypeObject) _obj);
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
    public void delete(DataObject _obj) {
        Integer id;
        if (_obj instanceof LayersObject) {
            id = this.str2id.get("layers");
        } else if (_obj instanceof SoilTypeObject) {
            id = this.str2id.get("soil_type");
        } else if (_obj instanceof PlantsObject) {
            id = this.str2id.get("plants");
        } else if (_obj instanceof PlantTypeObject) {
            id = this.str2id.get("plant_type");
        } else {
            return;
        }

        ArrayList<DataObject> lst = this.dataObjectList.get(id);
        for (DataObject obj : lst) {
            /* Find the correct record */
            if (obj.getId() == _obj.getId()) {
                /* Remove the record */
                this.dataObjectList.get(id).remove(obj);

                /* Append SQL query to DB queue */
                this.db.delete(_obj);
                break;
            }
        }
    }
}
