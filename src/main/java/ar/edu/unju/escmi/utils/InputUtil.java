package ar.edu.unju.escmi.utils;
import java.util.Scanner;

public class InputUtil {
    public static Scanner sc = new Scanner(System.in);

    public static int inputInt( String msg){
        int aux =0; boolean valido = false;
        while(!valido){
            try{
                System.out.println(msg); aux=sc.nextInt();
                sc.nextLine();
                valido=true;
            } catch (Exception e){
                System.out.println("Debe ingresar un numero entero valido");
                sc.nextLine();
            }
        }
        return aux;
    }

     public static long inputLong(String msg) {
        long aux = 0; 
        boolean valido = false;
        while (!valido) {
            try {
                System.out.print(msg);
                aux = sc.nextLong(); 
                sc.nextLine();
                valido = true;
            } catch (Exception e) {
                System.out.println("Debe ingresar un número largo válido");
                sc.nextLine();
            }
        }
        return aux;
    }
    public static String inputString(String message) {
        System.out.print(message);
        sc.nextLine(); // limpiar buffer
        return sc.nextLine();
    }

    public static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine();
    }

    public static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextInt()) {
            System.out.print("Error. Ingrese un número entero: ");
            sc.next(); // descarta lo que no es un entero
        }
        int valor = sc.nextInt();
        sc.nextLine();
        return valor;
    }

    public static double leerDouble(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextDouble()) {
            System.out.print("Error. Ingrese un número decimal: ");
            sc.next(); // descarta lo que no es un double
        }
        double valor = sc.nextDouble();
        sc.nextLine();
        return valor;
    }

    public static Long leerLong(String mensaje) {
    while (true) {
        try {
            System.out.print(mensaje);
            return Long.parseLong(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Ingrese un número largo (long).");
        }
    }
}

}
