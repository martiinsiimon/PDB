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

/**
 * Database application program interface. Contains methods to access the DB and
 * connector to execute queries
 *
 * @author Martin Simon
 */
public class DatabaseAPI {

    Connector connector;

    /**
     * Constructor of DatabaseAPI.
     *
     * @param login Username to access database
     * @param password Password to access database
     */
    public DatabaseAPI(String login, String password) {
        this.connector = new Connector(login, password);
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
     * Delete last query in query queue - step back.
     */
    public void deleteLastQuery() {
        this.connector.delCommit();
    }

    /**
     * Reset database (remote) data to the initial state.
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

        File f = new File("assets/pict/" + _obj.getName() + ".jpg");
        if (f.exists()) {
            /* Add image */
            this.connector.storeImage(_obj, f.getPath());
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
        this.addQuery(_obj.getInsertSQL());
    }

    /**
     * Generate query to insert object into DB and append it to the internal
     * queries stack
     *
     * @param _obj Object to insert
     */
    public void insert(SoilObject _obj) {
        this.addQuery(_obj.getInsertSQL());
    }

    /**
     * Generate query to insert object into DB and append it to the internal
     * queries stack
     *
     * @param _obj Object to insert
     */
    public void insert(PathObject _obj) {
        this.addQuery(_obj.getInsertSQL());
    }

    /**
     * Generate query to insert object into DB and append it to the internal
     * queries stack
     *
     * @param _obj Object to insert
     */
    public void insert(WaterObject _obj) {
        this.addQuery(_obj.getInsertSQL());
    }

    /**
     * Generate query to insert object into DB and append it to the internal
     * queries stack
     *
     * @param _obj Object to insert
     */
    public void insert(FencesObject _obj) {
        this.addQuery(_obj.getInsertSQL());
    }

    /**
     * Generate query to insert object into DB and append it to the internal
     * queries stack
     *
     * @param _obj Object to insert
     */
    public void insert(SignObject _obj) {
        this.addQuery(_obj.getInsertSQL());
    }

    /**
     * Generate query to insert object into DB and append it to the internal
     * queries stack
     *
     * @param _obj Object to insert
     */
    public void insert(PlantsObject _obj) {
        this.addQuery(_obj.getInsertSQL());

        File f = new File("assets/pict/" + _obj.getName() + ".jpg");
        if (f.exists()) {
            /* Add image */
            this.connector.storeImage(_obj, f.getPath());
        }
    }

    /**
     * Generate query to insert object into DB and append it to the internal
     * queries stack
     *
     * @param _obj Object to insert
     */
    public void insert(DataObject _obj) {
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
}
