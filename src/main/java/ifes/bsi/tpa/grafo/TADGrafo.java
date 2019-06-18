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
public class TADGrafo {
    private int [][] mat = null;
    private String nome;
    
    private int quantVertices = 0;
    private int quantEdges = 0;
    
    private int geraIDedge = 1;
    private int geraIDvertice = 0;
    
    TADDicChain dicLblVertex = new TADDicChain();
    TADDicChain dicLblEdge = new TADDicChain();
    
    private int primVertice = 0; //define o primeiro vertice (acredito q seja da matriz
    private int ultiVertice = 0; //define o ultimo vertice visitavel
    //list of deleted vertex
    private LinkedList<Integer> lstEliminados = new LinkedList<Integer>();
    
    
    public TADGrafo(String nome){
        mat = new int[16][16];
        this.nome = nome;
    }
    
    public TADGrafo(String nome, int i) { //gera a matriz relaçao de vertices
    	mat = new int[i][i];
        this.nome = nome;
    }
    
    public void printmat(){
        System.out.println("Matriz:");
        for(int i = primVertice; i <= ultiVertice; i++) {
            if(!lstEliminados.contains(i)) {
                for(int k = primVertice; k <= ultiVertice; k++) {
                    if(!lstEliminados.contains(i)) {
                        System.out.printf(String.format("%d ",mat[i][k]));
                    }
                }
                
            System.out.println();    
            }
        }
    }
    
    public void printgrafo() {
        System.out.println("Grafo: ");
        ArrayList<String> al = new ArrayList<String>();
        String s, labelOrigem = "", labelDestino = "", labelEdge = "";
        
        Vertex v;
        Edge e;
        
        LinkedList<Object> lstVs = dicLblVertex.keys();
        LinkedList<Object> lstEs = dicLblEdge.keys();
        
        for(int i = primVertice; i <= ultiVertice; i++) { //percorrendo a matriz de relação
            s = "";
            
            if(!lstEliminados.contains(i)) {
                
                
                for(int j = 0; j < lstVs.size(); j++) {
                    v = (Vertex)dicLblVertex.findElement(lstVs.get(j)); // ele percorre a lista que tem todos os dados do dicionario procurando se ele existe no dicionario que extraiu (isso vai ser 100% dos casos.. que codigo estranho)
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
                                    labelEdge = e.getLabel();
                                    break;
                                }
                            }
//                            System.out.println("destin:"+labelDestino);
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
    
    
    

    
    public Vertex[] vertices() {
		Vertex[] v = new Vertex[numVertices()];
		LinkedList<Object> vertex = dicLblVertex.elements();
		for (int i = 0; i < vertex.size(); i++) {
			v[i] = (Vertex)vertex.get(i);
		}
		return v;
	}
    
    public Edge[] edges() {
		Edge [] e = new Edge[numEdges()];
		int pos = 0;
		LinkedList<Object> edges = dicLblEdge.elements();
		for(int i = 0; i < edges.size(); i++) {
			Edge ed = (Edge)edges.get(i);
			if(!validaEdge(e,ed.getLabel())) {
				e[pos] = ed;
				pos++;
				
			}
				
			
		}
		return e;
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
            Edge e = this.intToEdge(idEdge);
            return e;
        }
    }
    
        public Vertex getVertex(String label) {
		Vertex vertex = (Vertex)dicLblVertex.findElement(label);
		if(dicLblVertex.NO_SUCH_KEY()) {
			return null;
		}
		else {
			return vertex;
		}
	}
    
    public Vertex intToVertex(int id) {
    	LinkedList<Object> lst = dicLblVertex.elements();
        for (Object object : lst) {
            Vertex v = (Vertex)object;
            if(id == v.getId())
                return v;
        }
    	return null;
    }
    
    
    public Edge intToEdge (int id) {
    	LinkedList<Object> lst = dicLblEdge.elements();
    	for(int i = 0; i< lst.size();i++) {
    		Edge e = (Edge)lst.get(i);
    		if(id == e.getId())
    			return e;
    	}
    	return null;
    }
    
     public Vertex[] endVertices(String labelE){ //retorna os vertices que estão ligados no edge (inicio e fim)
    	Edge oE = (Edge)dicLblEdge.findElement(labelE);
    	if(dicLblEdge.NO_SUCH_KEY())
    		return null;
    	int idE = oE.getId();
        for(int i = primVertice;i<=ultiVertice;i++){
            if(!(lstEliminados.contains(i))){
                for(int k=primVertice;k<=ultiVertice;k++){
                    if(mat[i][k] == idE){
                        Vertex[] v = new Vertex[2];
                        v[0] = intToVertex(i);
                        v[1] = intToVertex(k);
                        return v; 
                    }
                }
            }
        }
        
        return null;
    }
     
    
    
    public Vertex opposite(String v, String e){ //oposto do vertice tal no caminho tal (ou seja em qual vertice cai o destino indo por caminho x)
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
                Vertex oposto = this.intToVertex(i);
                return oposto;
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
            
            mat[vOrigem.getId()][vDestino.getId()] = e.getId(); //se for bidirecional é só adicionar o inverso também
            quantEdges++;
        } //Update of a existent edge
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
                        mat[i][j] = 0;  //remove apenas a primeira relação que existe este ID.
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
            
        if(idV == primVertice)
            for(int i = primVertice+1; i <= ultiVertice; i++)
                if(!lstEliminados.contains(i)){ 
                    primVertice = i;
                    break;
                }

        if(idV == ultiVertice)
            for(int i = ultiVertice-1; i >= primVertice; i--)
                if(!lstEliminados.contains(i)){
                    ultiVertice = i;
                    break;
                }
        
        for(int i = primVertice; i <= ultiVertice; i++) {
            //Fill removed vertex line with 0's that means the vertex does not exist
            if(mat[idV][i] != 0) {
                mat[idV][i] = 0;
                quantEdges--;
            }
            
            //Fill removed vertex column with 0's that means the vertex does not exist
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
        
        return mat[vOrigem.getId()][vDestino.getId()] != 0; //retorna o id do edge
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
        
        
        Vertex v = (Vertex)dicLblVertex.findElement(label);
        
        //Including a new vertex
        if(dicLblVertex.NO_SUCH_KEY()) {
            int idVertex = geraIDVertex();
            
            if(idVertex > ultiVertice) ultiVertice = idVertex;
            if(idVertex < primVertice) primVertice = idVertex;
            
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

    /*
 * M�TODOS ESPEC�FICOS PARA GRAFOS DIRIGIDOS
 */
    public Vertex destination (String labelE) {
    	Vertex [] vet = endVertices(labelE);
    	if(vet!= null)  // da pra socar um ternario ai so de zoas
    		return vet[1];
    	else
    		return null;
    }
    
    public Vertex origin (String labelE) {
    	Vertex [] vet = endVertices(labelE);
    	if(vet!= null)
    		return vet[0];
    	else
    		return null;
    }
    
    public LinkedList<Edge> inIncidentEdges (String labelV){ //todas as edges entrando
    	Vertex v = (Vertex)dicLblVertex.findElement(labelV);
    	if(dicLblVertex.NO_SUCH_KEY())
    		return null;
    	LinkedList<Edge> lst = new LinkedList <Edge>();
    	int id = v.getId();
    	
    	for(int k = primVertice; k<= ultiVertice; k++)
    		if((!lstEliminados.contains(k) && (mat[id][k] != 0)))
    			lst.add(intToEdge(mat[id][k]));
    	return lst;
    }
    
    public LinkedList<Edge> outIncidentEdges (String labelV){
    	Vertex v = (Vertex)dicLblVertex.findElement(labelV);
    	if(dicLblVertex.NO_SUCH_KEY())
    		return null;
    	LinkedList<Edge> lst = new LinkedList <Edge>();
    	int id = v.getId();
    	
    	for(int i = primVertice; i<= ultiVertice; i++)
    		if((!lstEliminados.contains(i) && (mat[i][id] != 0)))
    			lst.add(intToEdge(mat[i][id]));
    	return lst;
    }
    
    
    public LinkedList<Vertex> outAdjacentVertices(String labelV){ //retorna todos os vertices adjacentes a escolhido (collection) entrando
    	Vertex v = (Vertex)dicLblVertex.findElement(labelV);
    	if(dicLblVertex.NO_SUCH_KEY())
    		return null;
    	LinkedList<Vertex> lst = new LinkedList<Vertex>();
    	int id = v.getId();
    	for(int k = primVertice; k<= ultiVertice; k++)
    		if(!lstEliminados.contains(k) && (mat[id][k] != 0))
    			lst.add(intToVertex(k));
    	return lst;
    }
    
    
    
    public LinkedList<Vertex> inAdjacentVertices(String labelV){  //retorna todos os vertices adjacentes a escolhido (collection) entrando
    	Vertex v = (Vertex)dicLblVertex.findElement(labelV);
    	if(dicLblVertex.NO_SUCH_KEY()) {
    		//System.out.println("OUT OF BOUND");
    		return null;
    	}
    	LinkedList<Vertex> lst = new LinkedList<Vertex>();
    	int id = v.getId();
    	for(int k = primVertice; k<= ultiVertice; k++)
    		if(!lstEliminados.contains(k) && (mat[id][k] != 0))  //se não tiver sido removido e não for 0 na matriz relação pega o numero e converte em vertex
    			lst.add(intToVertex(k));
    	return lst;
    }
    
   
    public LinkedList<Edge> incidentEdges(String labelV){ //retorna a lista de todas as Edges conectadas (entrando e saindo)
    	LinkedList<Edge> lst = inIncidentEdges(labelV);
    	lst.addAll(outIncidentEdges(labelV));
    	return lst;
    }
    
    public LinkedList<Vertex> adjacentVertices(String labelV){ //retorna a lista de todas as vertices conectadas (entrando e saindo)
    	LinkedList<Vertex> lst = inAdjacentVertices(labelV);
    	lst.addAll(outAdjacentVertices(labelV));
    	return lst;
    	
    }
    //PRIVATE
	   
	private boolean validaEdge(Edge[] vetor, String str) {
		for (Edge i : vetor) {
			if(i != null && i.getLabel().equals(str)) {
				return true;
			}
		}
		return false;
	}
    
    
           
}


///TO DO 
///public Integer degree(string str)
///public Edge insertEdge(String vu, String vv, String label, Object o)