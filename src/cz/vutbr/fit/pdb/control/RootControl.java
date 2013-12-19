/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb.control;

import cz.vutbr.fit.pdb.view.RootPanel;

/**
 * Class for controlling the RootPanel.
 * @author casey
 */
public class RootControl{
    
    private RootPanel view;

    /**
     * Initialization function of the RootControl class.
     * @param view RootPanel object.
     */
    public RootControl(RootPanel view){
        this.view = view;
        
    }

    /**
     * Function enabling editing mode.
     */
    public void enableEdit(){
        this.view.enableEditMode();
    
    }
    
    /**
     * Function disabling editing mode.
     */
    public void disableEdit(){
        this.view.disableEditMode();
    }

    
}
