/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.dic.aplication;

import ifes.bsi.tpa.matriz.TADMatriz;
import java.io.IOException;

/**
 *
 * @author luizg
 */
public class mainMatriz {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        TADMatriz matriz = new TADMatriz(2, 3);
        matriz.setElem(1, 1, 1f);
        matriz.setElem(1, 2, 2f);
        matriz.setElem(1, 3, 3f);
        matriz.setElem(2, 1, 4f);
        matriz.setElem(2, 2, 5f);
        matriz.setElem(2, 3, 6f);
        
        TADMatriz matriz2 = new TADMatriz(2, 3);
        matriz2.setElem(1, 1, 1f);
        matriz2.setElem(1, 2, 2f);
        matriz2.setElem(1, 3, 3f);
        matriz2.setElem(2, 1, 4f);
        matriz2.setElem(2, 2, 5f);
        matriz2.setElem(2, 3, 6f);
        
        TADMatriz matriz4 = matriz.soma(matriz2);
        
        TADMatriz matriz3 = matriz.transposta();
        
        TADMatriz matriz5 = matriz.multi(matriz3);
        
        mainMatriz.print(matriz,"1");
        mainMatriz.print(matriz2,"2");
        mainMatriz.print(matriz3,"Transposta");
        mainMatriz.print(matriz4,"Soma");
        mainMatriz.print(matriz5,"Multi");
        
        TADMatriz matrizCarregada = null;
                matrizCarregada = matrizCarregada.carrega("C:\\Users\\luizg\\Documents\\NetBeansProjects\\TPA_meaven\\src\\main\\java\\ativ-1-tpa-taddic\\bdmatrizes\\A.txt");
        
        mainMatriz.print(matrizCarregada, "Carregada");
            matrizCarregada.salva("teste");
        
        
        matrizCarregada.imprimeDados();
    }
    
    public static void print(TADMatriz m, String type){
        if (m == null){
            System.out.println("Impossivel imprimir, matriz com erro");
            return;
        }else{
            System.out.println("Matriz "+type+":");
            m.printMatriz();
            System.out.println("======================");
        }
    }
    
    
    
}
