/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.vutbr.fit.pdb.model;

import oracle.jdbc.OracleResultSet;

/**
 *
 * @author martin
 */
public class DataObject extends Table {

    protected String name;

    public DataObject() {
        this.id = -1;
    }

    public DataObject(OracleResultSet rset) throws Exception {
        this.id = rset.getInt("id");
    }

    @Override
    String getStoreSQL() {
        String query;
        if (this.id == -1) {
            query = "INSERT INTO " + this.tableName + " VALUES ("
                    + "id_" + this.tableName + "_seq.NEXTVAL" + ", "
                    + this.name + ")";
        } else {
            query = "UPDATE signs"
                    + " SET name = '" + this.tableName + "'"
                    + " WHERE id = " + this.id;
        }
        return query;
    }

    @Override
    String getDeleteSQL() {
        String query = "DELETE * FROM signs WHERE id = " + this.id;
        return query;
    }

    @Override
    String getSelectSQL(int id) {
        String query = "SELECT * FROM " + this.tableName + " WHERE id = " + id;
        return query;
    }
}
