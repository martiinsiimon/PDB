/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.vutbr.fit.pdb.model;

import oracle.jdbc.OracleResultSet;

/**
 * Java object for patch from DB. java objekt pro objekt cesty na mape.
 *
 * @author martin
 */
public class PathObject extends SpatialObject {
    public PathObject() {
        super();
        this.tableName = "paths";
    }

    public PathObject(OracleResultSet rset) throws Exception {
        super(rset);
        this.tableName = "paths";
    }

    @Override
    public String getUpdateSQL() {
        String query = "UPDATE path"
                + " SET geometry = '" + this.geometry + "'"
                + " WHERE id = " + this.id;

        return query;
    }

    @Override
    public String getInsertSQL() {
        String query = "INSERT INTO path VALUES ("
                + this.id + ", "
                + "(SELECT id FROM layers WHERE name = 'path')" + ", "
                + "'" + this.geometry + "')";
        return query;
    }

    @Override
    public String getDeleteSQL() {
        String query = "DELETE * FROM path WHERE id = " + this.id;
        return query;
    }

    @Override
    public String getSelectSQL(int id) {
        String query = "SELECT * FROM path WHERE id = " + id;
        return query;
    }
}
