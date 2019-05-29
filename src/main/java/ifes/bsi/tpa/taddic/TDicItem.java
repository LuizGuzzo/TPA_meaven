package ifes.bsi.tpa.taddic;

/**
 *
 * @author luizg
 */
public class TDicItem {
    
    private Object chave; 
    private Object valor;
    private long cach_hash;  //pra q serve isso?

    public TDicItem(Object chave, Object valor) {
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

    public long getCach_hash() {
        return cach_hash;
    }

    public void setCach_hash(long cach_hash) {
        this.cach_hash = cach_hash;
    }


    @Override
    public String toString(){
        String key = (String) chave;
        String val = (String) valor.toString();
        return "chave:"+key+" / valor: "+val+ "/ cach: "+cach_hash;
    }
}
