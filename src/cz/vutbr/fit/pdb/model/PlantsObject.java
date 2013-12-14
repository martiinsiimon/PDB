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

    public PlantsObject() {
        super();
        this.tableName = "plants";
        image = null;
    }

    public PlantsObject(OracleResultSet rset) throws Exception {
        super(rset);
        this.tableName = "plants";

        //TODO photo & photo_sig
    }

    public void setName(String _name) {
        this.name = _name;
    }

    public String getName() {
        return this.name;
    }

    //TODO SQL method to store has to be overriden due the multimedial context!
    @Override
    String getStoreSQL() {
        String query = "";
        return query;
    }

}
