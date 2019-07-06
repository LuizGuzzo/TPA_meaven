/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.grafo.processadores;

import ifes.bsi.tpa.grafo.Vertex;
import ifes.bsi.tpa.taddic.TADDicChain;
import java.util.LinkedList;

/**
 *
 * @author luizg
 */
public class DSFloydW extends DataSet{

    private int[][] mat_custos;
    private TADDicChain dic_vertex_label_int = new TADDicChain(null);

    public DSFloydW (int[][] mat_custos, TADDicChain dic) {
        this.mat_custos = mat_custos;
        this.dic_vertex_label_int = dic;
    }

    @Override
    public LinkedList<Vertex> caminho(String origem, String destino){
        return null;

    }

    @Override
    public int custo (String origem, String destino) {
        int idLine = (int) dic_vertex_label_int.findElement(origem);
        int idColumn =(int) dic_vertex_label_int.findElement(destino);
        
        return mat_custos[idLine][idColumn];
    }

    public int[][] getMat_custos() {
        return mat_custos;
    }

    public TADDicChain getDic_vertex_label_int() {
        return dic_vertex_label_int;
    }
    
    
}
