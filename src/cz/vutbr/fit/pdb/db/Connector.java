package cz.vutbr.fit.pdb.db;

import java.sql.Connection;
//import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.pool.OracleDataSource;

/**
 *
 * @author Martin Simon
 */
public class Connector {

    private static String url = "jdbc:oracle:thin:@berta.fit.vutbr.cz:1522:dbfit";
    private String login;
    private String password;
    //TODO define internal stack to handle all queries in queue

    public Connector(String login, String password) {
        this.login = login;
        this.password = password;
        //TODO initialize the internal stack to be free
    }

    public void addCommit(String query) {
        /* Add a commit to internal stack */
        //TODO add query into stack
        System.out.print(query);
    }

    public void executeQuery() {
        /* Execute all commits in internal stack in one connection */

        try {
            // create a OracleDataSource instance
            OracleDataSource ods = new OracleDataSource();
            ods.setURL(Connector.url);
            ods.setUser(this.login); // command line: ... -Dlogin=xnovak99 ...
            ods.setPassword(this.password); // command line: ... -Dpassword=tajneheslo ...
            // connect to the database
            Connection conn = ods.getConnection();
            try {
                //TODO execute all queries in internal stack
                //TODO commit
            } finally {
                conn.close(); // close the connection
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }

        //TODO clear internal stack
    }
}
