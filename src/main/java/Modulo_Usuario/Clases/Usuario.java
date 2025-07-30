package Modulo_Usuario.Clases;

import GestionAprendizaje_Modulo.Logica.Curso;
import MetodosGlobales.LeccionesCompletadas;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Usuario extends UsuarioBase {
    private String nombre;
    private String email;
    private int xp;
    private TipoDeAcceso rol;
    private Curso curso;


    // Constructor por defecto
    public Usuario() {
        super();
        this.xp = 0;
        this.rol = TipoDeAcceso.USUARIO;
    }

    // Constructor con parámetros
    public Usuario(String username, String password) {
        super(username, password);
        this.xp = 0;
        this.rol = TipoDeAcceso.USUARIO;
    }

    // Constructor completo
    public Usuario(String username, String password, String nombre, String email) {
        super(username, password);
        this.nombre = nombre;
        this.email = email;
        this.xp = 0;
        this.rol = TipoDeAcceso.USUARIO;
    }

    // Constructor con XP
    public Usuario(String username, String password, String nombre, String email, int xp) {
        super(username, password);
        this.nombre = nombre;
        this.email = email;
        this.xp = xp;
        this.rol = TipoDeAcceso.USUARIO;
    }

    public Usuario(String username, String password, String nombre, String email, int xp, TipoDeAcceso rol) {
        super(username, password);
        this.nombre = nombre;
        this.email = email;
        this.xp = xp;
        this.rol = rol;
    }


    // ... otros getters y setters existentes ...


    public int getXp() {
        return xp;
    }

    public TipoDeAcceso getRol() {
        return rol;
    }

    public void setRol(TipoDeAcceso rol) {
        this.rol = rol;
    }

    public void setXp(int xp) {
        if (xp > 10000) {
            this.xp = 10000;
        } else {
            this.xp = xp;
        }
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        // Usar getters para username y password heredados de UsuarioBase
        return getUsername() + ";" + getPassword() + ";" + nombre + ";" + email + ";" + xp + ";" + rol;
    }

    // Método para crear usuario desde línea de archivo
    public static Usuario fromString(String linea) {
        String[] datos = linea.split(";");
        if (datos.length >= 2) {
            Usuario usuario = new Usuario(datos[0], datos[1]);
            if (datos.length >= 3) usuario.setNombre(datos[2]);
            if (datos.length >= 4) usuario.setEmail(datos[3]);
            if (datos.length >= 5) {
                try {
                    usuario.setXp(Integer.parseInt(datos[4]));
                } catch (NumberFormatException e) {
                    usuario.setXp(0);
                }
            }
            if (datos.length >= 6) {
                try {
                    usuario.setRol(TipoDeAcceso.valueOf(datos[5]));
                } catch (Exception e) {
                    usuario.setRol(TipoDeAcceso.USUARIO);
                }
            }
            return usuario;
        }
        return null;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void registrarCurso() {
        String rutaArchivo = "src/main/java/Modulo_Usuario/Usuarios/cursosDelUsuario.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo, true))) {
            // Unir todos los datos con ;
            String linea = String.join(";",
                    getUsername(),
                    curso.getId(),
                    String.valueOf(LeccionesCompletadas.getLeccionesCompletadas())
            );
            writer.write(linea);
            writer.newLine();
            System.out.println("Datos guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }
}