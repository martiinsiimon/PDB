/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb.control;

import cz.vutbr.fit.pdb.view.RootPanel;

/**
 *
 * @author casey
 */
public class RootControl{
    
    private RootPanel view;
    
    
    public RootControl(RootPanel view){
        this.view = view;
        
    }

    
    public void enableEdit(){
        this.view.enableEditMode();
    
    }
    
    public void disableEdit(){
        this.view.disableEditMode();
    }

    
}
