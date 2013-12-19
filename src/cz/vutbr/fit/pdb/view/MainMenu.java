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
    
    private JMenu mFile, mAbout, mEdit, mDatabase, mSpatial, mMultimedial, mTemporal;
    private JMenuItem mF_quit, mD_Connect, mD_Init, mA_Help, mA_About;
    private JMenuItem mS_BedBySoil, mS_BedsWithFences, mS_DistBtwBeds, mS_BiggestBed, mS_SmallestBed;
    private JMenuItem mM_FindSimilar;
    private JCheckBoxMenuItem mE_cbEdit;
    
    
    public MainMenu(){
        super();
        mFile = new JMenu("File");
        mAbout = new JMenu("About");
        mEdit =  new JMenu("Edit");
        mDatabase = new JMenu("Database");
        mSpatial = new JMenu("Spatial operations");
        mMultimedial = new JMenu("Multimedial operations");
        mTemporal = new JMenu("Temporal operations");
        
        mF_quit = new JMenuItem("Quit");
        mF_quit.setActionCommand("f_quit");
       
        mE_cbEdit = new JCheckBoxMenuItem("Enable");
        
        mD_Connect = new JMenuItem("Connect To");
        mD_Connect.setActionCommand("db_connect");
        
        mD_Init = new JMenuItem("Init");
        mD_Init.setActionCommand("db_init");
        
        mS_BedBySoil = new JMenuItem("Show beds on given soil");
        mS_BedBySoil.setActionCommand("s_bed_by_soil");
        mS_BedsWithFences = new JMenuItem("Show beds bordered by fences");
        mS_BedsWithFences.setActionCommand("s_beds_with_fences");
        mS_DistBtwBeds = new JMenuItem("Print distance between given beds");
        mS_DistBtwBeds.setActionCommand("s_dist_btw_beds");
        mS_BiggestBed = new JMenuItem("Show biggest beds");
        mS_BiggestBed.setActionCommand("s_biggest_bed");
        mS_SmallestBed = new JMenuItem("Show smallest beds");
        mS_SmallestBed.setActionCommand("s_smallest_bed");
        
        mM_FindSimilar = new JMenuItem("Find the most similar by picture");
        mM_FindSimilar.setActionCommand("m_find_similar");
        
        mA_Help = new JMenuItem("Help");
        mA_Help.setActionCommand("a_help");
        
        mA_About = new JMenuItem("About");
        mA_About.setActionCommand("a_about");
        
        mFile.add(mF_quit);
        
        mEdit.add(mE_cbEdit);
        
        mDatabase.add(mD_Connect);
        mDatabase.add(mD_Init);
        
        mSpatial.add(mS_BedBySoil);
        mSpatial.add(mS_BedsWithFences);
        mSpatial.add(mS_DistBtwBeds);
        mSpatial.add(mS_BiggestBed);
        mSpatial.add(mS_SmallestBed);
        
        mMultimedial.add(mM_FindSimilar);
        
        mAbout.add(mA_Help);
        mAbout.add(mA_About);
        
        add(mFile);
        add(mEdit);
        add(mDatabase);
        add(mSpatial);
        add(mMultimedial);
        add(mTemporal);
        add(mAbout);
    }
    
    
    
    public void registerItemListener(ItemListener controler){
        this.mE_cbEdit.addItemListener(controler);
        
    }
    
    public void registerActionListener(ActionListener controler){
        this.mE_cbEdit.addActionListener(controler);
        this.mF_quit.addActionListener(controler);
        this.mD_Connect.addActionListener(controler);
        this.mD_Init.addActionListener(controler);
        this.mS_BedBySoil.addActionListener(controler);
        this.mS_BedsWithFences.addActionListener(controler);
        this.mS_DistBtwBeds.addActionListener(controler);
        this.mS_BiggestBed.addActionListener(controler);
        this.mS_SmallestBed.addActionListener(controler);
        this.mM_FindSimilar.addActionListener(controler);
        this.mA_About.addActionListener(controler);
        this.mA_Help.addActionListener(controler);
    }
    
}
