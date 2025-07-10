package Gamificacion_Modulo;

import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Logro> logrosDisponibles = new ArrayList<>();
    private static List<Desafio> desafiosActivos = new ArrayList<>();
    private static Ranking ranking = new Ranking();
    
    public static void main(String[] args) {
        // Crear estudiante principal
        Estudiante estudiante = new Estudiante("Juan Pérez", "juan@email.com", "juan123");
        ProgresoEstudiante progreso = new ProgresoEstudiante(estudiante);
        
        // Inicializar logros predeterminados
        inicializarLogros();
        
        System.out.println("*** BIENVENIDO AL SISTEMA DE GAMIFICACION ***");
        System.out.println("Estudiante: " + estudiante.getNombre() + " (" + estudiante.getUsuario() + ")");
        System.out.println("===============================================");
        
        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            int opcion = obtenerOpcion();
            
            switch (opcion) {
                case 1:
                    crearDesafioSemanal(progreso);
                    break;
                case 2:
                    crearDesafioMensual(progreso);
                    break;
                case 3:
                    crearLogroPersonalizado();
                    break;
                case 4:
                    simularActividad(progreso);
                    break;
                case 5:
                    mostrarProgreso(progreso);
                    break;
                case 6:
                    mostrarRanking(progreso);
                    break;
                case 7:
                    mostrarDesafiosActivos();
                    break;
                case 8:
                    mostrarLogrosDisponibles();
                    break;
                case 0:
                    continuar = false;
                    System.out.println("Gracias por usar el sistema de gamificacion!");
                    break;
                default:
                    System.out.println(">>> Opcion invalida. Intenta de nuevo.");
            }
            
            if (continuar) {
                System.out.println("\nPresiona Enter para continuar...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    private static void mostrarMenu() {
        System.out.println("\n===== MENU PRINCIPAL =====");
        System.out.println("1. Crear Desafio Semanal");
        System.out.println("2. Crear Desafio Mensual");
        System.out.println("3. Crear Logro Personalizado");
        System.out.println("4. Simular Actividad");
        System.out.println("5. Ver Mi Progreso");
        System.out.println("6. Ver Ranking");
        System.out.println("7. Ver Desafios Activos");
        System.out.println("8. Ver Logros Disponibles");
        System.out.println("0. Salir");
        System.out.print(">> Selecciona una opcion: ");
    }
    
    private static int obtenerOpcion() {
        try {
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            return opcion;
        } catch (Exception e) {
            scanner.nextLine(); // Limpiar buffer
            return -1;
        }
    }
    
    private static void inicializarLogros() {
        logrosDisponibles.add(new Logro("Principiante", "Completar tu primer desafio", "desafios_completados:1", 100));
        logrosDisponibles.add(new Logro("Dedicado", "Completar 3 desafios", "desafios_completados:3", 250));
        logrosDisponibles.add(new Logro("Acumulador", "Obtener 500 puntos", "puntos_totales:500", 150));
        logrosDisponibles.add(new Logro("Coleccionista", "Obtener 5 logros", "logros_obtenidos:5", 300));
        System.out.println(">>> Logros predeterminados cargados: " + logrosDisponibles.size());
    }
    
    private static void crearDesafioSemanal(ProgresoEstudiante progreso) {
        System.out.println("\n=== CREAR DESAFIO SEMANAL ===");
        System.out.print(">> Numero de actividades para completar (1-20): ");
        int meta = Math.max(1, Math.min(20, obtenerOpcion()));
        
        // Seleccionar logros manualmente
        List<Logro> logrosDesafio = seleccionarLogrosManualmente();
        DesafioSemanal desafio = new DesafioSemanal(meta, logrosDesafio);
        
        desafio.activar();
        desafiosActivos.add(desafio);
        
        System.out.println(">>> Desafio semanal creado con meta de " + meta + " actividades");
        System.out.println("Logros asociados: ");
        for (Logro logro : logrosDesafio) {
            System.out.println("   * " + logro.getNombre() + " (+" + logro.getPuntos() + " pts)");
        }
    }
    
    private static void crearDesafioMensual(ProgresoEstudiante progreso) {
        System.out.println("\n=== CREAR DESAFIO MENSUAL ===");
        System.out.print(">> Numero de actividades para completar (10-100): ");
        int meta = Math.max(10, Math.min(100, obtenerOpcion()));
        
        List<Logro> logrosDesafio = seleccionarLogrosManualmente();
        DesafioMensual desafio = new DesafioMensual(meta, logrosDesafio);
        
        desafio.activar();
        desafiosActivos.add(desafio);
        
        System.out.println(">>> Desafio mensual creado con meta de " + meta + " actividades");
        System.out.println("Logros asociados: ");
        for (Logro logro : logrosDesafio) {
            System.out.println("   * " + logro.getNombre() + " (+" + logro.getPuntos() + " pts)");
        }
    }
    
    private static void crearLogroPersonalizado() {
        System.out.println("\n=== CREAR LOGRO PERSONALIZADO ===");
        System.out.print(">> Nombre del logro: ");
        String nombre = scanner.nextLine();
        System.out.print(">> Descripcion: ");
        String descripcion = scanner.nextLine();
        
        System.out.println("\nTipos de criterios disponibles:");
        System.out.println("1. Completar cierta cantidad de desafios");
        System.out.println("2. Obtener cierta cantidad de puntos");
        System.out.println("3. Desbloquear cierta cantidad de logros");
        System.out.print(">> Selecciona tipo de criterio (1-3): ");
        int tipoCriterio = obtenerOpcion();
        
        String criterio = "";
        switch (tipoCriterio) {
            case 1:
                System.out.print(">> Cuantos desafios debe completar? ");
                int desafios = obtenerOpcion();
                criterio = "desafios_completados:" + desafios;
                break;
            case 2:
                System.out.print(">> Cuantos puntos debe obtener? ");
                int puntosRequeridos = obtenerOpcion();
                criterio = "puntos_totales:" + puntosRequeridos;
                break;
            case 3:
                System.out.print(">> Cuantos logros debe desbloquear? ");
                int logros = obtenerOpcion();
                criterio = "logros_obtenidos:" + logros;
                break;
            default:
                System.out.println(">>> Opcion invalida, usando criterio por defecto");
                criterio = "desafios_completados:1";
        }
        
        System.out.print(">> Puntos de recompensa (50-500): ");
        int puntos = Math.max(50, Math.min(500, obtenerOpcion()));
        
        Logro nuevoLogro = new Logro(nombre, descripcion, criterio, puntos);
        logrosDisponibles.add(nuevoLogro);
        
        System.out.println(">>> Logro personalizado creado!");
        System.out.println("* " + nombre + " - " + descripcion);
        System.out.println("  Criterio: " + criterio + " | Recompensa: +" + puntos + " pts");
    }
    
    private static void simularActividad(ProgresoEstudiante progreso) {
        if (desafiosActivos.isEmpty()) {
            System.out.println(">>> No hay desafios activos. Crea uno primero.");
            return;
        }
        
        System.out.println("\n=== SIMULAR ACTIVIDAD ===");
        System.out.println("Desafios activos:");
        for (int i = 0; i < desafiosActivos.size(); i++) {
            Desafio d = desafiosActivos.get(i);
            System.out.println((i + 1) + ". " + d.getNombre() + " - " + d.getDescripcion());
        }
        
        System.out.print(">> Selecciona desafio (1-" + desafiosActivos.size() + "): ");
        int indice = obtenerOpcion() - 1;
        
        if (indice >= 0 && indice < desafiosActivos.size()) {
            Desafio desafio = desafiosActivos.get(indice);
            System.out.print(">> Numero de actividades a completar (1-10): ");
            int actividades = Math.max(1, Math.min(10, obtenerOpcion()));
            
            // Simular actividades
            if (desafio instanceof DesafioSemanal) {
                ((DesafioSemanal) desafio).actualizarActividades(actividades);
            } else if (desafio instanceof DesafioMensual) {
                ((DesafioMensual) desafio).actualizarActividades(actividades);
            }
            
            // Actualizar progreso
            progreso.actualizarProgreso(desafio);
            ranking.actualizarRanking(progreso);
            
            // Remover desafio si esta completado
            if (desafio.estaCompletado() && !desafio.getEstaActivo()) {
                desafiosActivos.remove(desafio);
                System.out.println("*** Desafio completado y removido de la lista activa!");
            }
        } else {
            System.out.println(">>> Seleccion invalida.");
        }
    }
    
    private static void mostrarProgreso(ProgresoEstudiante progreso) {
        System.out.println("\n=== MI PROGRESO ===");
        System.out.println("Estudiante: " + progreso.getEstudiante().getNombre());
        System.out.println("Puntos Totales: " + progreso.getPuntosTotal());
        System.out.println("Desafios Completados: " + progreso.getDesafiosCompletados());
        System.out.println("Logros Desbloqueados: " + progreso.getLogros().size());
        
        System.out.println("\n=== LOGROS OBTENIDOS ===");
        if (progreso.getLogros().isEmpty()) {
            System.out.println("   (Ningun logro desbloqueado aun)");
        } else {
            for (Logro logro : progreso.getLogros()) {
                System.out.println("   * " + logro.getNombre() + " - " + logro.getDescripcion() + " (+" + logro.getPuntos() + " pts)");
            }
        }
        
        System.out.println("\n=== PROGRESO EN DESAFIOS ===");
        if (progreso.getListaDesafios().isEmpty()) {
            System.out.println("   (No hay progreso en desafios)");
        } else {
            for (Map.Entry<String, Double> entry : progreso.getListaDesafios().entrySet()) {
                System.out.println("   > Desafio ID " + entry.getKey() + ": " + String.format("%.1f", entry.getValue()) + "%");
            }
        }
    }
    
    private static void mostrarRanking(ProgresoEstudiante progreso) {
        System.out.println("\n=== RANKING GENERAL ===");
        List<ProgresoEstudiante> top = ranking.obtenerRankingGeneral();
        
        if (top.isEmpty()) {
            System.out.println("   (No hay datos de ranking)");
        } else {
            for (int i = 0; i < top.size(); i++) {
                ProgresoEstudiante p = top.get(i);
                String medalla = (i == 0) ? "[1]" : (i == 1) ? "[2]" : (i == 2) ? "[3]" : "[" + (i+1) + "]";
                System.out.println((i + 1) + ". " + medalla + " " + 
                                 p.getEstudiante().getNombre() + " - " + 
                                 p.getPuntosTotal() + " puntos (" + 
                                 p.getLogros().size() + " logros)");
            }
        }
        
        Map<String, Object> stats = ranking.generarEstadisticasRanking();
        System.out.println("\n=== ESTADISTICAS ===");
        System.out.println("Total estudiantes: " + stats.get("total_estudiantes"));
        if (stats.containsKey("lider")) {
            System.out.println("Lider actual: " + stats.get("lider") + " (" + stats.get("puntos_maximo") + " puntos)");
        }
    }
    
    private static void mostrarDesafiosActivos() {
        System.out.println("\n=== DESAFIOS ACTIVOS ===");
        if (desafiosActivos.isEmpty()) {
            System.out.println("   (No hay desafios activos)");
        } else {
            for (Desafio desafio : desafiosActivos) {
                System.out.println("* " + desafio.getNombre() + " - " + desafio.getDescripcion());
                System.out.println("   Estado: " + (desafio.getEstaActivo() ? "Activo" : "Inactivo"));
                System.out.println("   Criterios: " + desafio.definirCriterios());
                
                if (desafio instanceof DesafioSemanal) {
                    DesafioSemanal ds = (DesafioSemanal) desafio;
                    System.out.println("   Progreso: " + ds.getActividadesCompletadas() + "/" + ds.getMetaSemanal() + 
                                     " (" + String.format("%.1f", ds.getProgreso()) + "%)");
                } else if (desafio instanceof DesafioMensual) {
                    DesafioMensual dm = (DesafioMensual) desafio;
                    System.out.println("   Progreso: " + dm.getActividadesCompletadas() + "/" + dm.getObjetivoMensual() + 
                                     " (" + String.format("%.1f", dm.getProgreso()) + "%)");
                }
                System.out.println();
            }
        }
    }
    
    private static List<Logro> seleccionarLogrosManualmente() {
        List<Logro> seleccionados = new ArrayList<>();
        
        if (logrosDisponibles.isEmpty()) {
            System.out.println(">>> No hay logros disponibles");
            return seleccionados;
        }
        
        System.out.println("\n=== SELECCIONAR LOGROS PARA EL DESAFIO ===");
        System.out.println("Logros disponibles:");
        for (int i = 0; i < logrosDisponibles.size(); i++) {
            Logro logro = logrosDisponibles.get(i);
            System.out.println((i + 1) + ". " + logro.getNombre() + " - " + logro.getDescripcion() + " (+" + logro.getPuntos() + " pts)");
        }
        
        System.out.print(">> Cuantos logros quieres agregar? (0-" + logrosDisponibles.size() + "): ");
        int cantidad = Math.max(0, Math.min(logrosDisponibles.size(), obtenerOpcion()));
        
        for (int i = 0; i < cantidad; i++) {
            System.out.print(">> Selecciona logro " + (i + 1) + " (1-" + logrosDisponibles.size() + "): ");
            int indice = obtenerOpcion() - 1;
            
            if (indice >= 0 && indice < logrosDisponibles.size()) {
                Logro logro = logrosDisponibles.get(indice);
                if (!seleccionados.contains(logro)) {
                    seleccionados.add(logro);
                    System.out.println("   + Agregado: " + logro.getNombre());
                } else {
                    System.out.println("   ! Logro ya seleccionado");
                    i--; // Repetir esta iteracion
                }
            } else {
                System.out.println("   ! Seleccion invalida");
                i--; // Repetir esta iteracion
            }
        }
        
        return seleccionados;
    }
    
    private static void mostrarLogrosDisponibles() {
        System.out.println("\n=== LOGROS DISPONIBLES ===");
        if (logrosDisponibles.isEmpty()) {
            System.out.println("   (No hay logros disponibles)");
        } else {
            for (int i = 0; i < logrosDisponibles.size(); i++) {
                Logro logro = logrosDisponibles.get(i);
                System.out.println((i + 1) + ". " + logro.getNombre());
                System.out.println("   Descripcion: " + logro.getDescripcion());
                System.out.println("   Puntos: +" + logro.getPuntos());
                System.out.println("   Criterio: " + logro.getCriteriosDesbloqueo());
                System.out.println();
            }
        }
    }
} 