package ifes.bsi.tpa.dic.aplication;

/**
 *
 * @author luizg
 */
public class Traduzir_IGtoPT {
    private String portugues;
    private String ingles;

    public Traduzir_IGtoPT(String ingles, String portugues) {
        this.portugues = portugues;
        this.ingles = ingles;
    }

    public String getPortugues() {
        return portugues;
    }

    public void setPortugues(String portugues) {
        this.portugues = portugues;
    }

    public String getIngles() {
        return ingles;
    }

    public void setIngles(String ingles) {
        this.ingles = ingles;
    }

    @Override
    public String toString() {
        return "{" + "portugues=" + portugues + ", ingles=" + ingles + "}";
    }
    
    
}
