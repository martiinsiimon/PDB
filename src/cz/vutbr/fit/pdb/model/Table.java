/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb.model;

/**
 *
 * @author casey
 */
public abstract class Table {

    protected String tableName;
    protected int id = -1;

    public void setId(int _id) {
        this.id = _id;
    }

    public int getId() {
        return this.id;
    }

    public void setTableName(String _table) {
        this.tableName = _table;
    }

    public String getTableName() {
        return this.tableName;
    }

    public static void findAll(){}
    public static void findByID(){}

    abstract String getStoreSQL();

    abstract String getDeleteSQL();

    abstract String getSelectSQL(int id);


}



