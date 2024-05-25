import java.util.*;
import java.io.*;

/*
    Utilice esta clase para guardar la informacion de su
    AFN. NO DEBE CAMBIAR LOS NOMBRES DE LA CLASE NI DE LOS 
    METODOS que ya existen, sin embargo, usted es libre de 
    agregar los campos y metodos que desee.
*/

public class AFN {
	private int[] estadosFinales;
	private String[] alfabeto;
	private int estados;
	private int[][][] transiciones;

	public AFN(String path) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));

			// La primera línea contendrá los símbolos terminales (o el alfabeto de entrada)
			// separados por una coma
			alfabeto = reader.readLine().split(",");

			// La segunda línea contendrá la cantidad de estados del afn (incluido el estado
			// absorbente).
			estados = Integer.parseInt(reader.readLine());

			// La tercera línea contendrá los estados finales separados por una coma.
			String[] estadosFinalesStr = reader.readLine().split(",");
			estadosFinales = new int[estadosFinalesStr.length];
			for (int i = 0; i < estadosFinalesStr.length; i++) {
				estadosFinales[i] = Integer.parseInt(estadosFinalesStr[i]);
			}

			// filas de la matriz de transicion
			transiciones = new int[alfabeto.length + 1][estados][];

			// lee las transiciones del archico y las asigna a la matriz
			for (int i = 0; i <= alfabeto.length; i++) {
				String linea = reader.readLine();
				String[] valores = linea.split(",");
				for (int j = 0; j < valores.length; j++) {
					String[] estadosDestino = valores[j].split(";");
					transiciones[i][j] = new int[estadosDestino.length];
					for (int k = 0; k < estadosDestino.length; k++) {
						transiciones[i][j][k] = Integer.parseInt(estadosDestino[k]);
					}
				}
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public boolean accept(String string) {
		Set<Integer> estadosActuales = new HashSet<>();
		estadosActuales.add(1); // Agregar el estado inicial

		for (char simbolo : string.toCharArray()) {
			Set<Integer> nuevosEstados = new HashSet<>();

			for (int estadoActual : estadosActuales) {

				// Obtener los estados destino para el símbolo actual y el estado actual
				int[] siguienteEstado = transiciones[0][estadoActual];

				// Agregar los estados destino a la lista de nuevos estados
				agregarEstadosDestino(estadosActuales, siguienteEstado, estadoActual);

			}
			// Para cada estado actual, obtener los estados a los que se puede transicionar
			// con el símbolo actual
			for (int estadoActual : estadosActuales) {
				int simboloIndicado = obtenerIndiceSimbolo(simbolo) + 1;
				if (simboloIndicado == 0) {
					return false; // El símbolo no está en el alfabeto
				}

				// Obtener los estados destino para el símbolo actual y el estado actual
				int[] siguienteEstado = transiciones[simboloIndicado][estadoActual];

				// Agregar los estados destino a la lista de nuevos estados
				agregarEstadosDestino(nuevosEstados, siguienteEstado, estadoActual);

			}

			// Actualizar los estados actuales con los nuevos estados obtenidos
			estadosActuales = nuevosEstados;
		}

		// Verificar si alguno de los estados actuales es final
		for (int estadoActual : estadosActuales) {
			if (esEstadoFinal(estadoActual)) {
				return true; // La cuerda es aceptada
			}
		}

		return false; // La cuerda es rechazada
	}

	private int obtenerIndiceSimbolo(char simbolo) {
		for (int i = 0; i < alfabeto.length; i++) {
			if (alfabeto[i].charAt(0) == simbolo) {
				return i;
			}
		}
		return -1; // Símbolo no encontrado en el alfabeto
	}

	private void agregarEstadosDestino(Set<Integer> estados, int[] siguienteEstado, int estadoActual) {
		// Iterar sobre los bits de nextStateIndices para obtener los estados destino
		if (siguienteEstado != null) {
			for (int i = 0; i < siguienteEstado.length; i++) {
				if (siguienteEstado[i] != 0) {
					estados.add(siguienteEstado[i]);
				}
			}
		}

	}

	private boolean esEstadoFinal(int estado) {
		for (int estadoFinal : estadosFinales) {
			if (estado == estadoFinal) {
				return true;
			}
		}
		return false;
	}

	public void toAFD(String afdPath) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(afdPath));

			// Escribir los símbolos terminales en la primera línea
			writer.write(String.join(",", alfabeto));
			writer.newLine();

			// Escribir la cantidad de estados del AFD (solo M filas)
			int estadosAFD = estados - 1;
			writer.write(Integer.toString(estadosAFD));
			writer.newLine();

			// Escribir los estados finales del AFD (si los hay)
			// Como el estado de error ya no existe en el AFD, no se incluye aquí
			StringBuilder finalStatesStr = new StringBuilder();
			for (int estadoFinal : estadosFinales) {
				if (estadoFinal != 0) {
					finalStatesStr.append(estadoFinal);
					finalStatesStr.append(",");
				}
			}
			writer.write(finalStatesStr.toString());
			writer.newLine();

			// Escribir las transiciones del AFD
			for (int i = 0; i < alfabeto.length; i++) {
				// Para cada símbolo del alfabeto, escribir el mismo estado para todas las filas
				for (int j = 1; j <= estadosAFD; j++) {
					StringBuilder transitionStates = new StringBuilder();
					for (int k = 0; k < transiciones[i][j].length; k++) {
						transitionStates.append(transiciones[i][j][k]); // Escribir el mismo estado para todos los
																		// estados del AFD
						if (k < transiciones[i][j].length - 1) {
							transitionStates.append(",");
						}
					}
					writer.write(transitionStates.toString());
					if (j < estadosAFD) {
						writer.write(",");
					}
				}
				writer.newLine();
			}

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
	}
}
