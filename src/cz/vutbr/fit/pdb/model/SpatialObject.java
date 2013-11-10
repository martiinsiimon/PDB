/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb.model;

import oracle.jdbc.OracleResultSet;
import oracle.spatial.geometry.JGeometry;

/**
 *
 * @author casey
 */
public class SpatialObject {

    protected int id;
    protected JGeometry geometry;

    public SpatialObject() {
        this.id = -1;
    }

    public SpatialObject(OracleResultSet rset) throws Exception {
        this.id = rset.getInt("id");
        this.geometry = JGeometry.load(rset.getBytes("geometry"));
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
//TODO add draw methods
}
