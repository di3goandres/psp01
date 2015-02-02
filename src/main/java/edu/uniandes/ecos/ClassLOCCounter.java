/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.uniandes.ecos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author COCO
 */
public class ClassLOCCounter {
        private int loc = 0;
        private int empty = 0;
        private List fileList;
        private boolean ignoreEmptyLines;
        private int methods = 0;

        public ClassLOCCounter(String fileLocation, boolean scanRecursive, boolean ignoreEmptyLines) {
                this.ignoreEmptyLines = ignoreEmptyLines;
                fileList = new ArrayList();
                System.out.println("Getting file list...");
                if(scanRecursive) {
                        fileList = getFileListRecursive(new File(fileLocation), fileList);
                } else {
                        fileList = getFileListNonRecursive(new File(fileLocation));
                }
        }
        
        public int getLoc() {
                return this.loc;
        }

        public int getEmptyLines() {
                return this.empty;
        }

        public void process() throws Exception {
                //System.out.println("Calculating loc count...");
                Iterator it = fileList.iterator();
                while(it.hasNext()) {
                        File file = (File)it.next();
                        String line = null;
                        BufferedReader reader = null;
                        try {
                                reader = new BufferedReader(new InputStreamReader(
                                                new FileInputStream(file)));
                        } catch (IOException e) {
                                e.printStackTrace();
                                throw e;
                        }
                        
                        while ((line = reader.readLine()) != null) {

                                line = line.trim();
                                // Is the line empty
                                if (ignoreEmptyLines && line.length() == 0) {
                                        this.empty++;
                                        continue;
                                } else{
                                        ++this.loc;
                                
                                }
                        }
                }
               // System.out.println("Done.");
        }

        public static List getFileListRecursive(File file, List fileList) {
                if (file.isDirectory()) {
                        File[] files = file.listFiles();
                        for (int i = 0; i < files.length; i++) {
                                getFileListRecursive(files[i], fileList);
                        }
                } else {
                        fileList.add(file);
                }
                return fileList;
        }
        
        public static List getFileListNonRecursive(File file) {
                List fileList = new ArrayList();
                
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                        if(files[i].isFile()) {
                                fileList.add(files[i]);
                        }
                }
                
                return fileList;
        }
        
        public List getFileList() {
                return fileList;
        }
        
        public List getMessages() {
                List messages = new ArrayList();
                messages.add("No. of files : " + fileList.size());
                messages.add("Total no. of lines : " + loc);
                if(ignoreEmptyLines) {
                        messages.add("Empty Lines   : " + empty);
                }
                return messages;
        }
}
