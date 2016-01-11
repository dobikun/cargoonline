package cargo.web.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author Roman
 */

@ManagedBean()
@SessionScoped
public class User {
    
    private String username;
    private Integer parametr;
    
    public User() {}
    
    public void usernameStringChanged(ValueChangeEvent e) {
        username = e.getNewValue().toString();
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public Integer getParametr() {
        return parametr;
    }
    
    public void setParametr(Integer parametr) {
        this.parametr = parametr;
    }
}
