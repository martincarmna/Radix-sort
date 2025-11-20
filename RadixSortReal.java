import java.io.*;
import java.util.ArrayList;

public class RadixSortReal {

    public static void main(String[] args) {
        String archivoEntrada = "numeros.txt";
        String archivoSalida = "ordenados.txt";

        try {
            // Leer números desde archivo
            double[] numeros = leerNumeros(archivoEntrada);

            System.out.println("Números originales:");
            imprimir(numeros);

            // Ordenar con Radix Sort real
            radixSortReal(numeros, 3);

            System.out.println("\nNúmeros ordenados:");
            imprimir(numeros);

            // Guardar en archivo
            guardarNumeros(archivoSalida, numeros);

            System.out.println("\nArchivo '" + archivoSalida + "' generado correctamente.");

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
    // Radix Sort Real (para decimales)
    // ------------------------------------
    public static void radixSortReal(double[] arr, int decimales) {

        int factor = (int) Math.pow(10, decimales);

        int[] enteros = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            enteros[i] = (int) (arr[i] * factor);
        }

        radixSort(enteros);

        // Regresar a reales
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (double) enteros[i] / factor;
        }
    }

    // ------------------------------------
    // Radix Sort entero
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

        // Contar dígitos
        for (int i = 0; i < n; i++) {
            int digito = (arr[i] / exp) % 10;
            conteo[digito]++;
        }

        // Acumulado
        for (int i = 1; i < 10; i++) {
            conteo[i] += conteo[i - 1];
        }

        // Ordenar
        for (int i = n - 1; i >= 0; i--) {
            int digito = (arr[i] / exp) % 10;
            salida[conteo[digito] - 1] = arr[i];
            conteo[digito]--;
        }

        // Copiar
        for (int i = 0; i < n; i++) {
            arr[i] = salida[i];
        }
    }

    // Máximo
    public static int obtenerMax(int[] arr) {
        int max = arr[0];
        for (int num : arr) {
            if (num > max) max = num;
        }
        return max;
    }

    // Imprimir
    public static void imprimir(double[] arr) {
        for (double n : arr) {
            System.out.print(n + " ");
        }
        System.out.println();
    }
}
