
package br.serra.ifes.tpa.dic;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import java.io.IOException;
import java.util.*;
/**
 *
 * @author luizg
 */
public class TADDic {
    private LinkedList[] vet = null;
    private int qnt_entradas = 0;
    private int size = 0;
    private HashEngine hashEngine;
    
    public TADDic() {
        this.size = 256;
        this.StartTADDic(size, new HashEngineInicial());
    }
    
    public TADDic(int n) {
        double fc = 0.75;
        this.size = (int)(n/fc);
        this.StartTADDic(size, new HashEngineInicial()); //fator de carga
    }
    
    private void StartTADDic(int n, HashEngine hashEngine) {
        this.qnt_entradas = 0;
        this.hashEngine = hashEngine;
        this.insert_default_list_TADDic(n);
    }
    
    private void insert_default_list_TADDic(int n){
        vet = new LinkedList[n];
        for(int i = 0; i < n; i++){
            vet[i] = new LinkedList<RegDados>(); //para cada casa do vetor cria uma lista de "RegDados"
        }
    }
    
    private int getIndice(Object o) {
        long hash = this.hashEngine.gerarHash(o);
        int index = (int)(hash % this.vet.length);
        return index;
    }

    public void insert(Object o, Object reg){
        //RegDados aux = find(o); //carrega o object correspondente a key, se encontrar adiciona nele (para fazer o replace automatico)
        int indice = this.getIndice(o); //pega o indice correspondente a este object
        vet[indice].add((RegDados)reg); // adiciona ele no vetor em que se encontra
        qnt_entradas++;
        
        // se o tamanho de inserção estiver chegando no tamanho maximo ele redimensiona
        if(qnt_entradas >= (size*0.9)){
            redimesionar(size*2);
        }
    }
    
    public RegDados find(Object o){
        int indice = this.getIndice(o); //pega o indice correspondente a este object
        int pos = 0;
        while(pos < vet[indice].size()){ //percorrendo a lista do indice encontrado
            if(((RegDados)(vet[indice].get(pos))).getChave().equals(o)){ // se na posição do indice em que ele está tiver a mesma chave do Objeto q passou
                return (RegDados) vet[indice].get(pos); // pega o object encontrado
            }
            pos++;
        }
        return null;
    }
    
    public RegDados remove(Object o){
        RegDados aux = find(o);
        
        if(aux == null){
            return null;
        }else{
            int indice = this.getIndice(o);
            int pos = 0;
            while(pos < vet[indice].size()){ //enquanto não acabar a lista
                if (((RegDados)(vet[indice].get(pos))).getChave().equals(o)) // se encontrou o dado na lista
                    break;
                pos++;
            }
            
            vet[indice].remove(pos);
            qnt_entradas--;
            return aux;
        }
    }
    
    public Integer[] getColisoes(){
        Integer[] colisoes = new Integer[this.vet.length]; 
        for(int i=0; i < this.vet.length; i++){
            colisoes[i] = this.vet[i].size();
        }
        return colisoes;
    }
    
    public int quant_element(){
        return qnt_entradas;
    }
    
    public int getSize(){
        return this.size;
    }
    
    public void showall(){
        for (LinkedList linkedListReg : vet) {
            for (Object object : linkedListReg) {
                System.out.printf("%s\n",object.toString());
            }
        }
    }
    
    
    private void redimesionar(int tam) {
        
        //criar o vetor de tamanho "tam" com as listas
        //popular este vetor com o vetor antigo
        //sobreescrever o vetor antigo pelo novo
        
        LinkedList[] auxVet = new LinkedList[tam];
        int indice;
        
        for(int i = 0; i < tam; i++){
            auxVet[i] = new LinkedList<>(); //para cada casa do vetor cria uma lista de "RegDados"
        }
        
        for (LinkedList pai : this.vet){
            for (Object filho : pai) {
                indice = this.getIndice(filho);
                auxVet[indice].add((RegDados)filho); // adiciona ele no vetor em que se encontra
            }
        }
        
        this.vet = auxVet;
        this.size = tam;
        
        
    }
}
