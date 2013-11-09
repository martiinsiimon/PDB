/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb.model;
import oracle.spatial.geometry.JGeometry;
/**
 *
 * @author casey
 */
public class SpatialObject extends Table{
    
    protected static String tableName = ""; //TODO- tablename
    protected boolean selected = false;
    protected boolean hovered = false; 
    protected JGeometry geometry;
    
    private boolean isSelected(){
        return this.selected;
    }
    
    private boolean isHovered(){
        return this.hovered;
    }
    
    public void setSelection(boolean sb) {
        this.selected = sb;
    }
    public void setHovering(boolean hb) {
        this.hovered = hb;
    }
    
    public JGeometry getGeometry() {
        return geometry;
    }
    
    
    
}
