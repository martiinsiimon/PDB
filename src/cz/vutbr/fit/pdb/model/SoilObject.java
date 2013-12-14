/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.vutbr.fit.pdb.model;

import oracle.jdbc.OracleResultSet;

/**
 * Java object for soil from DB. Java objekt pro zeminu na mape. Prostorovy
 * objekt je navic rozsiren o typ zeminy (soil_type - black soil, brown soil,
 * sand) a prislusne metody.
 *
 * @author martin
 */
public class SoilObject extends SpatialObject {
    private int soilType;

    public SoilObject() {
        super();
        this.tableName = "soil";

        this.soilType = -1;
    }

    public SoilObject(OracleResultSet rset) throws Exception {
        super(rset);
        this.tableName = "soil";

        this.soilType = rset.getInt("soil_type");
    }

    public void setSoilType(int _soilType) {
        this.soilType = _soilType;
    }

    public int getSoilType() {
        return this.soilType;
    }

    @Override
    public String getStoreSQL() {
        String query;
        if (this.id == -1) {
            query = "INSERT INTO soil VALUES ("
                    + "id_soil_seq.NEXTVAL" + ", "
                    + "(SELECT id FROM layers WHERE name = 'soil')" + ", "
                    + this.soilType + ", "
                    + "'" + this.geometry + "')";
        } else {
            query = "UPDATE soil"
                    + " SET geometry = '" + this.geometry + "'"
                    + " SET soil_type = " + this.soilType
                    + " WHERE id = " + this.id;
        }

        return query;
    }

    @Override
    public String getDeleteSQL() {
        String query = "DELETE * FROM soil WHERE id = " + this.id;
        return query;
    }

    @Override
    public String getSelectSQL(int id) {
        String query = "SELECT * FROM soil WHERE id = " + id;
        return query;
    }

}
