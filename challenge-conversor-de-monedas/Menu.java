import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    // Menú principal
    public static void menu(){
        getMenu();

        ConversorMonedas monedas = new ConversorMonedas();
        
        Scanner teclado = new Scanner(System.in);
        String entrada = "";

        while(!entrada.equals("4")) {

            entrada = teclado.next();

            switch (entrada) {
                case "1":
                    System.out.println("Ingrese el código de tres letras de la divisa de origen:");
                    String origen = teclado.next().toUpperCase();

                    System.out.println("Ingrese el código de tres letras de la divisa a la que desea hacer la conversión:");
                    String conversion = teclado.next().toUpperCase();

                    try {
                        System.out.println("Ingrese la cantidad a convertir:");
                        Double valorConversion = teclado.nextDouble();
                        monedas.solicitudAPI(origen, conversion, valorConversion);
                    } catch(InputMismatchException e) {
                        System.out.println("ERROR: Debes ingresar un valor numérico a convertir.");
                        
                        // Para consumir la línea vacía después de .nextDouble()
                        teclado.nextLine();

                    } catch(IOException | InterruptedException e){
                        System.out.println(e.getMessage());
                    }
                    
                    getMenu();
                    break;

                case "2":
                    getDenominacionesPrincipales();
                    getMenu();
                    break;
                    
                case "3":
                    try {
                        getDenominaciones();
                    } catch(IOException | InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                    getMenu();
                    break;

                case "4":
                    System.out.println("Gracias por usar el conversor de monedas. Hasta luego");
                    break;

                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
                    getMenu();
                    break;
            }  
        }
        teclado.close();
    }

    // Opciones disponibles
    private static void getMenu(){
        System.out.println("""

            ¿QUÉ DESEAS HACER?
            1) Convertir una divisa a otra
            2) Ver divisas disponibles (principales)
            3) Ver divisas disponibles (todas)
            4) Salir

            """);
    }

    // Denominaciones principales
    private static void getDenominacionesPrincipales(){
        System.out.println("""
            ARS - Peso argentino
            BOB - Boliviano boliviano
            BRL - Real brasileño
            CLP - Peso chileno
            COP - Peso colombiano
            USD - Dólar estadounidense
            MXN - Peso mexicano
            """);
    }

    // Denominaciones completas
    private static void getDenominaciones() throws IOException, InterruptedException {
        ConversorMonedas monedas = new ConversorMonedas();
        monedas.codigosAPI();
    }

}
