
package ifes.bsi.tpa.dic;
import ifes.bsi.tpa.dic.aplication.Hash_engine;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import java.io.IOException;
import java.util.*;
/**
 *
 * @author luizg
 */



/*
ToDo:
Keys();
elements();
*/
public class TADDicChain {
    private LinkedList<TDicItem>[] vet = null;
    private double fator_de_carga = 0.75;
    
    private int quant_entradas = 0;
    private boolean achou = false;
    
    private int size = 13; //lenght do vetor
    private HashEngineDefault hashEngine;
    
    public TADDicChain() {
        this.StartTADDic(size, new Hash_engine()); //perguntar porque quando uso o construtor novamente ele "buga"
    }
    public TADDicChain(int n) {
        
        this.StartTADDic(n, new Hash_engine()); //fator de carga
    }
    public TADDicChain(HashEngineDefault hash){
        this.StartTADDic(size, hash);
    }
    public TADDicChain(int n,HashEngineDefault hash){
        
        this.StartTADDic(n, hash); //fator de carga
    }
    private void StartTADDic(int n, HashEngineDefault hashEngine) {
        this.size = (int)(n/this.fator_de_carga);
        this.quant_entradas = 0;
        this.hashEngine = hashEngine;
        this.vet = inicia_vet(this.size);
    }
    
//cria um vetor vazio de buckets com tamanho X
    private LinkedList[] inicia_vet(int tam){ 
        LinkedList<TDicItem>[] vet = new LinkedList[tam];
        for(int i = 0; i < tam; i++){
            vet[i] = new LinkedList<TDicItem>();
        }
        return vet;
    }
    
    public void insertItem(Object chave, Object valor){
        TDicItem tDicItem = (TDicItem) this.findDado(chave,this.vet);
        if(this.NO_SUCH_KEY() == false){
            insertItem(chave,valor,this.vet);
            this.quant_entradas++;
        }else{//altera o valor para o novo
            tDicItem.setValor(valor);
        }
        
        
        
    }
    

    
    //insere dado no vetor do dic porem com redimensionamento
    private void insertItem(Object chave, Object valor, LinkedList[] vet){ 
        insereData(chave,valor,vet);
        
        if(check_size_bucket()){
            this.redimensiona(size*2);
        }
    }
    //insere dado no vetor do dic
    private void insereData(Object chave, Object valor, LinkedList[] vet){
        long hash = this.getHash(chave, vet);
        insereData(chave,valor,vet,hash);
    }
    //passando já o hash
    private void insereData(Object chave, Object valor, LinkedList[] vet,long hash){
        TDicItem tDicItem = new TDicItem(chave,valor);
        int indice = this.getIndice(hash);
        tDicItem.setCach_hash(hash);
        vet[indice].add(tDicItem);
    }
    
    public Object findElement(Object chave){ //retorna valor
        this.achou = false;
        TDicItem dado = this.findDado(chave,this.vet);
        if (dado == null){
            return null;
        }else{
            this.achou = true;
            return dado.getValor();
        }
    }
    
    private TDicItem findDado(Object chave,LinkedList[] vet){ // retorna o Item
        this.achou = false;
        int indice = this.getIndice(chave,vet);

        if (this.vet[indice].isEmpty() == false) {
            int pos = 0;
            
            while (pos < vet[indice].size()) {
                TDicItem dado = this.vet[indice].get(pos);
                if (dado != null && dado.getChave().equals(chave)) {
                    this.achou = true;
                    return dado;
                }
                pos++;
            }
        }
        return null;
    }

    
    
    //esse método é inutil já q to sempre usando direto pelo hash
    private int getIndice(Object chave, LinkedList[] vet){ 
        long hash = getHash(chave,vet);
        return getIndice(hash);
    }
    private long getHash(Object chave, LinkedList[] vet) {
        return this.hashEngine.hash_func(chave);
    }
    private int getIndice(long hash){
        return (int) (hash % vet.length);
    }
    
    public Object removeElement(Object chave){
        this.achou = false;
        TDicItem dado = this.findDado(chave,this.vet);
        if (dado == null){
            return null;
        }else{
            int indice = this.getIndice(chave,this.vet);
            this.vet[indice].remove(dado);
            quant_entradas--;
            this.achou = true;
            return dado.getValor();
        }
    }
     
    public boolean isEmpty(){
        return (this.quant_entradas == 0)? true:false;
    }
    // isso daqui é o tamanho da quantidade de entradas
    public int size(){ 
        return quant_entradas;
    }    
    
    public boolean NO_SUCH_KEY(){
        return this.achou;
    }

    public LinkedList keys(){
        LinkedList chaves = new LinkedList();
        for (LinkedList<TDicItem> buckets : this.vet) {
            for (TDicItem filho : buckets) {
                chaves.add(filho.getChave());
            }
        }
        return chaves;
    }
    
    //
    public LinkedList elements(){
        LinkedList valor = new LinkedList();
        for (LinkedList<TDicItem> buckets : this.vet) {
            for (TDicItem filho : buckets) {
                valor.add(filho.getValor());
            }
        }
        return valor;
    }

    public TADDicChain clone(){
        return clone(this.size,this.hashEngine);
    }
    
    private TADDicChain clone(int size, HashEngineDefault hashEngine){
        TADDicChain dic_clone = new TADDicChain(size,hashEngine);
        
        for (LinkedList<TDicItem> pai : this.vet) {
            for (TDicItem filho : pai) {
                dic_clone.insertItem(filho.getChave(), filho.getValor());
            }
        }
        
        
        return dic_clone;
    }
    //percorrer todo o vetor e todas as listas dos vetores comparando todos os dados


    @Override
    public boolean equals(Object obj) {
        
        TADDicChain dic2 = (TADDicChain) obj;

        
        if(this.getHashEngine().equals(dic2.getHashEngine())){
            //System.out.printf("teste: 1 | qnt_ent1: "+ this.size() +"|qnt_ent2: "+ dic2.size() +"\n");
            if(this.size() == dic2.size()){
                
                for (LinkedList<TDicItem> pai : vet) {
                    for (TDicItem filho : pai) {
                        
                        Object chave = (Object)filho.getChave();
                        Object valor = (Object)filho.getValor();
                        //não pus para comparar o cache
                        
                        Object val2 = dic2.findElement(chave); 
                        
                        if(!dic2.NO_SUCH_KEY()){ //se o find não encontrou a key
                            return false;
                        }else{
                            
                            if(!val2.equals(valor)){ //se o valor é o mesmo correspondente a chave
                                /*
                                possivel problema de incremento que ele vai pegar o primeiro e não vai ver os demais dando falso sinal
                                soluções:
                                    (feito)não permitir mesma chave carregar varios valores (seria interessante o TDicItem carregar uma lista)
                                    rodar todo o bucket em busca do valor, se não achar falha
                                */
                                return false;
                            }
                        }
                    }
                }
                
            }else{
                return false;
            }
        }else{
            return false;
        }
        
        return true;

    }
        
    
    
    private void redimensiona(int tam) {
        
        LinkedList[] auxVet = this.inicia_vet(tam);
        auxVet = vet_copy(auxVet,this.vet);
        // deveria dar um drop no vetor antigo?
        
        this.vet = auxVet;
        this.size = tam;
    }
    
    private LinkedList[] vet_copy(LinkedList[] auxVet, LinkedList[] vet) {
            
        for (LinkedList<TDicItem> pai : vet){
            for (TDicItem filho : pai) {
                
                Object chave = filho.getChave();
                Object valor = filho.getValor();
                long cache_hash = filho.getCach_hash();
                
                this.insereData(chave, valor, auxVet,cache_hash);

            }
        }
        return auxVet;
    }
    
    private boolean check_size_bucket(){
        int maxSize = 0;
        for (LinkedList<TDicItem> linkedList : vet) {
            if(linkedList.size() > maxSize){
                maxSize = linkedList.size();
            }
        }
        return (maxSize >= size*0.25);
    }

    //Métodos não contidos na imagem de descrição

    public Integer[] getColisoes(){
        Integer[] colisoes = new Integer[this.vet.length]; 
        for(int i=0; i < this.vet.length; i++){
            colisoes[i] = this.vet[i].size();
        }
        return colisoes;
    }
    
    public int getSizeVetBuckets(){
        return this.size;
    }
    
    public void showall(){
        for (LinkedList<TDicItem> linkedListReg : this.vet) {
            for (TDicItem data : linkedListReg) {
                System.out.printf("%s\n",data.toString());
            }
        }
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

    public HashEngineDefault getHashEngine() {
        return hashEngine;
    }
   
    
    
    
    
    

    
}
