/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.vutbr.fit.pdb.model;

import oracle.jdbc.OracleResultSet;

/**
 * Java object for water from DB.
 *
 * @author martin
 */
public class WaterObject extends SpatialObject {

    /**
     * Initialization function for WaterObject class.
     */
    public WaterObject() {
        super();
        this.tableName = "water";
    }

    /**
     * WaterObject Exception.
     * @param rset
     * @throws Exception
     */
    public WaterObject(OracleResultSet rset) throws Exception {
        super(rset);
        this.tableName = "water";
    }

    /**
     * Returns SQL update command.
     * @return SQL command
     */
    @Override
    public String getUpdateSQL() {
        String query = "UPDATE water"
                + " SET geometry = '" + this.geometry + "'"
                + " WHERE id = " + this.id;

        return query;
    }

    /**
     * Returns SQL insert command.
     * @return SQL command
     */
    @Override
    public String getInsertSQL() {
        String query = "INSERT INTO water VALUES ("
                + this.id + ", "
                + "(SELECT id FROM layers WHERE name = 'water')" + ", "
                + "'" + this.geometry + "')";
        return query;
    }

    /**
     * Returns SQL delete command.
     * @return SQL command
     */
    @Override
    public String getDeleteSQL() {
        String query = "DELETE * FROM water WHERE id = " + this.id;
        return query;
    }

    /**
     * Returns SQL select command.
     * @param id WaterObject ID
     * @return SQL command
     */
    @Override
    public String getSelectSQL(int id) {
        String query = "SELECT * FROM water WHERE id = " + id;
        return query;
    }
}
