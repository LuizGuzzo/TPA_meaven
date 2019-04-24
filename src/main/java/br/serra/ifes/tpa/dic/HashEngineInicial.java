package br.serra.ifes.tpa.dic;

/**
 *
 * @author luizg
 */
public class HashEngineInicial extends HashEngine {
    
    public long hashDefault(Object o) {
        long hash = 0;
        for (int i = 0; i < o.toString().length(); i++) {
            hash = hash + (int)o.toString().charAt(i);
        }
        return hash;
    }
    
    public long hashBerstain(Object o){
        long hash = 0;
        String str = o.toString();
        for (int i = 0; i < str.length(); i++) {
            hash = str.charAt(i) + ((hash << 5) - hash);
        }
        return hash;
    }
    
    public long hashBerstainMod(Object o){
        long hash = 0;
        return hash;
    }
}
