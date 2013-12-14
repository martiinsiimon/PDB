/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.vutbr.fit.pdb.model;

import oracle.jdbc.OracleResultSet;

/**
 * Java object for plant from DB
 *
 * @author martin
 */
public class PlantsObject extends DataObject {

    public PlantsObject() {
        super();
        this.tableName = "plants";
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

    //TODO photo & photo_sig
}
