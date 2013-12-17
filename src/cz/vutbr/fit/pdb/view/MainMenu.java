/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb.view;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.beans.EventHandler;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author casey
 */
public class MainMenu extends JMenuBar{
    
    private JMenu mFile,mAbout,mEdit,mDatabase;
    private JMenuItem mF_quit, mD_Connect, mD_Init, mA_Help, mA_About;
    private JCheckBoxMenuItem mE_cbEdit;
    
    
    public MainMenu(){
        super();
        mFile = new JMenu("File");
        mAbout = new JMenu("About");
        mEdit =  new JMenu("Edit");
        mDatabase = new JMenu("Database");
        
        mF_quit = new JMenuItem("Quit");
        
        mE_cbEdit = new JCheckBoxMenuItem("Enable");
        
        mD_Connect = new JMenuItem("Connect To");
        mD_Init = new JMenuItem("Init");
        
        mA_Help = new JMenuItem("Help");
        mA_About = new JMenuItem("About");
        
        
        mFile.add(mF_quit);
        mEdit.add(mE_cbEdit);
        mDatabase.add(mD_Connect);
        mDatabase.add(mD_Init);
        mAbout.add(mA_Help);
        mAbout.add(mA_About);
        
        add(mFile);
        add(mEdit);
        add(mDatabase);
        add(mAbout);
        
    
    }
    
    
    
    public void registerItemListener(ItemListener controler){
        this.mE_cbEdit.addItemListener(controler);
        
    }
    
    public void registerActionListener(ActionListener controler){
        this.mE_cbEdit.addActionListener(controler);
    }
    
}
