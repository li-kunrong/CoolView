package com.coolview.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import sun.swing.plaf.synth.DefaultSynthStyle.StateInfo;

public class FileHelper {
    public static void createFile(String path,String name) throws IOException {
        File file=new File(path+"/"+name);
        if(!file.exists())
            file.createNewFile();
    }
    
    
    public static String read(String path) throws IOException{
        File file=new File(path);
        if(!file.exists()||file.isDirectory())
            throw new FileNotFoundException();
        BufferedReader br=new BufferedReader(new FileReader(file));
        String temp=null;
        StringBuffer sb=new StringBuffer();
        temp=br.readLine();
        while(temp!=null){
            sb.append(temp);
            temp=br.readLine();
        }
        return sb.toString();
    }
    
    public static void changeTheme(String theme) throws IOException {
        File file=new File("D:/360/elicpse/CoolView/data");
        if(!file.exists())
            file.createNewFile();
        FileOutputStream out=new FileOutputStream(file);       
        out.write(new String("").getBytes());
        StringBuffer sb=new StringBuffer();
        sb.append(theme);
        out.write(sb.toString().getBytes("utf-8"));     
        out.close();
    }
}
