/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.vutbr.fit.pdb.model;

import oracle.jdbc.OracleResultSet;

/**
 * Java object for fences from DB. Java objekt pro ploty na mape.
 *
 * @author martin
 */
public class FencesObject extends SpatialObject {
    public FencesObject() {
        super();
        this.tableName = "fences";
    }

    public FencesObject(OracleResultSet rset) throws Exception {
        super(rset);
        this.tableName = "fences";
    }

    @Override
    public String getUpdateSQL() {
        String query = "UPDATE fences"
                + " SET geometry = '" + this.geometry + "'"
                + " WHERE id = " + this.id + " AND date_to = TO_DATE('12-31-9999', 'MM-DD-YYYY')";

        return query;
    }

    @Override
    public String getInsertSQL() {
        String query = "INSERT INTO fences VALUES ("
                + this.id + ", "
                + "(SELECT id FROM layers WHERE name = 'fences')" + ", "
                + "'" + this.geometry + "'" + ", "
                + "TO_DATE('11-11-2013','MM-DD-YYYY')" + ", " //TODO recent date!!
                + "TO_DATE('12-31-9999','MM-DD-YYYY')" + ")";
        return query;
    }

    @Override
    public String getDeleteSQL() {
        String query = "UPDATE fences"
                + " SET date_to = TO_DATE('11-11-2013', 'MM-DD-YYYY')" //TODO recent date
                + " WHERE id = " + this.id + " AND date_to = TO_DATE('12-31-9999', 'MM-DD-YYYY')";
        return query;
    }

    @Override
    public String getSelectSQL(int id) {
        String query = "SELECT * FROM fences WHERE id = "
                + id + " AND date_to = TO_DATE('12-31-9999', 'MM-DD-YYYY')";
        return query;
    }

}
