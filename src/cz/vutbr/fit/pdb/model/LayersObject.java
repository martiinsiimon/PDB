/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.vutbr.fit.pdb.model;

import oracle.jdbc.OracleResultSet;

/**
 * Java object for layer from DB.
 *
 * @author martin
 */
public class LayersObject extends DataObject {

    /**
     * Initialization function for LayersObject class.
     */
    public LayersObject() {
        super();
        this.tableName = "layers";
    }

    /**
     * LayersObject Exception
     * @param rset
     * @throws Exception
     */
    public LayersObject(OracleResultSet rset) throws Exception {
        super(rset);
        this.tableName = "layers";
    }

}
