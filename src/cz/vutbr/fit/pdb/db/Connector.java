package cz.vutbr.fit.pdb.db;

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
import java.util.Iterator;
import java.util.logging.Logger;

import oracle.jdbc.pool.OracleDataSource;

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
     * Execute query given in parameter and return ResultSet of the query.
     *
     * @param query String containing select SQL query
     * @return ResultSet of result of the select query
     */
    public ResultSet executeQueryWithResults(String query) {
        /* Execute query in parameter and return ResultSet */
        ResultSet result = null;
        try {
            /* Connect to the database */
            Connection conn = this.getConnection();
            try {
                Statement stm = conn.createStatement();

                /* Execute query */
                result = stm.executeQuery(query);

                /* Close statement */
                stm.close();
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
}
