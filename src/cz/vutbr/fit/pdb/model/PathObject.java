/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.vutbr.fit.pdb.model;

import oracle.jdbc.OracleResultSet;

/**
 * Java object for patch from DB.
 *
 * @author martin
 */
public class PathObject extends SpatialObject {

    /**
     * Initialization function for PathObject class.
     */
    public PathObject() {
        super();
        this.tableName = "paths";
    }

    /**
     * PathObject Exception.
     * @param rset
     * @throws Exception
     */
    public PathObject(OracleResultSet rset) throws Exception {
        super(rset);
        this.tableName = "paths";
    }

    /**
     * Returns update SQL command.
     * @return update SQL command
     */
    @Override
    public String getUpdateSQL() {
        String query = "UPDATE path"
                + " SET geometry = '" + this.geometry + "'"
                + " WHERE id = " + this.id;

        return query;
    }

    /**
     * Returns insert SQL command.
     * @return insert SQL command
     */
    @Override
    public String getInsertSQL() {
        String query = "INSERT INTO path VALUES ("
                + this.id + ", "
                + "(SELECT id FROM layers WHERE name = 'path')" + ", "
                + "'" + this.geometry + "')";
        return query;
    }

    /**
     * Returns delete SQL command.
     * @return delete SQL command
     */
    @Override
    public String getDeleteSQL() {
        String query = "DELETE * FROM path WHERE id = " + this.id;
        return query;
    }

    /**
     * Returns select SQL command.
     * @param id PathObject ID
     * @return select SQL command
     */
    @Override
    public String getSelectSQL(int id) {
        String query = "SELECT * FROM path WHERE id = " + id;
        return query;
    }
}
