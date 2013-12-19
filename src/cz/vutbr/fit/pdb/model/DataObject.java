/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb.model;

import oracle.jdbc.OracleResultSet;

/**
 * Generic class for data object
 * @author martin
 */
public class DataObject extends Table {

    /**
     * Name of the object
     */
    protected String name;

    /**
     * Initialization function for DataObject class.
     */
    public DataObject() {
        this.id = -1;
        this.name = "";
    }

    /**
     * DataObject Exception
     * @param rset
     * @throws Exception
     */
    public DataObject(OracleResultSet rset) throws Exception {
        this.id = rset.getInt("id");
        this.name = rset.getString("name");
    }

    /**
     * Name setter.
     * @param _name
     */
    public void setName(String _name) {
        this.name = _name;
    }

    /**
     * Name getter.
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns update sql command.
     * @return update sql command
     */
    @Override
    public String getUpdateSQL() {
        String query;
        query = "UPDATE " + this.tableName
                + " SET name = '" + this.name + "'"
                + " WHERE id = " + this.id;
        return query;
    }

    /**
     * Return insert SQL command.
     * @return insert SQL command
     */
    @Override
    public String getInsertSQL() {
        String query;
        query = "INSERT INTO " + this.tableName + " VALUES ("
                + this.id + ", "
                + this.name + ")";
        return query;
    }

    /**
     * Return delete SQL command.
     * @return delete SQL command
     */
    @Override
    public String getDeleteSQL() {
        String query = "DELETE FROM " + this.tableName + " WHERE id = " + this.id;
        return query;
    }

    /**
     * Return select SQL command.
     * @param id DataObject ID
     * @return select SQL command
     */
    @Override
    public String getSelectSQL(int id) {
        String query = "SELECT * FROM " + this.tableName + " WHERE id = " + id;
        return query;
    }
}
