package ifes.bsi.tpa.dic;
import ifes.bsi.tpa.dic.aplication.Hash_engine;
import ifes.bsi.tpa.dic.aplication.Traduzir_IGtoPT;
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
    public static void main(String[] args) throws IOException{
        
        TADMatrizEsp tadMz = new TADMatrizEsp(2,6);
        TADMatrizEsp tadMz3 = new TADMatrizEsp(2,6);
        
        tadMz.DicToMatriz();
        tadMz3.DicToMatriz();
        
        tadMz.salvar("matriz");
        
        
        System.out.printf("Equals:"+ tadMz.equals(tadMz3));
        
        
        
        
        
        
        
        
        
//        
//        Hash_engine hash_engine = new Hash_engine();
//    	TADDicChain dic = new TADDicChain(10,hash_engine); 
//    	TADDicChain dic2 = new TADDicChain(10,hash_engine);
//        
//        
//        dic.insertItem("home", "casa");
//        dic.insertItem("﻿the", "o,a,os,as");
//        dic.insertItem("of", "de");
//        dic.insertItem("to", "para");
//        dic.insertItem("KeyBoard", "teclado");
//        dic.insertItem("a","um,uma");
//        dic.insertItem("in","em,dentro");
//        dic.insertItem("is","é");
//        dic.insertItem("it","este");
//        dic.insertItem("you","você");
//        dic.insertItem("that","que,aquele");
//        
////        dic.showCollisionsDiagram();
////        popular(dic);
//        printData(dic);
//        
////	System.out.println("\n");
//        System.out.println("SAME DATA INPUT\n");
//        
//        //popular(dic);
//        
//        dic.insertItem("home", "casa");
//        dic.insertItem("﻿the", "o,a,os,as");
//        dic.insertItem("of", "de");
//        dic.insertItem("to", "para");
//        dic.insertItem("KeyBoard", "teclado");
//        dic.insertItem("a","um,uma");
//        dic.insertItem("in","em,dentro");
//        dic.insertItem("is","é");
//        dic.insertItem("it","este");
//        dic.insertItem("you","você");
//        dic.insertItem("that","que,aquele");
//
//        
//        printData(dic);        	
//        System.out.printf("dando find... \n");
//        System.out.printf("GOTCHA: "+dic.findElement("that")+"\n");
//        System.out.printf("NoSuchKey value: "+dic.NO_SUCH_KEY()+"\n");
//        
//        System.out.printf("Clonagem \n");
//        TADDicChain dic_clone = dic.clone();
//
//
//        System.out.printf(" Equals: "+ dic.equals(dic_clone) +"\n \n");
//        
//        printData(dic_clone);
//        System.out.printf("Removendo um valor \n");
//        dic_clone.removeElement("that");
//        printData(dic_clone);
//        System.out.printf("dando find no clone... \n");
//        System.out.printf("GOTCHA: "+dic_clone.findElement("that")+"\n");
//        System.out.printf("NoSuchKey value: "+dic_clone.NO_SUCH_KEY()+"\n");
//        
//        System.out.println("TESTE DE REMOÇÃO TOTAL POR KEYs");
//        
//        System.out.println("dic1: ");
//        printData(dic);
//        System.out.println("dic2: ");
//        printData(dic2);
//        LinkedList<Object> lstKs = dic.keys();
//		
//		int i = 0;
//		while(dic.size() > 0) {
//			String dado = (String) dic.removeElement(lstKs.get(i));
//			if(dic.NO_SUCH_KEY()) {
//				System.out.println("**Problemas!\nFalha na remoção da chave " +  lstKs.get(i).toString() + " do dicionário, abortando o benchmark.");
//				System.exit(0);
//			}
//			dic2.insertItem(lstKs.get(i),dado);	
//			i = i + 1;
//		}
//        
//        System.out.println("dic1: ");
//        printData(dic);
//        System.out.println("dic2: ");
//        printData(dic2);
//        
//        
////        System.out.println("keys:");
////        System.out.println(Arrays.toString(dic.keys()));
////        System.out.println("Elements:");
////        System.out.println(Arrays.toString(dic.elements()));
//        
////        dic.showall();
////        System.out.println("\n");
////        System.out.println("\n");
////        dic_clone.showall();
////        dic.showCollisionsDiagram();
////        dic_clone.showCollisionsDiagram();
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
