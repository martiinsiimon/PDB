/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb.model;

/**
 *
 * @author casey
 */
public class Table {
    
    protected static String tableName = "";
    protected int id = -1;
    
    public Table(){}
    
    public static void findAll(){}
    public static void findByID(){}
    
    protected void update(){}
    protected void insert(){}
    
    public void save(){
        if(this.id == -1){
            this.insert();
        }else{
            this.update();
        }
    }
    
    public void delete(){}
    
    
}



