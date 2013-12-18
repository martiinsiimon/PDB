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
 * Abstraktni trida prostorovych objektu. Rozsiruje obecnou tabulku o informaci
 * o vrstve, geometrii a informaci o tom, jestli je objekt na mape vybran nebo
 * jestli je nad nim kursor.
 *
 * @author casey
 */
public abstract class SpatialObject extends Table {
    protected JGeometry geometry;
    protected int layer;
    protected boolean selected = false;
    protected boolean hovered = false;

    public SpatialObject() {
        this.id = -1;
        this.layer = -1;
    }

    public SpatialObject(OracleResultSet rset) throws Exception {
        this.id = rset.getInt("id");
        this.layer = rset.getInt("layer");
        this.geometry = JGeometry.load(rset.getBytes("geometry"));
    }

    public void setGeometry(JGeometry _geometry) {
        this.geometry = _geometry;
    }

    public JGeometry getGeometry() {
        return this.geometry;
    }

    public void setLayer(int _layer) {
        this.layer = _layer;
    }

    public int getLayer() {
        return this.layer;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public boolean isHovered() {
        return this.hovered;
    }

    public void setSelection(boolean sb) {
        this.selected = sb;
    }

    public void setHovering(boolean hb) {
        this.hovered = hb;
    }
    
    public boolean isMouseOver(Point mousePosition, AffineTransform at){
        Shape s = this.geometry.createShape(at);
        if(this.geometry.isPoint()){
            Point2D p =this.geometry.getJavaPoint();
            at.transform(p, p);
            if(p.distance(mousePosition) <= 10){
                return true;
            }else{
                return false;
            }
        }
        if(s.contains(mousePosition)){
            return true;
        }
        return false;
    }
    
    public boolean hoverIfMouseOver(Point mousePosition, AffineTransform at){
        this.hovered = this.isMouseOver(mousePosition, at);
        return this.hovered;
    }

}
