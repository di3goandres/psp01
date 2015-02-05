/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniandes.ecos;

import java.util.List;

/**
 *
 * @author COCO
 */
public class DesviacionEstandar {

    public List<Double> listasNumeros;

    public double mediaAritmetica;

    public void DesviacionEstandarAdd(List<Double> listaNumeros) {

        this.listasNumeros = listaNumeros;

    }

    public double MediaAritmetica() {
        double total = 0;
        for (Double mediaP1 : this.listasNumeros) {
            total = total + mediaP1;
        }
        this.mediaAritmetica = total / this.listasNumeros.size();
        return this.mediaAritmetica;
    }

    public double desviacion;

    public Double DesviacionStandard() {
        double sumatoria = 0;
        double calculos = 0;
        double media = this.MediaAritmetica();
        for (Double listaNumeros1 : this.listasNumeros) {
            sumatoria = sumatoria + (listaNumeros1 - media) * (listaNumeros1 - media);
        }

        calculos = Math.sqrt(sumatoria / (this.listasNumeros.size() - 1));
        this.desviacion = calculos;
        return this.desviacion;

    }
}
