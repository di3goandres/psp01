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
        }

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
            if (files[i].isFile()) {
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
        if (ignoreEmptyLines) {
            messages.add("Empty Lines   : " + empty);
        }
        return messages;
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
