package cz.vutbr.fit.pdb.db;

import cz.vutbr.fit.pdb.model.SignObject;
import cz.vutbr.fit.pdb.containers.SpatialContainer;
import java.util.Iterator;
import java.util.List;
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
     * Build generic select query and add it to query queue
     *
     * @param from Table to select from
     * @param whatList List of columns to be returned
     * @param whereList List of expressions which must be met
     */
    public void selectQuery(String from, List<String> whatList, List<String> whereList) {
        String query = "SELECT ";
        for (Iterator<String> iterator = whatList.iterator(); iterator.hasNext();) {
            String what = iterator.next();
            query += what;
            if (iterator.hasNext()) {
                query += ", ";
            }
        }

        query += " FROM " + from
                + " WHERE ";

        for (Iterator<String> iterator = whereList.iterator(); iterator.hasNext();) {
            String where = iterator.next();
            query += where;
            if (iterator.hasNext()) {
                query += ", ";
            }
        }

        this.addQuery(query);
    }

    /**
     * Add query given in parameter to query queue
     *
     * @param query SQL query
     */
    private void addQuery(String query) {
        this.connector.addQuery(query);
    }

    ///////////////////////////////////////////////////////////////////////////
    /* Set of insert/update queries                                          */
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Generate query to add/update sign into DB and append it to the internal
     * queries stack
     */
    public void storeSign(SignObject obj) {
        this.addQuery(obj.getStoreSQL());
    }

    public void storeFence(/*fence object in java representation*/) {
        //TODO implement
    }

    public void addTree(/*tree object in java representation*/) {
        //TODO implement
    }

    public void addBush(/*bush object in java representation*/) {
        //TODO implement
    }

    ///////////////////////////////////////////////////////////////////////////
    /* Set of delete queries                                                 */
    ///////////////////////////////////////////////////////////////////////////
    public void delSign(SignObject obj) {
        this.addQuery(obj.getDeleteSQL());
    }

    public void delFence(/*sign object in java representation*/) {
        //TODO implement
    }

    public void delTree(/*tree object in java representation*/) {
        //TODO implement
    }

    public void delBush(/*bush object in java representation*/) {
        //TODO implement
    }

    /**
     * Commit all update/insert/delete queries in the stack
     */
    public void commit() {
        this.connector.executeQuery();
    }

    ///////////////////////////////////////////////////////////////////////////
    /* Set of select queries                                                 */
    ///////////////////////////////////////////////////////////////////////////
    public SignObject getSign(int id) {
        try {
            return new SignObject((OracleResultSet) this.connector.executeQueryWithResults(SignObject.getSelectSQL(id)));
        } catch (Exception e) {
            Logger.getLogger(DatabaseAPI.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            return new SignObject();
        }
    }

    public SpatialContainer getSigns() {
        try {
            //return new SpatialContainer((OracleResultSet) this.connector.executeQueryWithResults(SignObject.getAllSQL()));
        } catch (Exception e) {
            Logger.getLogger(DatabaseAPI.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            return null;
        }
    }
}
