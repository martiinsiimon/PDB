/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import oracle.ord.im.OrdImage;
import oracle.ord.im.OrdImageSignature;

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
        image = new MultimedialObject();
    }

    public PlantsObject(OracleResultSet rset) throws Exception {
        super(rset);
        this.tableName = "plants";
        this.plant_type = rset.getInt("plant_type");
        this.image = new MultimedialObject();

        this.image.setImage((OrdImage) rset.getORAData("photo", OrdImage.getORADataFactory()));
        this.image.setSignature((OrdImageSignature) rset.getORAData("photo_sig", OrdImageSignature.getORADataFactory()));
    }

    public void setImage(MultimedialObject img) {
        this.image = img;
    }

    public MultimedialObject getImage() {
        return this.image;
    }

    public void setPlantType(int _plantType) {
        this.plant_type = _plantType;
    }

    public int getPlantType() {
        return this.plant_type;
    }

    @Override
    public String getInsertSQL() {
        String query
                = "INSERT INTO " + this.tableName + " VALUES ("
                + this.id + ", '"
                + this.name + "', "
                + this.plant_type
                + ", ordsys.ordimage.init(), ordsys.ordimagesignature.init())";
        return query;
    }

    public void loadPhotoFromFile(Connection connection, String filename) throws SQLException, IOException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        try {
            OrdImage imgProxy = null;
            OrdImageSignature sigProxy = null;
            // ziskame proxy
            OraclePreparedStatement pstmtSelect
                    = (OraclePreparedStatement) connection.prepareStatement(
                            "SELECT photo, photo_sig FROM " + this.tableName + " WHERE id = ? FOR UPDATE");
            try {
                pstmtSelect.setInt(1, this.id);

                OracleResultSet rset = (OracleResultSet) pstmtSelect.executeQuery();
                try {
                    if (rset.next()) {
                        imgProxy = (OrdImage) rset.getORAData("photo", OrdImage.getORADataFactory());
                        sigProxy = (OrdImageSignature) rset.getORAData("photo_sig", OrdImageSignature.getORADataFactory());
                    }
                } finally {
                    rset.close();
                }
            } finally {
                pstmtSelect.close();
            }

            // pouzijeme proxy
            if (imgProxy == null || sigProxy == null) {
                connection.setAutoCommit(autoCommit);
                return;
            }
            imgProxy.loadDataFromFile(filename);
            imgProxy.setProperties();
            sigProxy.generateSignature(imgProxy);

            // ulozime zmenene obrazky
            OraclePreparedStatement pstmtUpdate1
                    = (OraclePreparedStatement) connection.prepareStatement(
                            "UPDATE " + this.tableName + " SET photo = ? WHERE id = ?");
            try {
                pstmtUpdate1.setORAData(1, imgProxy);
                pstmtUpdate1.setInt(2, this.id);
                pstmtUpdate1.executeUpdate();
            } finally {
                pstmtUpdate1.close();
            }

            OraclePreparedStatement pstmtUpdate2 = (OraclePreparedStatement) connection.prepareStatement(
                    "UPDATE " + this.tableName + " v SET v.photo_sig = ? WHERE id = ?");
            try {
                pstmtUpdate2.setORAData(1, sigProxy);
                pstmtUpdate2.setInt(2, this.id);
                pstmtUpdate2.executeUpdate();
            } finally {
                pstmtUpdate2.close();
            }
            connection.commit();
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }
}
