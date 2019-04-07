package br.serra.ifes.tpa.dic;

/**
 *
 * @author luizg
 */
public class TDicItem<C,V> {
    
    private C chave; 
    private V valor;

    public TDicItem(C chave, V valor) {
        this.chave = chave;
        this.valor = valor;
    }

    public C getChave() {
        return chave;
    }

    public void setChave(C chave) {
        this.chave = chave;
    }

    public V getValor() {
        return valor;
    }

    public void setValor(V valor) {
        this.valor = valor;
    }

    @Override
    public String toString(){
        String key = (String) chave;
        String val = (String) valor.toString();
        return "chave:"+key+" / valor: "+val;
    }
}
