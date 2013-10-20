/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 *
 * @author casey
 */
public class Core {
    
    private JFrame mainWindow;
    
    
    public Core(){
        mainWindow = new JFrame("PDB 2013");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    
    }
    
    
    private void setOnCenter(Dimension frameSize){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainWindow.setLocation(screenSize.width / 2 -frameSize.width / 2, screenSize.height /2 - frameSize.height / 2);
    
    }
    
    
    public static void maina(String[] args){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                Core c = new Core();
                //c.initGui();
                //c.showGui();
            }
        });
    
    }
}