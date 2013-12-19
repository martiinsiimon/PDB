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
 * Java object for plant from DB.
 *
 * @author martin
 */
public class PlantsObject extends DataObject {
    private Boolean imgChanged;
    private BufferedImage image;
    private int plant_type;
    private static final String THRES = "200 200";

    /**
     * Initialization function of PlantsObject class.
     */
    public PlantsObject() {
        super();
        this.tableName = "plants";
        this.plant_type = -1;
        this.imgChanged = false;
        this.image = null;
    }

    /**
     * PlantsObject Exception.
     * @param rset
     * @throws Exception
     */
    public PlantsObject(OracleResultSet rset) throws Exception {
        super(rset);
        this.tableName = "plants";
        this.plant_type = rset.getInt("plant_type");
        this.imgChanged = false;
        this.image = null;
    }

    /**
     * IsImgChanged getter.
     * @return True/False
     */
    public Boolean isImgChanged() {
        return this.imgChanged;
    }

    /**
     * ImgChanged setter.
     */
    public void setImgChanged() {
        this.imgChanged = true;
    }

    /**
     * ImgChanged unsetter.
     */
    public void unsetImgChanged() {
        this.imgChanged = false;
    }

    /**
     * Image setter.
     * @param img BufferedImage to be set.
     */
    public void setImage(BufferedImage img) {
        this.image = img;
        this.setImgChanged();
    }

    /**
     * Conditional image setter.
     * @param img image to be set
     * @param change True/False
     */
    public void setImage(BufferedImage img, Boolean change) {
        this.image = img;
        if (change) {
            this.setImgChanged();
        }
    }

    /**
     * Image getter.
     * @return image
     */
    public BufferedImage getImage() {
        return this.image;
    }

    /**
     * Deletes image.
     */
    public void delImage() {
        this.image = null;
        this.setImgChanged();
    }

    /**
     * plantType setter.
     * @param _plantType
     */
    public void setPlantType(int _plantType) {
        this.plant_type = _plantType;
    }

    /**
     * PlantType getter.
     * @return
     */
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

    /**
     * Returns SQL command for getting image from DB
     * @return SQL command
     */
    public String getImageSQL() {
        return "SELECT photo FROM " + this.tableName + " WHERE id = " + this.id;
    }

    /**
     * Returns SQL command for getting image thumbnail from DB
     * @return SQL command
     */
    public String getImageThumbSQL() {
        return "SELECT photo_thumb photo FROM " + this.tableName + " WHERE id = " + this.id;
    }

    /**
     * Returns SQL command for getting similar pictures.
     * @return SQL command
     */
    public String getImageSimilarSQL() {
        String query
                = "SELECT dst.id, SI_ScoreByFtrList("
                + "new SI_FeatureList(src.photo_ac,0.4,src.photo_ch,0.4,src.photo_pc,0.1,src.photo_tx,0.1),dst.photo_si)"
                + " as similarity FROM " + this.tableName + " src, " + this.tableName + " dst "
                + "WHERE (src.id <> dst.id) "
                + "AND src.id = " + this.id + " ORDER BY similarity ASC";

        return query;
    }

    /**
     * Function for loading photo from file.
     * @param connection Connection object
     * @param filename filename of the photo
     * @throws SQLException
     * @throws IOException
     */
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
