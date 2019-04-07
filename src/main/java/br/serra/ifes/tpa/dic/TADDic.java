
package br.serra.ifes.tpa.dic;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import java.io.IOException;
import java.util.*;
/**
 *
 * @author luizg
 */
public class TADDic<C,V> {
    private LinkedList<RegDados>[] vet = null;
    private int qnt_entradas = 0;
    private int size = 0;
    private HashEngine hashEngine;
    
    public TADDic() {
        this.size = 256;
        this.StartTADDic(size, new HashEngineInicial()); //perguntar porque quando uso o construtor novamente ele "buga"
    }
    
    public TADDic(int n) {
        double fc = 0.75;
        this.size = (int)(n/fc);
        this.StartTADDic(size, new HashEngineInicial()); //fator de carga
    }
    
    private void StartTADDic(int n, HashEngine hashEngine) {
        this.qnt_entradas = 0;
        this.hashEngine = hashEngine;
        this.vet = new LinkedList[n];
        for(int i = 0; i < n; i++){
            vet[i] = new LinkedList<RegDados>();
        }
    }
    
    private int getIndice(C chave){
        return getIndice(chave,this.vet);
    }
    
    private int getIndice(C chave, LinkedList[] vet) {
        long hash = this.hashEngine.gerarHash(chave);
        int index = (int)(hash % vet.length);
        return index;
    }


        
    public void insert(C chave, V valor){
        int indice = this.getIndice(chave);
        this.vet[indice].add(new RegDados(chave,valor));
        this.qnt_entradas++;
        if(qnt_entradas >= (this.size*0.9)){
            this.redimesionar(size*2);
        }
    }
    

    private RegDados<C,V> findDado(C chave){
        return findDado(chave,this.vet);
    }
    
    private RegDados<C,V> findDado(C chave,LinkedList[] vet){
        int indice = this.getIndice(chave);

        if (this.vet[indice].isEmpty() == false) {
            int pos = 0;
            
            while (pos < vet[indice].size()) {
                RegDados dado = this.vet[indice].get(pos);
                if (dado != null && dado.getChave().equals(chave)) {
                    return dado;
                }
                pos++;
            }
        }
        return null;
    }
    
    private V find(C chave){
        RegDados<C,V> dado = this.findDado(chave);
        if (dado == null){
            return null;
        }else{
            return dado.getValor();
        }
    }
    

    public V remove(C chave){
        RegDados<C,V> dado = this.findDado(chave);
        if (dado == null){
            return null;
        }else{
            int indice = this.getIndice(chave);
            vet[indice].remove(dado);
            qnt_entradas--;
            
            return dado.getValor();
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
        for (LinkedList linkedListReg : this.vet) {
            for (Object object : linkedListReg) {
                System.out.printf("%s\n",object.toString());
            }
        }
    }
    
    
    private void redimesionar(int tam) {
        
        
        LinkedList<RegDados>[] auxVet = new LinkedList[tam];
        int qnt_val = 0;
        
        for(int i = 0; i < tam; i++){
            auxVet[i] = new LinkedList<RegDados>();
        }
        
        for (LinkedList<RegDados> pai : this.vet){
            for (RegDados filho : pai) {
                
                C chave = (C)filho.getChave();
                V valor = (V)filho.getValor();
                
                int indice = this.getIndice(chave,auxVet);
                auxVet[indice].add(new RegDados(chave,valor));
                qnt_val++;

            }
        }
        
        // deveria dar um drop no vetor antigo?
        
        this.qnt_entradas = qnt_val;
        this.vet = auxVet;
        this.size = tam;
        
        
    }
}
