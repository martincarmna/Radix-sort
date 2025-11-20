import java.io.*;
import java.util.ArrayList;

public class RadixSortReal {

    public static void main(String[] args) {

        // Ruta ajustada por ti
        String archivoEntrada = "C:\\archivos\\numeros.txt";
        String archivoSalida = "C:\\archivos\\ordenados.txt";

        try {
            double[] numeros = leerNumeros(archivoEntrada);

            System.out.println("Números originales:");
            imprimir(numeros);

            radixSortReal(numeros, 3); // precisión de 3 decimales

            System.out.println("\nNúmeros ordenados:");
            imprimir(numeros);

            guardarNumeros(archivoSalida, numeros);

            System.out.println("\nArchivo guardado correctamente en:");
            System.out.println(archivoSalida);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ------------------------------------
    // Persistencia: Leer archivo
    // ------------------------------------
    public static double[] leerNumeros(String nombreArchivo) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
        ArrayList<Double> lista = new ArrayList<>();

        String linea;
        while ((linea = br.readLine()) != null) {
            if (!linea.trim().isEmpty()) {
                lista.add(Double.parseDouble(linea.trim()));
            }
        }
        br.close();

        double[] arr = new double[lista.size()];
        for (int i = 0; i < arr.length; i++) arr[i] = lista.get(i);

        return arr;
    }

    // ------------------------------------
    // Persistencia: Guardar archivo
    // ------------------------------------
    public static void guardarNumeros(String nombreArchivo, double[] arr) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo));

        for (double num : arr) {
            bw.write(num + "\n");
        }
        bw.close();
    }

    // ------------------------------------
    // Radix Sort Real (soporta negativos)
    // ------------------------------------
    public static void radixSortReal(double[] arr, int decimales) {

        int factor = (int) Math.pow(10, decimales);

        int[] enteros = new int[arr.length];
        int minimo = 0;

        // Convertir a enteros y registrar el mínimo
        for (int i = 0; i < arr.length; i++) {
            enteros[i] = (int) (arr[i] * factor);
            if (enteros[i] < minimo)
                minimo = enteros[i];
        }

        // Si hay negativos, desplazamos todo
        int offset = Math.abs(minimo);

        if (offset > 0) {
            for (int i = 0; i < enteros.length; i++) {
                enteros[i] += offset;
            }
        }

        // Ordenar como Radix normal
        radixSort(enteros);

        // Regresar al valor original
        if (offset > 0) {
            for (int i = 0; i < enteros.length; i++) {
                enteros[i] -= offset;
            }
        }

        // Convertir de vuelta a double
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (double) enteros[i] / factor;
        }
    }

    // ------------------------------------
    // Radix Sort entero estándar
    // ------------------------------------
    public static void radixSort(int[] arr) {
        int max = obtenerMax(arr);

        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSort(arr, exp);
        }
    }

    public static void countingSort(int[] arr, int exp) {

        int n = arr.length;
        int[] salida = new int[n];
        int[] conteo = new int[10];

        for (int i = 0; i < n; i++) {
            int digito = (arr[i] / exp) % 10;
            conteo[digito]++;
        }

        for (int i = 1; i < 10; i++) {
            conteo[i] += conteo[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            int digito = (arr[i] / exp) % 10;
            salida[conteo[digito] - 1] = arr[i];
            conteo[digito]--;
        }

        for (int i = 0; i < n; i++) {
            arr[i] = salida[i];
        }
    }

    public static int obtenerMax(int[] arr) {
        int max = arr[0];
        for (int num : arr) if (num > max) max = num;
        return max;
    }

    public static void imprimir(double[] arr) {
        for (double n : arr) System.out.print(n + " ");
        System.out.println();
    }
}
