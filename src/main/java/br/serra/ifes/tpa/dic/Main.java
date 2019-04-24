package br.serra.ifes.tpa.dic;
import java.io.IOException;
import java.util.*;
/**
 *
 * @author luizg
 */
public class Main {
    /*
        criar um noSuchKey
        mudar a cara do grafico pq ele ta fei bagarai
        criar mais metodos de HASH
        fazer varios graficos de comparação de organização em hash, e salva-los em uma pasta
    */
    public static void main(String[] args){
        
    	TADDic<Object,Object> dic = new TADDic<Object,Object>(100); 
        
        
        dic.insert("home", "casa");
        dic.insert("﻿the", "o,a,os,as");
        dic.insert("of", "de");
        dic.insert("to", "para");
        dic.insert("KeyBoard", "teclado");
        dic.insert("a","um,uma");
        dic.insert("in","em,dentro");
        dic.insert("is","é");
        dic.insert("it","este");
        dic.insert("you","você");
        dic.insert("that","que,aquele");
        
        printData(dic);
        //dic.showCollisionsDiagram();
        popular(dic);
        
	System.out.println("\n");
        dic.insert("Casa", "Home");
        
        printData(dic);        	
        System.out.println("\n");
        TADDic<Object,Object> dic_clone = dic.clone();
        printData(dic_clone);
        
        //dic.showall();
        dic.showCollisionsDiagram();
        dic_clone.showCollisionsDiagram();
    }
    
    
    public static void printData(TADDic<Object,Object> dic){
        System.out.printf("quantElement:%d \n",dic.quant_element());
        System.out.printf("Size: %d \n",dic.getSize());
        System.out.println(Arrays.toString(dic.getColisoes()));
    }
    
    public static void popular(TADDic<Object,Object> dic){
        Arquivo arquivo = new Arquivo("C:\\Users\\luizg\\Documents\\NetBeansProjects\\TPA_meaven\\src\\main\\java\\br\\serra\\ifes\\tpa\\dic\\arquivo.txt");
        try{
            arquivo.lerArquivo(dic); //tenta ler o arquivo
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Arquivo Não Lido");
        }
    }
    
    
}
