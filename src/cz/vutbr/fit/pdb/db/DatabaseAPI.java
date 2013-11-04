package cz.vutbr.fit.pdb.db;

import cz.vutbr.fit.pdb.model.SignObject;
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
    private void selectQuery(String from, List<String> whatList, List<String> whereList) {
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
    /* Set of insert queries                                                 */
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Generate query to add sign into DB and append it to the internal queries
     * stack
     */
    public void addSign(SignObject obj) {
        //TODO check inserted values, length and possible duplicity!
        String query = "INSERT INTO signs VALUES ("
                + "id_signs_seq.NEXTVAL" + ", "
                + "(SELECT id FROM layers WHERE name = 'signs')" + ", "
                + "'" + obj.getGeometry() + "'" + ", "
                + "'" + obj.getDescription() + "'" + ", "
                + obj.getPlant() + ", "
                + "TO_DATE('11-11-2013','MM-DD-YYYY')" + ", " //TODO recent date!!
                + "TO_DATE('12-31-9999','MM-DD-YYYY')" + ")";
        this.addQuery(query);
    }

    public void addFence(/*fence object in java representation*/) {
        //TODO implement
    }

    public void addTree(/*tree object in java representation*/) {
        //TODO implement
    }

    public void addBush(/*bush object in java representation*/) {
        //TODO implement
    }

    ///////////////////////////////////////////////////////////////////////////
    /* Set of update queries                                                 */
    ///////////////////////////////////////////////////////////////////////////
    public void updateSign(SignObject obj) {
        //TODO check inserted values, length and possible duplicity!
        String query = "UPDATE signs"
                + " SET geometry = '" + obj.getGeometry() + "'"
                + " SET description = '" + obj.getDescription() + "'"
                + " SET plant = " + obj.getPlant()
                + " WHERE id = " + obj.getId() + " AND date_to = TO_DATE('12-31-9999', 'MM-DD-YYYY')";
        this.addQuery(query);
    }

    public void updateFence() {
        //TODO implement
    }

    public void updateTree() {
        //TODO implement
    }

    public void updateBush() {
        //TODO implement
    }

    ///////////////////////////////////////////////////////////////////////////
    /* Set of delete queries                                                 */
    ///////////////////////////////////////////////////////////////////////////
    public void delSign(SignObject obj) {
        String query = "UPDATE signs"
                + " SET date_to = TO_DATE('11-11-2013', 'MM-DD-YYYY')" //TODO recent date
                + " WHERE id = " + obj.getId() + " AND date_to = TO_DATE('12-31-9999', 'MM-DD-YYYY')";
        this.addQuery(query);
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
        String query = "SELECT * FROM signs WHERE id = " + id; //TODO date missing
        try {
            return new SignObject((OracleResultSet) this.connector.executeQueryWithResults(query));
        } catch (Exception e) {
            Logger.getLogger(DatabaseAPI.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            return new SignObject();
        }
    }
}
