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
                        ClassLOCCounter counter = new ClassLOCCounter("resource/", true, true);
                        counter.process();
                        System.out.println("No. of files : " + counter.getFileList().size());
                        System.out.println("Total no. of lines : " + counter.getLoc());
                        System.out.println("Empty Lines   : " + counter.getEmptyLines());
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
}
