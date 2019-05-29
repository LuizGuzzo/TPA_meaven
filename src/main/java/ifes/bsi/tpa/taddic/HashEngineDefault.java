package ifes.bsi.tpa.taddic;

/**
 *
 * @author luizg
 */
public abstract class HashEngineDefault {
    public long hash_func(Object o){
        long hash = 0;
        for (int i = 0; i < o.toString().length(); i++) {
            hash = hash + (int)o.toString().charAt(i);
        }
        return hash;
    }
}
