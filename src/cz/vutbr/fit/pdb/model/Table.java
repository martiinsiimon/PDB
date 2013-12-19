/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb.model;

/**
 * Abstract class of all the objects representing tables.
 *
 * @author casey
 */
public abstract class Table {

    /**
     * Name of table.
     */
    protected String tableName;

    /**
     * Max table ID.
     */
    protected int id = -1;

    /**
     * ID setter.
     * @param _id
     */
    public void setId(int _id) {
        this.id = _id;
    }

    /**
     * ID getter.
     * @return ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * TableName setter.
     * @param _table
     */
    public void setTableName(String _table) {
        this.tableName = _table;
    }

    /**
     * TableName getter.
     * @return TableName
     */
    public String getTableName() {
        return this.tableName;
    }

    abstract String getUpdateSQL();

    abstract String getInsertSQL();

    abstract String getDeleteSQL();

    abstract String getSelectSQL(int id);

    /**
     * Returns SQL command to get all the enteries.
     * @return SQL command
     */
    public String getAllSQL() {
        String query = "SELECT * FROM " + this.tableName;
        return query;
    }

    /**
     * Returns SQL command for getting the highest ID.
     * @return SQL command
     */
    public String getHighestIDSQL() {
        String query = "SELECT id FROM " + this.tableName + " WHERE id = (SELECT MAX(id) FROM " + this.tableName + ")";
        return query;
    }
}


