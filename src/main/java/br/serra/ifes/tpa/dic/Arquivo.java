package br.serra.ifes.tpa.dic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luizg
 */
public class Arquivo {
    private String localArquivo;
    
    public Arquivo(String localArquivo) {
        this.localArquivo = localArquivo;
    }
    
    public void lerArquivo(TADDic dic) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(localArquivo))) {
            String linha;
            String[] array_split;
            while((linha = br.readLine()) != null){
                array_split = linha.split(";");
                Traduzir_IGtoPT traducao = new Traduzir_IGtoPT(array_split[0],array_split[1]);
                dic.insert(array_split[0], traducao);
            }
        }
    }
}
