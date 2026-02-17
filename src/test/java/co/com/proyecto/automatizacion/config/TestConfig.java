package co.com.proyecto.automatizacion.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuración de pruebas. Lee credenciales y parámetros desde:
 * 1. Variables de entorno (ORANGEHRM_USERNAME, ORANGEHRM_PASSWORD)
 * 2. Propiedades del sistema (-Dorangehrm.username=...)
 * 3. serenity.properties
 */
public final class TestConfig {

    private static final Properties PROPS = loadProperties();

    private TestConfig() {
    }

    private static Properties loadProperties() {
        Properties p = new Properties();
        try (InputStream is = TestConfig.class.getResourceAsStream("/serenity.properties")) {
            if (is != null) {
                p.load(is);
            }
        } catch (IOException ignored) {
            // Usar valores por defecto
        }
        return p;
    }

    public static String getUsername() {
        return firstNonNull(
                System.getenv("ORANGEHRM_USERNAME"),
                System.getProperty("orangehrm.username"),
                PROPS.getProperty("orangehrm.username"),
                "Admin"
        );
    }

    public static String getPassword() {
        return firstNonNull(
                System.getenv("ORANGEHRM_PASSWORD"),
                System.getProperty("orangehrm.password"),
                PROPS.getProperty("orangehrm.password"),
                "admin123"
        );
    }

    private static String firstNonNull(String... values) {
        for (String v : values) {
            if (v != null && !v.isBlank()) {
                return v;
            }
        }
        return null;
    }
}
