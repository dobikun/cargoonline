/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cargo.web.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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
