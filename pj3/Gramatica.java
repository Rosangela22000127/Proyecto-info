import java.util.*;
import java.io.*;

public class Gramatica {

	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("Uso: java Main 'archivo_gramatica' -afn o -afd 'archivo_salida'");
			System.out.println("Uso: java Main 'archivo_gramatica' -check");
			System.exit(1);
		}

		String entrada = args[0];
		String opcion = args[1];
		String salida = args[2];

		GLD gld = new GLD();

		if (opcion.equals("-afn")) {
			gld.convertirDesdeGLD(entrada, salida);
		} else if (opcion.equals("-afd")) {

		} else if (opcion.equals("-check")) {
			// Lógica para el modo CHECK
		} else {
			System.out.println("Revise el formato de ingreso: ");
			System.out.println("Uso: java Main 'archivo_gramatica' -afn o -afd 'archivo_salida'");
			System.out.println("Uso: java Main 'archivo_gramatica' -check");
			System.exit(1);
		}
	}
}

