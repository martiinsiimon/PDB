/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb.view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author casey
 */
public class MainMenu extends JMenuBar{
    
    private JMenu mFile,mAbout;
    private JMenuItem mF_quit;
    
    
    
    public MainMenu(){
        super();
        mFile = new JMenu("File");
        mAbout = new JMenu("About");
        
        mF_quit = new JMenuItem("Quit");
        
        mFile.add(mF_quit);
        
        add(mFile);
        add(mAbout);
        
    
    }
    
}
