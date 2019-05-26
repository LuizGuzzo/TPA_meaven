/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.matriz;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 *
 * @author luizg
 */
public class TADMatriz {
    
    private int linhas;
    private int colunas;
    private Float mz[][];

    public TADMatriz(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.mz = create_matriz(linhas,colunas);
    }

    
    private Float[][] create_matriz(int linha, int coluna){
        
        Float mz[][] = new Float[linha][coluna];
        
        for (int i= 0; i <linha; i++){
            for (int j= 0; j <coluna; j++){
                mz[i][j] = new Float(0);		
            }
        }
        return mz;
    }
    
    
    
    public void salvar (String nome_arq) throws IOException{
        /*
        salva a matriz corrente (this) em um arquivo texto de nome nome_arq cada linha do arquivo deve ser uma linha da matriz
        */
        BufferedWriter buffWrite = new BufferedWriter(new FileWriter("C:\\Users\\luizg\\Documents\\NetBeansProjects\\TPA_meaven\\src\\main\\java\\ifes\\bsi\\tpa\\dic\\"+nome_arq+".txt"));
        String linha = "";

        Float[][] c = this.mz;
       
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[i].length; j++) {
                System.out.printf(c[i][j] + " ");
                buffWrite.append(""+c[i][j]+" ");
            }
            buffWrite.append("/n"); //PORQUE NAO FUNCIONA
        }
        buffWrite.close();
    }
    
    public TADMatriz carregar(String nome_arq){
        /*
        carrega uma matriz a partir de um arquivo texto de nome "nome_arq" O arquivo tem o formato descrito no metodo "Salvar" (metodo acima). O me´todo retorna uma matriz povoada pelos dados do arquivo
        */
        return null;
    }
//    retorna true se a matriz atual (this) for igual a matriz m, retorna false caso contrario. As matrizes são iguais se possuirem as mesmas dimensões e os mesmos contéudos nas posições x,y.
    public boolean equals(TADMatriz m){
        
        Float[][] a = this.getMz();
        Float[][] b = m.getMz();
        
        if(m.quantLinhas() == this.quantLinhas() && m.quantColunas() == this.quantColunas()){
            for (int i = 0; i < this.quantLinhas(); i++) {
                for (int j = 0; j < this.quantColunas(); j++) {
//                    System.out.printf("a["+i+"]["+j+"]:"+a[i][j]+"| b["+i+"]["+j+"]:"+b[i][j]+"\n");
                    if(a[i][j] != b[i][j]) return false;
                }
            }
        
            
        }else return false;
        
        return true;
    }
    
    //to convertendo nada, to fazendo "rand" aqui só pra testa msm
    public void DicToMatriz(/*TADDicChain dic*/){
        
        Float mz[][] = new Float[this.quantLinhas()][this.quantColunas()];
        Float count = new Float(1);
        
        for (int i= 0; i <this.quantLinhas(); i++){
            for (int j= 0; j <this.quantColunas(); j++){
                mz[i][j] = count;
                count += 1;
            }
        }
        
        this.mz = mz;
    }
    

   
    public Float[][] getMz() {
        return mz;
    }

    public void setMz(Float[][] mz) {
        this.mz = mz;
    }

    public void printMatriz() {
        
        Float[][] c = this.mz;
        
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[i].length; j++) {
                System.out.printf(c[i][j] + " ");
            }
            System.out.println("");
        }
    }
    
    /*Oque o professor pediu esta aqui em baixo (não testei ainda por motivos da documentação estar incompleta) ##################################################################################*/
    
    
    // retorna um novo TADMatriz resultado da multiplicação da matriz atual (this) pela argumento m
    public TADMatriz multi(TADMatriz m){
        Float[][] a = this.getMz();
        Float[][] b = m.getMz();
        Float aux;
        
        if(this.quantColunas() == m.quantLinhas()){ // acredito que exista uma garantia melhor do q ser identico (se sim atualize o metodo)
            
            linhas = (this.quantLinhas()> m.quantLinhas())? m.quantLinhas(): this.quantLinhas();
            colunas = (this.quantColunas()> m.quantColunas())? m.quantColunas(): this.quantColunas();            
            
            Float c[][] = new Float[linhas][colunas];
                        
            
                    for (int i = 0; i < a.length; i++) {
                        for (int j = 0; j < b[0].length; j++) {
                            aux = new Float(0);
                            for (int k = 0; k < a[0].length; k++) {
                                aux = aux + a[i][k] * b[k][j];
//                                System.out.printf("+("+ a[i][k] + "*"+ b[k][j] +")");
                            }
//                            System.out.printf("= "+aux+"\n");
                            c[i][j] = aux;
                        }
                    }
                    

                
            
            this.mz = c;
//            printMatriz();
            TADMatriz tadMatrizEsp = new TADMatriz(this.quantLinhas(),this.quantColunas());
            tadMatrizEsp.setMz(c);
            return tadMatrizEsp;
        }else{
            System.out.println("O numero de colunas da matriz A tem que ser igual ao numero de linhas da matriz B \n");
        }
        
        return null;
    }

    public Float getElem(int i, int j){
        Float data = this.getMz()[i][j];
        return(i>this.getMz().length || j> this.getMz()[0].length || data==0)?null:data;
        
    }
    
    public int quantColunas() {
        return this.colunas;
    }
    
    public int quantLinhas() {
        return this.linhas;
    }
     
    public Float setElem(int i, int j, Float valor){
        if(i>this.getMz().length || j> this.getMz()[0].length)
            return null;
        else{
            this.getMz()[i][j] = valor;
            return valor;
        }
    }
    
    public TADMatriz soma(TADMatriz m){
        if(m.quantColunas() == this.quantColunas() && m.quantLinhas() == this.quantLinhas()){
            TADMatriz newMatriz = new TADMatriz(this.quantLinhas(),this.quantColunas());
            for (int i = 0; i < this.quantLinhas(); i++) {
                for (int j = 0; j < this.quantColunas(); j++) {
                    newMatriz.setElem(i, j, this.getElem(i, j) + m.getElem(i, j));
                }
            }
            return newMatriz;
        }else{
            return null;
        }
    }
    
    public void vezesK(Float k){
        for (Float[] floats : mz)
            for (Float aFloat : floats)
                aFloat = aFloat*k;
    }
    
    public TADMatriz transposta(){
        TADMatriz newMatriz = new TADMatriz(this.quantColunas(),this.quantLinhas());
        for (int i = 0; i < this.quantLinhas(); i++) {
            for (int j = 0; j < this.quantColunas(); j++) {
                newMatriz.setElem(j, i, this.getElem(i, j));
            }
        }
        return newMatriz;
    }
    
    //To Make
    public static TADMatriz carrega(String nome_arq){
        return null;
    }
    
    //To Make
    public String salva(String nome_arq){
        return null;
    }
}
