package cz.vutbr.fit.pdb.db;

import cz.vutbr.fit.pdb.model.SignObject;
import java.util.Iterator;
import java.util.List;

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
        StringBuilder builder = new StringBuilder("select ");
        for (Iterator<String> iterator = whatList.iterator(); iterator.hasNext();) {
            String what = iterator.next();
            builder.append(what);
            if (iterator.hasNext()) {
                builder.append(", ");
            }
        }

        builder.append(" from ");
        builder.append(from);

        for (Iterator<String> iterator = whereList.iterator(); iterator.hasNext();) {
            String where = iterator.next();
            builder.append(" ");
            builder.append(where);
            if (iterator.hasNext()) {
                builder.append(",");
            }
        }

        this.addQuery(builder.toString());
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
        StringBuilder builder = new StringBuilder("insert ");


        this.addQuery(builder.toString());
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
    public void updateSign() {
        //TODO implement
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
    public void delSign(/*sign object in java representation*/) {
        //TODO implement
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
}
