/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb;

import cz.vutbr.fit.pdb.containers.DataContainer;
import cz.vutbr.fit.pdb.containers.SpatialContainer;
import cz.vutbr.fit.pdb.control.EditControl;
import cz.vutbr.fit.pdb.control.MapControl;
import cz.vutbr.fit.pdb.control.RootControl;
import cz.vutbr.fit.pdb.db.DatabaseAPI;
import cz.vutbr.fit.pdb.model.DataObject;
import cz.vutbr.fit.pdb.model.PlantsObject;
import cz.vutbr.fit.pdb.model.SpatialObject;
import cz.vutbr.fit.pdb.view.EditPanel;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
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
    private MapPanel mp;
    private InfoPanel ip;
    private EditPanel ep;
    private EditControl ec;

    private DatabaseAPI dbAPI;

    private DataContainer dc;
    private SpatialContainer sc;

    public Core(){
        mainWindow = new JFrame("PDB 2013");
        mainMenu = new MainMenu();
        dc = null;
        sc = null;
        mp = null;
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
        sc = new SpatialContainer(dbAPI);
        sc.initialize();
        dc = new DataContainer(dbAPI);
        dc.initialize();

        ip = new InfoPanel();
        mp = new MapPanel(sc);
        ep = new EditPanel();

        rootPanel = new RootPanel(mp, ip, ep);
        rootControl = new RootControl(rootPanel);
        mapControl = new MapControl(mp, ip, sc, dc);
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
               System.exit(0);
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
           } else if ("s_bed_by_soil".equals(e.getActionCommand())) {
               getBedBySoilDialog();
           } else if ("s_beds_with_fences".equals(e.getActionCommand())) {
               getBedsWithFencesDialog();
           } else if ("s_dist_btw_beds".equals(e.getActionCommand())) {
               getDistBtwBedsDialog();
           }else if ("s_biggest_bed".equals(e.getActionCommand())) {
               getBiggestBedDialog();
           } else if ("s_smallest_bed".equals(e.getActionCommand())) {
               getSmallestBedDialog();
           } else if ("m_find_similar".equals(e.getActionCommand())) {
               getSimilarDialog();
           } else if ("t_sign_desc".equals(e.getActionCommand())) {
               getSignDescDialog();
           } else if ("t_plant_names".equals(e.getActionCommand())) {
               getPlantNamesDialog();
           } else if ("t_sign_desc_for_plants".equals(e.getActionCommand())) {
               getSignDescForPlantsDialog();
           } else if ("t_change_date".equals(e.getActionCommand())) {
               changeDateDialog();
           } else if ("t_remove_fences".equals(e.getActionCommand())) {
               removeFencesDialog();
           }

           else if("a_help".equals(e.getActionCommand())){

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
                ec = new EditControl(ep, mp, ip, sc, dc, dbAPI);
                mapControl.enableEdit(ec);
            }else{
                rootControl.disableEdit();
                mapControl.disableEdit();
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

    public void getBedBySoilDialog() {
        if (dc == null) {
            return;
        }
       ArrayList<String> plant_types = new ArrayList<String>();
       for (DataObject o: dc.getPlantType()) {
           plant_types.add(o.getName());
       }
       ArrayList<String> soil_types = new ArrayList<String>();
       for (DataObject o: dc.getSoilType()) {
           soil_types.add(o.getName());
       }

       JList soil_type_list = new JList(soil_types.toArray());
       soil_type_list.setSelectedIndex(0);
       JList plant_type_list = new JList(plant_types.toArray());
       plant_type_list.setSelectedIndex(0);
       final JComponent[] inputs = new JComponent[]{
           new JLabel("Soil type"), soil_type_list,
           new JLabel("Plant type"), plant_type_list
       };
       int a = JOptionPane.showInternalConfirmDialog(mainMenu, inputs, "Select soil type", JOptionPane.OK_CANCEL_OPTION);
       if (a == JOptionPane.OK_OPTION) {
           ArrayList<Integer> bedsID = dbAPI.getBedsBySoil(plant_type_list.getSelectedIndex()+1, soil_type_list.getSelectedIndex()+1);
           ArrayList<SpatialObject> bedsSpatial = new ArrayList<SpatialObject>();
           for (Integer i: bedsID) {
               bedsSpatial.add(sc.getBed(i));
           }
           sc.setTheseAsSelected(bedsSpatial);
           mp.updateUI();
        }
    }

    public void getBedsWithFencesDialog() {
        if (sc == null || dbAPI == null) {
            return;
        }
       ArrayList<Integer> bedsID = dbAPI.getBedsBorderedWithFence();
       ArrayList<SpatialObject> bedsSpatial = new ArrayList<SpatialObject>();
       for (Integer i : bedsID) {
           bedsSpatial.add(sc.getBed(i));
       }
       sc.setTheseAsSelected(bedsSpatial);
       mp.updateUI();
   }

   public void getDistBtwBedsDialog() {
//       final JComponent[] inputs = new JComponent[]{
//           new JLabel("?? TODO")
//       };
       //int a = JOptionPane.showConfirmDialog(mainMenu, inputs, "Select beds to measure distance between", JOptionPane.OK_CANCEL_OPTION);
       //if (a == JOptionPane.OK_OPTION) {
//           ArrayList<Integer> bedsID = dbAPI.getBedsBySoil(plant_type_list.getSelectedIndex()+1, soil_type_list.getSelectedIndex()+1);
//           ArrayList<SpatialObject> bedsSpatial = new ArrayList<SpatialObject>();
//           for (Integer i: bedsID) {
//               bedsSpatial.add(sc.getBed(i));
//           }
//           sc.setTheseAsSelected(bedsSpatial);
//           mp.updateUI();
        //}
   }

    public void getBiggestBedDialog() {
        if (sc == null || dbAPI == null) {
            return;
        }
       ArrayList<Integer> bedsID = dbAPI.getBiggestBed();
       ArrayList<SpatialObject> bedsSpatial = new ArrayList<SpatialObject>();
       for (Integer i : bedsID) {
           bedsSpatial.add(sc.getBed(i));
       }
       sc.setTheseAsSelected(bedsSpatial);
       mp.updateUI();
   }

    public void getSmallestBedDialog() {
        if (sc == null || dbAPI == null) {
            return;
        }
       ArrayList<Integer> bedsID = dbAPI.getSmallestBed();
       ArrayList<SpatialObject> bedsSpatial = new ArrayList<SpatialObject>();
       for (Integer i : bedsID) {
           bedsSpatial.add(sc.getBed(i));
       }
       sc.setTheseAsSelected(bedsSpatial);
       mp.updateUI();
   }

    public void getSimilarDialog() {
        if (dc == null || dbAPI == null) {
            return;
        }
       ArrayList<String> plant_names = new ArrayList<String>();
       for (DataObject o: dc.getPlants()) {
           plant_names.add(o.getName());
       }
       Collections.sort(plant_names);

       JList plant_name_list = new JList(plant_names.toArray());
       plant_name_list.setSelectedIndex(0);
       final JComponent[] inputs = new JComponent[]{
           new JLabel("Plant name"), plant_name_list
       };
       int a = JOptionPane.showInternalConfirmDialog(mainMenu, inputs, "Select plant name", JOptionPane.OK_CANCEL_OPTION);
       if (a == JOptionPane.OK_OPTION) {
           String selected_plant_name = (String) plant_name_list.getSelectedValue();
           DataObject selected_plant = dc.getPlants(selected_plant_name);

           Integer similarPlantID = dbAPI.getMostSimilar((PlantsObject)selected_plant);

           DataObject similar_plant = dc.getPlants(similarPlantID);
           JLabel sim_plant_name = new JLabel(similar_plant.getName());
           final JComponent[] outputs = new JComponent[]{
               sim_plant_name
               // TODO: Pridat do outputs obrazek podobne rostliny
           };
           JOptionPane.showInternalMessageDialog(mainMenu, outputs, "Similar plant", JOptionPane.INFORMATION_MESSAGE);
        }
   }

    public void getSignDescDialog() {
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date date_from = new Date();
        Date date_to = new Date();
        Calendar cal = Calendar.getInstance();

        JTextField textdate_from = new JTextField(dateFormat.format(cal.getTime()));
        JTextField textdate_to = new JTextField(dateFormat.format(cal.getTime()));

        final JComponent[] inputs = new JComponent[]{
            new JLabel("From (MM-DD-YYYY)"), textdate_from,
            new JLabel("To (MM-DD-YYYY)"), textdate_to
        };

        int a = JOptionPane.showInternalConfirmDialog(mainMenu, inputs, "Select from ... to ...", JOptionPane.OK_CANCEL_OPTION);
        if (a == JOptionPane.OK_OPTION) {
            // TODO: implement
            final JComponent[] outputs = new JComponent[]{
            };
            JOptionPane.showInternalMessageDialog(mainMenu, outputs, "Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void getPlantNamesDialog() {
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date date_from = new Date();
        Date date_to = new Date();
        Calendar cal = Calendar.getInstance();

        JTextField textdate_from = new JTextField(dateFormat.format(cal.getTime()));
        JTextField textdate_to = new JTextField(dateFormat.format(cal.getTime()));

        final JComponent[] inputs = new JComponent[]{
            new JLabel("From (MM-DD-YYYY)"), textdate_from,
            new JLabel("To (MM-DD-YYYY)"), textdate_to
        };

        int a = JOptionPane.showInternalConfirmDialog(mainMenu, inputs, "Select from ... to ...", JOptionPane.OK_CANCEL_OPTION);
        if (a == JOptionPane.OK_OPTION) {
            // TODO: implement
            final JComponent[] outputs = new JComponent[]{
            };
            JOptionPane.showInternalMessageDialog(mainMenu, outputs, "Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void getSignDescForPlantsDialog() {
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date date_from = new Date();
        Date date_to = new Date();
        Calendar cal = Calendar.getInstance();

        JTextField textdate_from = new JTextField(dateFormat.format(cal.getTime()));
        JTextField textdate_to = new JTextField(dateFormat.format(cal.getTime()));

        final JComponent[] inputs = new JComponent[]{
            new JLabel("From (MM-DD-YYYY)"), textdate_from,
            new JLabel("To (MM-DD-YYYY)"), textdate_to
        };

        int a = JOptionPane.showInternalConfirmDialog(mainMenu, inputs, "Select from ... to ...", JOptionPane.OK_CANCEL_OPTION);
        if (a == JOptionPane.OK_OPTION) {
            // TODO: implement
            final JComponent[] outputs = new JComponent[]{
            };
            JOptionPane.showInternalMessageDialog(mainMenu, outputs, "Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void changeDateDialog() {
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date date = new Date();
        Calendar cal = Calendar.getInstance();

        JTextField textdate = new JTextField(dateFormat.format(cal.getTime()));

        final JComponent[] inputs = new JComponent[]{
            new JLabel("From (MM-DD-YYYY)"), textdate,
        };

        int a = JOptionPane.showInternalConfirmDialog(mainMenu, inputs, "Select actual date (MM-DD-YYYY)", JOptionPane.OK_CANCEL_OPTION);
        if (a == JOptionPane.OK_OPTION) {
            //TODO add check
            dateFormat.setLenient(false);
            try {
                dateFormat.parse(textdate.getText());
                sc.setDate(textdate.getText());
                System.out.println(textdate.getText());
                mp.updateUI();
                final JComponent[] result = new JComponent[]{new JLabel("The date has been set.")};
                JOptionPane.showInternalMessageDialog(mainMenu, result, "Date has been set.", JOptionPane.INFORMATION_MESSAGE);
            } catch (ParseException ex) {
                final JComponent[] result = new JComponent[]{new JLabel("The date you have entered is invalid!")};
                JOptionPane.showInternalMessageDialog(mainMenu, result, "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void removeFencesDialog() {
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date date_from = new Date();
        Date date_to = new Date();
        Calendar cal = Calendar.getInstance();

        JTextField textdate_from = new JTextField(dateFormat.format(cal.getTime()));
        JTextField textdate_to = new JTextField(dateFormat.format(cal.getTime()));

        final JComponent[] inputs = new JComponent[]{
            new JLabel("From (MM-DD-YYYY)"), textdate_from,
            new JLabel("To (MM-DD-YYYY)"), textdate_to
        };

        int a = JOptionPane.showInternalConfirmDialog(mainMenu, inputs, "Select from ... to ...", JOptionPane.OK_CANCEL_OPTION);
        if (a == JOptionPane.OK_OPTION) {
            // TODO: implement
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