/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.vutbr.fit.pdb.model;

import oracle.jdbc.OracleResultSet;

/**
 * Java object for plant type from DB.
 *
 * @author martin
 */
public class PlantTypeObject extends DataObject {

    /**
     * Initialization function for PlantTypeObject class.
     */
    public PlantTypeObject() {
        super();
        this.tableName = "plant_type";
    }

    /**
     * PlantTypeObject Exception
     * @param rset
     * @throws Exception
     */
    public PlantTypeObject(OracleResultSet rset) throws Exception {
        super(rset);
        this.tableName = "plant_type";
    }

}
