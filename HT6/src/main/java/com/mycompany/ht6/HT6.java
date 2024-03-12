/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.ht6;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
/**
 *
 * @author Oscar Escriba
 */

interface Carta {
    String getNombre();
    String getTipo();
}

class Monstruo implements Carta {
    private String nombre;
    private String tipo;

    public Monstruo(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public String getTipo() {
        return tipo;
    }
}

class Trampa implements Carta {
    private String nombre;
    private String tipo;

    public Trampa(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public String getTipo() {
        return tipo;
    }
}

class Hechizo implements Carta {
    private String nombre;
    private String tipo;

    public Hechizo(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public String getTipo() {
        return tipo;
    }
}

class FabricaMapas {
    public static Map<String, Carta> crearMapa(int tipoMapa) {
        switch (tipoMapa) {
            case 1:
                return new HashMap<>();
            case 2:
                return new TreeMap<>();
            case 3:
                return new LinkedHashMap<>();
            default:
                throw new IllegalArgumentException("Tipo de mapa no válido");
        }
    }
}

public class HT6 {
    public static void main(String[] args) {
        Map<String, Carta> cartasDisponibles = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("cartas.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|");
                String nombre = partes[0].trim();
                String tipo = partes[1].trim();
                Carta carta;
                switch (tipo) {
                    case "Monstruo":
                        carta = new Monstruo(nombre, tipo);
                        break;
                    case "Trampa":
                        carta = new Trampa(nombre, tipo);
                        break;
                    case "Hechizo":
                        carta = new Hechizo(nombre, tipo);
                        break;
                    default:
                        throw new IllegalArgumentException("Tipo de carta desconocido");
                }
                cartasDisponibles.put(nombre, carta);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Seleccione el tipo de mapa (1 para HashMap, 2 para TreeMap, 3 para LinkedHashMap):");
        int tipoMapa = scanner.nextInt();
        Map<String, Carta> mapa = FabricaMapas.crearMapa(tipoMapa);

        boolean salir = false;
        while (!salir) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Agregar una carta a la colección del usuario");
            System.out.println("2. Mostrar el tipo de una carta específica");
            System.out.println("3. Mostrar el nombre, tipo y cantidad de cada carta en la colección del usuario");
            System.out.println("4. Mostrar el nombre, tipo y cantidad de cada carta en la colección del usuario, ordenadas por tipo");
            System.out.println("5. Mostrar el nombre y tipo de todas las cartas existentes");
            System.out.println("6. Mostrar el nombre y tipo de todas las cartas existentes, ordenadas por tipo");
            System.out.println("7. Salir");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer del scanner
            
            switch (opcion) {
                case 1:
                    System.out.println("Ingrese el nombre de la carta que desea agregar:");
                    String nombreCarta = scanner.nextLine();
                    if (cartasDisponibles.containsKey(nombreCarta)) {
                        mapa.put(nombreCarta, cartasDisponibles.get(nombreCarta));
                        System.out.println("Carta agregada correctamente");
                    } else {
                        System.out.println("La carta no se encuentra entre las cartas disponibles");
                    }
                    break;
                case 2:
                    System.out.println("Ingrese el nombre de la carta:");
                    String nombreCartaBuscada = scanner.nextLine();
                    if (mapa.containsKey(nombreCartaBuscada)) {
                        System.out.println("Tipo de carta: " + mapa.get(nombreCartaBuscada).getTipo());
                    } else {
                        System.out.println("La carta no se encuentra en la colección del usuario");
                    }
                    break;
                case 3:
                    for (Carta c : mapa.values()) {
                        System.out.println("Nombre: " + c.getNombre() + ", Tipo: " + c.getTipo());
                    }
                    break;
                case 4:
                    Map<String, Integer> contadorPorTipo = new HashMap<>();
                    for (Carta c : mapa.values()) {
                        contadorPorTipo.put(c.getTipo(), contadorPorTipo.getOrDefault(c.getTipo(), 0) + 1);
                    }
                    contadorPorTipo.entrySet().stream()
                            .sorted(Map.Entry.comparingByKey())
                            .forEach(entry -> System.out.println("Tipo: " + entry.getKey() + ", Cantidad: " + entry.getValue()));
                    break;
                case 5:
                    for (Carta c : cartasDisponibles.values()) {
                        System.out.println("Nombre: " + c.getNombre() + ", Tipo: " + c.getTipo());
                    }
                    break;
                case 6:
                    cartasDisponibles.values().stream()
                            .sorted(Comparator.comparing(Carta::getTipo))
                            .forEach(c -> System.out.println("Nombre: " + c.getNombre() + ", Tipo: " + c.getTipo()));
                    break;
                case 7:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
        scanner.close();
    }
}

