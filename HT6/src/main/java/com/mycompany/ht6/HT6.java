/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.ht6;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Scanner;
import java.util.stream.Collectors;

public class HT6 {
    private static final String RUTA_ARCHIVO = "C:\\Users\\Oscar Escriba\\Desktop\\7mo semestre UVG\\Algoritmos y estructura de datos\\HojaDeTrabajo6-ED\\HT6\\src\\main\\java\\com\\mycompany\\ht6\\Cartas.txt";
    private static Map<String, String> cartasDisponibles;
    private static Map<String, Integer> coleccionUsuario = new HashMap<>();

    public static void main(String[] args) {
        int tipoMapa = seleccionarMapa();
        cartasDisponibles = leerCartas(RUTA_ARCHIVO, tipoMapa);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nSeleccione una opción:");
            System.out.println("1. Buscar una carta");
            System.out.println("2. Agregar una carta a su colección");
            System.out.println("3. Mostrar todas las cartas de su colección");
            System.out.println("4. Mostrar todas las cartas existentes");
            System.out.println("5. Mostrar todas las cartas existentes, ordenadas por tipo");
            System.out.println("6. Mostrar el tipo de una carta específica");
            System.out.println("7. Mostrar el nombre, tipo y cantidad de cada carta en su colección");
            System.out.println("8. Mostrar el nombre, tipo y cantidad de cada carta en su colección, ordenadas por tipo");
            System.out.println("9. Salir");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    buscarCarta(cartasDisponibles);
                    break;
                case 2:
                    agregarCartaAColeccion(cartasDisponibles);
                    break;
                case 3:
                    mostrarCartasColeccion(coleccionUsuario);
                    break;
                case 4:
                    mostrarTodasLasCartas(cartasDisponibles);
                    break;
                case 5:
                    mostrarTodasLasCartasOrdenadasPorTipo(cartasDisponibles);
                    break;
                case 6:
                    mostrarTipoDeCartaEspecifica(cartasDisponibles);
                    break;
                case 7:
                    mostrarDetalleCartasColeccion(coleccionUsuario);
                    break;
                case 8:
                    mostrarDetalleCartasColeccionOrdenadasPorTipo(coleccionUsuario);
                    break;
                case 9:
                    System.out.println("Saliendo del programa...");
                    return;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static int seleccionarMapa() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Seleccione el tipo de mapa:");
        System.out.println("1. HashMap");
        System.out.println("2. TreeMap");
        System.out.println("3. LinkedHashMap");
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        return opcion;
    }

    private static Map<String, String> leerCartas(String rutaArchivo, int tipoMapa) {
        Map<String, String> cartas;
        switch (tipoMapa) {
            case 1:
                cartas = new HashMap<>();
                break;
            case 2:
                cartas = new TreeMap<>();
                break;
            case 3:
                cartas = new LinkedHashMap<>();
                break;
            default:
                throw new IllegalArgumentException("Tipo de mapa no válido");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length == 2) {
                    String nombre = partes[0].trim();
                    String tipo = partes[1].trim();
                    cartas.put(nombre, tipo);
                } else {
                    System.err.println("Error: formato de línea incorrecto en el archivo de cartas.");
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de cartas: " + e.getMessage());
        }
        return cartas;
    }

    private static void buscarCarta(Map<String, String> cartas) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el nombre de la carta a buscar: ");
        String nombre = scanner.nextLine();
        String tipo = cartas.get(nombre);
        if (tipo != null) {
            System.out.println("La carta '" + nombre + "' es de tipo '" + tipo + "'.");
        } else {
            System.out.println("La carta '" + nombre + "' no se encontró en la lista.");
        }
    }

    private static void agregarCartaAColeccion(Map<String, String> cartas) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el nombre de la carta a agregar a su colección: ");
        String nombre = scanner.nextLine();
        if (!cartas.containsKey(nombre)) {
            System.out.println("La carta '" + nombre + "' no se encuentra en la lista de cartas disponibles.");
            return;
        }
        coleccionUsuario.put(nombre, coleccionUsuario.getOrDefault(nombre, 0) + 1);
        System.out.println("Se ha agregado la carta '" + nombre + "' a su colección.");
    }

    private static void mostrarCartasColeccion(Map<String, Integer> coleccion) {
        System.out.println("Cartas en su colección:");
        for (Map.Entry<String, Integer> entry : coleccion.entrySet()) {
            System.out.println("- " + entry.getKey() + ": " + entry.getValue() + " carta(s)");
        }
    }

    private static void mostrarTodasLasCartas(Map<String, String> cartas) {
        System.out.println("Todas las cartas existentes:");
        cartas.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(entry -> System.out.println("- " + entry.getKey() + ": " + entry.getValue()));
    }

private static void mostrarTodasLasCartasOrdenadasPorTipo(Map<String, String> cartas) {
        System.out.println("Todas las cartas existentes, ordenadas por tipo:");
        cartas.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(entry -> System.out.println("- " + entry.getKey() + ": " + entry.getValue()));
    }

    private static void mostrarTipoDeCartaEspecifica(Map<String, String> cartas) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el nombre de la carta de la que desea conocer el tipo: ");
        String nombre = scanner.nextLine();
        String tipo = cartas.get(nombre);
        if (tipo != null) {
            System.out.println("La carta '" + nombre + "' es de tipo '" + tipo + "'.");
        } else {
            System.out.println("La carta '" + nombre + "' no se encontró en la lista.");
        }
    }

    private static void mostrarDetalleCartasColeccion(Map<String, Integer> coleccion) {
        System.out.println("Detalle de cartas en su colección:");
        for (Map.Entry<String, Integer> entry : coleccion.entrySet()) {
            System.out.println("- Nombre: " + entry.getKey() + ", Tipo: " + cartasDisponibles.get(entry.getKey()) + ", Cantidad: " + entry.getValue());
        }
    }
    private static void mostrarDetalleCartasColeccionOrdenadasPorTipo(Map<String, Integer> coleccion) {
    System.out.println("Detalle de cartas en su colección, ordenadas por tipo:");
    coleccion.entrySet().stream()
            .sorted((c1, c2) -> {
                String tipo1 = cartasDisponibles.get(c1.getKey());
                String tipo2 = cartasDisponibles.get(c2.getKey());
                return tipo1.compareTo(tipo2);
            })
            .forEach(entry -> System.out.println("- Nombre: " + entry.getKey() + ", Tipo: " + cartasDisponibles.get(entry.getKey()) + ", Cantidad: " + entry.getValue()));
}
} 


