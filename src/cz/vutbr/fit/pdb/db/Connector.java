package cz.vutbr.fit.pdb.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.pool.OracleDataSource;

/**
 *
 * @author Martin Simon
 */
public class Connector {

    private static String dbUrl = "jdbc:oracle:thin:@berta.fit.vutbr.cz:1522:dbfit";

    public Connector() {
    }

    public static void addCommit() {
        /* Add a commit to internal stack */
    }

    public static void executeQuery() {
        /* Execute all commits in internal stack in one connection */

        try {
            // create a OracleDataSource instance
            OracleDataSource ods = new OracleDataSource();
            ods.setURL(dbUrl);
            ods.setUser(System.getProperty("login")); // command line: ... -Dlogin=xnovak99 ...
            ods.setPassword(System.getProperty("password")); // command line: ... -Dpassword=tajneheslo ...
            // connect to the database
            Connection conn = ods.getConnection();
            try {
                // create a Statement
                Statement stmt = conn.createStatement();
                try {
                    // select something from the system's dual table
                    ResultSet rset = stmt.executeQuery(
                            "select 1+2 as col1, 3-4 as col2 from dual");
                    try {
                        // iterate through the result and print the values
                        while (rset.next()) {
                            System.out.println("col1: '" + rset.getString(1)
                                    + "'\tcol2: '" + rset.getString(2) + "'");
                        }
                    } finally {
                        rset.close(); // close the ResultSet
                    }
                } finally {
                    stmt.close(); // close the Statement
                }
            } finally {
                conn.close(); // close the connection
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
