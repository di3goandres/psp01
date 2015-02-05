/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniandes.ecos;

/**
 *
 * @author COCO
 */
public class PrettyPrintingLOC {

    public static void main(String[] args) {

        try {
            ClassLOCCounter counter = new ClassLOCCounter("resource/Program_1/", true, false);
            counter.process();
            System.out.println("Programa 1");
            System.out.println(counter.getMessages());
            
            ClassLOCCounter counterprograma2 = new ClassLOCCounter("resource/Program_2/", true, false);
            System.out.println("Programa 2");
             counterprograma2.process();
            System.out.println(counterprograma2.getMessages());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
