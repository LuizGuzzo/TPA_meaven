/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.grafo;

import java.util.LinkedList;
import java.util.LinkedList;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;


/**
 *
 * @author luizg
 */
public class ToGStream {

    
    private Graph grafo;
    private boolean dirigido;
    private boolean vertexVisivel;
    private boolean edgeVisivel;
 
    
    public ToGStream(TADGrafoDV3 g,boolean vertexVisivel, boolean edgeVisivel, boolean dirigido) {
		
        this.grafo = new SingleGraph(g.getNome());

        if(vertexVisivel) {
            LinkedList<Vertex> vertices = g.vertices();
            for (Vertex v : vertices) {
                Node no = this.grafo.addNode(v.getLabel());
                no.addAttribute("ui.label", v.getLabel());
            }
            if(edgeVisivel) {
                LinkedList<Edge> edges = g.edges();
                for (Edge e : edges) {
                    Vertex[] endV = g.endVertices(e.getLabel());
                    if(dirigido) {
                        org.graphstream.graph.Edge edge = this.grafo.addEdge(e.getLabel(), endV[0].getLabel(),endV[1].getLabel());
                        edge.addAttribute("ui.label", e.getLabel());
                    }
                    else {
                        org.graphstream.graph.Edge edge = this.grafo.addEdge(e.getLabel(), endV[0].getLabel(),endV[1].getLabel());
                        edge.addAttribute("ui.label", e.getLabel());
                    }
                }
            }
        }	
    }
    
    public void exibe() {	
        
        this.grafo.display();
    }
}
