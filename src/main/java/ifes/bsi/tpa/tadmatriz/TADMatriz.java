/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.tadmatriz;

import ifes.bsi.tpa.taddic.TADDicChain;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luizg
 */
public class TADMatriz {
    
    private int linhas;
    private int colunas;
    private TADDicChain dados; //armazena os dados que a matriz recebe
    private List<ChaveMatriz> chaves; //é a lista de "tuplas" das chaves
    

    public TADMatriz(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.chaves = new ArrayList<>();
        this.dados = new TADDicChain();
        
    }
    
   
    public Float getElem(int i, int j){
        
        if(!this.checkSizeTam(i, j)) return null;
        
        ChaveMatriz chave = this.findChaveMatriz(i, j);
        return (chave != null)? (Float)dados.findElement(chave) : 0f;

    }
    
    public boolean checkSizeTam(int i,int j){
        //verificação de tamanho e se é positivo
        if(i > this.linhas || j > this.colunas) return false;
        if(i <= 0 || j <= 0) return false;
        return true;
    }
    
    private ChaveMatriz findChaveMatriz(int i, int j){
        
        if(!this.checkSizeTam(i, j)) return null;
        //percorrendo a lista em busca da chave com a "tupla" (linha,coluna)
        for (ChaveMatriz chave : chaves)
            if(chave.getLinha() == i && chave.getColuna() == j)
                return chave;
        
        return null;
    }
    
    public void printMatriz() {
        for(int i=1; i <= this.linhas;i++){
            for(int j=1; j <= this.colunas;j++){
                System.out.print(this.getElem(i, j)+" | ");
            }
            System.out.println("");
        }
    }
    
    public void imprimeDados(){
        System.out.printf("A quantidade de dados armazenadas é: " + this.dados.size()+"\n");
    }
    
    public int quantColunas() {
        return this.colunas;
    }
    
    public int quantLinhas() {
        return this.linhas;
    }
     
    public Float setElem(int i, int j, Float valor){
        if(!this.checkSizeTam(i, j)) return null;
        
        if(valor == 0f) return null;
        
        ChaveMatriz chave = this.findChaveMatriz(i, j);
        if(chave == null){
            chave = new ChaveMatriz(i, j);
            this.chaves.add(chave);
            dados.insertItem(chave, valor);
            return valor;
        }
        dados.insertItem(chave, valor);
        return null;
    }
    
    public TADMatriz soma(TADMatriz m){
        if(m.quantColunas() == this.quantColunas() && m.quantLinhas() == this.quantLinhas()){
            TADMatriz result = new TADMatriz(this.quantLinhas(), this.quantColunas());
            for(int i=1; i <= this.quantLinhas();i++){
                for(int j=1; j <= this.quantColunas();j++){
                    result.setElem(i, j, this.getElem(i, j) + m.getElem(i, j));
                }
            }
            return result;
        }
        return null;
    }
    
        
     // retorna um novo TADMatriz resultado da multiplicação da matriz atual (this) pela argumento m
    public TADMatriz multi(TADMatriz m){
        if(this.quantColunas() != m.quantLinhas()) return null;
        TADMatriz result = new TADMatriz(this.quantLinhas(), m.quantColunas());
        for(int i=1; i<=this.quantLinhas();i++)
            for(int j=1;j<=m.quantColunas();j++)
                for(int k=1;k<=this.quantColunas();k++)
                    result.setElem(i, j, result.getElem(i, j) + (this.getElem(i, k) * m.getElem(k, j)));

        return result;
    }

    public void vezesK(Float k){
        for (ChaveMatriz chave : chaves) {
            Float valor = (Float)this.dados.findElement(chave);
            this.dados.insertItem(chave, valor*k);
        }
     }
    
    public TADMatriz transposta(){
        TADMatriz newMatriz = new TADMatriz(this.quantColunas(),this.quantLinhas());
        for(int i=1;i<=this.quantLinhas();i++){
            for(int j=1;j<=this.quantColunas();j++){
                newMatriz.setElem(j, i, this.getElem(i, j));
            }
        }
        return newMatriz;
        
    }
    
    private TADMatriz principal(){
        TADMatriz tadMatriz = new TADMatriz(this.quantLinhas(),this.quantColunas());
        
        for (int i = 0; i < this.quantLinhas()+1; i++) {
            for (int j = 0; j < this.quantColunas()+1; j++) {
                if(i == j){
//                    System.out.printf("i: "+i+"|j: "+j+"|");
                    tadMatriz.setElem(i, j, this.getElem(i, j));
                }else{
                    tadMatriz.setElem(i, j, 0f);
                }
            }
//            System.out.println("");
        }
        return tadMatriz;
    }
    
    private TADMatriz secundaria(){
        TADMatriz tadMatriz = new TADMatriz(this.quantLinhas(),this.quantColunas());
        int colunSize = this.quantColunas()+1;
        
        for (int i = 0; i < this.quantLinhas()+1; i++) {
            for (int j = this.quantColunas()+1; j > 0; j--) {
                if(i + j == colunSize){
//                    System.out.printf("i: "+i+"|j: "+j+"|");
                    tadMatriz.setElem(i, j, this.getElem(i, j));
                }else{
                    tadMatriz.setElem(i, j, 0f);
                }
            }
//            System.out.println("");
        }
        return tadMatriz;
    }
    
    private void onlyDataP(){
        for (int i = 0; i < this.quantLinhas()+1; i++) {
            for (int j = 1; j < this.quantColunas()+1; j++) {
                if(i == j){
                    System.out.printf(this.getElem(i, j)+"\n");
                }
            }
        }
    }
        
    private void onlyDataS(){
        int colunSize = this.quantColunas()+1;
        
        for (int i = 1; i < this.quantLinhas()+1; i++) {
            for (int j = this.quantColunas()+1; j > 0; j--) {
                if(i + j == colunSize){
                    System.out.printf(this.getElem(i, j)+"\n");
                }
            }
        }
    }
    
    public void printDiagonal(){
        TADMatriz tadMatriz = new TADMatriz(this.quantLinhas(),this.quantColunas());
        System.out.println("--------------- Diagonal Principal -----------------");
        tadMatriz = this.principal();
        tadMatriz.printMatriz();
        System.out.println("--------------- Diagonal Secundaria -----------------");
        tadMatriz = this.secundaria();
        tadMatriz.printMatriz();
        System.out.println("--------------- Diagonal Principal Only Data -----------------");
        this.onlyDataP();
        System.out.println("--------------- Diagonal Principal Only Data-----------------");
        this.onlyDataS();
        
    }
    
    public static TADMatriz carrega(String nome_arq) throws FileNotFoundException, IOException{
        BufferedReader buffRead = new BufferedReader(new FileReader(nome_arq));
        String linha = "";
        int i=0,j=0;
        
        List<String> resultado = new ArrayList<>();
                
        while (true) {
            if (linha != null) {
                if(!linha.trim().isEmpty()){
                    resultado.add(linha);
                    i++;
                }
            } else
                break;
            linha = buffRead.readLine();
        }
        buffRead.close();
        
        
        
        String[] line = resultado.get(1).split("\\s+");
        for (String string : line) {
            if(!string.isEmpty()){
                j++;
//                System.out.println("line");
            }
        }
        
//        System.out.printf("quantidade de linhas: "+i+"\n");
//        System.out.printf("quantidade de colunas: "+j+"\n");
        
        TADMatriz matriz = new TADMatriz(i,j);
        int countI=1,countJ=1;
        for (String lineResult : resultado) {
            line = lineResult.split("\\s+");
            for (String cell : line) 
                if(!cell.isEmpty()){
                    matriz.setElem(countI, countJ, Float.parseFloat(cell));
                    countJ++;
                }
            countJ=1;
            countI++;
        }
        
        return matriz;
        
    }
    
    
    
    public String salva(String nome_arq) throws IOException{
        /*
        salva a matriz corrente (this) em um arquivo texto de nome nome_arq cada linha do arquivo deve ser uma linha da matriz
        */
        BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(nome_arq, true));
        String line;
        for(int i=1;i<=this.quantLinhas();i++){
            line = "";
            for(int j=1;j<=this.quantColunas();j++){
                line += this.getElem(i, j)+" ";
            }
            line += "\n";
            bufferWriter.write(line);
        }
        bufferWriter.close();
        return nome_arq;
    }
}
