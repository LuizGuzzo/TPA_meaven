package br.serra.ifes.tpa.dic;

/**
 *
 * @author luizg
 */
public class RegDados<C,V> {
    
    private C chave; 
    private V valor;

    public RegDados(C chave, V valor) {
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
        String val = (String) valor;
        return "chave:"+key+" / valor: "+val;
    }
}
