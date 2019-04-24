
package br.serra.ifes.tpa.dic;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import java.io.IOException;
import java.util.*;
/**
 *
 * @author luizg
 */

/*
    ignora
    pedido,laudo de exame, raio-x, ressonancia, telefone de contato, tudo da carteira.
    recepcao@maternidadesantapaula.com.br
    */

/*
    tirar o generics e jogar cast q nem loco
    renomear para o nome dos metodos que ele deseja
    fazer os metodos remanescentes
*/
public class TADDic<C,V> {
    private LinkedList<TDicItem>[] vet = null;
    private int qnt_entradas = 0;
    private int size = 0;
    private boolean achou = false;
    private HashEngine hashEngine;
    private int first_size;
    
    public TADDic() {
        this.size = 256;
        this.first_size = size;
        this.StartTADDic(size, new HashEngineInicial()); //perguntar porque quando uso o construtor novamente ele "buga"
    }
    
    public TADDic(int n) {
        this.first_size = n;
        double fc = 0.75;
        this.size = (int)(n/fc);
        this.StartTADDic(size, new HashEngineInicial()); //fator de carga
    }
    
    public TADDic(HashEngine hash){
        this.size = 256;
        this.first_size = size;
        this.StartTADDic(size, hash);
    }
    
    public TADDic(int n,HashEngine hash){
        this.first_size = n;
        double fc = 0.75;
        this.size = (int)(n/fc);
        this.StartTADDic(size, hash); //fator de carga
    }
    private void StartTADDic(int n, HashEngine hashEngine) {
        this.qnt_entradas = 0;
        this.hashEngine = hashEngine;
        this.vet = new LinkedList[n]; // esse N podia ser this.size (logo nem precisava ta no parametro tbm)
        for(int i = 0; i < n; i++){
            vet[i] = new LinkedList<TDicItem>();
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
        this.vet[indice].add(new TDicItem(chave,valor));
        this.qnt_entradas++;
        
        if(check_size_bucket()){
            this.redimesionar(size*2);
        }
    }
    
    private boolean check_size_bucket(){
        int maxSize = 0;
        for (LinkedList<TDicItem> linkedList : vet) {
            if(linkedList.size() > maxSize){
                maxSize = linkedList.size();
            }
        }
        return (maxSize >= size*0.25) ? true : false;
    }

    private TDicItem<C,V> findDado(C chave){
        return findDado(chave,this.vet);
    }
    
    private TDicItem<C,V> findDado(C chave,LinkedList[] vet){
        int indice = this.getIndice(chave);

        if (this.vet[indice].isEmpty() == false) {
            int pos = 0;
            
            while (pos < vet[indice].size()) {
                TDicItem dado = this.vet[indice].get(pos);
                if (dado != null && dado.getChave().equals(chave)) {
                    return dado;
                }
                pos++;
            }
        }
        return null;
    }
    
    private V find(C chave){
        this.achou = false;
        TDicItem<C,V> dado = this.findDado(chave);
        if (dado == null){
            return null;
        }else{
            this.achou = true;
            return dado.getValor();
        }
    }
    

    public V remove(C chave){
        this.achou = false;
        TDicItem<C,V> dado = this.findDado(chave);
        if (dado == null){
            return null;
        }else{
            int indice = this.getIndice(chave);
            vet[indice].remove(dado);
            qnt_entradas--;
            this.achou = true;
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
        for (LinkedList<TDicItem> linkedListReg : this.vet) {
            for (TDicItem data : linkedListReg) {
                System.out.printf("%s\n",data.toString());
            }
        }
    }
    
    
    private void redimesionar(int tam) {
        
        LinkedList[] auxVet = this.clone_vet(tam);
        
        // deveria dar um drop no vetor antigo?
        
        this.vet = auxVet;
        this.size = tam;
    }
    
    public void showCollisionsDiagram() {
        Plot plot = Plot.create();
        plot.plot().add(Arrays.asList(this.getColisoes()));
        plot.xlabel("indice da lista");
        plot.ylabel("Quantidade de entradas");
        plot.title("Colisoes");
        try {
            plot.show();
        } catch (PythonExecutionException | IOException e) {
            e.printStackTrace();
        }
    }
    public LinkedList[] keys(){
        LinkedList<TDicItem>[] lista = null;
        // retornar a collections de apenas Keys
        return lista;
    }
    public LinkedList[] elements(){
        LinkedList<TDicItem>[] lista = null;
        // retornar a collections de apenas os elementos
        return lista;
    }
    
    public TADDic clone(){
        TADDic<Object,Object> dic_clone = new TADDic<Object,Object>(this.first_size);
        
        for (LinkedList<TDicItem> pai : this.vet) {
            for (TDicItem filho : pai) {
                
                C chave = (C)filho.getChave();
                V valor = (V)filho.getValor();
                                
                dic_clone.insert(chave, valor);
            }
            
        }
        
        
        return dic_clone;
    }
    
    public boolean equals(){
        //percorrer todo o vetor e todas as listas dos vetores comparando todos os dados
        return false;
    }
    
    public boolean NO_SUCH_KEY(){
        return this.achou;
    }

    private LinkedList[] clone_vet(){
        return this.clone_vet(this.size);
    }
    
    private LinkedList[] clone_vet(int tam) {
        LinkedList<TDicItem>[] auxVet = new LinkedList[tam];
        
        
        for(int i = 0; i < tam; i++){ // preencher os buckets com listas
            auxVet[i] = new LinkedList<TDicItem>();
        }
        
        for (LinkedList<TDicItem> pai : this.vet){
            for (TDicItem filho : pai) {
                
                C chave = (C)filho.getChave();
                V valor = (V)filho.getValor();
                
                int indice = this.getIndice(chave,auxVet);
                auxVet[indice].add(new TDicItem(chave,valor));

            }
        }
        return auxVet;
    }
}
