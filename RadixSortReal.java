public class RadixSortReal {
      public static void main(String[] args) {

        double[] numeros = {5.23, 1.2, 9.812, 4.4, 3.1416, 7.77};

        System.out.println("Arreglo original:");
        imprimir(numeros);

        radixSortReal(numeros, 3); // 3 decimales de precisión

        System.out.println("\nArreglo ordenado:");
        imprimir(numeros);
    }

    // ------------------------------------
    // Radix Sort adaptado para números reales
    // ------------------------------------
    public static void radixSortReal(double[] arr, int decimales) {

        int factor = (int) Math.pow(10, decimales);

        // Convertir reales a enteros
        int[] enteros = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            enteros[i] = (int) (arr[i] * factor);
        }

        // Ordenar con Radix Sort normal
        radixSort(enteros);

        // Regresar a reales
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (double) enteros[i] / factor;
        }
    }

 // ------------------------------------
    // Radix Sort tradicional para enteros
    // ------------------------------------
    public static void radixSort(int[] arr) {
        int max = obtenerMax(arr);

        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSort(arr, exp);
        }
     // Counting Sort por dígito
    public static void countingSort(int[] arr, int exp) {

        int n = arr.length;
        int[] salida = new int[n];
        int[] conteo = new int[10];

        // Contar apariciones del dígito
        for (int i = 0; i < n; i++) {
            int digito = (arr[i] / exp) % 10;
            conteo[digito]++;
        }
    // Acumulados
        for (int i = 1; i < 10; i++) {
            conteo[i] += conteo[i - 1];
        }

        // Construir arreglo ordenado
        for (int i = n - 
            1; i >= 0; i--) {
            int digito = (arr[i] / exp) % 10;
            salida[conteo[digito] - 1] = arr[i];
            conteo[digito]--;
        }
    }















}
