package cargo.web.controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Roman
 */
@ManagedBean
@RequestScoped
public class LoginController {
    
    public String exit(){  
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index?faces-redirect=true";
    }  
}
