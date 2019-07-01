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
public class DSFloydW extends DataSet{

    private int [][] custos;

    public DSFloydW (int [][] custos) {
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
    
}
