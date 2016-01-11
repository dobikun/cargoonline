package cargo.web.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Roman
 */
public class Database {
    
    private static Connection conn;
    
    public static Connection getConnection() {
        
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cargoonline", "root", "12345");
 
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Can't connect to database.");
        }

        return conn;
    }
} 

