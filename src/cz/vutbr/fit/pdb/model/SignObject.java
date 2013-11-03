package cz.vutbr.fit.pdb.model;

import java.sql.SQLException;
import oracle.jdbc.OracleResultSet;
import oracle.spatial.geometry.JGeometry;
/**
 *
 * @author Martin Simon
 */
public class SignObject {

    private int id;
    private int layer;
    private JGeometry geometry;
    private String description;
    private int plant;

    public SignObject() {
        this.id = -1;
    }

    public SignObject(OracleResultSet rset) throws SQLException, Exception {

        this.id = rset.getInt("id");
        this.layer = rset.getInt("layer");
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

    public void setLayer(int _layer) {
        this.layer = _layer;
    }

    public int getLayer() {
        return this.layer;
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
