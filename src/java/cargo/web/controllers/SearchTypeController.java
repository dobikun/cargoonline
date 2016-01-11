
package cargo.web.controllers;

/**
 *
 * @author Roman
 */
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import cargo.web.enums.SearchType;

@ManagedBean
@RequestScoped //Allows to clear searchList collection
public class SearchTypeController {

    private static Map<String, SearchType> searchList = new HashMap<String, SearchType>();

    public SearchTypeController() {

        ResourceBundle bundle = ResourceBundle.getBundle("cargo.web.prop.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        searchList.clear();
        searchList.put(bundle.getString("company_name"), SearchType.COMPANY);
        searchList.put(bundle.getString("cars_name"), SearchType.CARS);
        searchList.put(bundle.getString("cargo_type_name"), SearchType.CARGO_TYPE);
    }

    public Map<String, SearchType> getSearchList() {
        return searchList;
    }
}
