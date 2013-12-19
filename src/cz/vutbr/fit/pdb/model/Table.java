/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb.model;

/**
 * Abstraktni trida vsech objektu. Kazda Kazdy objekt je identifikovan nazvem
 * tabulky a identifikatorem id. Trida navis deklaruje abstraktni metody pro SQL
 * dotazy a definuje dotaz pro vyber vsech polozek z dane tabulky.
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

    abstract String getUpdateSQL();

    abstract String getInsertSQL();

    abstract String getDeleteSQL();

    abstract String getSelectSQL(int id);

    public String getAllSQL() {
        String query = "SELECT * FROM " + this.tableName;
        return query;
    }

    public String getHighestIDSQL() {
        String query = "SELECT id FROM " + this.tableName + " WHERE id = (SELECT MAX(id) FROM " + this.tableName + ")";
        return query;
    }
}


