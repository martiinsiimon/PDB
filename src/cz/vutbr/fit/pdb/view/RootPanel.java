/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb.view;

import java.awt.GridBagConstraints;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 *
 * @author casey
 */
public class RootPanel extends JPanel{
    
    private JSplitPane splitPane;
    private JSplitPane editSplitPane;
    private GridBagConstraints gbc;
    private MapPanel mPanel;
    private InfoPanel iPanel;
    private boolean editMode = false;
    
    public RootPanel(MapPanel mp, InfoPanel ip){
    gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    mPanel = mp;
    iPanel = ip;
        
    }
    
    private void rebake(){
        removeAll();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
 
        if(this.editMode == true){
            
            
        }else{
            splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, mPanel, iPanel);
            splitPane.setResizeWeight(1);
            splitPane.setOneTouchExpandable(true);
            splitPane.setContinuousLayout(true);
            add(splitPane);
            
        }
    
    }
    
    public void setMapPanel(MapPanel mp){
        this.mPanel = mp;
    }
    
    public void setInfoPanel(InfoPanel ip){
        this.iPanel = ip;
    }
    
    public void enableEditMode(){
        this.editMode = true;
        this.rebake();
    
    }
    
    public void disableEditMode(){
        this.editMode = false;
        this.rebake();
    
    }
          
    
}
