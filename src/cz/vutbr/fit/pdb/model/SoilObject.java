/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.vutbr.fit.pdb.model;

import oracle.jdbc.OracleResultSet;

/**
 * Java object for soil from DB.
 *
 * @author martin
 */
public class SoilObject extends SpatialObject {
    private int soilType;

    /**
     * Initialization function of SoilObject class.
     */
    public SoilObject() {
        super();
        this.tableName = "soil";

        this.soilType = -1;
    }

    /**
     * SoilObject Exception
     * @param rset
     * @throws Exception
     */
    public SoilObject(OracleResultSet rset) throws Exception {
        super(rset);
        this.tableName = "soil";

        this.soilType = rset.getInt("soil_type");
    }

    /**
     * SoilType setter.
     * @param _soilType
     */
    public void setSoilType(int _soilType) {
        this.soilType = _soilType;
    }

    /**
     * SoilType getter.
     * @return
     */
    public int getSoilType() {
        return this.soilType;
    }

    /**
     * Returns update SQL command.
     * @return SQL command
     */
    @Override
    public String getUpdateSQL() {
        String query;
        query = "UPDATE soil"
                + " SET geometry = '" + this.geometry + "'"
                + " SET soil_type = " + this.soilType
                + " WHERE id = " + this.id;

        return query;
    }

    /**
     * Returns insert SQL command.
     * @return SQL command
     */
    @Override
    public String getInsertSQL() {
        String query;
        query = "INSERT INTO soil VALUES ("
                + this.id + ", "
                + "(SELECT id FROM layers WHERE name = 'soil')" + ", "
                + this.soilType + ", "
                + "'" + this.geometry + "')";
        return query;
    }

    /**
     * Returns delete SQL command.
     * @return SQL command
     */
    @Override
    public String getDeleteSQL() {
        String query = "DELETE * FROM soil WHERE id = " + this.id;
        return query;
    }

    /**
     * Returns select SQL command.
     * @param id SoilObject ID
     * @return SQL command
     */
    @Override
    public String getSelectSQL(int id) {
        String query = "SELECT * FROM soil WHERE id = " + id;
        return query;
    }

}
