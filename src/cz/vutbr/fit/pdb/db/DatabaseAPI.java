package cz.vutbr.fit.pdb.db;

import cz.vutbr.fit.pdb.model.SignObject;
import cz.vutbr.fit.pdb.model.BedsObject;
import cz.vutbr.fit.pdb.model.DataObject;
import cz.vutbr.fit.pdb.model.FencesObject;
import cz.vutbr.fit.pdb.model.LayersObject;
import cz.vutbr.fit.pdb.model.PathObject;
import cz.vutbr.fit.pdb.model.PlantTypeObject;
import cz.vutbr.fit.pdb.model.PlantsObject;
import cz.vutbr.fit.pdb.model.SoilObject;
import cz.vutbr.fit.pdb.model.SoilTypeObject;
import cz.vutbr.fit.pdb.model.SpatialObject;
import cz.vutbr.fit.pdb.model.WaterObject;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;
import oracle.ord.im.OrdImage;

/**
 * Database application program interface. Contains methods to access the DB and
 * connector to execute queries
 *
 * @author Martin Simon
 */
public class DatabaseAPI {

    Connector connector;
    Integer highestID;

    /**
     * Constructor of DatabaseAPI.
     *
     * @param login Username to access database
     * @param password Password to access database
     */
    public DatabaseAPI(String login, String password) {
        this.connector = new Connector(login, password);
        this.highestID = -1;
    }

    /**
     * Add query given in parameter to query queue
     *
     * @param query SQL query
     */
    private void addQuery(String query) {
        this.connector.addQuery(query);
    }


    /**
     * Reset database (remote) data to the initial state. Use default sql script
     * path
     *
     * @see Connector#predefinedPath
     */
    public void resetDBData() {
        this.connector.resetData(null);

        ArrayList<DataObject> plants = this.getPlantsAll();
        for (DataObject o : plants) {
            File f = new File("assets/pict/" + o.getName() + ".jpg");
            if (f.exists()) {
                /* Add image */
                this.connector.storeImage((PlantsObject) o, f.getPath());
            } else {
                System.out.println("File assets/pict/" + o.getName() + ".jpg not found (" + f.getPath() + ")");
            }

        }
    }

    /**
     * Reset database (remote) data to the initial state. Use sql script
     * determined by given path.
     *
     * @param _path Path to the sql script with initial data
     */
    public void resetDBData(String _path) {
        this.connector.resetData(_path);

        ArrayList<DataObject> plants = this.getPlantsAll();
        for (DataObject o : plants) {
            File f = new File("assets/pict/" + o.getName() + ".jpg");
            if (f.exists()) {
                /* Add image */
                this.connector.storeImage((PlantsObject) o, f.getPath());
            } else {
                System.out.println("File assets/pict/" + o.getName() + ".jpg not found (" + f.getPath() + ")");
            }
        }
    }
    ///////////////////////////////////////////////////////////////////////////
    /* Set of update queries                                                 */
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Generate query to update object in DB and append it to the internal
     * queries stack
     *
     * @param _obj Object to update
     */
    public void update(BedsObject _obj) {
        this.addQuery(_obj.getUpdateSQL());
    }

    /**
     * Generate query to update object in DB and append it to the internal
     * queries stack
     *
     * @param _obj Object to update
     */
    public void update(SoilObject _obj) {
        this.addQuery(_obj.getUpdateSQL());
    }

    /**
     * Generate query to update object in DB and append it to the internal
     * queries stack
     *
     * @param _obj Object to update
     */
    public void update(PathObject _obj) {
        this.addQuery(_obj.getUpdateSQL());
    }

    /**
     * Generate query to update object in DB and append it to the internal
     * queries stack
     *
     * @param _obj Object to update
     */
    public void update(WaterObject _obj) {
        this.addQuery(_obj.getUpdateSQL());
    }

    /**
     * Generate query to update object in DB and append it to the internal
     * queries stack
     *
     * @param _obj Object to update
     */
    public void update(FencesObject _obj) {
        this.addQuery(_obj.getUpdateSQL());
    }

    /**
     * Generate query to update object in DB and append it to the internal
     * queries stack
     *
     * @param _obj Object to update
     */
    public void update(SignObject _obj) {
        this.addQuery(_obj.getUpdateSQL());
    }

    /**
     * Generate query to update object in DB and append it to the internal
     * queries stack. This method also contains multimedia upload.
     *
     * @param _obj Object to update
     */
    public void update(PlantsObject _obj) {
        this.addQuery(_obj.getUpdateSQL());

        if (_obj.isImgChanged()) {
            this.connector.storeImage(_obj, null);
        }
    }

    /**
     * Generate query to update object in DB and append it to the internal
     * queries stack
     *
     * @param _obj Object to update
     */
    public void update(DataObject _obj) {
        this.addQuery(_obj.getUpdateSQL());
    }

    ///////////////////////////////////////////////////////////////////////////
    /* Set of insert queries                                                 */
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Generate query to insert object into DB and append it to the internal
     * queries stack
     *
     * @param _obj Object to insert
     */
    public void insert(BedsObject _obj) {
        if (this.highestID == -1) {
            Integer id = this.connector.executeQueryWithResultsInteger(_obj.getHighestIDSQL()).get(0);
            //id++;
            this.highestID = id;
        }
        _obj.setId(++this.highestID);
        this.addQuery(_obj.getInsertSQL());
    }

    /**
     * Generate query to insert object into DB and append it to the internal
     * queries stack
     *
     * @param _obj Object to insert
     */
    public void insert(SoilObject _obj) {
        if (this.highestID == -1) {
            Integer id = this.connector.executeQueryWithResultsInteger(_obj.getHighestIDSQL()).get(0);
            //id++;
            this.highestID = id;
        }
        _obj.setId(++this.highestID);
        this.addQuery(_obj.getInsertSQL());
    }

    /**
     * Generate query to insert object into DB and append it to the internal
     * queries stack
     *
     * @param _obj Object to insert
     */
    public void insert(PathObject _obj) {
        if (this.highestID == -1) {
            Integer id = this.connector.executeQueryWithResultsInteger(_obj.getHighestIDSQL()).get(0);
            //id++;
            this.highestID = id;
        }
        _obj.setId(++this.highestID);
        this.addQuery(_obj.getInsertSQL());
    }

    /**
     * Generate query to insert object into DB and append it to the internal
     * queries stack
     *
     * @param _obj Object to insert
     */
    public void insert(WaterObject _obj) {
        if (this.highestID == -1) {
            Integer id = this.connector.executeQueryWithResultsInteger(_obj.getHighestIDSQL()).get(0);
            //id++;
            this.highestID = id;
        }
        _obj.setId(++this.highestID);
        this.addQuery(_obj.getInsertSQL());
    }

    /**
     * Generate query to insert object into DB and append it to the internal
     * queries stack
     *
     * @param _obj Object to insert
     */
    public void insert(FencesObject _obj) {
        if (this.highestID == -1) {
            Integer id = this.connector.executeQueryWithResultsInteger(_obj.getHighestIDSQL()).get(0);
            //id++;
            this.highestID = id;
        }
        _obj.setId(++this.highestID);
        this.addQuery(_obj.getInsertSQL());
    }

    /**
     * Generate query to insert object into DB and append it to the internal
     * queries stack
     *
     * @param _obj Object to insert
     */
    public void insert(SignObject _obj) {
        if (this.highestID == -1) {
            Integer id = this.connector.executeQueryWithResultsInteger(_obj.getHighestIDSQL()).get(0);
            //id++;
            this.highestID = id;
        }
        _obj.setId(++this.highestID);
        this.addQuery(_obj.getInsertSQL());
    }

    /**
     * Generate query to insert object into DB and append it to the internal
     * queries stack
     *
     * @param _obj Object to insert
     */
    public void insert(PlantsObject _obj) {
        if (this.highestID == -1) {
            Integer id = this.connector.executeQueryWithResultsInteger(_obj.getHighestIDSQL()).get(0);
            //id++;
            this.highestID = id;
        }
        _obj.setId(++this.highestID);
        this.addQuery(_obj.getInsertSQL());

        if (_obj.isImgChanged()) {
            this.connector.storeImage(_obj, null);
        }
    }

    /**
     * Generate query to insert object into DB and append it to the internal
     * queries stack
     *
     * @param _obj Object to insert
     */
    public void insert(DataObject _obj) {
        if (this.highestID == -1) {
            Integer id = this.connector.executeQueryWithResultsInteger(_obj.getHighestIDSQL()).get(0);
            //id++;
            this.highestID = id;
        }
        _obj.setId(++this.highestID);
        this.addQuery(_obj.getInsertSQL());
    }

    ///////////////////////////////////////////////////////////////////////////
    /* Set of delete queries                                                 */
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Generate query to delete object from DB and append it to the internal
     * queries stack
     *
     * @param _obj Object to delete
     */
    public void delete(BedsObject _obj) {
        this.addQuery(_obj.getDeleteSQL());
    }

    /**
     * Generate query to delete object from DB and append it to the internal
     * queries stack
     *
     * @param _obj Object to delete
     */
    public void delete(SoilObject _obj) {
        this.addQuery(_obj.getDeleteSQL());
    }

    /**
     * Generate query to delete object from DB and append it to the internal
     * queries stack
     *
     * @param _obj Object to delete
     */
    public void delete(PathObject _obj) {
        this.addQuery(_obj.getDeleteSQL());
    }

    /**
     * Generate query to delete object from DB and append it to the internal
     * queries stack
     *
     * @param _obj Object to delete
     */
    public void delete(WaterObject _obj) {
        this.addQuery(_obj.getDeleteSQL());
    }

    /**
     * Generate query to delete object from DB and append it to the internal
     * queries stack
     *
     * @param _obj Object to delete
     */
    public void delete(FencesObject _obj) {
        this.addQuery(_obj.getDeleteSQL());
    }

    /**
     * Generate query to delete object from DB and append it to the internal
     * queries stack
     *
     * @param _obj Object to delete
     */
    public void delete(SignObject _obj) {
        this.addQuery(_obj.getDeleteSQL());
    }

    /**
     * Generate query to delete object from DB and append it to the internal
     * queries stack
     *
     * @param _obj Object to delete
     */
    public void delete(DataObject _obj) {
        this.addQuery(_obj.getDeleteSQL());
    }

    /**
     * Commit all update/insert/delete queries in the stack to the database.
     */
    public void commit() {
        this.highestID = -1;
        this.connector.executeQueries();
    }

    ///////////////////////////////////////////////////////////////////////////
    /* Set of select by id queries                                           */
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Get a beds object determined by id from database
     *
     * @param id Identificator of the object
     * @return Java object or null if
     */
    public BedsObject getBedsByID(int id) {
        try {
            ArrayList<SpatialObject> lst = this.connector.executeQueryWithResultsBeds(new BedsObject().getSelectSQL(id));
            if (lst.isEmpty()) {
                /* The list is empty */
                return null;
            } else {
                /* The list is not empty */
                return (BedsObject) lst.get(0);
            }
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            System.out.println("Exception: " + e.getMessage());
            return null;
        }
    }

    /**
     * Get a soil object determined by id from database
     *
     * @param id Identificator of the object
     * @return Java object or null if
     */
    public SoilObject getSoilByID(int id) {
        try {
            ArrayList<SpatialObject> lst = this.connector.executeQueryWithResultsSoil(new SoilObject().getSelectSQL(id));
            if (lst.isEmpty()) {
                /* The list is empty */
                return null;
            } else {
                /* The list is not empty */
                return (SoilObject) lst.get(0);
            }
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            System.out.println("Exception: " + e.getMessage());
            return null;
        }
    }

    /**
     * Get a path object determined by id from database
     *
     * @param id Identificator of the object
     * @return Java object or null if
     */
    public PathObject getPathByID(int id) {
        try {
            ArrayList<SpatialObject> lst = this.connector.executeQueryWithResultsPath(new PathObject().getSelectSQL(id));
            if (lst.isEmpty()) {
                /* The list is empty */
                return null;
            } else {
                /* The list is not empty */
                return (PathObject) lst.get(0);
            }
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            System.out.println("Exception: " + e.getMessage());
            return null;
        }
    }

    /**
     * Get a water object determined by id from database
     *
     * @param id Identificator of the object
     * @return Java object or null if
     */
    public WaterObject getWaterByID(int id) {
        try {
            ArrayList<SpatialObject> lst = this.connector.executeQueryWithResultsWater(new WaterObject().getSelectSQL(id));
            if (lst.isEmpty()) {
                /* The list is empty */
                return null;
            } else {
                /* The list is not empty */
                return (WaterObject) lst.get(0);
            }
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            System.out.println("Exception: " + e.getMessage());
            return null;
        }
    }

    /**
     * Get a fences object determined by id from database
     *
     * @param id Identificator of the object
     * @return Java object or null if
     */
    public FencesObject getFencesByID(int id) {
        try {
            ArrayList<SpatialObject> lst = this.connector.executeQueryWithResultsFences(new FencesObject().getSelectSQL(id));
            if (lst.isEmpty()) {
                /* The list is empty */
                return null;
            } else {
                /* The list is not empty */
                return (FencesObject) lst.get(0);
            }
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            System.out.println("Exception: " + e.getMessage());
            return null;
        }
    }

    /**
     * Get a sign object determined by id from database
     *
     * @param id Identificator of the object
     * @return Java object or null if
     */
    public SignObject getSignByID(int id) {
        try {
            ArrayList<SpatialObject> lst = this.connector.executeQueryWithResultsSign(new SignObject().getSelectSQL(id));
            if (lst.isEmpty()) {
                /* The list is empty */
                return null;
            } else {
                /* The list is not empty */
                return (SignObject) lst.get(0);
            }
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            System.out.println("Exception: " + e.getMessage());
            return null;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    /* Set of select all queries                                             */
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Get all the beds objects from DB and return a list of their Java
     * representatives
     *
     * @return List of BedsObject objects
     */
    public ArrayList<SpatialObject> getBedsAll() {
        return this.connector.executeQueryWithResultsBeds(new BedsObject().getAllSQL());
    }

    /**
     * Get all the soil objects from DB and return a list of their Java
     * representatives
     *
     * @return List of SpatialObject objects
     */
    public ArrayList<SpatialObject> getSoilAll() {
        return this.connector.executeQueryWithResultsSoil(new SoilObject().getAllSQL());
    }

    /**
     * Get all the path objects from DB and return a list of their Java
     * representatives
     *
     * @return List of SpatialObject objects
     */
    public ArrayList<SpatialObject> getPathAll() {
        return this.connector.executeQueryWithResultsPath(new PathObject().getAllSQL());
    }

    /**
     * Get all the water objects from DB and return a list of their Java
     * representatives
     *
     * @return List of SpatialObject objects
     */
    public ArrayList<SpatialObject> getWaterAll() {
        return this.connector.executeQueryWithResultsWater(new WaterObject().getAllSQL());
    }

    /**
     * Get all the fences objects from DB and return a list of their Java
     * representatives
     *
     * @return List of SpatialObject objects
     */
    public ArrayList<SpatialObject> getFencesAll() {
        return this.connector.executeQueryWithResultsFences(new FencesObject().getAllSQL());
    }

    /**
     * Get all the sign objects from DB and return a list of their Java
     * representatives
     *
     * @return List of SpatialObject objects
     */
    public ArrayList<SpatialObject> getSignAll() {
        return this.connector.executeQueryWithResultsSign(new SignObject().getAllSQL());
    }

    /**
     * Get all the layers objects from DB and return a list of their Java
     * representatives
     *
     * @return List of DataObject objects
     */
    public ArrayList<DataObject> getLayersAll() {
        return this.connector.executeQueryWithResultsLayers(new LayersObject().getAllSQL());
    }

    /**
     * Get all the soil_type objects from DB and return a list of their Java
     * representatives
     *
     * @return List of DataObject objects
     */
    public ArrayList<DataObject> getSoilTypeAll() {
        return this.connector.executeQueryWithResultsSoilType(new SoilTypeObject().getAllSQL());
    }

    /**
     * Get all the plant_type objects from DB and return a list of their Java
     * representatives
     *
     * @return List of DataObject objects
     */
    public ArrayList<DataObject> getPlantTypeAll() {
        return this.connector.executeQueryWithResultsPlantType(new PlantTypeObject().getAllSQL());
    }

    /**
     * Get all the plants objects from DB and return a list of their Java
     * representatives
     *
     * @return List of PlantsObject objects
     */
    public ArrayList<DataObject> getPlantsAll() {
        return this.connector.executeQueryWithResultsPlants(new PlantsObject().getAllSQL());
    }

    ///////////////////////////////////////////////////////////////////////////
    // Specific queries                                                      //
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Get original image stored in database by given object, store and return
     * it.
     *
     * @param o Object which is looking for image
     * @return Image which belongs to the object or null if there is no such an
     * object. The object is stored too, if it is not null
     */
    public OrdImage getPlantsImage(PlantsObject o) {
        OrdImage img = this.connector.getImage(o.getImageSQL());
        if (img != null) {
            o.setImage(img);
        }
        return img;
    }

    /**
     * Get thumbnail of image which belongs to the object in parameter
     *
     * @param o Object whom image belongs
     * @return Thumbnailed image of determined object or null if there is no
     * such a object * stored in database
     */
    public OrdImage getPlantsImageThumb(PlantsObject o) {
        return this.connector.getImage(o.getImageThumbSQL());
    }

    /**
     * Get id of plant of most similar (in image level) to the object in
     * parameter
     *
     * @param o Object what finds the most similar
     * @return Most similar object (in image level) id or null if there is no
     * such an object
     */
    public Integer getMostSimilar(PlantsObject o) {
        return this.connector.getImageSimilar(o.getImageSimilarSQL());
    }

    ///////////////////////////////////////////////////////////////////////////
    // Advanced spatial queries                                              //
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Get list of DataObjects of plants which grows on soil. In given plant has
     * to be planType set and in given soil has to be set soilType.
     *
     * @param plant PlantObject instace with plantType attribute set
     * @param soil SoilObject instance with soilType set
     * @return List of data object
     */
    public ArrayList<Integer> getBedsBySoil(PlantsObject plant, SoilObject soil) {
        return this.getBedsBySoil(plant.getPlantType(), soil.getSoilType());
    }

    /**
     * Get list of ID's of beds what grows on given soil. Return all occurencies
     * of given plantType on given soilType.
     *
     * @param plantType Id of plantType (from database)
     * @param soilType Id of soilType (from database)
     * @return List of id's of BedsObject (from database)
     */
    public ArrayList<Integer> getBedsBySoil(Integer plantType, Integer soilType) {
        String query
                = "SELECT b.id"
                + " FROM beds b"
                + " WHERE b.plant IN (SELECT id FROM plants  WHERE plant_type = " + plantType + ")"
                + " AND EXISTS (SELECT 1 FROM soil s WHERE s.soil_type = " + soilType
                + " AND SDO_RELATE(s.geometry, b.geometry, 'mask=ANYINTERACT') = 'TRUE')";
        return this.connector.executeQueryWithResultsInteger(query);
    }

    /**
     * Get all beds which are borderer (at least partially) with any fence.
     *
     * @return List of id's of BedsObject (from database)
     */
    public ArrayList<Integer> getBedsBorderedWithFence() {
        String query
                = "SELECT b.id"
                + " FROM beds b"
                + " WHERE EXISTS (SELECT 1 FROM fences f"
                + " WHERE SDO_RELATE(f.geometry, b.geometry, 'mask=ANYINTERACT') = 'TRUE')";
        return this.connector.executeQueryWithResultsInteger(query);
    }

    /**
     * Get distance between two beds determined by id
     *
     * @param b1 ID of first bed (ID from database)
     * @param b2 ID of second bed (ID from database)
     * @return Distance in points or -1 if fail or no such objects
     */
    public Integer getDistance(Integer b1, Integer b2) {
        String query
                = "SELECT SDO_GEOM.SDO_DISTANCE(a.geometry, b.geometry, 1) id"
                + " FROM beds a, beds b"
                + " WHERE a.id = " + b1 + " AND b.id = " + b2;
        ArrayList<Integer> lst = this.connector.executeQueryWithResultsInteger(query);
        if (lst.isEmpty()) {
            return -1;
        } else {
            return lst.get(0);
        }
    }

    /**
     * Get a list of the biggest beds of flowers on the map.
     *
     * @return List of ID's of the biggest beds of flower
     */
    public ArrayList<Integer> getBiggestBed() {
        String query
                = "SELECT b.id"
                + " FROM beds b"
                + " WHERE b.plant IN (SELECT id FROM plants WHERE plant_type = (SELECT id from plant_type WHERE name = 'flower'))"
                + " AND NOT EXISTS"
                + "	(SELECT 1 FROM beds comp"
                + "	WHERE (SDO_GEOM.SDO_AREA(comp.geometry, 1) > SDO_GEOM.SDO_AREA(b.geometry, 1)) AND (b.id <> comp.id))";
        return this.connector.executeQueryWithResultsInteger(query);
    }

    /**
     * Get count of unique plants that grow on given soil.
     *
     * @param plantType Type of plant you want to count
     * @param soilType Type of soil you want to search in
     * @return
     */
    public Integer getCountOfUniquePlantOnSoil(Integer plantType, Integer soilType) {
        String query
                = "SELECT COUNT(p.id) id"
                + " FROM plants p"
                + " WHERE p.plant_type = " + plantType
                + " AND EXISTS (SELECT 1 FROM beds b, soil s WHERE p.id = b.plant"
                + " AND s.soil_type = " + soilType
                + " AND SDO_RELATE(b.geometry, s.geometry,'mask=ANYINTERACT') = 'TRUE')";

        ArrayList<Integer> lst = this.connector.executeQueryWithResultsInteger(query);
        if (lst.isEmpty()) {
            return -1;
        } else {
            return lst.get(0);
        }
    }
    /**
     * Get a list of the smallest beds of flowers on the map.
     *
     * @return List of ID's of the smallest beds of flower
     */
    public ArrayList<Integer> getSmallestBed() {
        String query
                = "SELECT b.id"
                + " FROM beds b"
                + " WHERE b.plant IN (SELECT id FROM plants WHERE plant_type = (SELECT id from plant_type WHERE name = 'flower'))"
                + " AND NOT EXISTS"
                + "	(SELECT 1 FROM beds comp"
                + "	WHERE comp.plant IN (SELECT id FROM plants WHERE plant_type = (SELECT id from plant_type WHERE name = 'flower'))"
                + " AND (SDO_GEOM.SDO_AREA(comp.geometry, 1) < SDO_GEOM.SDO_AREA(b.geometry, 1)) AND (b.id <> comp.id))";
        return this.connector.executeQueryWithResultsInteger(query);
    }


    /**
     * Get a list of plants that are independent on soil where they grow. It
     * means they grow on two or more different soil types.
     *
     * @param plantType The plant type you are interested in
     * @return List of all multisoil plants
     */
    public ArrayList<Integer> getMultisoilPlants(Integer plantType) {
        String query
                = "SELECT p.id"
                + " FROM plants p"
                + " WHERE p.plant_type = " + plantType
                + " AND EXISTS (SELECT 1 FROM beds b1, soil s1, beds b2, soil s2"
                + " WHERE p.id = b1.plant AND p.id = b2.plant AND s1.id <> s2.id"
                + " AND SDO_RELATE(b1.geometry, s1.geometry,'mask=ANYINTERACT') = 'TRUE'"
                + " AND SDO_RELATE(b2.geometry, s2.geometry,'mask=ANYINTERACT') = 'TRUE')";
        return this.connector.executeQueryWithResultsInteger(query);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Advanced temporal queries                                             //
    ///////////////////////////////////////////////////////////////////////////
}
