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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.OracleResultSet;

/**
 *
 * @author Martin Simon
 */
public class DatabaseAPI {


    Connector connector;

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
     * Delete last query in query queue - step back
     */
    public void deleteLastQuery() {
        this.connector.delCommit();
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
     * Commit all update/insert/delete queries in the stack
     */
    public void commit() {
        this.connector.executeQuery();
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
            OracleResultSet rs = (OracleResultSet) this.connector.executeQueryWithResults(new BedsObject().getSelectSQL(id));
            if (rs == null || !rs.next()) {
                /* The ResultSet is empty */
                return null;
            } else {
                /* The ResultSet is not empty */
                return new BedsObject(rs);
            }
        } catch (java.sql.SQLSyntaxErrorException e) {
            System.out.println("Table of beds does not exists");
            return null;
        } catch (Exception e) {
            Logger.getLogger(DatabaseAPI.class.getName()).log(Level.SEVERE, null, e);
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
            OracleResultSet rs = (OracleResultSet) this.connector.executeQueryWithResults(new SoilObject().getSelectSQL(id));
            if (rs == null || !rs.next()) {
                /* The ResultSet is empty */
                return null;
            } else {
                /* The ResultSet is not empty */
                return new SoilObject(rs);
            }
        } catch (SQLException e) {
            System.out.println("Table of soils does not exists");
            return null;
        } catch (Exception e) {
            Logger.getLogger(DatabaseAPI.class.getName()).log(Level.SEVERE, null, e);
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
            OracleResultSet rs = (OracleResultSet) this.connector.executeQueryWithResults(new PathObject().getSelectSQL(id));
            if (rs == null || !rs.next()) {
                /* The ResultSet is empty */
                return null;
            } else {
                /* The ResultSet is not empty */
                return new PathObject(rs);
            }
        } catch (SQLException e) {
            System.out.println("Table of pathes does not exists");
            return null;
        } catch (Exception e) {
            Logger.getLogger(DatabaseAPI.class.getName()).log(Level.SEVERE, null, e);
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
            OracleResultSet rs = (OracleResultSet) this.connector.executeQueryWithResults(new WaterObject().getSelectSQL(id));
            if (rs == null || !rs.next()) {
                /* The ResultSet is empty */
                return null;
            } else {
                /* The ResultSet is not empty */
                return new WaterObject(rs);
            }
        } catch (SQLException e) {
            System.out.println("Table of water objects does not exists");
            return null;
        } catch (Exception e) {
            Logger.getLogger(DatabaseAPI.class.getName()).log(Level.SEVERE, null, e);
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
            OracleResultSet rs = (OracleResultSet) this.connector.executeQueryWithResults(new FencesObject().getSelectSQL(id));
            if (rs == null || !rs.next()) {
                /* The ResultSet is empty */
                return null;
            } else {
                /* The ResultSet is not empty */
                return new FencesObject(rs);
            }
        } catch (SQLException e) {
            System.out.println("Table of fences does not exists");
            return null;
        } catch (Exception e) {
            Logger.getLogger(DatabaseAPI.class.getName()).log(Level.SEVERE, null, e);
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
            OracleResultSet rs = (OracleResultSet) this.connector.executeQueryWithResults(new SignObject().getSelectSQL(id));
            if (rs == null || !rs.next()) {
                /* The ResultSet is empty */
                return null;
            } else {
                /* The ResultSet is not empty */
                return new SignObject(rs);
            }
        } catch (SQLException e) {
            System.out.println("Table of signs does not exists");
            return null;
        } catch (Exception e) {
            Logger.getLogger(DatabaseAPI.class.getName()).log(Level.SEVERE, null, e);
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
        try {
            OracleResultSet rs = (OracleResultSet) this.connector.executeQueryWithResults(new BedsObject().getAllSQL());
            if (rs == null || !rs.next()) {
                /* The ResultSet is empty */
                return new ArrayList<SpatialObject>();
            } else {
                /* The ResultSet is not empty */
                ArrayList<SpatialObject> lst = new ArrayList<SpatialObject>();

                /* Add the first object */
                lst.add(new BedsObject(rs));

                /* Add all the remaining objects */
                while (rs.next()) {
                    lst.add(new BedsObject(rs));
                }

                /* Return the list */
                return lst;
            }
        } catch (SQLException e) {
            System.out.println("Table of beds does not exists");
            return new ArrayList<SpatialObject>();
        } catch (Exception e) {
            Logger.getLogger(DatabaseAPI.class.getName()).log(Level.SEVERE, null, e);
            return new ArrayList<SpatialObject>();
        }
    }

    /**
     * Get all the soil objects from DB and return a list of their Java
     * representatives
     *
     * @return List of SpatialObject objects
     */
    public ArrayList<SpatialObject> getSoilAll() {
        try {
            OracleResultSet rs = (OracleResultSet) this.connector.executeQueryWithResults(new SoilObject().getAllSQL());
            if (rs == null || !rs.next()) {
                /* The ResultSet is empty */
                return new ArrayList<SpatialObject>();
            } else {
                /* The ResultSet is not empty */
                ArrayList<SpatialObject> lst = new ArrayList<SpatialObject>();

                /* Add the first object */
                lst.add(new SoilObject(rs));

                /* Add all the remaining objects */
                while (rs.next()) {
                    lst.add(new SoilObject(rs));
                }

                /* Return the list */
                return lst;
            }
        } catch (SQLException e) {
            System.out.println("Table of soils does not exists");
            return new ArrayList<SpatialObject>();
        } catch (Exception e) {
            Logger.getLogger(DatabaseAPI.class.getName()).log(Level.SEVERE, null, e);
            return new ArrayList<SpatialObject>();
        }
    }

    /**
     * Get all the path objects from DB and return a list of their Java
     * representatives
     *
     * @return List of SpatialObject objects
     */
    public ArrayList<SpatialObject> getPathAll() {
        try {
            OracleResultSet rs = (OracleResultSet) this.connector.executeQueryWithResults(new PathObject().getAllSQL());
            if (rs == null || !rs.next()) {
                /* The ResultSet is empty */
                return new ArrayList<SpatialObject>();
            } else {
                /* The ResultSet is not empty */
                ArrayList<SpatialObject> lst = new ArrayList<SpatialObject>();

                /* Add the first object */
                lst.add(new PathObject(rs));

                /* Add all the remaining objects */
                while (rs.next()) {
                    lst.add(new PathObject(rs));
                }

                /* Return the list */
                return lst;
            }
        } catch (SQLException e) {
            System.out.println("Table of pathes does not exists");
            return new ArrayList<SpatialObject>();
        } catch (Exception e) {
            Logger.getLogger(DatabaseAPI.class.getName()).log(Level.SEVERE, null, e);
            return new ArrayList<SpatialObject>();
        }
    }

    /**
     * Get all the water objects from DB and return a list of their Java
     * representatives
     *
     * @return List of SpatialObject objects
     */
    public ArrayList<SpatialObject> getWaterAll() {
        try {
            OracleResultSet rs = (OracleResultSet) this.connector.executeQueryWithResults(new WaterObject().getAllSQL());
            if (rs == null || !rs.next()) {
                /* The ResultSet is empty */
                return new ArrayList<SpatialObject>();
            } else {
                /* The ResultSet is not empty */
                ArrayList<SpatialObject> lst = new ArrayList<SpatialObject>();

                /* Add the first object */
                lst.add(new WaterObject(rs));

                /* Add all the remaining objects */
                while (rs.next()) {
                    lst.add(new WaterObject(rs));
                }

                /* Return the list */
                return lst;
            }
        } catch (SQLException e) {
            System.out.println("Table of water objects does not exists");
            return new ArrayList<SpatialObject>();
        } catch (Exception e) {
            Logger.getLogger(DatabaseAPI.class.getName()).log(Level.SEVERE, null, e);
            return new ArrayList<SpatialObject>();
        }
    }

    /**
     * Get all the fences objects from DB and return a list of their Java
     * representatives
     *
     * @return List of SpatialObject objects
     */
    public ArrayList<SpatialObject> getFencesAll() {
        try {
            OracleResultSet rs = (OracleResultSet) this.connector.executeQueryWithResults(new FencesObject().getAllSQL());
            if (rs == null || !rs.next()) {
                /* The ResultSet is empty */
                return new ArrayList<SpatialObject>();
            } else {
                /* The ResultSet is not empty */
                ArrayList<SpatialObject> lst = new ArrayList<SpatialObject>();

                /* Add the first object */
                lst.add(new FencesObject(rs));

                /* Add all the remaining objects */
                while (rs.next()) {
                    lst.add(new FencesObject(rs));
                }

                /* Return the list */
                return lst;
            }
        } catch (SQLException e) {
            System.out.println("Table of fences does not exists");
            return new ArrayList<SpatialObject>();
        } catch (Exception e) {
            Logger.getLogger(DatabaseAPI.class.getName()).log(Level.SEVERE, null, e);
            return new ArrayList<SpatialObject>();
        }
    }

    /**
     * Get all the sign objects from DB and return a list of their Java
     * representatives
     *
     * @return List of SpatialObject objects
     */
    public ArrayList<SpatialObject> getSignAll() {
        try {
            OracleResultSet rs = (OracleResultSet) this.connector.executeQueryWithResults(new SignObject().getAllSQL());
            if (rs == null || !rs.next()) {
                /* The ResultSet is empty */
                return new ArrayList<SpatialObject>();
            } else {
                /* The ResultSet is not empty */
                ArrayList<SpatialObject> lst = new ArrayList<SpatialObject>();

                /* Add the first object */
                lst.add(new SignObject(rs));

                /* Add all the remaining objects */
                while (rs.next()) {
                    lst.add(new SignObject(rs));
                }

                /* Return the list */
                return lst;
            }
        } catch (SQLException e) {
            System.out.println("Table of signs does not exists");
            return new ArrayList<SpatialObject>();
        } catch (Exception e) {
            Logger.getLogger(DatabaseAPI.class.getName()).log(Level.SEVERE, null, e);
            return new ArrayList<SpatialObject>();
        }
    }

    /**
     * Get all the layers objects from DB and return a list of their Java
     * representatives
     *
     * @return List of DataObject objects
     */
    public ArrayList<DataObject> getLayersAll() {
        try {
            OracleResultSet rs = (OracleResultSet) this.connector.executeQueryWithResults(new LayersObject().getAllSQL());
            if (rs == null || !rs.next()) {
                /* The ResultSet is empty */
                return new ArrayList<DataObject>();
            } else {
                /* The ResultSet is not empty */
                ArrayList<DataObject> lst = new ArrayList<DataObject>();

                /* Add the first object */
                lst.add(new LayersObject(rs));

                /* Add all the remaining objects */
                while (rs.next()) {
                    lst.add(new LayersObject(rs));
                }

                /* Return the list */
                return lst;
            }
        } catch (SQLException e) {
            System.out.println("Table of layers does not exists");
            return new ArrayList<DataObject>();
        } catch (Exception e) {
            Logger.getLogger(DatabaseAPI.class.getName()).log(Level.SEVERE, null, e);
            return new ArrayList<DataObject>();
        }
    }

    /**
     * Get all the soil_type objects from DB and return a list of their Java
     * representatives
     *
     * @return List of DataObject objects
     */
    public ArrayList<DataObject> getSoilTypeAll() {
        try {
            OracleResultSet rs = (OracleResultSet) this.connector.executeQueryWithResults(new SoilTypeObject().getAllSQL());
            if (rs == null || !rs.next()) {
                /* The ResultSet is empty */
                return new ArrayList<DataObject>();
            } else {
                /* The ResultSet is not empty */
                ArrayList<DataObject> lst = new ArrayList<DataObject>();

                /* Add the first object */
                lst.add(new SoilTypeObject(rs));

                /* Add all the remaining objects */
                while (rs.next()) {
                    lst.add(new SoilTypeObject(rs));
                }

                /* Return the list */
                return lst;
            }
        } catch (SQLException e) {
            System.out.println("Table of soil type does not exists");
            return new ArrayList<DataObject>();
        } catch (Exception e) {
            Logger.getLogger(DatabaseAPI.class.getName()).log(Level.SEVERE, null, e);
            return new ArrayList<DataObject>();
        }
    }

    /**
     * Get all the plant_type objects from DB and return a list of their Java
     * representatives
     *
     * @return List of DataObject objects
     */
    public ArrayList<DataObject> getPlantTypeAll() {
        try {
            OracleResultSet rs = (OracleResultSet) this.connector.executeQueryWithResults(new PlantTypeObject().getAllSQL());
            if (rs == null || !rs.next()) {
                /* The ResultSet is empty */
                return new ArrayList<DataObject>();
            } else {
                /* The ResultSet is not empty */
                ArrayList<DataObject> lst = new ArrayList<DataObject>();

                /* Add the first object */
                lst.add(new PlantTypeObject(rs));

                /* Add all the remaining objects */
                while (rs.next()) {
                    lst.add(new PlantTypeObject(rs));
                }

                /* Return the list */
                return lst;
            }
        } catch (SQLException e) {
            System.out.println("Table of plant_type does not exists");
            return new ArrayList<DataObject>();
        } catch (Exception e) {
            Logger.getLogger(DatabaseAPI.class.getName()).log(Level.SEVERE, null, e);
            return new ArrayList<DataObject>();
        }
    }

    /**
     * Get all the plants objects from DB and return a list of their Java
     * representatives
     *
     * @return List of PlantsObject objects
     */
    public ArrayList<DataObject> getPlantsAll() {
        try {
            OracleResultSet rs = (OracleResultSet) this.connector.executeQueryWithResults(new PlantsObject().getAllSQL());
            if (rs == null || !rs.next()) {
                /* The ResultSet is empty */
                return new ArrayList<DataObject>();
            } else {
                /* The ResultSet is not empty */
                ArrayList<DataObject> lst = new ArrayList<DataObject>();

                /* Add the first object */
                lst.add(new PlantsObject(rs));

                /* Add all the remaining objects */
                while (rs.next()) {
                    lst.add(new PlantsObject(rs));
                }

                /* Return the list */
                return lst;
            }
        } catch (SQLException e) {
            System.out.println("Table of plants does not exists");
            return new ArrayList<DataObject>();
        } catch (Exception e) {
            Logger.getLogger(DatabaseAPI.class.getName()).log(Level.SEVERE, null, e);
            return new ArrayList<DataObject>();
        }
    }
}
