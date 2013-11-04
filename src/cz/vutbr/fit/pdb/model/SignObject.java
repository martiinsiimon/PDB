package cz.vutbr.fit.pdb.model;

import oracle.jdbc.OracleResultSet;
import oracle.spatial.geometry.JGeometry;
/**
 *
 * @author Martin Simon
 */
public class SignObject {

    private int id;
    private JGeometry geometry;
    private String description;
    private int plant;

    public SignObject() {
        this.id = -1;
    }

    public SignObject(OracleResultSet rset) throws Exception {

        this.id = rset.getInt("id");
        this.geometry = JGeometry.load(rset.getBytes("geometry"));
        this.description = rset.getString("description");
        this.plant = rset.getInt("plant");
    }

    public void setId(int _id) {
        this.id = _id;
    }

    public int getId() {
        return this.id;
    }

    public void setGeometry(JGeometry _geometry) {
        this.geometry = _geometry;
    }

    public JGeometry getGeometry() {
        return this.geometry;
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
}
