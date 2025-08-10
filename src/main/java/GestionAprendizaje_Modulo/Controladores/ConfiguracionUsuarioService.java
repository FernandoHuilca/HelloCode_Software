package GestionAprendizaje_Modulo.Controladores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para manejar la configuración de usuarios (lenguaje, nivel, primera vez)
 */
public class ConfiguracionUsuarioService {

    private static final String ARCHIVO_CONFIGURACION = "/GestionAprendizaje_Modulo/data/configuracion_usuarios.txt";
    private static ConfiguracionUsuarioService instancia;

    public static ConfiguracionUsuarioService getInstancia() {
        if (instancia == null) {
            instancia = new ConfiguracionUsuarioService();
        }
        return instancia;
    }

    public static class ConfiguracionUsuario {
        private String username;
        private String lenguaje;
        private String nivel;
        private boolean primeraVez;

        public ConfiguracionUsuario(String username, String lenguaje, String nivel, boolean primeraVez) {
            this.username = username;
            this.lenguaje = lenguaje;
            this.nivel = nivel;
            this.primeraVez = primeraVez;
        }

        public String getUsername() { return username; }
        public String getLenguaje() { return lenguaje; }
        public String getNivel() { return nivel; }
        public boolean isPrimeraVez() { return primeraVez; }

        public void setPrimeraVez(boolean primeraVez) { this.primeraVez = primeraVez; }
    }

    public boolean usuarioTieneConfiguracion(String username) {
        try {
            List<ConfiguracionUsuario> configuraciones = leerConfiguraciones();
            return configuraciones.stream().anyMatch(config -> config.getUsername().equals(username));
        } catch (Exception e) {
            System.err.println("Error al verificar configuración del usuario: " + e.getMessage());
            return false;
        }
    }

    public boolean esPrimeraVez(String username) {
        try {
            List<ConfiguracionUsuario> configuraciones = leerConfiguraciones();
            for (ConfiguracionUsuario config : configuraciones) {
                if (config.getUsername().equals(username)) {
                    return config.isPrimeraVez();
                }
            }
            return true;
        } catch (Exception e) {
            System.err.println("Error al verificar primera vez del usuario: " + e.getMessage());
            return true;
        }
    }

    public ConfiguracionUsuario obtenerConfiguracion(String username) {
        try {
            List<ConfiguracionUsuario> configuraciones = leerConfiguraciones();
            for (ConfiguracionUsuario config : configuraciones) {
                if (config.getUsername().equals(username)) {
                    return config;
                }
            }
            return null;
        } catch (Exception e) {
            System.err.println("Error al obtener configuración del usuario: " + e.getMessage());
            return null;
        }
    }

    public void guardarConfiguracion(String username, String lenguaje, String nivel) {
        try {
            List<ConfiguracionUsuario> configuraciones = leerConfiguraciones();

            int index = -1;
            for (int i = 0; i < configuraciones.size(); i++) {
                if (configuraciones.get(i).getUsername().equals(username)) {
                    index = i;
                    break;
                }
            }

            ConfiguracionUsuario nueva = new ConfiguracionUsuario(username, lenguaje, nivel, false);
            if (index >= 0) {
                configuraciones.set(index, nueva); // actualizar sin eliminar durante el foreach
            } else {
                configuraciones.add(nueva);
            }

            escribirConfiguraciones(configuraciones);
            System.out.println("Configuración guardada para usuario: " + username + " - " + lenguaje + " - " + nivel);

        } catch (Exception e) {
            System.err.println("Error al guardar configuración del usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void marcarUsuarioNuevo(String username) {
        try {
            List<ConfiguracionUsuario> configuraciones = leerConfiguraciones();
            boolean existe = configuraciones.stream().anyMatch(config -> config.getUsername().equals(username));
            if (!existe) {
                configuraciones.add(new ConfiguracionUsuario(username, "", "", true));
                escribirConfiguraciones(configuraciones);
                System.out.println("Usuario marcado como nuevo: " + username);
            }
        } catch (Exception e) {
            System.err.println("Error al marcar usuario como nuevo: " + e.getMessage());
        }
    }

    private List<ConfiguracionUsuario> leerConfiguraciones() throws IOException {
        List<ConfiguracionUsuario> configuraciones = new ArrayList<>();

        String rutaCompleta = System.getProperty("user.dir") + "/src/main/resources" + ARCHIVO_CONFIGURACION;
        Path path = Paths.get(rutaCompleta);

        if (Files.exists(path)) {
            try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    parsearLinea(configuraciones, linea);
                }
            }
            return configuraciones;
        }

        // Fallback: intentar leer desde el classpath la primera vez
        try (InputStream inputStream = getClass().getResourceAsStream(ARCHIVO_CONFIGURACION)) {
            if (inputStream == null) {
                System.out.println("Archivo de configuración no encontrado, se creará al guardar.");
                return configuraciones;
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    parsearLinea(configuraciones, linea);
                }
            }
        }
        return configuraciones;
    }

    private void parsearLinea(List<ConfiguracionUsuario> configuraciones, String linea) {
        if (!linea.trim().isEmpty() && !linea.startsWith("#")) {
            String[] partes = linea.split(";");
            if (partes.length == 4) {
                String username = partes[0].trim();
                String lenguaje = partes[1].trim();
                String nivel = partes[2].trim();
                boolean primeraVez = Boolean.parseBoolean(partes[3].trim());
                configuraciones.add(new ConfiguracionUsuario(username, lenguaje, nivel, primeraVez));
            }
        }
    }

    private void escribirConfiguraciones(List<ConfiguracionUsuario> configuraciones) throws IOException {
        String rutaCompleta = System.getProperty("user.dir") + "/src/main/resources" + ARCHIVO_CONFIGURACION;
        Path path = Paths.get(rutaCompleta);
        if (path.getParent() != null && !Files.exists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write("# Archivo de configuración de usuarios\n");
            writer.write("# Formato: username;lenguaje;nivel;primeraVez\n");
            writer.write("# primeraVez: true = necesita configuración inicial, false = ya configurado\n");

            for (ConfiguracionUsuario config : configuraciones) {
                writer.write(String.format("%s;%s;%s;%s\n",
                        config.getUsername(),
                        config.getLenguaje(),
                        config.getNivel(),
                        config.isPrimeraVez()));
            }
        }
    }
}