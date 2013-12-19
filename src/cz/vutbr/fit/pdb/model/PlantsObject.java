/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb.model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import oracle.ord.im.OrdImage;

/**
 * Java object for plant from DB. Datovy Java objekt pro jednotlive rostliny.
 * Datovy objekt rozsiruje o grafickou informaci (TODO)
 *
 * @author martin
 */
public class PlantsObject extends DataObject {
    private Boolean imgChanged;
    private BufferedImage image;
    private int plant_type;
    private static final String THRES = "200 200";


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

    public void setImage(BufferedImage img) {
        this.image = img;
        this.setImgChanged();
    }

    public void setImage(BufferedImage img, Boolean change) {
        this.image = img;
        if (change) {
            this.setImgChanged();
        }
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public void delImage() {
        this.image = null;
        this.setImgChanged();
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
                = "INSERT INTO " + this.tableName + " (id, name, plant_type, photo, photo_thumb) VALUES ("
                + this.id + ", '"
                + this.name + "', "
                + this.plant_type
                + ", ordsys.ordimage.init(), ordsys.ordimage.init())";
        return query;
    }

    @Override
    public String getUpdateSQL() {
        String query = "UPDATE " + this.tableName
                    + " SET name = '" + this.name + "',"
                    + " plant_type = " + this.plant_type
                    + " WHERE id = " + this.id;
        return query;
    }

    public String getImageSQL() {
        return "SELECT photo FROM " + this.tableName + " WHERE id = " + this.id;
    }

    public String getImageThumbSQL() {
        return "SELECT photo_thumb photo FROM " + this.tableName + " WHERE id = " + this.id;
    }


    public String getImageSimilarSQL() {
        String query
                = "SELECT dst.id, SI_ScoreByFtrList("
                + "new SI_FeatureList(src.photo_ac,0.4,src.photo_ch,0.4,src.photo_pc,0.1,src.photo_tx,0.1),dst.photo_si)"
                + " as similarity FROM " + this.tableName + " src, " + this.tableName + " dst "
                + "WHERE (src.id <> dst.id) "
                + "AND src.id = " + this.id + " ORDER BY similarity ASC";

        return query;
    }

    public void loadPhotoFromFile(Connection connection, String filename) throws SQLException, IOException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        try {
            OrdImage imgProxy = null;
            OrdImage thProxy = null;
            // ziskame proxy
            OraclePreparedStatement pstmtSelect
                    = (OraclePreparedStatement) connection.prepareStatement(
                            "SELECT photo, photo_thumb FROM " + this.tableName + " WHERE id = ? FOR UPDATE");
            try {
                pstmtSelect.setInt(1, this.id);

                OracleResultSet rset = (OracleResultSet) pstmtSelect.executeQuery();
                try {
                    if (rset.next()) {
                        imgProxy = (OrdImage) rset.getORAData("photo", OrdImage.getORADataFactory());
                        thProxy = (OrdImage) rset.getORAData("photo_thumb", OrdImage.getORADataFactory());
                    }
                } finally {
                    rset.close();
                }
            } finally {
                pstmtSelect.close();
            }

            // pouzijeme proxy
            if (imgProxy == null) {
                connection.setAutoCommit(autoCommit);
                return;
            }

                System.out.println("File " + filename);
                imgProxy.loadDataFromFile(filename);


            imgProxy.processCopy("maxScale =" + PlantsObject.THRES, thProxy);
            imgProxy.setProperties();

            // ulozime zmenene obrazky
            OraclePreparedStatement pstmtUpdate1
                    = (OraclePreparedStatement) connection.prepareStatement(
                            "UPDATE " + this.tableName + " SET photo = ?, photo_thumb = ? WHERE id = ?");
            try {
                pstmtUpdate1.setORAData(1, imgProxy);
                pstmtUpdate1.setORAData(2, thProxy);
                pstmtUpdate1.setInt(3, this.id);
                pstmtUpdate1.executeUpdate();
            } finally {
                pstmtUpdate1.close();
            }
            OraclePreparedStatement pstmtUpdate2
                    = (OraclePreparedStatement) connection.prepareStatement(
                            "UPDATE " + this.tableName + " p "
                            + "SET p.photo_si = SI_StillImage(p.photo.getContent()) "
                            + "WHERE id = ?");
            try {
                pstmtUpdate2.setInt(1, this.id);
                pstmtUpdate2.executeUpdate();
            } finally {
                pstmtUpdate2.close();
            }
            OraclePreparedStatement pstmtUpdate3
                    = (OraclePreparedStatement) connection.prepareStatement(
                            "UPDATE " + this.tableName
                            + " SET photo_ac = SI_AverageColor(photo_si), "
                            + "photo_ch = SI_ColorHistogram(photo_si), "
                            + "photo_pc = SI_PositionalColor(photo_si), "
                            + "photo_tx = SI_Texture(photo_si) "
                            + "WHERE id = ?");
            try {
                pstmtUpdate3.setInt(1, this.id);
                pstmtUpdate3.executeUpdate();
            } finally {
                pstmtUpdate3.close();
            }

            connection.commit();
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }
}
