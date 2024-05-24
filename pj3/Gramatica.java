import java.util.*;
import java.io.*;

public class Gramatica {

	public static void main(String[] args) {
		//En el caso que escriban las banderas con mas argumentos de los que ncesitamos
		if (args.length < 3) {
			System.out.println("Uso: java Main 'archivo_gramatica' -afn o -afd 'archivo_salida'");
			System.out.println("Uso: java Main 'archivo_gramatica' -check");
			System.exit(1);
		}

		String archivo = args[0];
		String opcion = args[1];
		String salida = args[2];

		AFN afn = new AFN(archivo);

		//La ejecucion del lo pedido por el programa
		if (opcion.equals("-afn")) {
			// boolean aceptada = afn.accept();
í
		} else if (opcion.equals("-afd")) {
			afn.toAFD(salida);
		} else if (opcion.equals("-check")) {
			
		} else {
			System.out.println("Revise el formato de ingreso: ");
			System.out.println("Uso: java Main 'archivo_gramatica' -afn o -afd 'archivo_salida'");
			System.out.println("Uso: java Main 'archivo_gramatica' -check");
			System.exit(1);
		}
	}
}
