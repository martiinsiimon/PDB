/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.vutbr.fit.pdb.model;

import oracle.jdbc.OracleResultSet;

/**
 * Java object for water from DB. Java objekt pro vodni plochy na mape.
 *
 * @author martin
 */
public class WaterObject extends SpatialObject {
    public WaterObject() {
        super();
        this.tableName = "water";
    }

    public WaterObject(OracleResultSet rset) throws Exception {
        super(rset);
        this.tableName = "water";
    }

    @Override
    public String getUpdateSQL() {
        String query = "UPDATE water"
                + " SET geometry = " + this.geometry
                + " WHERE id = " + this.id;

        return query;
    }

    @Override
    public String getInsertSQL() {
        String query = "INSERT INTO water VALUES ("
                + this.id + ", "
                + "(SELECT id FROM layers WHERE name = 'water')" + ", "
                + "'" + this.geometry + "')";
        return query;
    }

    @Override
    public String getDeleteSQL() {
        String query = "DELETE FROM water WHERE id = " + this.id;
        return query;
    }

    @Override
    public String getSelectSQL(int id) {
        String query = "SELECT * FROM water WHERE id = " + id;
        return query;
    }
}
