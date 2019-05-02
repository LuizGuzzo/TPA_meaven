package ifes.bsi.tpa.dic.aplication;

import ifes.bsi.tpa.dic.HashEngineDefault;

/**
 *
 * @author luizg
 */
//era essa que deveria ser abstrata..
public class Hash_engine extends HashEngineDefault {
    
    //@Override
    public long hash_func2(Object o) {
        long hash = 0;
        for (int i = 0; i < o.toString().length(); i++) {
            hash = hash + (int)o.toString().charAt(i);
        }
        return hash;
    }
    
    @Override
    public long hash_func(Object o){
        long hash = 0;
        String str = o.toString();
        for (int i = 0; i < str.length(); i++) {
            hash = 33 * hash + (int)str.charAt(i);
        }
        return Math.abs((int)hash);
    }
    
    public long hashBerstainMod(Object o){
        long hash = 0;
        String str = o.toString();
        for (int i = 0; i < str.length(); i++) {
            hash = (33 * hash)^(int)str.charAt(i);
        }
        return Math.abs((int)hash);
    }
}
