package br.serra.ifes.tpa.dic;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author luizg
 */
public class Main {
    /*
        Falta Plota no grafico
    */
    public static void main(String[] args){
        
    	TADDic<String,String> dic = new TADDic<String,String>(10); 
        /*
        Arquivo arquivo = new Arquivo("C:\\Users\\luizg\\Documents\\NetBeansProjects\\TPA_meaven\\src\\main\\java\\br\\serra\\ifes\\tpa\\dic\\arquivo.txt");
        try{
            arquivo.lerArquivo(dic); //tenta ler o arquivo
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Arquivo Não Lido");
        }
        */
        
		
        
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
        
        
        
        //dic.showall();
        System.out.printf("quantElement:%d \n",dic.quant_element());
        System.out.printf("Size: %d \n",dic.getSize());
        System.out.println(Arrays.toString(dic.getColisoes()));
        
        System.out.println("\n");
        dic.insert("Casa", "Home");
        
        System.out.printf("quantElement:%d \n",dic.quant_element());
        System.out.printf("Size: %d \n",dic.getSize());
        System.out.println(Arrays.toString(dic.getColisoes()));
        


        //teste();
    }
    
    public static void teste(){
        Plot plt = Plot.create();
        plt.plot()
            .add(Arrays.asList(1.3, 2))
            .label("label")
            .linestyle("--");
        plt.xlabel("xlabel");
        plt.ylabel("ylabel");
        plt.text(0.5, 0.2, "text");
        plt.title("Title!");
        plt.legend();
        try {
            plt.show();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PythonExecutionException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
