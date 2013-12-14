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
public abstract class SpatialObject extends Table {
    protected JGeometry geometry;
    protected boolean selected = false;
    protected boolean hovered = false;

    public SpatialObject() {
        this.id = -1;
    }

    public SpatialObject(OracleResultSet rset) throws Exception {
        this.id = rset.getInt("id");
        this.geometry = JGeometry.load(rset.getBytes("geometry"));
    }

    public void setGeometry(JGeometry _geometry) {
        this.geometry = _geometry;
    }

    public JGeometry getGeometry() {
        return this.geometry;
    }

    private boolean isSelected() {
        return this.selected;
    }

    private boolean isHovered() {
        return this.hovered;
    }

    public void setSelection(boolean sb) {
        this.selected = sb;
    }

    public void setHovering(boolean hb) {
        this.hovered = hb;
    }

//TODO add draw methods
    abstract void draw();

    @Override
    abstract String getStoreSQL();

    @Override
    abstract String getDeleteSQL();

    @Override
    abstract String getSelectSQL(int id);
}
