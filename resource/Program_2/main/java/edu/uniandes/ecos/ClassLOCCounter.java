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
    private int comment = 0;
    private List fileList;
    private boolean ignoreEmptyLines;
    private int methods = 0;
    private String nombreClase = "";
    private List mensajes = new ArrayList();
    private int totalPrograma = 0;

    public ClassLOCCounter(String fileLocation, boolean scanRecursive, boolean ignoreEmptyLines) {
        this.ignoreEmptyLines = ignoreEmptyLines;
        fileList = new ArrayList();
        if (scanRecursive) {
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

    public int getTotalMethods() {
        return this.methods;
    }

    public int getCommentLines() {
        return this.comment;
    }

    @SuppressWarnings("empty-statement")
    public void process() throws Exception {
        Iterator it = fileList.iterator();
        while (it.hasNext()) {
            File file = (File) it.next();
            String line = null;
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(
                        new FileInputStream(file)));
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
            this.nombreClase = file.getName();
            while ((line = reader.readLine()) != null) {

                line = line.trim();
                // Is the line empty
                if (ignoreEmptyLines && line.length() == 0) {
                    this.empty++;
                    continue;
                } else {
                    //se cuenta las lineas que esta contando
                    ++this.loc;
                    if (line.startsWith("//") || line.startsWith("*") || line.startsWith("/*") || line.startsWith("*/")) //comment 
                    {
                        /*
                         * cuenta si es un comentario y resta uno por que 
                         * los comentarios no son lineas de codigo en el estandar definido
                         */
                        ++this.comment;
                        --this.loc;
                    } else if (line.contains("(") && line.contains(")") && !line.contains(";")) {
                        String[] wordsArray = new String[6];
                        wordsArray[0] = "for";
                        wordsArray[1] = "foreach";
                        wordsArray[2] = "catch";
                        wordsArray[3] = "while";
                        wordsArray[4] = "if";
                        wordsArray[5] = "switch";
                        if (!ContainsSingleWord(line, wordsArray)) {
                            this.methods++;
                        }

                    }

                }
            }
            setMessages();
            totalPrograma = totalPrograma + loc;
            this.loc = 0;
            this.methods = 0;
            this.empty = 0;
            this.comment = 0;
            this.nombreClase = "";
        }

    }

    public static List getFileListRecursive(File file, List fileList) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                getFileListRecursive(files[i], fileList);
            }
        } else {
            if (file.getName().endsWith(".java")) {
                fileList.add(file);
            }
        }
        return fileList;
    }

    public static List getFileListNonRecursive(File file) {
        List fileList = new ArrayList();

        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                if (files[i].getName().endsWith(".java")) {
                    fileList.add(files[i]);
                }
            }
        }

        return fileList;
    }

    public List getFileList() {
        return fileList;
    }

    public String getMessages() {
        String retorno = "";
        for (Object mensajeP1 : this.mensajes) {
            retorno = retorno + mensajeP1.toString() + '\n';
        }
        retorno = retorno + "Total de lineas: " + totalPrograma;
        return retorno;
    }

    public void setMessages() {
        List messages = new ArrayList();
        messages.add("Archivo : " + this.nombreClase + ". Total Lineas : " + loc + ". Total metodos : " + this.methods);

        if (ignoreEmptyLines) {
            messages.add("Empty Lines   : " + empty);
        }
        mensajes.add(messages);

    }

    private boolean ContainsSingleWord(String source, String[] words) {
        for (String word : words) {
            if (source.contains(word)) {
                int antes = source.indexOf(word) - 1;
                int despues = source.indexOf(word) + word.length();
                if ((source.length() - 1) >= despues && antes >= 0) {
                    if ((source.charAt(antes) == ' ') || source.charAt(antes) == '\t' || source.charAt(antes) == ':' && source.charAt(despues) == ' ' || source.charAt(despues) == '\t' || (source.charAt(despues) == '(')) {
                        return true;
                    }
                } else {
                    if ((source.length() - 1) < despues && antes >= 0) {
                        if (source.charAt(antes) == ' ' || source.charAt(antes) == '\t' || source.charAt(antes) == ':') {
                            return true;
                        }
                    }
                    if ((source.length() - 1) >= despues && antes < 0) {

                        if (source.charAt(despues) == ' ' || source.charAt(despues) == '\t' || source.charAt(despues) == '(') {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
