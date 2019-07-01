/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.grafo.processadores;

import ifes.bsi.tpa.grafo.Vertex;
import java.util.LinkedList;

/**
 *
 * @author luizg
 */
public class DSDijkstra extends DataSet{
    private int custos[];
    private String antecessores[];
    private LinkedList<Vertex> percorridos;
    private int total;

    public DSDijkstra(int custos[], String antecessores[]){
        this.antecessores = antecessores;
        this.custos = custos;
    }
    
    @Override
    public LinkedList<Vertex> caminho(String origem, String destino) {
        return null;
    }

    @Override
    public int custo(String origem, String destino) {
       return 0;
    }
    
    public String[] getAntecessores(){
        return this.antecessores;
    }
    
    public int[] getCustos(){
        return this.custos;
    }
    
}
