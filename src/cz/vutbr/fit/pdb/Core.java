/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb;

import cz.vutbr.fit.pdb.containers.SpatialContainer;
import cz.vutbr.fit.pdb.control.MapControl;
import cz.vutbr.fit.pdb.control.RootControl;
import cz.vutbr.fit.pdb.db.DatabaseAPI;
import cz.vutbr.fit.pdb.view.InfoPanel;
import cz.vutbr.fit.pdb.view.MainMenu;
import cz.vutbr.fit.pdb.view.MapPanel;
import cz.vutbr.fit.pdb.view.RootPanel;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

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

    private DatabaseAPI dbAPI;

    public Core(){
        mainWindow = new JFrame("PDB 2013");
        mainMenu = new MainMenu();
    }

    public void initGui(){
        
        
        MenuControl mc = new MenuControl();
        mainMenu.registerActionListener(mc);
        mainMenu.registerItemListener(mc);
        
        mainWindow.setJMenuBar(mainMenu);
        
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(800, 600);
    }
    
    public void initMainPanel(){
        SpatialContainer sc = new SpatialContainer(dbAPI);
        sc.initialize();
        
        InfoPanel ip = new InfoPanel();
        MapPanel mp = new MapPanel(sc);
       
        rootPanel = new RootPanel(mp, ip);
        rootControl = new RootControl(rootPanel);
        mapControl = new MapControl(mp, sc);
        rootPanel.rebake();
        mainWindow.add(rootPanel);
        mainWindow.invalidate();
        
    
    }

    public void showGui(){
        mainWindow.setVisible(true);
        mainWindow.setExtendedState(mainWindow.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }


    private void setOnCenter(Dimension frameSize){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainWindow.setLocation(screenSize.width / 2 -frameSize.width / 2, screenSize.height /2 - frameSize.height / 2);

    }
    
    
    class MenuControl implements ActionListener,ItemListener{
    
        @Override
        public void actionPerformed(ActionEvent e) {
           if("f_quit".equals(e.getActionCommand())){
               
           }else if("db_connect".equals(e.getActionCommand())){
               dbSetUp();
               
           }else if("db_init".equals(e.getActionCommand())){
               if(dbAPI == null){
                   dbSetUp();
               }
               if(dbAPI != null){
                   JOptionPane.showMessageDialog(mainWindow, "Inicializace Databaze, prosim cekejte.");
                   dbAPI.resetDBData();
                   
               }
           }else if("a_help".equals(e.getActionCommand())){
               
           }else if("a_about".equals(e.getActionCommand())){
               JOptionPane.showMessageDialog(mainWindow, "PDB Projekt 2013 - Botanická zahrada \nJan Jeřábek - xjerab13\nMartin Šimon - xsimon14\nMilan Bárta - xbarta32");
               
           }else if("a".equals(e.getActionCommand())){
               
           }else if("b".equals(e.getActionCommand())){
               
           }else if("c".equals(e.getActionCommand())){
               
           }else if("d".equals(e.getActionCommand())){
               
           }
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            if(((JMenuItem)(e.getSource())).isSelected()){
                rootControl.enableEdit();
            }else{
                rootControl.disableEdit();
            }

        }
    }
    
   public void dbSetUp() {
        JTextField username = new JTextField("xjerab13");
        JPasswordField password = new JPasswordField("w0z7dnrt");
        JTextField url = new JTextField("berta.fit.vutbr.cz");
        JTextField port = new JTextField("1522");
        JTextField serviceName = new JTextField("DBFIT");
        final JComponent[] inputs = new JComponent[]{
            new JLabel("Login"),
            username,
            new JLabel("Password"),
            password,
            new JLabel("URL"),
            url,
            new JLabel("Port"),
            port,
            new JLabel("Service Name"),
            serviceName,};
        int a = JOptionPane.showInternalConfirmDialog(mainMenu, inputs, "Connect to...", JOptionPane.OK_CANCEL_OPTION);
        if (a == JOptionPane.OK_OPTION) {
           dbAPI = new DatabaseAPI(username.getText(), password.getText());
           initMainPanel(); 
        }

    } 


    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                Core c = new Core();
                c.initGui();
                c.showGui();
                c.dbSetUp();
            }
        });

    }
}