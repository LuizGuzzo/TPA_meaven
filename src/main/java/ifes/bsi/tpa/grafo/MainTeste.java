/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.grafo;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author luizg
 */
public class MainTeste {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String path = "C:\\Users\\luizg\\Documents\\NetBeansProjects\\TPA_meaven\\src\\main\\java\\ifes\\bsi\\tpa\\grafo\\";
        String arq = "lowmovie.txt";
        Conversor conversor = new Conversor(path);
        conversor.converte(arq,"saida.tgf");
        
    }
    
}
