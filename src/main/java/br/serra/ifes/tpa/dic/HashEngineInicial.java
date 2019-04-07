package br.serra.ifes.tpa.dic;

/**
 *
 * @author luizg
 */
public class HashEngineInicial extends HashEngine {
    @Override
    public long gerarHash(Object o) {
        long hash = 0;
        for (int i = 0; i < o.toString().length(); i++) {
            hash = hash + (int)o.toString().charAt(i);
        }
        return hash;
    }
}
