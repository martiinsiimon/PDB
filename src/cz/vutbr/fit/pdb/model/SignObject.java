package cz.vutbr.fit.pdb.model;

import oracle.jdbc.OracleResultSet;

/**
 * Java object for signs from DB. Java objekt pro znacky na mape. Znacka ma
 * popis a vaze se dane rostline.
 *
 * @author Martin Simon
 */

public class SignObject extends SpatialObject {
    private String description;
    private int plant;

    public SignObject() {
        super();
        this.tableName = "signs";

        this.description = "";
        this.plant = -1;
    }

    public SignObject(OracleResultSet rset) throws Exception {
        super(rset);
        this.tableName = "signs";

        this.description = rset.getString("description");
        this.plant = rset.getInt("plant");
    }


    public void setDescription(String _description) {
        this.description = _description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setPlant(int _plant) {
        this.plant = _plant;
    }

    public int getPlant() {
        return this.plant;
    }

    @Override
    public String getStoreSQL() {
        String query;
        if (this.getId() == -1) {
            query = "INSERT INTO signs VALUES ("
                    + "id_signs_seq.NEXTVAL" + ", "
                    + "(SELECT id FROM layers WHERE name = 'signs')" + ", "
                    + "'" + this.getGeometry() + "'" + ", "
                    + "'" + this.getDescription() + "'" + ", "
                    + this.getPlant() + ", "
                    + "TO_DATE('11-11-2013','MM-DD-YYYY')" + ", " //TODO recent date!!
                    + "TO_DATE('12-31-9999','MM-DD-YYYY')" + ")";
        } else {
            query = "UPDATE signs"
                    + " SET geometry = '" + this.getGeometry() + "'"
                    + " SET description = '" + this.getDescription() + "'"
                    + " SET plant = " + this.getPlant()
                    + " WHERE id = " + this.getId() + " AND date_to = TO_DATE('12-31-9999', 'MM-DD-YYYY')";
        }

        return query;
    }

    @Override
    public String getDeleteSQL() {
        String query = "UPDATE signs"
                + " SET date_to = TO_DATE('11-11-2013', 'MM-DD-YYYY')" //TODO recent date
                + " WHERE id = " + this.getId() + " AND date_to = TO_DATE('12-31-9999', 'MM-DD-YYYY')";
        return query;
    }

    @Override
    public String getSelectSQL(int id) {
        String query = "SELECT * FROM signs WHERE id = "
                + id + " AND date_to = TO_DATE('12-31-9999', 'MM-DD-YYYY')";
        return query;
    }
}
