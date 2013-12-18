package cz.vutbr.fit.pdb.db;

import cz.vutbr.fit.pdb.model.BedsObject;
import cz.vutbr.fit.pdb.model.DataObject;
import cz.vutbr.fit.pdb.model.FencesObject;
import cz.vutbr.fit.pdb.model.LayersObject;
import cz.vutbr.fit.pdb.model.PathObject;
import cz.vutbr.fit.pdb.model.PlantTypeObject;
import cz.vutbr.fit.pdb.model.PlantsObject;
import cz.vutbr.fit.pdb.model.SignObject;
import cz.vutbr.fit.pdb.model.SoilObject;
import cz.vutbr.fit.pdb.model.SoilTypeObject;
import cz.vutbr.fit.pdb.model.SpatialObject;
import cz.vutbr.fit.pdb.model.WaterObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.logging.Logger;
import oracle.jdbc.OracleResultSet;

import oracle.jdbc.pool.OracleDataSource;
import oracle.ord.im.OrdImage;

/**
 * Class representing connection to the database and git-like system.
 *
 * TODO: edit class to run in separate thread
 *
 * @author Martin Simon
 */
public class Connector {

    private static final String url = "jdbc:oracle:thin:@berta.fit.vutbr.cz:1522:dbfit";
    private final String login;
    private final String password;
    private Stack<String> queries;
    private static final String predefinedPath = "assets/db_init.sql";

    public Connector(String login, String password) {
        this.login = login;
        this.password = password;
        this.queries = new Stack<String>();
    }

    /**
     * Add a query to the internal stack.
     *
     * @param query Query to be added to the queue
     */
    public void addQuery(String query) {
        this.queries.push(query);
    }

    /**
     * Delete the last commit from queue.
     */
    public void delCommit() {
        this.queries.pop();
    }

    /**
     * Returns connection to DB.
     *
     * @return New connection to database
     * @throws SQLException
     */
    private Connection getConnection() throws SQLException {
        /* Create a OracleDataSource instance */
        OracleDataSource ods = new OracleDataSource();
        ods.setURL(Connector.url);
        ods.setUser(this.login);
        ods.setPassword(this.password);

        /* Connect to the database */
        return ods.getConnection();
    }

    /**
     * Execute update (insert,update,delete) queries stored in internal stack.
     */
    public void executeQueries() {
        if (this.queries.isEmpty()) {
            return;
        }
        try {
            /* Connect to the database */
            Connection conn = this.getConnection();
            try {
                /* Disable autocommit */
                conn.setAutoCommit(false);

                Statement stm = conn.createStatement();
                /* Execute every query in stack */
                for (String query : this.queries) {
                    System.out.println(query);
                    stm.addBatch(query);
                }

                /* Execute batch and commit */
                stm.executeBatch();
                conn.commit();
                stm.close();
            } finally {
                /* Enable autocommit */
                conn.setAutoCommit(true);
                /* Close connection */
                conn.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(e.getMessage());
            System.out.println("SQLException: " + e.getMessage());
        }

        this.queries.clear();
    }

    /**
     * Execute query given in parameter and return array list of spatial objects
     * of the query.
     *
     * @param query String containing select SQL query
     * @return Array of all returned spatial objects
     */
    public ArrayList<SpatialObject> executeQueryWithResultsBeds(String query) {
        /* Execute query in parameter and return ResultSet */
        ResultSet rs;
        ArrayList<SpatialObject> result = new ArrayList<SpatialObject>();
        try {
            /* Connect to the database */
            Connection conn = this.getConnection();
            try {
                Statement stm = conn.createStatement();

                /* Execute query */
                rs = stm.executeQuery(query);

                /* Copy the result to array */
                while (rs.next()) {
                    result.add(new BedsObject((OracleResultSet) rs));
                }

                /* Close statement */
                stm.close();
            } catch (Exception e) {
                Logger.getLogger(e.getMessage());
                System.out.println("Exception: " + e.getMessage());
            } finally {
                /* Close connection */
                conn.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(e.getMessage());
            System.out.println("SQLException: " + e.getMessage());
        }

        /* Return result */
        return result;
    }

    /**
     * Execute query given in parameter and return array list of spatial objects
     * of the query.
     *
     * @param query String containing select SQL query
     * @return Array of all returned spatial objects
     */
    public ArrayList<SpatialObject> executeQueryWithResultsSoil(String query) {
        /* Execute query in parameter and return ResultSet */
        ResultSet rs;
        ArrayList<SpatialObject> result = new ArrayList<SpatialObject>();
        try {
            /* Connect to the database */
            Connection conn = this.getConnection();
            try {
                Statement stm = conn.createStatement();

                /* Execute query */
                rs = stm.executeQuery(query);

                /* Copy the result to array */
                while (rs.next()) {
                    result.add(new SoilObject((OracleResultSet) rs));
                }

                /* Close statement */
                stm.close();
            } catch (Exception e) {
                Logger.getLogger(e.getMessage());
                System.out.println("Exception: " + e.getMessage());
            } finally {
                /* Close connection */
                conn.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(e.getMessage());
            System.out.println("SQLException: " + e.getMessage());
        }

        /* Return result */
        return result;
    }

    /**
     * Execute query given in parameter and return array list of spatial objects
     * of the query.
     *
     * @param query String containing select SQL query
     * @return Array of all returned spatial objects
     */
    public ArrayList<SpatialObject> executeQueryWithResultsPath(String query) {
        /* Execute query in parameter and return ResultSet */
        ResultSet rs;
        ArrayList<SpatialObject> result = new ArrayList<SpatialObject>();
        try {
            /* Connect to the database */
            Connection conn = this.getConnection();
            try {
                Statement stm = conn.createStatement();

                /* Execute query */
                rs = stm.executeQuery(query);

                /* Copy the result to array */
                while (rs.next()) {
                    result.add(new PathObject((OracleResultSet) rs));
                }

                /* Close statement */
                stm.close();
            } catch (Exception e) {
                Logger.getLogger(e.getMessage());
                System.out.println("Exception: " + e.getMessage());
            } finally {
                /* Close connection */
                conn.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(e.getMessage());
            System.out.println("SQLException: " + e.getMessage());
        }

        /* Return result */
        return result;
    }

    /**
     * Execute query given in parameter and return array list of spatial objects
     * of the query.
     *
     * @param query String containing select SQL query
     * @return Array of all returned spatial objects
     */
    public ArrayList<SpatialObject> executeQueryWithResultsWater(String query) {
        /* Execute query in parameter and return ResultSet */
        ResultSet rs;
        ArrayList<SpatialObject> result = new ArrayList<SpatialObject>();
        try {
            /* Connect to the database */
            Connection conn = this.getConnection();
            try {
                Statement stm = conn.createStatement();

                /* Execute query */
                rs = stm.executeQuery(query);

                /* Copy the result to array */
                while (rs.next()) {
                    result.add(new WaterObject((OracleResultSet) rs));
                }

                /* Close statement */
                stm.close();
            } catch (Exception e) {
                Logger.getLogger(e.getMessage());
                System.out.println("Exception: " + e.getMessage());
            } finally {
                /* Close connection */
                conn.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(e.getMessage());
            System.out.println("SQLException: " + e.getMessage());
        }

        /* Return result */
        return result;
    }

    /**
     * Execute query given in parameter and return array list of spatial objects
     * of the query.
     *
     * @param query String containing select SQL query
     * @return Array of all returned spatial objects
     */
    public ArrayList<SpatialObject> executeQueryWithResultsFences(String query) {
        /* Execute query in parameter and return ResultSet */
        ResultSet rs;
        ArrayList<SpatialObject> result = new ArrayList<SpatialObject>();
        try {
            /* Connect to the database */
            Connection conn = this.getConnection();
            try {
                Statement stm = conn.createStatement();

                /* Execute query */
                rs = stm.executeQuery(query);

                /* Copy the result to array */
                while (rs.next()) {
                    result.add(new FencesObject((OracleResultSet) rs));
                }

                /* Close statement */
                stm.close();
            } catch (Exception e) {
                Logger.getLogger(e.getMessage());
                System.out.println("Exception: " + e.getMessage());
            } finally {
                /* Close connection */
                conn.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(e.getMessage());
            System.out.println("SQLException: " + e.getMessage());
        }

        /* Return result */
        return result;
    }

    /**
     * Execute query given in parameter and return array list of spatial objects
     * of the query.
     *
     * @param query String containing select SQL query
     * @return Array of all returned spatial objects
     */
    public ArrayList<SpatialObject> executeQueryWithResultsSign(String query) {
        /* Execute query in parameter and return ResultSet */
        ResultSet rs;
        ArrayList<SpatialObject> result = new ArrayList<SpatialObject>();
        try {
            /* Connect to the database */
            Connection conn = this.getConnection();
            try {
                Statement stm = conn.createStatement();

                /* Execute query */
                rs = stm.executeQuery(query);

                /* Copy the result to array */
                while (rs.next()) {
                    result.add(new SignObject((OracleResultSet) rs));
                }

                /* Close statement */
                stm.close();
            } catch (Exception e) {
                Logger.getLogger(e.getMessage());
                System.out.println("Exception: " + e.getMessage());
            } finally {
                /* Close connection */
                conn.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(e.getMessage());
            System.out.println("SQLException: " + e.getMessage());
        }

        /* Return result */
        return result;
    }

    /**
     * Execute query given in parameter and return array list of data objects of
     * the query.
     *
     * @param query String containing select SQL query
     * @return Array of all returned data objects
     */
    public ArrayList<DataObject> executeQueryWithResultsLayers(String query) {
        /* Execute query in parameter and return ResultSet */
        ResultSet rs;
        ArrayList<DataObject> result = new ArrayList<DataObject>();
        try {
            /* Connect to the database */
            Connection conn = this.getConnection();
            try {
                Statement stm = conn.createStatement();

                /* Execute query */
                rs = stm.executeQuery(query);

                /* Copy the result to array */
                while (rs.next()) {
                    result.add(new LayersObject((OracleResultSet) rs));
                }

                /* Close statement */
                stm.close();
            } catch (Exception e) {
                Logger.getLogger(e.getMessage());
                System.out.println("Exception: " + e.getMessage());
            } finally {
                /* Close connection */
                conn.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(e.getMessage());
            System.out.println("SQLException: " + e.getMessage());
        }

        /* Return result */
        return result;
    }

    /**
     * Execute query given in parameter and return array list of data objects of
     * the query.
     *
     * @param query String containing select SQL query
     * @return Array of all returned data objects
     */
    public ArrayList<DataObject> executeQueryWithResultsSoilType(String query) {
        /* Execute query in parameter and return ResultSet */
        ResultSet rs;
        ArrayList<DataObject> result = new ArrayList<DataObject>();
        try {
            /* Connect to the database */
            Connection conn = this.getConnection();
            try {
                Statement stm = conn.createStatement();

                /* Execute query */
                rs = stm.executeQuery(query);

                /* Copy the result to array */
                while (rs.next()) {
                    result.add(new SoilTypeObject((OracleResultSet) rs));
                }

                /* Close statement */
                stm.close();
            } catch (Exception e) {
                Logger.getLogger(e.getMessage());
                System.out.println("Exception: " + e.getMessage());
            } finally {
                /* Close connection */
                conn.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(e.getMessage());
            System.out.println("SQLException: " + e.getMessage());
        }

        /* Return result */
        return result;
    }

    /**
     * Execute query given in parameter and return array list of data objects of
     * the query.
     *
     * @param query String containing select SQL query
     * @return Array of all returned data objects
     */
    public ArrayList<DataObject> executeQueryWithResultsPlantType(String query) {
        /* Execute query in parameter and return ResultSet */
        ResultSet rs;
        ArrayList<DataObject> result = new ArrayList<DataObject>();
        try {
            /* Connect to the database */
            Connection conn = this.getConnection();
            try {
                Statement stm = conn.createStatement();

                /* Execute query */
                rs = stm.executeQuery(query);

                /* Copy the result to array */
                while (rs.next()) {
                    result.add(new PlantTypeObject((OracleResultSet) rs));
                }

                /* Close statement */
                stm.close();
            } catch (Exception e) {
                Logger.getLogger(e.getMessage());
                System.out.println("Exception: " + e.getMessage());
            } finally {
                /* Close connection */
                conn.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(e.getMessage());
            System.out.println("SQLException: " + e.getMessage());
        }

        /* Return result */
        return result;
    }

    /**
     * Execute query given in parameter and return array list of data objects of
     * the query.
     *
     * @param query String containing select SQL query
     * @return Array of all returned data objects
     */
    public ArrayList<DataObject> executeQueryWithResultsPlants(String query) {
        /* Execute query in parameter and return ResultSet */
        ResultSet rs;
        ArrayList<DataObject> result = new ArrayList<DataObject>();
        try {
            /* Connect to the database */
            Connection conn = this.getConnection();
            try {
                Statement stm = conn.createStatement();

                /* Execute query */
                rs = stm.executeQuery(query);

                /* Copy the result to array */
                while (rs.next()) {
                    result.add(new PlantsObject((OracleResultSet) rs));
                }

                /* Close statement */
                stm.close();
            } catch (Exception e) {
                Logger.getLogger(e.getMessage());
                System.out.println("Exception: " + e.getMessage());
            } finally {
                /* Close connection */
                conn.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(e.getMessage());
            System.out.println("SQLException: " + e.getMessage());
        }

        /* Return result */
        return result;
    }

    /**
     * Execute query given in parameter and return array list of ids of spatial
     * objects.
     *
     * @param query String containing select SQL query
     * @return Array of all ids of resulted spatial objects
     */
    public ArrayList<Integer> executeQueryWithResultsInteger(String query) {
        /* Execute query in parameter and return ResultSet */
        ResultSet rs;
        ArrayList<Integer> result = new ArrayList<Integer>();
        try {
            /* Connect to the database */
            Connection conn = this.getConnection();
            try {
                Statement stm = conn.createStatement();

                /* Execute query */
                rs = stm.executeQuery(query);

                /* Copy the result to array */
                while (rs.next()) {
                    result.add(rs.getInt("id"));
                }

                /* Close statement */
                stm.close();
            } catch (SQLException e) {
                Logger.getLogger(e.getMessage());
                System.out.println("Exception: " + e.getMessage());
            } finally {
                /* Close connection */
                conn.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(e.getMessage());
            System.out.println("SQLException: " + e.getMessage());
        }

        /* Return result */
        return result;
    }

    /**
     * Store image to the object in parameter to the database.
     *
     * @param o Object to which the object should be stored
     * @param path Path to the image. If null, image from object properties
     * should be stored
     */
    public void storeImage(PlantsObject o, String path) {
        try {
            /* Connect to the database */
            Connection conn = this.getConnection();
            try {
                o.loadPhotoFromFile(conn, path);
            } catch (IOException e) {
                Logger.getLogger(e.getMessage());
                System.out.println("IOException: " + e.getMessage());
            } catch (SQLException e) {
                Logger.getLogger(e.getMessage());
                System.out.println("SQLException: " + e.getMessage());
            } finally {
                /* Close connection */
                conn.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(e.getMessage());
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    /**
     * Get image which belongs to the object in parameter. Image transformations
     * are determined by given sql query.
     *
     * @param query SQL query
     * @return Image of determined object or null if there is no such a object
     * stored in database
     */
    public OrdImage getImage(String query) {
        OrdImage img = null;
        OracleResultSet rs;
        try {
            /* Connect to the database */
            Connection conn = this.getConnection();
            try {
                Statement stm = conn.createStatement();

                /* Execute query */
                rs = (OracleResultSet) stm.executeQuery(query);

                /* Copy the result to array */
                if (rs.next()) {
                    img = (OrdImage) rs.getORAData("photo", OrdImage.getORADataFactory());
                }

                /* Close statement */
                stm.close();
            } catch (SQLException e) {
                Logger.getLogger(e.getMessage());
                System.out.println("Exception: " + e.getMessage());
            } finally {
                /* Close connection */
                conn.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(e.getMessage());
            System.out.println("SQLException: " + e.getMessage());
        }

        /* Return result */
        return img;
    }

    /**
     * Get PlantsObject most similar (in image level) to the object in parameter
     *
     * @param query SQL query
     * @return Most similar object (in image level) or new PlantsObject if there
     * is no such an object
     */
    public Integer getImageSimilar(String query) {
        Integer id = null;
        OracleResultSet rs;
        try {
            /* Connect to the database */
            Connection conn = this.getConnection();
            try {
                Statement stm = conn.createStatement();

                /* Execute query */
                rs = (OracleResultSet) stm.executeQuery(query);

                /* Get result */
                if (rs.next()) {
                    id = rs.getInt("id");
                }

                /* Close statement */
                stm.close();
            } catch (SQLException e) {
                Logger.getLogger(e.getMessage());
                System.out.println("SQLException: " + e.getMessage());
            } finally {
                /* Close connection */
                conn.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(e.getMessage());
            System.out.println("SQLException: " + e.getMessage());
        }

        /* Return result */
        return id;
    }

    /**
     * Read file determined by parameter or predefined i parameter is null and
     * reset data in db by it
     *
     * @see "The code of this method is based on following forum"
     * @see <a
     * href="http://www.coderanch.com/t/306966/JDBC/databases/Execute-sql-file-java">
     * Execute .sql file using java </a>
     *
     * @param _path Path to the sql script file. If null, predefined path is
     * used.
     */
    public void resetData(String _path) {
        String path;
        this.queries.clear();
        if (_path == null) {
            /* Use predefined sql path */
            path = Connector.predefinedPath;
        } else {
            /* Use path given in parameter */
            path = _path;
        }
        String s;
        StringBuilder sb = new StringBuilder();

        try {
            FileReader fr = new FileReader(new File(path));

            BufferedReader br = new BufferedReader(fr);

            while ((s = br.readLine()) != null) {
                /* Ignore comments beginning with # */
                int indexOfCommentSign = s.indexOf('#');
                if (indexOfCommentSign != -1) {
                    if (s.startsWith("#")) {
                        s = "";
                    } else {
                        s = new String(s.substring(0, indexOfCommentSign - 1));
                    }
                }
                /* Ignore comments beginning with -- */
                indexOfCommentSign = s.indexOf("--");
                if (indexOfCommentSign != -1) {
                    if (s.startsWith("--")) {
                        s = "";
                    } else {
                        s = new String(s.substring(0, indexOfCommentSign - 1));
                    }
                }
                /* Ignore comments beginning with // */
                indexOfCommentSign = s.indexOf("//");
                if (indexOfCommentSign != -1) {
                    if (s.startsWith("//")) {
                        s = "";
                    } else {
                        s = new String(s.substring(0, indexOfCommentSign - 1));
                    }
                }
                /* Ignore comments surrounded by /* */
                indexOfCommentSign = s.indexOf("/*");
                if (indexOfCommentSign != -1) {
                    if (s.startsWith("#")) {
                        s = "";
                    } else {
                        s = new String(s.substring(0, indexOfCommentSign - 1));
                    }

                    sb.append(s).append(" ");
                    /* Ignore all characters within the comment */
                    do {
                        s = br.readLine();
                    } while (s != null && !s.contains("*/"));
                    if (s != null) {
                        indexOfCommentSign = s.indexOf("*/");
                        if (indexOfCommentSign != -1) {
                            if (s.endsWith("*/")) {
                                s = "";
                            } else {
                                s = new String(s.substring(indexOfCommentSign + 2, s.length() - 1));
                            }
                        }
                    }
                }

                /* Append space for sure */
                sb.append(s).append(" ");
            }
            br.close();

            /* Split the script by default delimited into two queries list */
            ArrayList<String> easyQueries = new ArrayList<String>();
            ArrayList<String> dropQueries = new ArrayList<String>();
            for (String q : sb.toString().split(";")) {
                if (q.trim().startsWith("DROP") || q.trim().startsWith("DELETE")) {
                    dropQueries.add(q.trim());
                } else if (q.trim().startsWith("SELECT")) {
                    /* Ignore select queries in this batch */
                } else {
                    if (!q.trim().equals("") && !q.trim().equals("\t")) {
                        easyQueries.add(q.trim());
                    }
                }
            }

            /* Execute drop queries one-by-one due the possible (and very often)
             * exceptions */
            for (String q : dropQueries) {
                this.addQuery(q);
                this.executeQueries();
            }

            /* Execute all the 'easy' queries in one batch */
            for (String q : easyQueries) {
                this.addQuery(q);
            }
            this.executeQueries();

        } catch (IOException e) {
            Logger.getLogger(e.getMessage());
            System.out.println("IOException: " + e.getMessage());
        }
    }

    public ArrayList<DataObject> getMultisoilTrees(String query) {
        /* Execute query in parameter and return ResultSet */
        ResultSet rs;
        ArrayList<DataObject> result = new ArrayList<DataObject>();
        try {
            /* Connect to the database */
            Connection conn = this.getConnection();
            try {
                Statement stm = conn.createStatement();
                System.out.println(query);
                /* Execute query */
                rs = stm.executeQuery(query);

                /* Copy the result to array */
                while (rs.next()) {
                    result.add(new PlantsObject((OracleResultSet) rs));
                }

                /* Close statement */
                stm.close();
            } catch (Exception e) {
                Logger.getLogger(e.getMessage());
                System.out.println("Exception: " + e.getMessage());
            } finally {
                /* Close connection */
                conn.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(e.getMessage());
            System.out.println("SQLException: " + e.getMessage());
        }

        /* Return result */
        return result;
    }
}
