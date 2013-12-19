/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb.view;

import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
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
    private EditPanel ePanel;
    private boolean editMode = false;
    
    public RootPanel(MapPanel mp, InfoPanel ip, EditPanel ep){
    gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    mPanel = mp;
    iPanel = ip;
    ePanel = ep;
    }
    
    public void rebake(){
        removeAll();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
 
        if(this.editMode == true){
            editSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, mPanel, ePanel);
            editSplitPane.setResizeWeight(1);
            editSplitPane.setOneTouchExpandable(true);
            editSplitPane.setContinuousLayout(false);
            splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, editSplitPane, iPanel);
            splitPane.setResizeWeight(0.8);
            splitPane.setOneTouchExpandable(true);
            splitPane.setContinuousLayout(false);
            add(splitPane);
            
        }else{
            splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, mPanel, iPanel);
            splitPane.setResizeWeight(1);
            splitPane.setOneTouchExpandable(true);
            splitPane.setContinuousLayout(false);
            add(splitPane);
            
        }
        revalidate();
    
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
    
    public EditPanel getEPanel(){
        return this.ePanel;
    }
          
    
}
