/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.grafo;

import java.util.Objects;

/**
 *
 * @author luizg
 */
public class Edge {
    private int id;
    private String label;
    private Object dado;
    
    public Edge(String label, Object dado){
        this.label = label;
        this.dado = dado;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getDado() {
        return dado;
    }

    public void setDado(Object dado) {
        this.dado = dado;
    }

    public boolean equals(Edge e) {
        if(this.label == e.getLabel())
            if(this.dado == e.getDado())
                return true;
        return false;
    }
    
    
    
}