/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb;

import cz.vutbr.fit.pdb.containers.SpatialContainer;
import cz.vutbr.fit.pdb.control.MapControl;
import cz.vutbr.fit.pdb.control.RootControl;
import cz.vutbr.fit.pdb.view.InfoPanel;
import cz.vutbr.fit.pdb.view.MainMenu;
import cz.vutbr.fit.pdb.view.MapPanel;
import cz.vutbr.fit.pdb.view.RootPanel;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 *
 * @author casey
 */
public class Core {
    
    private JFrame mainWindow;
    private RootPanel rootPanel;
    private MainMenu mainMenu;
    private RootControl rootControl;
    private MapControl mapControl;
    
    
    public Core(){
        mainWindow = new JFrame("PDB 2013");
        mainMenu = new MainMenu();
    }
    
    public void initGui(){
        
        InfoPanel ip = new InfoPanel();
        MapPanel mp = new MapPanel();
        SpatialContainer sc = new SpatialContainer();
        rootPanel = new RootPanel(mp, ip);
        rootControl = new RootControl(rootPanel);
        mapControl = new MapControl(mp, sc);
        mainWindow.setJMenuBar(mainMenu);
        mainWindow.add(rootPanel);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(800, 600);
    }
    
    public void showGui(){
        mainWindow.setVisible(true);
        mainWindow.setExtendedState(mainWindow.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }
    
    
    private void setOnCenter(Dimension frameSize){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainWindow.setLocation(screenSize.width / 2 -frameSize.width / 2, screenSize.height /2 - frameSize.height / 2);
    
    }
    
    
    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                Core c = new Core();
                c.initGui();
                c.showGui();
            }
        });
    
    }
}