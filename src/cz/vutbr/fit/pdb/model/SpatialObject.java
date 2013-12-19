/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb.model;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import oracle.jdbc.OracleResultSet;
import oracle.spatial.geometry.JGeometry;

/**
 * Abstract class of spatial objects.
 *
 * @author casey
 */
public abstract class SpatialObject extends Table {

    /**
     * JGeometry object.
     */
    protected JGeometry geometry;

    /**
     * Layer.
     */
    protected int layer;

    /**
     * Selected boolean value.
     */
    protected boolean selected = false;

    /**
     * Hovered boolean value.
     */
    protected boolean hovered = false;

    /**
     * Initialization function for SpatialObject class.
     */
    public SpatialObject() {
        this.id = -1;
        this.layer = -1;
    }

    /**
     * SpatialObject Exception.
     * @param rset
     * @throws Exception
     */
    public SpatialObject(OracleResultSet rset) throws Exception {
        this.id = rset.getInt("id");
        this.layer = rset.getInt("layer");
        this.geometry = JGeometry.load(rset.getBytes("geometry"));
    }

    /**
     * Geometry setter.
     * @param _geometry
     */
    public void setGeometry(JGeometry _geometry) {
        this.geometry = _geometry;
    }

    /**
     * Geometry getter.
     * @return
     */
    public JGeometry getGeometry() {
        return this.geometry;
    }

    /**
     * Layer setter.
     * @param _layer
     */
    public void setLayer(int _layer) {
        this.layer = _layer;
    }

    /**
     * Layer getter.
     * @return
     */
    public int getLayer() {
        return this.layer;
    }

    /**
     * Returns true if object is selected.
     * @return True/False
     */
    public boolean isSelected() {
        return this.selected;
    }

    /**
     * Returns true if object has been hovered over by mouse.
     * @return True/False
     */
    public boolean isHovered() {
        return this.hovered;
    }

    /**
     * Sets selection.
     * @param sb True/False
     */
    public void setSelection(boolean sb) {
        this.selected = sb;
    }

    /**
     * Sets Hovering.
     * @param hb True/False
     */
    public void setHovering(boolean hb) {
        this.hovered = hb;
    }

    /**
     * Returns true if mouse pointer is over the object.
     * @param mousePosition current mouse cursor position
     * @param at affine transformation object
     * @return True/False
     */
    public boolean isMouseOver(Point mousePosition, AffineTransform at){
        Shape s = this.geometry.createShape(at);
        if(this.geometry.isPoint()){
            Point2D p =this.geometry.getJavaPoint();
            at.transform(p, p);
            return p.distance(mousePosition) <= 10;
        }
        return s.contains(mousePosition);
    }

    /**
     * Sets Hover if mouse cursor is over object.
     * @param mousePosition current mouse cursor position
     * @param at affine transformation object
     * @return True/False
     */
    public boolean hoverIfMouseOver(Point mousePosition, AffineTransform at){
        this.hovered = this.isMouseOver(mousePosition, at);
        return this.hovered;
    }

}
