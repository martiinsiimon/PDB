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
    private Boolean imgChanged;
    private OrdImage image;
    private int plant_type;


    public PlantsObject() {
        super();
        this.tableName = "plants";
        this.plant_type = -1;
        this.imgChanged = false;
        this.image = null;
    }

    public PlantsObject(OracleResultSet rset) throws Exception {
        super(rset);
        this.tableName = "plants";
        this.plant_type = rset.getInt("plant_type");
        this.imgChanged = false;
        this.image = null;
    }

    public Boolean isImgChanged() {
        return this.imgChanged;
    }

    public void setImgChanged() {
        this.imgChanged = true;
    }

    public void unsetImgChanged() {
        this.imgChanged = false;
    }

    public void setImage(OrdImage img) {
        this.image = img;
        this.setImgChanged();
    }

    public void setImage(OrdImage img, Boolean change) {
        this.image = img;
        if (change) {
            this.setImgChanged();
        }
    }

    public OrdImage getImage() {
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

    public String getImageSQL() {
        return "SELECT photo FROM " + this.tableName + " WHERE id = " + this.id;
    }

    public String getImageScaleSQL(Integer maxx, Integer maxy) {
        return "SELECT photo.process('maxScale=" + maxx + " " + maxy + "') FROM " + this.tableName + " WHERE id = " + this.id;
    }

    public String getImageRotateSQL(Float r) {
        return "SELECT photo.process('rotate=" + r + "') FROM " + this.tableName + " WHERE id = " + this.id;
    }

    public String getImageSimilarSQL() {
        String query
                = "SELECT * FROM " + this.tableName + " src, " + this.tableName + " dst "
                + "WHERE ordsys.IMGSimilar(src.photo_sig,dst.photo_sig,color=0.3,texture=0.3,shape=0.3,location=0.1,100,1) = 1 "
                + "AND (src.id <> dst.id) "
                + "AND src.id = " + this.id + " ORDER BY ordsys.IMGScore(1)";

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
            if (filename == null) {
                imgProxy = this.image;
                this.unsetImgChanged();
            } else {
                imgProxy.loadDataFromFile(filename);
            }
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

    //TODO getImageFromDB
    //TODO get image from db and store it to the this.image (don't set isChanged)
}
