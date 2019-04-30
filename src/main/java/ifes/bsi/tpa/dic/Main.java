package ifes.bsi.tpa.dic;
import java.io.IOException;
import java.util.*;
/**
 *
 * @author luizg
 */
public class Main {
    /*
        
        fazer varios graficos de comparação de organização em hash, e salva-los em uma pasta
    */
    public static void main(String[] args){
        
    	TADDicChain dic = new TADDicChain(10); 
        
        
        dic.insertItem("home", "casa");
        dic.insertItem("﻿the", "o,a,os,as");
        dic.insertItem("of", "de");
        dic.insertItem("to", "para");
        dic.insertItem("KeyBoard", "teclado");
        dic.insertItem("a","um,uma");
        dic.insertItem("in","em,dentro");
        dic.insertItem("is","é");
        dic.insertItem("it","este");
        dic.insertItem("you","você");
        dic.insertItem("that","que,aquele");
        
        printData(dic);
        //dic.showCollisionsDiagram();
        //popular(dic);
        
	System.out.println("\n");
        System.out.println("SAME DATA INPUT\n");
        
        dic.insertItem("home", "casa");
        dic.insertItem("﻿the", "o,a,os,as");
        dic.insertItem("of", "de");
        dic.insertItem("to", "para");
        dic.insertItem("KeyBoard", "teclado");
        dic.insertItem("a","um,uma");
        dic.insertItem("in","em,dentro");
        dic.insertItem("is","é");
        dic.insertItem("it","este");
        dic.insertItem("you","você");
        dic.insertItem("that","que,aquele");

        
        printData(dic);        	
        System.out.printf("dando find... \n");
        System.out.printf("GOTCHA: "+dic.findElement("home")+"\n");
        
        System.out.printf("Clonagem \n");
        TADDicChain dic_clone = dic.clone();
        printData(dic_clone);


        System.out.printf(" Equals: "+ dic.equals(dic_clone) +"\n");

        
//        dic.showall();
//        System.out.println("\n");
//        System.out.println("\n");
//        dic_clone.showall();
//        dic.showCollisionsDiagram();
//        dic_clone.showCollisionsDiagram();
    }
    
    
    public static void printData(TADDicChain dic){
        System.out.printf("quantElement:%d \n",dic.size());
        System.out.printf("Size: %d \n",dic.getSizeVetBuckets());
        System.out.println(Arrays.toString(dic.getColisoes()));
    }
    
    public static void popular(TADDicChain dic){
        Arquivo arquivo = new Arquivo("C:\\Users\\luizg\\Documents\\NetBeansProjects\\TPA_meaven\\src\\main\\java\\ifes\\bsi\\tpa\\dic\\arquivo.txt");
        try{
            arquivo.lerArquivo(dic); //tenta ler o arquivo
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Arquivo Não Lido");
        }
    }
    
    
}
