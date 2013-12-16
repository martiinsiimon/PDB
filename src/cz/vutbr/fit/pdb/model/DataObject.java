/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb.model;

import oracle.jdbc.OracleResultSet;

/**
 * Obecna trida pro datovy objekt. Obecnou tabulkovou tridu rozsiruje o nazev
 * (datova polozka je tvorena dvojici id - nazev). Obsahuje i prislusne SQL
 * dotazy, nebot datove objekty jsou vsechny stejne povahy (dvojice)
 *
 * @author martin
 */
public class DataObject extends Table {

    protected String name;

    public DataObject() {
        this.id = -1;
        this.name = "";
    }

    public DataObject(OracleResultSet rset) throws Exception {
        this.id = rset.getInt("id");
        this.name = rset.getString("name");
    }

    public void setName(String _name) {
        this.name = _name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String getUpdateSQL() {
        String query;
        query = "UPDATE " + this.tableName
                + " SET name = '" + this.name + "'"
                + " WHERE id = " + this.id;
        return query;
    }

    @Override
    public String getInsertSQL() {
        String query;
        query = "INSERT INTO " + this.tableName + " VALUES ("
                + this.id + ", "
                + this.name + ")";
        return query;
    }

    @Override
    public String getDeleteSQL() {
        String query = "DELETE * FROM " + this.tableName + " WHERE id = " + this.id;
        return query;
    }

    @Override
    public String getSelectSQL(int id) {
        String query = "SELECT * FROM " + this.tableName + " WHERE id = " + id;
        return query;
    }
}
