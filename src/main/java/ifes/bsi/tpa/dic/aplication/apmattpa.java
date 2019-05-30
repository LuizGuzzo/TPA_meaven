/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.dic.aplication;

import ifes.bsi.tpa.tadmatriz.TADMatriz;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author luizg
 */
public class apmattpa {
     public static void main(String[] args) throws FileNotFoundException, IOException{
       
        String nome_arq = "bdaritmat.csv"; //nome do arquivo
        String caminho_arq = "C:\\Users\\luizg\\Documents\\NetBeansProjects\\TPA_meaven\\src\\main\\java\\ativ-1-tpa-taddic\\bdmatrizes\\"; //Caminho para o arquivo
        String arqMatriz;
        BufferedReader bufferReader = new BufferedReader(new FileReader(caminho_arq+nome_arq));
        
        
        String line = bufferReader.readLine(); //primeira linha de carregamento
        arqMatriz = line+".txt";
        TADMatriz matriz = TADMatriz.carrega(caminho_arq+arqMatriz);
        TADMatriz matrizAux;
        System.out.println("Carregando arquivo: "+caminho_arq+arqMatriz);
        System.out.println("---------------- Matriz Inicial ----------------");
        matriz.printMatriz();
        
        
        line = bufferReader.readLine();
        while( line != null){
            String linha[] = line.split(",");
            switch (linha[0]){
                
                case "-":
                    arqMatriz = linha[1]+".txt";
                    matrizAux = TADMatriz.carrega(caminho_arq+arqMatriz);
                    matrizAux.vezesK(-1f);
                    matriz = matriz.soma(matrizAux);
                    System.out.println("---------------- Subtraida ----------------");
                    matriz.printMatriz();
                    break;
                    
                case "+":
                    arqMatriz = linha[1]+".txt";
                    matrizAux = TADMatriz.carrega(caminho_arq+arqMatriz);
                    matriz = matriz.soma(matrizAux);
                    System.out.println("================ Somada ----------------");
                    matriz.printMatriz();
                    break;
                
                case "t":
                    matriz = matriz.transposta();
                    System.out.println("---------------- Transposta ----------------");
                    matriz.printMatriz();
                    break;
                    
                case "*":
                    if(linha[1].matches("[0-9]+")){  //expressão regular
                        Float multiplicador = Float.parseFloat(linha[1]);
                        matriz.vezesK(multiplicador);
                        System.out.println("---------------- VezesK ----------------");
                        matriz.printMatriz();
                    }
                    else{
                        arqMatriz = linha[1]+".txt";
                        matrizAux = TADMatriz.carrega(caminho_arq+arqMatriz);
                        matriz = matriz.multi(matrizAux);
                        System.out.println("---------------- Multiplicada ----------------");
                        matriz.printMatriz();
                    }
                    break;
            }
            
            line = bufferReader.readLine();
        }
     
        System.out.println("---------------- Matriz Final ----------------");
        matriz.printMatriz();
        matriz.salva(caminho_arq+"resposta.txt");
        
    }
}
