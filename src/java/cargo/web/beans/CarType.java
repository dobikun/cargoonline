package cargo.web.beans;

import cargo.web.beans.Type;
import cargo.web.db.Database;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Roman
 */
@ManagedBean
@ApplicationScoped
public class CarType implements Serializable{
    
    private ArrayList<Type> typeList = new ArrayList<Type>();
    
    private ArrayList<Type> getTypes() {
        
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        
         try {
             
            conn = Database.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from cars order by id");
          
             while (rs.next()) {
                 Type type = new Type();
                 type.setId(rs.getLong("id"));
                 type.setName(rs.getString("name"));
                 typeList.add(type);
             }   
             
         } catch(SQLException ex) {
             Logger.getLogger(CarType.class.getName()).log(Level.SEVERE, null, ex);
         } finally {
             try {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(CarType.class.getName()).log(Level.SEVERE, null, ex);
            }
         }

        return typeList;  
    }
    
    public ArrayList<Type> getTypeList() {
        if (!typeList.isEmpty()) {
            return typeList;
        } else {
            return getTypes();
        }
    }
}
