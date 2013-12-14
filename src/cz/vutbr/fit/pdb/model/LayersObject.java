/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.vutbr.fit.pdb.model;

import oracle.jdbc.OracleResultSet;

/**
 * Java object for layer from DB. Datovy Java objekt pro jednotlive vrstvy
 *
 * @author martin
 */
public class LayersObject extends DataObject {

    public LayersObject() {
        super();
        this.tableName = "layers";
    }

    public LayersObject(OracleResultSet rset) throws Exception {
        super(rset);
        this.tableName = "layers";
    }

}
