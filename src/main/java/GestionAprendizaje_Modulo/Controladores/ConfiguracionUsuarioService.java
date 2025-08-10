package GestionAprendizaje_Modulo.Controladores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
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
        private List<String> lenguajes;
        private List<String> niveles;
        private boolean primeraVez;

        public ConfiguracionUsuario(String username, List<String> lenguajes, List<String> niveles, boolean primeraVez) {
            this.username = username;
            this.lenguajes = lenguajes != null ? lenguajes : new ArrayList<>();
            this.niveles = niveles != null ? niveles : new ArrayList<>();
            this.primeraVez = primeraVez;
        }

        // Constructor de compatibilidad para un solo lenguaje
        public ConfiguracionUsuario(String username, String lenguaje, String nivel, boolean primeraVez) {
            this.username = username;
            this.lenguajes = new ArrayList<>();
            this.niveles = new ArrayList<>();
            if (lenguaje != null && !lenguaje.isEmpty()) {
                this.lenguajes.add(lenguaje);
                this.niveles.add(nivel != null ? nivel : "Básico");
            }
            this.primeraVez = primeraVez;
        }

        public String getUsername() { return username; }
        public List<String> getLenguajes() { return lenguajes; }
        public List<String> getNiveles() { return niveles; }
        public boolean isPrimeraVez() { return primeraVez; }

        // Métodos de compatibilidad para código existente
        public String getLenguaje() {
            return lenguajes.isEmpty() ? "" : lenguajes.get(0);
        }
        public String getNivel() {
            return niveles.isEmpty() ? "Básico" : niveles.get(0);
        }

        public void setPrimeraVez(boolean primeraVez) { this.primeraVez = primeraVez; }

        // Métodos para manejar múltiples lenguajes
        public void agregarLenguaje(String lenguaje, String nivel) {
            if (!lenguajes.contains(lenguaje)) {
                lenguajes.add(lenguaje);
                niveles.add(nivel);
            }
        }

        public void actualizarNivel(String lenguaje, String nuevoNivel) {
            int index = lenguajes.indexOf(lenguaje);
            if (index != -1) {
                niveles.set(index, nuevoNivel);
            }
        }

        public String getNivelPorLenguaje(String lenguaje) {
            int index = lenguajes.indexOf(lenguaje);
            return index != -1 ? niveles.get(index) : "Básico";
        }
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

    // Nuevo método para agregar un lenguaje adicional a un usuario existente
    public void agregarLenguajeAUsuario(String username, String lenguaje, String nivel) {
        try {
            List<ConfiguracionUsuario> configuraciones = leerConfiguraciones();

            for (int i = 0; i < configuraciones.size(); i++) {
                ConfiguracionUsuario config = configuraciones.get(i);
                if (config.getUsername().equals(username)) {
                    config.agregarLenguaje(lenguaje, nivel);
                    configuraciones.set(i, config);
                    escribirConfiguraciones(configuraciones);
                    System.out.println("Lenguaje agregado para usuario: " + username + " - " + lenguaje + " - " + nivel);
                    return;
                }
            }

            // Si el usuario no existe, crear nueva configuración
            ConfiguracionUsuario nueva = new ConfiguracionUsuario(username, lenguaje, nivel, false);
            configuraciones.add(nueva);
            escribirConfiguraciones(configuraciones);

        } catch (Exception e) {
            System.err.println("Error al agregar lenguaje al usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para verificar si un usuario ya tiene un lenguaje específico
    public boolean usuarioTieneLenguaje(String username, String lenguaje) {
        ConfiguracionUsuario config = obtenerConfiguracion(username);
        return config != null && config.getLenguajes().contains(lenguaje);
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
                String lenguajesStr = partes[1].trim();
                String nivelesStr = partes[2].trim();
                boolean primeraVez = Boolean.parseBoolean(partes[3].trim());

                // Parsear múltiples lenguajes y niveles
                List<String> lenguajes = new ArrayList<>();
                List<String> niveles = new ArrayList<>();

                if (!lenguajesStr.isEmpty()) {
                    String[] lenguajesArray = lenguajesStr.split(",");
                    String[] nivelesArray = nivelesStr.split(",");

                    for (int i = 0; i < lenguajesArray.length; i++) {
                        lenguajes.add(lenguajesArray[i].trim());
                        if (i < nivelesArray.length) {
                            niveles.add(nivelesArray[i].trim());
                        } else {
                            niveles.add("Básico"); // Nivel por defecto
                        }
                    }
                }

                configuraciones.add(new ConfiguracionUsuario(username, lenguajes, niveles, primeraVez));
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
            writer.write("# Formato: username;lenguajes;niveles;primeraVez\n");
            writer.write("# lenguajes: separados por comas (Java,Python,C)\n");
            writer.write("# niveles: separados por comas en el mismo orden que los lenguajes (Básico,Intermedio,Avanzado)\n");
            writer.write("# primeraVez: true = necesita configuración inicial, false = ya configurado\n");

            for (ConfiguracionUsuario config : configuraciones) {
                String lenguajesStr = String.join(",", config.getLenguajes());
                String nivelesStr = String.join(",", config.getNiveles());

                writer.write(String.format("%s;%s;%s;%s\n",
                        config.getUsername(),
                        lenguajesStr,
                        nivelesStr,
                        config.isPrimeraVez()));
            }
        }
    }
}