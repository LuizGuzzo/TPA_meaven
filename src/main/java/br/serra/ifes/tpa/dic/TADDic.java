
package br.serra.ifes.tpa.dic;
import java.util.*;
/**
 *
 * @author luizg
 */
public class TADDic {
    private LinkedList[] vet = null;
    private int qnt_entradas = 0;
    private HashEngine hashEngine;
    
    public TADDic() {
        this.StartTADDic(256, new HashEngineInicial());
    }
    
    public TADDic(int n) {
        double fc = 0.75;
        int len = (int)(n/fc);
        this.StartTADDic(len, new HashEngineInicial()); //fator de carga
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
        RegDados aux = find(o); //carrega o object correspondente a key
        int indice = this.getIndice(o); //pega o indice correspondente a este object
        vet[indice].add((RegDados)reg); // adiciona ele no vetor em que se encontra
        qnt_entradas++;
        
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
    
    public int[] getColisoes(){
        int[] colisoes = new int[this.vet.length]; 
        for(int i=0; i < this.vet.length; i++){
            colisoes[i] = this.vet[i].size();
        }
        return colisoes;
    }
    
    public int quant_element(){
        return qnt_entradas;
    }
    
    public int tamVet(){
        return vet.length;
    }
    
    public void showall(){
        for (LinkedList linkedListReg : vet) {
            for (Object object : linkedListReg) {
                System.out.printf("%s\n",object.toString());
            }
        }
    }
}
