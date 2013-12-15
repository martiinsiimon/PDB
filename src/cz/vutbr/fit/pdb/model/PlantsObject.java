/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.vutbr.fit.pdb.model;

import oracle.jdbc.OracleResultSet;

/**
 * Java object for plant from DB. Datovy Java objekt pro jednotlive rostliny.
 * Datovy objekt rozsiruje o grafickou informaci (TODO)
 *
 * @author martin
 */
public class PlantsObject extends DataObject {
    private MultimedialObject image;
    private int plant_type;

    public PlantsObject() {
        super();
        this.tableName = "plants";
        this.plant_type = -1;
        image = null;
    }

    public PlantsObject(OracleResultSet rset) throws Exception {
        super(rset);
        this.tableName = "plants";
        this.plant_type = rset.getInt("plant_type");

        //TODO photo & photo_sig
    }

    public void setPlantType(int _plantType) {
        this.plant_type = _plantType;
    }

    public int getPlantType() {
        return this.plant_type;
    }

    //TODO SQL method to store has to be overriden due the multimedial and plant_type context!
    @Override
    public String getUpdateSQL() {
        String query = "";
        return query;
    }

    //TODO
    @Override
    public String getInsertSQL() {
        String query = "";
        return query;
    }
}
