package co.com.proyecto.automatizacion.pages.mapeos;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.annotations.findby.How;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.annotations.DefaultUrl;

/**
 * Page Object para la página de inicio de sesión.
 * 
 * Esta clase mapea todos los elementos web de la página de login.
 * Cuando llamas a loginPage.open(), Serenity navega automáticamente
 * a la URL configurada en @DefaultUrl.
 * 
 * @DefaultUrl: URL por defecto de la página.
 *              Cuando llamas a loginPage.open(), navega a esta URL.
 * 
 * @FindBy: Anotación de Serenity para mapear elementos web.
 *          Serenity busca el elemento cuando se accede por primera vez
 *          y lo cachea para uso posterior.
 */
@DefaultUrl("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login")
public class LoginPage extends PageObject {

    @FindBy(how = How.NAME, using = "username")
    public WebElementFacade inputUsername;

    @FindBy(how = How.NAME, using = "password")
    public WebElementFacade inputPassword;

    @FindBy(css = "button[type='submit']")
    public WebElementFacade btnLogin;

}
