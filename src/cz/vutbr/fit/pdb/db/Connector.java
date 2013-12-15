package cz.vutbr.fit.pdb.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import java.util.Stack;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import oracle.jdbc.pool.OracleDataSource;

/**
 * TODO: edit class to run in separate thread
 *
 * @author Martin Simon
 */
public class Connector {

    private static String url = "jdbc:oracle:thin:@berta.fit.vutbr.cz:1522:dbfit";
    private final String login;
    private final String password;
    private Stack<String> queries;

    public Connector(String login, String password) {
        this.login = login;
        this.password = password;
        queries.clear();
    }

    /**
     * Add a query to the internal stack
     *
     * @param query Query to be added to the queue
     */
    public void addQuery(String query) {
        this.queries.push(query);
    }

    /**
     * Delete the last commit from queue
     */
    public void delCommit() {
        this.queries.pop();
    }

    /**
     * Returns connection to DB
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
     * Execute update (insert,update,delete) queries stored in internal stack
     */
    public void executeQuery() {
        try {
            /* Connect to the database */
            Connection conn = this.getConnection();
            try {
                /* Disable autocommit */
                conn.setAutoCommit(false);

                Statement stm = conn.createStatement();
                /* Execute every query in stack */
                for (Iterator<String> iterator = this.queries.iterator(); iterator.hasNext();) {
                    stm.addBatch(iterator.next());
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
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.queries.clear();
    }


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
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }

        /* Return result */
        return result;
    }
}
