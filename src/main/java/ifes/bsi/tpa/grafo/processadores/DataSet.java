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
public abstract class DataSet {
    public abstract LinkedList<Vertex> caminho(String origem, String destino);
    public abstract int custo(String origem, String destino);
}
