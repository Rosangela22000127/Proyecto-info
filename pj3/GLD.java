import java.io.*;
import java.util.*;

public class GLD {
    public static void convertirDesdeGLD(String archivoEntrada, String archivoSalida) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(archivoEntrada));
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivoSalida));

            // Leer los símbolos no terminales
            String[] noTerminales = reader.readLine().split(",");
            // Leer los símbolos terminales (alfabeto)
            String[] alfabeto = reader.readLine().split(",");
            // Leer el símbolo inicial
            String simboloInicial = reader.readLine();
            // Leer las reglas de producción
            String regla;
            List<String> producciones = new ArrayList<>();
            while ((regla = reader.readLine()) != null) {
                producciones.add(regla);
            }

            // Escribir el alfabeto en la primera línea
            writer.write(String.join(",", alfabeto));
            writer.newLine();
            // Escribir la cantidad de estados (número de no terminales + 2)
            writer.write(Integer.toString(noTerminales.length + 2));
            writer.newLine();
            // Escribir el estado final (el último estado)
            writer.write(Integer.toString(noTerminales.length + 1));
            writer.newLine();
            // Escribir las transiciones lambda en la cuarta línea
            for (int i = 0; i <= noTerminales.length + 1; i++) {
                writer.write(",");
                if (i == noTerminales.length + 1) {
                    writer.write("0");
                } else {
                    writer.write(Integer.toString(i));
                }
            }
            writer.newLine();

            // Escribir las transiciones para cada símbolo del alfabeto
            for (String terminal : alfabeto) {
                writer.write(terminal);
                for (int i = 0; i < noTerminales.length + 1; i++) {
                    writer.write(",0");
                }
                writer.newLine();
            }

            // Cerrar los lectores y escritores
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Quitar antes de entrega.
        if (args.length < 2) {
            System.out.println("Uso: java AFN 'archivo_entrada.gld' 'archivo_salida.afn'");
            System.exit(1);
        }

        convertirDesdeGLD(args[0], args[1]);
    }
}
