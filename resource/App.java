package edu.uniandes.ecos;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        try {
            DesviacionEstandar desviacionEstandar = new DesviacionEstandar();
            List<Double> listaNumeros = new LinkedList<Double>();
            Scanner entradaDatos = new Scanner(System.in);
            System.out.println("Ingrese los numeros para realizar los calculos.\n Ingrese SI para finalizar.\n Numeros como 19.5, con punto");
            String datosIngresados = "";
            double valorIngresado = 0;
            int i = 1;
            boolean preguntarDatos = true;
            while (preguntarDatos) {
                System.out.print("Para calcular ingrese SI, Ingrese el valor " + i + ": ");
                datosIngresados = entradaDatos.nextLine();
                if ("SI".equals(datosIngresados.toUpperCase())) {
                    preguntarDatos = false;
                } else {
                    valorIngresado = Double.parseDouble(datosIngresados);
                    listaNumeros.add(valorIngresado);
                    i++;
                }
            }
            desviacionEstandar.DesviacionEstandarAdd(listaNumeros);
            System.out.println("Listado de números:");
            System.out.println(listaNumeros);
            System.out.println("Media Aritmética");
            System.out.println(desviacionEstandar.MediaAritmetica());
            System.out.println("Desviacion Estandar");
            System.out.println(desviacionEstandar.DesviacionStandard());
        } catch (NumberFormatException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
