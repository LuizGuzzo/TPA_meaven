
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
    private LinkedList<TDicItem>[] vetBuckets = null;
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
        this.vetBuckets = inicia_vet(this.size);
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
        TDicItem tDicItem = (TDicItem) this.findItem(chave,this.vetBuckets);
        if(this.NO_SUCH_KEY() == true){
            insertItem(chave,valor,this.vetBuckets);
            this.quant_entradas++;
        }else{//altera o valor para o novo
            tDicItem.setValor(valor);
        }
        
        
        
    }
    

    
    //insere dado no vetor do dic porem com redimensionamento
    private void insertItem(Object chave, Object valor, LinkedList[] vet){ 
        insereData(chave,valor,vet);
        
        if(check_size_bucket()){
            this.redimensiona();
        }
    }
    //insere dado no vetor do dic
    private void insereData(Object chave, Object valor, LinkedList[] vet){
        long hash = this.getHash(chave);
        insereData(chave,valor,vet,hash);
    }
    //passando já o hash
    private void insereData(Object chave, Object valor, LinkedList[] vet,long hash){
        TDicItem tDicItem = new TDicItem(chave,valor);
        int indice = this.getIndice(hash,vet);
        tDicItem.setCach_hash(hash);
        vet[indice].add(tDicItem);
    }
    
    public Object findElement(Object chave){ //retorna valor
        TDicItem dado = this.findItem(chave,this.vetBuckets);
        if (dado == null){
            return null;
        }else{
            return dado.getValor();
        }
    }
    
//    public Object findoElement(Object chave){ //TESTE
//        TDicItem dado = this.findoItem(chave,this.vetBuckets);
//        if (dado == null){
//            return null;
//        }else{
//            return dado.getValor();
//        }
//    }
//    private TDicItem findoItem(Object chave,LinkedList[] vet){ // TESTE
//        this.achou = false;
//        int indice = this.getIndice(chave,vet);
//        
//        if (!this.vetBuckets[indice].isEmpty()) {
//            int pos = 0;
//            while (pos < vet[indice].size()) {
//                TDicItem item = (TDicItem) vet[indice].get(pos);
//                System.out.printf("indice: "+ indice +" | pos: "+ pos +" | item_chave: "+ item.getChave().toString() +" | chave: "+ chave.toString() +
//                        "vet[indice].size = "+ vet[indice].size() +"\n");
//                if (item != null && item.getChave().equals(chave)) {
//                    System.out.println("GOTCHA");
//                    this.achou = true;
//                    return item;
//                }
//                pos++;
//            }
////            for (LinkedList<TDicItem> linkedList : vet) {
////                for (TDicItem item : linkedList) {
////                    if(item != null && item.getChave().equals(chave)){
//////                        System.out.println("GOTCHA");
////                        this.achou = true;
////                        return item;
////                    }
////                }
////            }
//        }
//        System.out.printf("objeto: "+ chave.toString() +" | não encontrada\n");
//        return null;
//    }
    
    private TDicItem findItem(Object chave,LinkedList[] vet){ // retorna o Item
        this.achou = false;
        int indice = this.getIndice(chave,vet);
        
        if (!this.vetBuckets[indice].isEmpty()) {
            int pos = 0;
            while (pos < vet[indice].size()) {
                TDicItem item = (TDicItem) vet[indice].get(pos);
                if (item != null && item.getChave().equals(chave)) {
//                    System.out.println("GOTCHA");
                    this.achou = true;
                    return item;
                }
                pos++;
            }
        }
//        System.out.printf("objeto: "+ chave.toString() +" | não encontrada\n");
        return null;
    }

    
    
    //esse método é inutil já q to sempre usando direto pelo hash
    //devia usar int em vez de passar um fodendo vetor
    private int getIndice(Object chave, LinkedList[] vet){ 
        long hash = getHash(chave);
        return (int) (hash % vet.length);
    }
    private long getHash(Object chave) {
        return this.hashEngine.hash_func(chave);
    }
    private int getIndice(long hash,LinkedList[] vet){
        return (int) (hash % vet.length);
    }
    
    public Object removeElement(Object chave){
//        System.out.println("removeElement");
        TDicItem item = this.findItem(chave,this.vetBuckets); 
        if (item != null){
            int indice = this.getIndice(chave,this.vetBuckets);
//            int indice = this.getIndice(item.getCach_hash()); //em vez de eu calcula o hash eu podia pegar direto e_e
            this.vetBuckets[indice].remove(item);
            quant_entradas--;
            return item.getValor();
            
        }else{
            return null;
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
        return !this.achou;
    }

    public LinkedList<Object> keys(){
        LinkedList<Object> chaves = new LinkedList();
        for (LinkedList<TDicItem> buckets : this.vetBuckets) {
            for (TDicItem filho : buckets) {
                chaves.add((Object)filho.getChave());
            }
        }
        return chaves;
    }
    
    //
    public LinkedList<Object> elements(){
        LinkedList<Object> valor = new LinkedList();
        for (LinkedList<TDicItem> buckets : this.vetBuckets) {
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
        
        for (LinkedList<TDicItem> pai : this.vetBuckets) {
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
                
                for (LinkedList<TDicItem> pai : vetBuckets) {
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
        
    
    
    private void redimensiona() {
        
        int tam = this.getSizeVetBuckets()*2;
        
        LinkedList<TDicItem>[] vet_backup = this.inicia_vet(tam); //re-analizar
        vet_backup = vet_copy(vet_backup,this.vetBuckets);
        // deveria dar um drop no vetor antigo?
        
        this.vetBuckets = vet_backup;
        this.size = tam;
    }
    
//    private void redimensionar() { //TESTE
//
//        int newTam = this.vetBuckets.length * 2;
//        LinkedList<TDicItem>[] newDicionario = new LinkedList[newTam];
//
//        for (int i = 0; i < newTam; i++) {
//            newDicionario[i] = new LinkedList<>();
//        }
//
//        for (int j = 0; j < this.vetBuckets.length; j++) {
//            if (this.vetBuckets[j] != null) {
//                for (int k = 0; k < this.vetBuckets[j].size(); k++) {
//                    Object key = this.vetBuckets[j].get(k).getChave();
//                    int index = getIndice(key, this.vetBuckets);
//                    newDicionario[index].add(this.vetBuckets[j].get(k));
//                }
//            }
//        }
//
//        this.vetBuckets = newDicionario;
//}
    
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
        for (LinkedList<TDicItem> linkedList : vetBuckets) {
            if(linkedList.size() > maxSize){
                maxSize = linkedList.size();
            }
        }
        return (maxSize >= size*0.25);
    }

    //Métodos não contidos na imagem de descrição

    public Integer[] getColisoes(){
        Integer[] colisoes = new Integer[this.vetBuckets.length]; 
        for(int i=0; i < this.vetBuckets.length; i++){
            colisoes[i] = this.vetBuckets[i].size();
        }
        return colisoes;
    }
    
    public int getSizeVetBuckets(){
        return this.size;
    }
    
    public void showall(){
        for (LinkedList<TDicItem> linkedListReg : this.vetBuckets) {
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
