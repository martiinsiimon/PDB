/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.vutbr.fit.pdb.model;

import oracle.jdbc.OracleResultSet;

/**
 * Java object for soil types from DB.
 *
 * @author martin
 */
public class SoilTypeObject extends DataObject {

    /**
     * Initialization function for SoilTypeObject class.
     */
    public SoilTypeObject() {
        super();
        this.tableName = "soil_type";
    }

    /**
     * SoilTypeObject Exception.
     * @param rset
     * @throws Exception
     */
    public SoilTypeObject(OracleResultSet rset) throws Exception {
        super(rset);
        this.tableName = "soil_type";
    }
}
