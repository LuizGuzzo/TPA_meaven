/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.matriz;

/**
 *
 * @author luizg
 */
public class ChaveMatriz {
    
    private int linha;
    private int coluna;

    public ChaveMatriz(){}
    public ChaveMatriz(int linha, int coluna){
        this.linha = linha;
        this.coluna = coluna;
    }
    
    public int getColuna() {
        return coluna;
    }

    public int getLinha() {
        return linha;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }
    
}

  