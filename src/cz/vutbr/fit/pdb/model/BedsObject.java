package cz.vutbr.fit.pdb.model;

import oracle.jdbc.OracleResultSet;

/**
 * Java object for bed from DB. 
 * @author martin
 */
public class BedsObject extends SpatialObject {

    private Integer plant;

    /**
     * Initialization function for BedsObject class.
     */
    public BedsObject() {
        super();
        this.tableName = "beds";

        this.plant = -1;
    }

    /**
     * BedsObject Exception.
     * @param rset
     * @throws Exception
     */
    public BedsObject(OracleResultSet rset) throws Exception {
        super(rset);
        this.tableName = "beds";

        this.plant = rset.getInt("plant");
    }

    /**
     * Plant setter.
     * @param _plant
     */
    public void setPlant(int _plant) {
        this.plant = _plant;
    }

    /**
     * Plant getter
     * @return
     */
    public int getPlant() {
        return this.plant;
    }

    /**
     * Returns update SQL command
     * @return update SQL command
     */
    @Override
    public String getUpdateSQL() {
        String query = "UPDATE beds"
                + " SET date_to = (SELECT TO_DATE((SELECT to_char(trunc(sysdate),'MM-DD-YYYY') FROM dual), 'MM-DD-YYYY') FROM dual)"
                + " WHERE id = " + this.id;
        return query;
    }

    /**
     * Return insert SQL command.
     * @return insert SQL command
     */
    @Override
    public String getInsertSQL() {
        String query = "INSERT INTO beds VALUES ("
                + this.id + ", "
                + "(SELECT id FROM layers WHERE name = 'beds')" + ", "
                + "?, "
                + this.plant + ", "
                + "(SELECT TO_DATE((SELECT to_char(trunc(sysdate),'MM-DD-YYYY') FROM dual), 'MM-DD-YYYY') FROM dual)" + ", "
                + "TO_DATE('12-31-9999','MM-DD-YYYY'))";
        return query;
    }

    /**
     * Return delete SQL command.
     * @return delete SQL command
     */
    @Override
    public String getDeleteSQL() {
        String query = "UPDATE beds"
                + " SET date_to = (SELECT TO_DATE((SELECT to_char(trunc(sysdate),'MM-DD-YYYY') FROM dual), 'MM-DD-YYYY') FROM dual)"
                + " WHERE id = " + this.id;
        return query;
    }

    /**
     * return select SQL command.
     * @param id Bed ID
     * @return select SQL command
     */
    @Override
    public String getSelectSQL(int id) {
        String query = "SELECT * FROM beds WHERE id = "
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
