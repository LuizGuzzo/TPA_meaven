/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.matriz;

import ifes.bsi.tpa.dic.TADDicChain;
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
    
    public TADMatriz(){
        this(100,100);
    }
    
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
    
    //CAOS TOTAL
    public static TADMatriz carrega(String nome_arq,int l, int c) throws FileNotFoundException, IOException{
        
        /*
        carrega uma matriz a partir de um arquivo texto de nome "nome_arq" O arquivo tem o
        formato descrito no metodo "Salvar" (metodo acima). O me´todo retorna uma matriz povoada pelos dados do arquivo
        */
        
        BufferedReader buffRead = new BufferedReader(new FileReader(nome_arq));
        String linha = "";
        int[] size;
        int i=0,j = 1;
        String[] line;
        
        TADMatriz matriz = new TADMatriz(l,c);
        
//        System.out.printf("i: "+i+"|j: "+j);
        
                
        while (true) {
            if (linha != null) {
                line = linha.split(" ");
                for (String cell : line) {
                    cell.replaceAll(" ","");
                    if(!cell.isEmpty()){
                        Float value = Float.parseFloat(cell);
                        matriz.setElem(i, j, value);
                        j++;
                    }
                    
                }
                j=1;
                i++;
 
            } else
                break;
            linha = buffRead.readLine();
        }
        
        buffRead.close();
        
        return matriz;
    }
    
    public static TADMatriz carrega(String nome_arq) throws FileNotFoundException, IOException{
        //Preciso saber a quantidade de linhas e colunas para definir a matriz no inicio
        /*
        carrega uma matriz a partir de um arquivo texto de nome "nome_arq" O arquivo tem o
        formato descrito no metodo "Salvar" (metodo acima). O me´todo retorna uma matriz povoada pelos dados do arquivo
        */
        
        BufferedReader buffRead = new BufferedReader(new FileReader(nome_arq));
        String linha = "";
        int i=0, j=0,z=0, x=0;
        String[] line;
        TADMatriz matriz = new TADMatriz(5,7);
                
        while (true) {
            if (linha != null) {
                line = linha.split(" ");
                for (String cell : line) {
                    cell.replaceAll(" ","");
                    if(!cell.isEmpty()){
                        j++;
                    }
                }
                z=j;
                x=i;
                j=0;
                i++;
 
            } else
                break;
            linha = buffRead.readLine();
        }
        
        buffRead.close();
//        System.out.printf("i: "+i+"|j: "+j+"|z: "+z+"|x: "+x+"\n");
        
        TADMatriz tadMatriz = TADMatriz.carrega(nome_arq,x,z);;
        
        return tadMatriz;
        
    }
    
    //To Make
    public String salva(String nome_arq) throws IOException{
        /*
        salva a matriz corrente (this) em um arquivo texto de nome nome_arq cada linha do arquivo deve ser uma linha da matriz
        */
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter("C:\\Users\\luizg\\Documents\\NetBeansProjects\\TPA_meaven\\src\\main\\java\\ativ-1-tpa-taddic\\bdmatrizes\\"+nome_arq+".txt");
            bw = new BufferedWriter(fw);
            String line;
            for(int i=1;i<=this.linhas;i++){
                line = "";
                for(int j=1;j<=this.colunas;j++){
                    line += this.getElem(i, j)+" ";
                }
                line += "\n";
                bw.write(line);
            }
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(TADMatriz.class.getName()).log(Level.SEVERE, null, ex);
        }
        bw = new BufferedWriter(fw);
        return nome_arq;
    }
}
