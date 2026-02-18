Automation – Serenity BDD POM (OrangeHRM Login)

Proyecto de automatización de pruebas con **Serenity BDD 4.2.0**, **Cucumber 7** (Gherkin) y patrón **Page Object Model (POM)**. Valida el flujo de login de **OrangeHRM Demo**.

**URL:** https://opensource-demo.orangehrmlive.com/web/index.php/auth/login  
**Credenciales:** configurables (ver [Credenciales configurables](#credenciales-configurables))

## Enfoque de pruebas

El proyecto aplica **BDD (Behavior Driven Development)** con Gherkin para describir los escenarios en lenguaje natural, facilitando la comunicación entre negocio y desarrollo. Se utiliza el patrón **Page Object Model (POM)** para separar la definición de elementos web (mapeos) de la lógica de los pasos, lo que mejora la mantenibilidad cuando la aplicación cambia. Los escenarios se ejecutan con **Serenity BDD**, que genera reportes con evidencia visual (capturas por paso) y permite rastrear cada acción en los reportes. El alcance actual cubre el flujo de login exitoso en OrangeHRM Demo.

## Stack

| Tecnología   | Versión   |
|-------------|-----------|
| Java        | 17        |
| Serenity BDD| 4.2.0     |
| Cucumber    | 7.18.2    |
| JUnit       | 4.13.2    |
| Gradle      | 8.5       |

## Estructura del proyecto

```
src/
└── test/
    ├── java/co/com/proyecto/automatizacion/
    │   ├── config/          # Configuración (TestConfig, credenciales)
    │   ├── definitions/     # Step definitions (Cucumber)
    │   ├── steps/           # Steps reutilizables (Serenity @Step)
    │   ├── pages/mapeos/    # Page Objects (LoginPage, MainPage) - extienden PageObject
    │   ├── hooks/           # Hooks (DriverHooks)
    │   └── runners/         # Runners por feature
    └── resources/
        ├── features/        # Escenarios Gherkin (.feature)
        ├── serenity.properties
        └── logback-test.xml
```

## Requisitos

- **JDK 17**
- **Chrome** (o configurar otro driver en `serenity.properties`)

## Cómo ejecutar

```bash
# Compilar
./gradlew clean compileJava compileTestJava

# Ejecutar tests de login
./gradlew clean test --tests "co.com.proyecto.automatizacion.runners.LoginRunner"
```

> **Windows:** usa `.\gradlew.bat` en lugar de `./gradlew`.

Tras `test`, la tarea **aggregate** genera el reporte completo en un directorio con timestamp: **`target/site/serenity-YYYYMMDD-HHmmss`** (index.html, estilos, capturas).

**Cada ejecución crea su propia carpeta**, permitiendo mantener un historial completo de reportes y capturas sin sobrescribir ejecuciones anteriores.

## Dónde ver el reporte y las capturas

1. **Encontrar el reporte más reciente**  
   Al ejecutar los tests, verás en la consola un mensaje como:
   ```
   ==========================================
   Reporte de esta ejecución: C:\...\target\site\serenity-20260217-110645
   ==========================================
   ```
   Los directorios se ordenan por fecha/hora, así que el más reciente es el último.

2. **Abrir el reporte principal**  
   Abre con doble clic (o desde el navegador):  
   **`target\site\serenity-YYYYMMDD-HHmmss\index.html`**  
   *(Abrirlo desde el Explorador de archivos para que las rutas relativas a CSS/imágenes funcionen. Si no ves los cambios tras volver a ejecutar tests, haz **actualización forzada** en el navegador: Ctrl+F5.)*

3. **Ver cada escenario**  
   En la portada del reporte, haz clic en el **nombre del escenario** (por ejemplo "Iniciar sesión exitosamente") para ir a la página de detalle de ese test.

4. **Desplegar los pasos (y la evidencia)**  
   En la página del escenario, los pasos suelen estar colapsados. Haz clic en la **flecha/caret (▶)** a la izquierda del paso para expandir y ver los subpasos y, si el reporte las enlaza, las capturas.

5. **Ver las capturas directamente**  
   Las capturas se guardan como archivos **`.png`** en la misma carpeta del reporte:  
   **`target\site\serenity-YYYYMMDD-HHmmss\`**  
   Cada ejecución tiene sus propias capturas organizadas por fecha/hora.

6. **Historial de ejecuciones**  
   Todas las ejecuciones anteriores se mantienen en `target\site\` con nombres como:
   - `serenity-20260217-110645` (17 feb 2026, 11:06:45)
   - `serenity-20260217-143022` (17 feb 2026, 14:30:22)
   - etc.

## Configuración

- **Serenity:** `src/test/resources/serenity.properties`  
  (driver, nombre del proyecto, capturas, encoding).
- **Gherkin:** idioma español en los `.feature` (`# language: es`).

### Credenciales configurables

Las credenciales se leen en este orden de prioridad:

1. **Variables de entorno:** `ORANGEHRM_USERNAME`, `ORANGEHRM_PASSWORD`
2. **Propiedades del sistema:** `-Dorangehrm.username=...`, `-Dorangehrm.password=...`
3. **serenity.properties:** `orangehrm.username`, `orangehrm.password`
4. **Valores por defecto:** `Admin` / `admin123`

```bash
# Ejemplo: usar variables de entorno
export ORANGEHRM_USERNAME=Admin
export ORANGEHRM_PASSWORD=admin123
./gradlew test --tests "co.com.proyecto.automatizacion.runners.LoginRunner"

# Ejemplo: usar propiedades del sistema
./gradlew test -Dorangehrm.username=Admin -Dorangehrm.password=admin123 --tests "co.com.proyecto.automatizacion.runners.LoginRunner"
```

## CI/CD

El proyecto incluye un pipeline de **GitHub Actions** (`.github/workflows/test.yml`) que:

- Se ejecuta en cada `push` y `pull_request` a `main` o `master`
- Usa JDK 17 y Gradle con caché
- Ejecuta los tests en modo headless (`-Dheadless.mode=true`)
- Sube el reporte Serenity como artefacto (disponible 7 días) para descargar tras cada ejecución

Para ver el reporte en GitHub: **Actions** → ejecución → **Artifacts** → `serenity-report`.

## Características

- Patrón **POM** con páginas en `pages/mapeos`.
- **Serenity 4.2.0** con `@Steps`, reportes y capturas en fallos.
- **Cucumber 7** con step definitions en español y glue explícito por definiciones.
- **Java 17** y dependencias alineadas (sin conflictos entre Serenity, Cucumber y JUnit 4).
- Runner de login con tag `@InicioSesionExitoso`.

## Licencia

Uso interno / portafolio.
