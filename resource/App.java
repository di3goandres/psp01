package edu.uniandes.ecos;

import java.util.LinkedList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        DesviacionEstandar desviacionEstandar = new DesviacionEstandar();
        List<Double> listaNumeros = new LinkedList<Double>();
        listaNumeros.add(160d);
        listaNumeros.add(591d);
        listaNumeros.add(114d);
        listaNumeros.add(229d);
        listaNumeros.add(230d);
        listaNumeros.add(270d);
        listaNumeros.add(128d);
        listaNumeros.add(1657d);
        listaNumeros.add(624d);
        listaNumeros.add(1503d);
        desviacionEstandar.DesviacionEstandarAdd(listaNumeros);
        System.out.println("Lista de números:");
        System.out.println(listaNumeros);
        System.out.println("Media Aritmética");
        System.out.println(desviacionEstandar.MediaAritmetica());
        System.out.println("Desviacion Estandar");
        System.out.println(desviacionEstandar.DesviacionStandard());

         System.out.println("º----------º");
        List<Double> listaNumerosDos = new LinkedList<Double>();

        listaNumerosDos.add(15.0d);
        listaNumerosDos.add(69.9d);
        listaNumerosDos.add(6.5d);
        listaNumerosDos.add(22.4d);
        listaNumerosDos.add(28.4d);
        listaNumerosDos.add(65.9d);
        listaNumerosDos.add(19.4d);
        listaNumerosDos.add(198.7d);
        listaNumerosDos.add(38.8d);
        listaNumerosDos.add(138.2d);
        desviacionEstandar.DesviacionEstandarAdd(listaNumerosDos);
        System.out.println("Lista de números:");
        System.out.println(listaNumerosDos);
        System.out.println("Media Aritmética");
        System.out.println(desviacionEstandar.MediaAritmetica());
        System.out.println("Desviacion Estandar");
        System.out.println(desviacionEstandar.DesviacionStandard());
    }
}
