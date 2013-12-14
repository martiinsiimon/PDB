/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.vutbr.fit.pdb.model;

import oracle.jdbc.OracleResultSet;

/**
 * Java object for plant type from DB. Datovy Java objekt pro jednotlive typy
 * roslin (kvetina, ker, strom)
 *
 * @author martin
 */
public class PlantTypeObject extends DataObject {

    public PlantTypeObject() {
        super();
        this.tableName = "plant_type";
    }

    public PlantTypeObject(OracleResultSet rset) throws Exception {
        super(rset);
        this.tableName = "plant_type";
    }

}
