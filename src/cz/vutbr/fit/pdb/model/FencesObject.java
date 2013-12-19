package cz.vutbr.fit.pdb.model;

import oracle.jdbc.OracleResultSet;

/**
 * Java object for fences from DB. Java objekt pro ploty na mape.
 *
 * @author martin
 */
public class FencesObject extends SpatialObject {
    public FencesObject() {
        super();
        this.tableName = "fences";
    }

    public FencesObject(OracleResultSet rset) throws Exception {
        super(rset);
        this.tableName = "fences";
    }

    @Override
    public String getUpdateSQL() {
        String query = "UPDATE fences"
                + " SET date_to = (SELECT TO_DATE((SELECT to_char(trunc(sysdate),'MM-DD-YYYY') FROM dual), 'MM-DD-YYYY') FROM dual)"
                + " WHERE id = " + this.id;

        return query;
    }

    @Override
    public String getInsertSQL() {
        String query = "INSERT INTO fences VALUES ("
                + this.id + ", "
                + "(SELECT id FROM layers WHERE name = 'fences')" + ", "
                + "'" + this.geometry + "'" + ", "
                + "(SELECT TO_DATE((SELECT to_char(trunc(sysdate),'MM-DD-YYYY') FROM dual), 'MM-DD-YYYY') FROM dual), "
                + "TO_DATE('12-31-9999','MM-DD-YYYY'))";
        return query;
    }

    @Override
    public String getDeleteSQL() {
        String query = "UPDATE fences"
                + " SET date_to = (SELECT TO_DATE((SELECT to_char(trunc(sysdate),'MM-DD-YYYY') FROM dual), 'MM-DD-YYYY') FROM dual)"
                + " WHERE id = " + this.id;
        return query;
    }

    @Override
    public String getSelectSQL(int id) {
        String query = "SELECT * FROM fences WHERE id = "
                + id + " AND date_to = TO_DATE('12-31-9999', 'MM-DD-YYYY')";
        return query;
    }

}
