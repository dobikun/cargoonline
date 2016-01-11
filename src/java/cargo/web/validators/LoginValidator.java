package cargo.web.validators;

import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Roman
 */
@FacesValidator("cargo.web.validators.LoginValidator")
public class LoginValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
         ResourceBundle bundle = ResourceBundle.getBundle("cargo.web.prop.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
         
         try {
            String newValue = value.toString();

            if (newValue.length() < 5) {
                throw new IllegalArgumentException(bundle.getString("login_length_error"));
            }

            if (!Character.isLetter(newValue.charAt(0))) {
                throw new IllegalArgumentException(bundle.getString("first_letter_error"));
            }

            // запрос в базу данных для проверки существующего имени
            
        } catch (IllegalArgumentException e) {
            FacesMessage message = new FacesMessage(e.getMessage());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
    
    
}
