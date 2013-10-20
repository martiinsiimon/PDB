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
    private GridBagConstraints gbc;
    private MapPanel mPanel;
    private InfoPanel iPanel;
    
    public RootPanel(){
    gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    mPanel = new MapPanel();
    iPanel = new InfoPanel();
    splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, mPanel, iPanel);
    splitPane.setResizeWeight(1);
    splitPane.setOneTouchExpandable(true);
    splitPane.setContinuousLayout(true);
    add(splitPane);
    
    }
    
}
