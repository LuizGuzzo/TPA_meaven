package br.serra.ifes.tpa.dic;

/**
 *
 * @author luizg
 */
public class RegDados {
    /*se for para tratar isso como Object é so converter td por obj, mas no momento estou querendo mostrar o funcionamento logo estou deixando como string*/
    private Object chave; 
    private Object valor;

    public RegDados(Object chave, Object valor) {
        this.chave = chave;
        this.valor = valor;
    }

    public Object getChave() {
        return chave;
    }

    public void setChave(Object chave) {
        this.chave = chave;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    @Override
    public String toString(){
        String key = (String) chave;
        String val = (String) valor;
        return "chave:"+key+" / valor: "+val;
    }
}
