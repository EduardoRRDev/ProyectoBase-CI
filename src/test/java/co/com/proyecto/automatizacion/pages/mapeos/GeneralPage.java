package co.com.proyecto.automatizacion.pages.mapeos;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.pages.PageObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase base para todas las Page Objects del proyecto.
 * 
 * Esta clase extiende PageObject de Serenity, que proporciona:
 * - Acceso al WebDriver
 * - Métodos de espera implícita
 * - Métodos de interacción con elementos
 * - Evaluación de JavaScript
 * 
 * Todas las páginas específicas (LoginPage, MainPage, etc.) extienden esta clase
 * para heredar funcionalidad común y evitar duplicación de código.
 * 
 * Patrón Page Object Model (POM):
 * - Cada página de la aplicación tiene su propia clase
 * - Los elementos web se mapean como campos públicos
 * - Los métodos de interacción se agrupan en clases de "interacciones"
 */
public class GeneralPage extends PageObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeneralPage.class);

    public WebElementFacade getButtonByText(String buttonText) {
        LOGGER.info("Se busca elemento web tipo Button con el texto:{}", buttonText);
        
        // Usa XPath para buscar un botón que contenga el texto especificado
        // XPath: //button[contains(text(), 'texto')]
        // Esto busca cualquier botón cuyo texto contenga el parámetro buttonText
        return element(String.format("//button[contains(text(), '%s')]", buttonText));
    }

}
