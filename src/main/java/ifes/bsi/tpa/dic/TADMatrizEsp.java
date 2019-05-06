/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.dic;

import java.util.LinkedList;

/**
 *
 * @author luizg
 */
public class TADMatrizEsp {
    
    private int linhas;
    private int colunas;
    private double mz[][];

    public TADMatrizEsp(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.mz = create_matriz(linhas,colunas);
    }

    
    private double[][] create_matriz(int linha, int coluna){
        
        double mz[][] = new double[linha][coluna];
        
        for (int i= 0; i <linha; i++){
            for (int j= 0; j <coluna; j++){
                mz[i][j] = 0;		
            }
        }
        return mz;
    }
    
    public TADMatrizEsp mult(TADMatrizEsp m){
        double[][] a = this.getMz();
        double[][] b = m.getMz();
        double aux;
        /* 
        retorna um novo TADMatrizEsp resultado da multiplicação da matriz atual (this) pela argumento m
        */
        if(this.getColunas() == m.getLinhas()){ // acredito que exista uma garantia melhor do q ser identico (se sim atualize o metodo)
            
            linhas = (this.getLinhas()> m.getLinhas())? m.getLinhas(): this.getLinhas();
            colunas = (this.getColunas()> m.getColunas())? m.getColunas(): this.getColunas();            
            
            double c[][] = new double[linhas][colunas];
                        
            
                    for (int i = 0; i < a.length; i++) {
                        for (int j = 0; j < b[0].length; j++) {
                            aux = 0;
                            for (int k = 0; k < a[0].length; k++) {
                                aux = aux + a[i][k] * b[k][j];
//                                System.out.printf("+("+ a[i][k] + "*"+ b[k][j] +")");
                            }
//                            System.out.printf("= "+aux+"\n");
                            c[i][j] = aux;
                        }
                    }
                    

                
            
            this.mz = c;
            printMatriz();
            TADMatrizEsp tadMatrizEsp = new TADMatrizEsp(this.getLinhas(),this.getColunas());
            tadMatrizEsp.setMz(c);
            return tadMatrizEsp;
        }else{
            System.out.println("O numero de colunas da matriz A tem que ser igual ao numero de linhas da martiz B \n");
        }
        
        return null;
    }

    public String salvar (String nome_arq){
        /*
        salva a matriz corrente (this) em um arquivo texto de nome nome_arq cada linha do arquivo deve ser uma linha da matriz
        */
        return null;
    }
    
    public TADMatrizEsp carregar(String nome_arq){
        /*
        carrega uma matriz a partir de um arquivo texto de nome "nome_arq" O arquivo tem o formato descrito no metodo "Salvar" (metodo acima). O me´todo retorna uma matriz povoada pelos dados do arquivo
        */
        return null;
    }
    
    public boolean equals(TADMatrizEsp m){
        /*
        retorna true se a matriz atual (this) for igual a matriz m, retorna false caso contrario. As matrizes são iguais se possuirem as mesmas dimensões e os mesmos contéudos nas posições x,y.
        */
        return false;
    }
    
    //to convertendo nada, to fazendo "rand" aqui só pra testa msm
    public void DicToMatriz(/*TADDicChain dic*/){
        
        double mz[][] = new double[this.getLinhas()][this.getColunas()];
        double count = 1;
        
        for (int i= 0; i <this.getLinhas(); i++){
            for (int j= 0; j <this.getColunas(); j++){
                mz[i][j] = count;
                count += 1;
            }
        }
        
        this.mz = mz;
    }
    /*
    vou encher ela com o vetor de buckets?
    se sim ficaria linkedlist como linha armazenando os buckets
    e dentro de cada linha um outro linkedlist que seria os dados de cada coluna correspondente a linha X, sendo as listas dentro dos buckets :D
    
    as linhas e colunas seriam definidas como:
    linhas = vetBuckets.length
    colunas = vetBuckets.maiorBucket()
    */

    public int getLinhas() {
        return linhas;
    }

    public void setLinhas(int linhas) {
        this.linhas = linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public void setColunas(int colunas) {
        this.colunas = colunas;
    }

    public double[][] getMz() {
        return mz;
    }

    public void setMz(double[][] mz) {
        this.mz = mz;
    }

    public void printMatriz() {
        
        double[][] c = this.mz;
        
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[i].length; j++) {
                System.out.printf(c[i][j] + " ");
            }
            System.out.println("");
        }
    }
    
}
