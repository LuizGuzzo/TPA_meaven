package br.serra.ifes.tpa.dic;
import java.io.IOException;
import java.util.*;
/**
 *
 * @author luizg
 */
public class Main {
    /*
        Falta Plota no grafico
    */
    public static void main(String[] args){
    	TADDic dic = new TADDic(1001); 
        Arquivo arquivo = new Arquivo("C:\\Users\\luizg\\Documents\\NetBeansProjects\\TPA_meaven\\src\\main\\java\\br\\serra\\ifes\\tpa\\dic\\arquivo.txt"); //cria a classe arquivo com parametro do caminho do arquivo
        try{
            arquivo.lerArquivo(dic); //tenta ler o arquivo
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Arquivo Não Lido");
        }
        
        
        System.out.println(Arrays.toString(dic.getColisoes()));
        dic.insert("Casa", new RegDados("Casa", "Home"));
        System.out.println(dic.quant_element());
        
        //dic.showall();
    }
}
