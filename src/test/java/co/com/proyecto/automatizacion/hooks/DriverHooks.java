package co.com.proyecto.automatizacion.hooks;

import io.cucumber.java.Before;
import net.serenitybdd.core.Serenity;
import org.junit.Assume;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hook de Cucumber que verifica la disponibilidad del WebDriver antes de ejecutar escenarios.
 * 
 * Los Hooks son métodos que se ejecutan automáticamente en ciertos momentos:
 * - @Before: Antes de cada escenario
 * - @After: Después de cada escenario
 * 
 * Este hook específicamente:
 * - Verifica si Chrome/WebDriver está disponible antes de ejecutar escenarios
 * - Si Chrome no está disponible, omite los escenarios (no falla el build)
 * - Esto es útil en CI/CD donde puede no haber navegador disponible
 * 
 * @Before(order = 0): Se ejecuta con prioridad 0 (antes que otros hooks)
 *                     El orden más bajo = mayor prioridad
 */
public class DriverHooks {

    private static final Logger LOG = LoggerFactory.getLogger(DriverHooks.class);
    private static Boolean driverAvailable = null;

    @Before(order = 0)
    public void ensureDriverAvailable() {
        // Si ya se verificó antes y no está disponible, omite el escenario
        if (driverAvailable != null) {
            if (!driverAvailable) {
                // Assume.assumeTrue con false omite el test sin fallarlo
                Assume.assumeTrue("Chrome no disponible; escenario omitido.", false);
            }
            return;  // Si está disponible, continúa normalmente
        }
        
        // Primera verificación: intenta obtener el driver
        try {
            // Obtiene el WebDriver gestionado por Serenity
            WebDriver driver = Serenity.getWebdriverManager().getCurrentDriver();
            
            // Si el driver existe, intenta navegar a una página en blanco
            // Esto confirma que el driver está funcionando correctamente
            if (driver != null) {
                driver.get("about:blank");
            }
            
            // Si llegamos aquí, el driver está disponible
            driverAvailable = true;
            
        } catch (Throwable t) {
            // Si hay algún error, el driver no está disponible
            LOG.warn("WebDriver no disponible (Chrome no pudo iniciar): {}. Omitiendo escenarios que requieren navegador.", t.getMessage());
            driverAvailable = false;
            
            // Omite el escenario sin fallar el build
            Assume.assumeTrue("Chrome no disponible: " + t.getMessage(), false);
        }
    }
}
