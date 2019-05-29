/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.grafo;

import ifes.bsi.tpa.taddic.TADDicChain;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author luizg
 */
public class TADGrafoD {
    private int [][] mat = null;
    private String nome;
    private int quantVertices = 0;
    private int quantEdges = 0;
    private int geraIDedge = 1;
    private int geraIDvertice = 0;
    
    TADDicChain dicLblVertex = new TADDicChain();
    TADDicChain dicLblEdge = new TADDicChain();
    
    private int primVertice = 0;
    private int ultiVertice = 0;
    //list of deleted vertex
    private LinkedList<Integer> lstEliminados = new LinkedList<Integer>();
    
    
    public TADGrafoD(String nome){
        mat = new int[16][16];
        this.nome = nome;
    }
    
    public void printmat(){
        for(int i = primVertice; i < ultiVertice; i++) {
            if(!lstEliminados.contains(i)) {
                for(int k = primVertice; k <= ultiVertice; k++) {
                    if(!lstEliminados.contains(i)) {
                        System.out.println(String.format("%d",mat[i][k]));
                    }
                }
                
            System.out.println();    
            }
        }
    }
    
    public void printgrafo() {
        ArrayList<String> al = new ArrayList<String>();
        String s, labelOrigem = "", labelDestino = "", labelEdge = "";
        
        Vertex v;
        Edge e;
        
        LinkedList<Object> lstVs = dicLblVertex.keys();
        LinkedList<Object> lstEs = dicLblEdge.keys();
        
        for(int i = primVertice; i <= ultiVertice; i++) {
            s = "";
            
            if(!lstEliminados.contains(i)) {
                for(int j = 0; j < lstVs.size(); j++) {
                    v = (Vertex)dicLblVertex.findElement(lstVs.get(j));
                    if(v.getId() == i) {
                        labelOrigem = v.getLabel();
                        break;
                    }
                }
                
                for(int k = primVertice; k <= ultiVertice; k++) {
                    if(!lstEliminados.contains(k)) {
                        for(int m = 0; m < lstVs.size(); m++) {
                            v = (Vertex)dicLblVertex.findElement(lstVs.get(m));
                            if(v.getId() == k) {
                                labelDestino = v.getLabel();
                                break;
                            }
                        }
                        
                        int idEdge = mat[i][k];
                        
                        if(idEdge != 0) {
                            for(int m = 0; m < lstEs.size(); m++) {
                                e = (Edge)dicLblEdge.findElement(lstEs.get(m));
                                if(e.getId() == idEdge) {
                                    labelDestino = e.getLabel();
                                    break;
                                }
                            }
                            
                            s = labelOrigem + "--" + labelEdge + "-->" + labelDestino;
                            al.add(s);
                        }
                    }
                }
            }
        } //for int i...
        
        //Island vertex treatment
        for(int i = 0; i < lstVs.size(); i++) {
            String lbl = (String)lstVs.get(i);
            if(degree(lbl) == 0) {
                al.add(lbl);
            }
        }
        
        Collections.sort(al);
        
        for(int n = 0; n < al.size(); n ++) {
            System.out.println(al.get(n));
        }
    }
    
    public int numVertices(){
        return quantVertices;
    }
    
    public int numEdges(){
        return quantEdges;
    }
    
    public String getNome() {
        return nome;
    }
    
    public boolean valido(int v){
        return((v >= primVertice) && (v<=ultiVertice) && !(lstEliminados.contains(v)));
    }
    
    public Edge getEdge(String origem, String destino) {
        Vertex vDestino = (Vertex)dicLblVertex.findElement(destino);
        if(dicLblVertex.NO_SUCH_KEY()) {
            return null;
        }
        
        Vertex vOrigem = (Vertex)dicLblVertex.findElement(origem);
        if(dicLblVertex.NO_SUCH_KEY()) {
            return null;
        }
        
        int idEdge = mat[vOrigem.getId()][vDestino.getId()];
        
        if(idEdge == 0) {
            return null;
        }
        else {
            LinkedList<Object> lstEdgeKeys = dicLblEdge.keys();
            
            for(int i = 0; i < lstEdgeKeys.size(); i++) {
                Edge e = (Edge)dicLblEdge.findElement(lstEdgeKeys.get(i));
                if(vOrigem.getId() == idEdge) {
                    return e;
                }
            }
        }
        
        return null;
    }
    
    public int[] endVertices(int e){ //???????????????????????????????????????????????????????????????????????????????????????????????
        for(int i = primVertice;i<=ultiVertice;i++){
            if(valido(i)){
                for(int k=primVertice;k<=ultiVertice;k++){
                    if(mat[i][k] == e){
                        int[] v = new int[2];
                        v[0] = i;
                        v[1] = k;
                        return v;
                    }
                }
            }
        }
        
        return null;
    }
    
    public Vertex opposite(String v, String e){
        Vertex objV = (Vertex)dicLblVertex.findElement(v);
        if(dicLblVertex.NO_SUCH_KEY()) {
            return null;
        }
        
        Edge objE = (Edge)dicLblEdge.findElement(e);
        if(dicLblEdge.NO_SUCH_KEY()) {
            return null;
        }
        
        for(int i = primVertice; i <= ultiVertice; i++) {
            if((!lstEliminados.contains(i)) && (mat[objV.getId()][i] == objE.getId())) {
                LinkedList<Object> lstVs = dicLblVertex.keys();
                
                for(int m = 0; m< lstVs.size(); m++) {
                    Vertex oU = (Vertex)dicLblVertex.findElement(lstVs.get(m));
                    if(oU.getId() == i) {
                        return oU;
                    }
                }
            }
        }
        
        return null;
    }
    
    // v, linha, i, coluna: todos as arestas saindo de v.
    public Integer outDegree(String label){
        Vertex v = (Vertex)dicLblVertex.findElement(label);
        if(dicLblVertex.NO_SUCH_KEY()) {
            return null;
        }
        else {
            int line = v.getId();
            int grade = 0;
            
            for(int i = primVertice; i <= ultiVertice; i++) {
                if((mat[line][i] != 0) && !lstEliminados.contains(i)) {
                    grade++;
                }
            }
            
            return grade;
        }
    }
    
    // v, coluna, i, linha: todos as arestas entrando de v.
    public Integer inDegree(String label) {
        Vertex v = (Vertex)dicLblVertex.findElement(label);
        if(dicLblVertex.NO_SUCH_KEY()) {
            return null;
        }
        else {
            int line = v.getId();
            int grade = 0;
            
            for(int i = primVertice; i <= ultiVertice; i++) {
                if((mat[i][line] != 0) && !lstEliminados.contains(i)) {
                    grade++;
                }
            }
            
            return grade;
        }
    }
    
    
    public Vertex insereVertex(String label, Object o){
        int idV = geraIDvertice++;
        
        if(idV > ultiVertice){
            ultiVertice = idV;
        }
        if(idV < primVertice){
            primVertice = idV;
        }
        
        
        Vertex v = (Vertex)dicLblVertex.findElement(label);
        if(dicLblVertex.NO_SUCH_KEY()){
            v = new Vertex(label, o);
            v.setId(idV);
            dicLblVertex.insertItem(label, v);
            quantVertices++;
        }
        else{
            v.setDado(o);
        }
        
        return v;
    }
    
    public Edge insertEdge(String origem, String destino, String label, Object o) {
        Vertex vOrigem = (Vertex)dicLblVertex.findElement(origem);
        if(dicLblVertex.NO_SUCH_KEY()) {
            return null;
        }
        
        Vertex vDestino = (Vertex)dicLblVertex.findElement(destino);
        if(dicLblVertex.NO_SUCH_KEY()) {
            return null;
        }
        
        Edge e = (Edge)dicLblEdge.findElement(label);
        
        //Inclusion of a new arch
        if(dicLblEdge.NO_SUCH_KEY()) {
            e = new Edge(label, o);
            e.setId(geraIDedge++);
            
            dicLblEdge.insertItem(label, e);
            
            mat[vOrigem.getId()][vDestino.getId()] = e.getId();
            quantEdges++;
        } //Update of a existent arch
        else {
            e.setDado(o);
        }
        
        return e; 
    }
    
    public Object removeEdge(String edge){
        Edge e  = (Edge)dicLblEdge.findElement(edge);
        if(dicLblEdge.NO_SUCH_KEY()) {
            return null;
        }
        
        int idE = e.getId();
        
        for(int i = primVertice; i <= ultiVertice; i++) {
            if(!lstEliminados.contains(i)) {
                for(int j = primVertice; j <= ultiVertice; j++) {
                    if(mat[i][j] == idE) {
                        mat[i][j] = 0;
                        quantEdges--;
                        dicLblEdge.removeElement(edge);
                        return e.getDado();
                    } 
                } 
            }      
        }
        
        /* Anomalia: o arco de label existe mas o seu id não se encontra */
        return null;
    }
    
    public Object removeVertex(String label) {
        Vertex v = (Vertex)dicLblVertex.findElement(label);
        if(dicLblVertex.NO_SUCH_KEY()) {
            return null;
        }
        
        int idV = v.getId();
            
        if(idV == primVertice) {
            for(int i = primVertice+1; i <= ultiVertice; i++) {
                if(!lstEliminados.contains(i)) {
                    primVertice = i;
                    break;
                }
            }
        }

        if(idV == ultiVertice) {
            for(int i = ultiVertice+1; i <= primVertice; i++) {
                if(!lstEliminados.contains(i)) {
                    ultiVertice = i;
                    break;
                }
            }
        }
        
        for(int i = primVertice; i <= ultiVertice; i++) {
            //Fill removed vertex line with 0's that means the vertex does not exist
            if(mat[idV][i] != 0) {
                quantEdges--;
                mat[idV][i] = 0;
            }
            
            //Fill removed vertex column with 0's that menas the vertex does not exist
            //Also prevent from decrementing quantEdges already decremented
            if((mat[i][idV] !=0) && (mat[idV][i] != mat[i][idV])) {
                quantEdges--;
                mat[i][idV] = 0;
            }
        }
        
        quantVertices--;
        lstEliminados.add(idV);
        return dicLblVertex.removeElement(label);   
    }
    
    public boolean areaAdjacent(String origem, String destino){
        Vertex vOrigem = (Vertex)dicLblVertex.findElement(origem);
        if(dicLblVertex.NO_SUCH_KEY()) {
            return false;
        }
        
        Vertex vDestino = (Vertex)dicLblVertex.findElement(destino);
        if(dicLblVertex.NO_SUCH_KEY()) {
            return false;
        }
        
        return mat[vOrigem.getId()][vDestino.getId()] != 0;
    }
    
    private int geraIDVertex() {
        int id;
        
        if(lstEliminados.size() == 0) {
            id = geraIDvertice++;
        }
        else {
            id = lstEliminados.get(0);
            lstEliminados.remove();
        }
        
        if(id < primVertice)
            primVertice = id;
        
        if(id > ultiVertice)
            ultiVertice = id;
        
        return id;
    }
    
    public Vertex insertVertex(String label, Object dado) {
        int idVertex = geraIDVertex();
        
        if(idVertex > ultiVertice) { 
            ultiVertice = idVertex;
        }
        
        if(idVertex < primVertice) {
            primVertice = idVertex;
        }
        
        Vertex v = (Vertex)dicLblVertex.findElement(label);
        
        //Including a new vertex
        if(dicLblVertex.NO_SUCH_KEY()) {
            v = new Vertex(label, dado);
            v.setId(idVertex);
            dicLblVertex.insertItem(label, v);
            quantVertices++;
        }
        else { //updating a existent vertex
            v.setDado(dado);
        }
        
        return v;
    }
    
    public Integer degree(String label) {
        Integer in = inDegree(label);
        Integer out = outDegree(label);
        
        if((in == null) || (out == null)) {
            return null;
        }
        else {
            return in + out;
        }
    }


    
    
           
}


///TO DO 
///public Integer degree(string str)
///public Edge insertEdge(String vu, String vv, String label, Object o)