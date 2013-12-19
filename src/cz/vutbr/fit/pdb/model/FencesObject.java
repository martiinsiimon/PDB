package cz.vutbr.fit.pdb.model;

import oracle.jdbc.OracleResultSet;

/**
 * Java object for fences from DB.
 *
 * @author martin
 */
public class FencesObject extends SpatialObject {

    /**
     * Initialization function of FencesObject class.
     */
    public FencesObject() {
        super();
        this.tableName = "fences";
    }

    /**
     * FencesObject Exception.
     * @param rset
     * @throws Exception
     */
    public FencesObject(OracleResultSet rset) throws Exception {
        super(rset);
        this.tableName = "fences";
    }

    /**
     * Returns update SQL command.
     * @return update SQL command
     */
    @Override
    public String getUpdateSQL() {
        String query = "UPDATE fences"
                + " SET date_to = (SELECT TO_DATE((SELECT to_char(trunc(sysdate),'MM-DD-YYYY') FROM dual), 'MM-DD-YYYY') FROM dual)"
                + " WHERE id = " + this.id;

        return query;
    }

    /**
     * Returns insert SQL command.
     * @return insert SQL command
     */
    @Override
    public String getInsertSQL() {
        String query = "INSERT INTO fences VALUES ("
                + this.id + ", "
                + "(SELECT id FROM layers WHERE name = 'fences')" + ", "
                + "?, "
                + "(SELECT TO_DATE((SELECT to_char(trunc(sysdate),'MM-DD-YYYY') FROM dual), 'MM-DD-YYYY') FROM dual), "
                + "TO_DATE('12-31-9999','MM-DD-YYYY'))";
        return query;
    }

    /**
     * Returns delete SQL command.
     * @return delete SQL command
     */
    @Override
    public String getDeleteSQL() {
        String query = "UPDATE fences"
                + " SET date_to = (SELECT TO_DATE((SELECT to_char(trunc(sysdate),'MM-DD-YYYY') FROM dual), 'MM-DD-YYYY') FROM dual)"
                + " WHERE id = " + this.id;
        return query;
    }

    /**
     * Returns select SQL command.
     * @param id FencesObject ID
     * @return select SQL command
     */
    @Override
    public String getSelectSQL(int id) {
        String query = "SELECT * FROM fences WHERE id = "
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
