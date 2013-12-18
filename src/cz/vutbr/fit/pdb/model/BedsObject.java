/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb.model;

import oracle.jdbc.OracleResultSet;

/**
 * Java object for bed from DB. Java objekt pro umisteni rostliny na mape.
 * Prostorovy objekt rozsiruje o typ rostliny (bedType - strom, ker, kvetina) a
 * samotny typ rostliny (plant) a prislusne gettery/settery
 *
 * @author martin
 */
public class BedsObject extends SpatialObject {

    private Integer plant;

    public BedsObject() {
        super();
        this.tableName = "beds";

        this.plant = -1;
    }

    public BedsObject(OracleResultSet rset) throws Exception {
        super(rset);
        this.tableName = "beds";

        this.plant = rset.getInt("plant");
    }

    public void setPlant(int _plant) {
        this.plant = _plant;
    }

    public int getPlant() {
        return this.plant;
    }

    @Override
    public String getUpdateSQL() {
        String query = "UPDATE beds"
                + " SET geometry = '" + this.geometry + "', "
                + " plant = " + this.plant
                + " WHERE id = " + this.id + " AND date_to = TO_DATE('12-31-9999', 'MM-DD-YYYY')";

        return query;
    }

    @Override
    public String getInsertSQL() {
        String query = "INSERT INTO beds VALUES ("
                + this.id + ", "
                + "(SELECT id FROM layers WHERE name = 'beds')" + ", "
                + "'" + this.geometry + "'" + ", "
                + this.plant + ", "
                + "TO_DATE('11-11-2013','MM-DD-YYYY')" + ", " //TODO recent date!!
                + "TO_DATE('12-31-9999','MM-DD-YYYY')" + ")";
        return query;
    }

    @Override
    public String getDeleteSQL() {
        String query = "UPDATE bed"
                + " SET date_to = TO_DATE('11-11-2013', 'MM-DD-YYYY')" //TODO recent date
                + " WHERE id = " + this.id + " AND date_to = TO_DATE('12-31-9999', 'MM-DD-YYYY')";
        return query;
    }

    @Override
    public String getSelectSQL(int id) {
        String query = "SELECT * FROM beds WHERE id = "
                + id + " AND date_to = TO_DATE('12-31-9999', 'MM-DD-YYYY')";
        return query;
    }

}
