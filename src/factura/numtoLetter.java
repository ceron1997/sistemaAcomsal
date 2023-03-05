/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factura;

/**
 *
 * @author admin
 */

public class numtoLetter {

    public String ConvertirNum(Double Numero) {
        // codigo original escrito en visual basic app por excel total 
        // trascripcion y modificacion en java hecha por ceron1997 en el año 2022

        String Moneda = "";
        String Monedas = "";
        String Centimo = "";
        String Centimos = "";
        String Preposicion = "";
        Double NumCentimos = 0.00;
        String Letra = "";
        final Double Maximo = 1999999999.99;

        Moneda = "Dolar"; //'Nombre de Moneda (Singular)
        Monedas = "Dolares";  //  'Nombre de Moneda (Plural)
        Centimo = "Centavo"; // 'Nombre de Céntimos (Singular)
        Centimos = "Centavos"; //  'Nombre de Céntimos (Plural)
        Preposicion = "Con";  //   'Preposición entre Moneda y Céntimos

//'Validar que el Numero está dentro de los límites
        if ((Numero >= 0) && (Numero <= Maximo)) {

            // extraer la partre entera 
            String entero_str = "";
            entero_str = (String.format("%.2f", Numero));
            entero_str = entero_str.substring(0, entero_str.indexOf("."));
            int entero_int = Integer.parseInt(entero_str);

            Letra = NumeroCursivo(entero_int);

            // extraer la parte decimal 
            String decimal_str = "";
            decimal_str = (String.format("%.2f", Numero));
            decimal_str = decimal_str.substring(decimal_str.indexOf(".") + 1);

            int decimal_int = Integer.valueOf(decimal_str);

            if (((decimal_int >= 1)) && (decimal_int <= 9)) {
                decimal_int *= 10;
            }

            if (decimal_int == 0) {
                Letra = Letra + " " + Preposicion + " " + "0" + decimal_int + "/100";
            } else {
                Letra = Letra + " " + Preposicion + " " + decimal_int + "/100";
            }

        } else {
            String numero_grande = (String.format("%.2f", Numero));
            Letra = String.valueOf(numero_grande);
        }

        return Letra;
    }

    public String NumeroCursivo(int numero) {
        String[] Unidades = {"", "Un", "Dos", "Tres", "Cuatro", "Cinco", "Seis",
            "Siete", "Ocho", "Nueve", "Diez", "Once", "Doce", "Trece", "Catorce",
            "Quince", "Dieciséis", "Diecisiete", "Dieciocho", "Diecinueve", "Veinte",
            "Veintiuno", "Veintidos", "Veintitres", "Veinticuatro", "Veinticinco",
            "Veintiseis", "Veintisiete", "Veintiocho", "Veintinueve"};
        String[] Decenas = {"", "Diez", "Veinte", "Treinta", "Cuarenta", "Cincuenta",
            "Sesenta", "Setenta", "Ochenta", "Noventa", "Cien"};
        String[] Centenas = {"", "Ciento", "Doscientos", "Trescientos", "Cuatrocientos",
            "Quinientos", "Seiscientos", "Setecientos", "Ochocientos", "Novecientos"};
        String resultado = "";

        if (numero == 0) {
            resultado = "cero";
        } else if ((numero >= 1) && (numero <= 29)) {
            resultado = Unidades[numero];
        } else if ((numero >= 30) && (numero <= 100)) {

            resultado = Decenas[numero / 10];
            if ((numero % 10) != 0) {
                resultado = resultado + " y " + NumeroCursivo(numero % 10);
            }

        } else if ((numero >= 101) && (numero <= 999)) {

            resultado = Centenas[numero / 100];
            if ((numero % 100) != 0) {
                resultado = resultado + " " + NumeroCursivo(numero % 100);
            }

        } else if ((numero >= 1000) && (numero <= 1999)) {

            resultado = "un mil";
            if ((numero % 1000) != 0) {
                resultado = resultado + " " + NumeroCursivo(numero % 1000);
            }

        } else if ((numero >= 2000) && (numero <= 999999)) {

            resultado = NumeroCursivo(numero / 1000) + " mil";
            if ((numero % 1000) != 0) {
                resultado = resultado + " " + NumeroCursivo(numero % 1000);
            }

        } else if ((numero >= 1000000) && (numero <= 1999999)) {

            resultado = "un millon";
            if ((numero % 1000000) != 0) {
                resultado = resultado + " " + NumeroCursivo(numero % 1000000);
            }

        } else if ((numero >= 2000000) && (numero <= 1999999999)) {

            resultado = NumeroCursivo(numero / 1000000) + " millones";
            if ((numero % 1000000) != 0) {
                resultado = resultado + " " + NumeroCursivo(numero % 1000000);
            }
        }

        return resultado;
    }
}
