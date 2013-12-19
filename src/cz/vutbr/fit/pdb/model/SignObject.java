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
    public String getUpdateSQL() {
        String query = "UPDATE signs"
                + " SET date_to = (SELECT TO_DATE((SELECT to_char(trunc(sysdate),'MM-DD-YYYY') FROM dual), 'MM-DD-YYYY') FROM dual)"
                + " WHERE id = " + this.id;
        return query;
    }

    @Override
    public String getInsertSQL() {
        String query;
        query = "INSERT INTO signs VALUES ("
                + this.id + ", "
                + "(SELECT id FROM layers WHERE name = 'signs')" + ", "
                + "?, "
                + "'" + this.description + "'" + ", "
                + this.plant + ", "
                + "(SELECT TO_DATE((SELECT to_char(trunc(sysdate),'MM-DD-YYYY') FROM dual), 'MM-DD-YYYY') FROM dual), "
                + "TO_DATE('12-31-9999','MM-DD-YYYY'))";
        return query;
    }

    @Override
    public String getDeleteSQL() {
        String query = "UPDATE signs"
                + " SET date_to = (SELECT TO_DATE((SELECT to_char(trunc(sysdate),'MM-DD-YYYY') FROM dual), 'MM-DD-YYYY') FROM dual)"
                + " WHERE id = " + this.id;
        return query;
    }

    @Override
    public String getSelectSQL(int id) {
        String query = "SELECT * FROM signs WHERE id = "
                + id + " AND date_to = TO_DATE('12-31-9999', 'MM-DD-YYYY')";
        return query;
    }

    /**
     * Return SQL query to get all beds from DB in given date.
     *
     * @param date String of date in format MM-DD-YYYY. Select only rows that
     * are valid in that day
     * @return String with sql query
     */
    public String getAllSQL(String date) {
        String query
                = "SELECT * FROM " + this.tableName
                + " WHERE date_to > TO_DATE('" + date + "', 'MM-DD-YYYY')"
                + " AND date_from <= TO_DATE('" + date + "', 'MM-DD-YYYY')";
        return query;
    }
}
