/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cargo.web.beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Roman
 */
@ManagedBean
@SessionScoped
public class Type implements Serializable {
    
    private String name;
    private long id;
    
    public Type(){  
    }
    
    public Type(String name, long id){  
        this.name = name;
        this.id = id;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
