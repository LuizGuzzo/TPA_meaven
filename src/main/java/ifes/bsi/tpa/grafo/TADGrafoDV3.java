/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.grafo;

import _my_tools.ArquivoTxt;
import ifes.bsi.tpa.taddic.TADDicChain;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luizg
 */
public class TADGrafoDV3 {
    private int [][] mat = null;
    private String nome;
    
    private int quantVertices = 0;
    private int quantEdges = 0;
    
    private int geraIDedge = 1;
    private int geraIDvertice = 0;
    
    TADDicChain dicLblVertex = new TADDicChain();
    TADDicChain dicLblEdge = new TADDicChain();
    
    private int primVertice = 0; //define o primeiro vertice 
    private int ultiVertice = 0; //define o ultimo vertice visitavel
    //list of deleted vertex
    private LinkedList<Integer> lstEliminados = new LinkedList<Integer>();
    
    
    public TADGrafoDV3(String nome){
        mat = new int[16][16];
        this.nome = nome;
    }
    
    public TADGrafoDV3(String nome, int i) { //gera a matriz relaçao de vertices
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
                            s = labelOrigem + "--" + labelEdge + "-->" + labelDestino;
                            al.add(s);
                        }
                    }
                }
            }
        } //for int i...
        
        //Vertex Ilha tratamento
        for (Object Vs : lstVs) {
            String lbl = (String)Vs;
            if(degree(lbl) == 0) {
                al.add(lbl);
            }
        }
        Collections.sort(al);
        
        for (String a : al) {
            System.out.println(a);
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
    
    public Edge getEdge(String labelE){
        return (Edge)dicLblEdge.findElement(labelE);
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
    
    
    public LinkedList<Vertex> vertices() {
        LinkedList<Vertex> v = new LinkedList<Vertex>();
        LinkedList<Object> objV = dicLblVertex.elements();
        for (Object object : objV) {
            v.add((Vertex) object);
        }
        return v;
    }
    
    public LinkedList<Edge> edges() {
        LinkedList<Edge> e = new LinkedList<Edge>();
        int pos = 0;
        LinkedList<Object> objE = dicLblEdge.elements();
        for (Object object : objE) {
            e.add((Edge)object);
        }
        return e;
    }
    
    
    public TADGrafoDV3 clone(){
        TADGrafoDV3 clone = new TADGrafoDV3("g_copia", this.numVertices());
        LinkedList<Vertex> vertices = this.vertices();
        for (Vertex vertice : vertices) {
            clone.insertVertex(vertice.getLabel(), vertice.getDado());
        }
        for(int i = primVertice; i <= ultiVertice; i++){
            for(int j = primVertice; j <= ultiVertice; j++){
                if(mat[i][j] != 0 && !lstEliminados.contains(j)) {
                    String origem = this.intToVertex(i).getLabel();
                    String destino = this.intToVertex(j).getLabel();
                    Edge edge = this.intToEdge(mat[i][j]);
                    clone.insertEdge(origem, destino, edge.getLabel(), edge.getDado());
                }
            }
        }
        return clone;
    }

    public boolean equals(TADGrafoDV3 outrog){
        if(this.numVertices() == outrog.numVertices() && this.numEdges() == outrog.numEdges()){
            LinkedList<Edge> otherEdges = outrog.edges();
            LinkedList<Vertex> otherVertexs = outrog.vertices();
            for (Vertex vertex : otherVertexs) {
                Vertex myVertex = (Vertex)this.dicLblVertex.findElement(vertex.getLabel());
                if(dicLblVertex.NO_SUCH_KEY()){
                    return false;
                }
                if(!vertex.equals(myVertex)){
                    return false;
                }
            }
            for (Edge edge : otherEdges) {
                Edge myEdge = (Edge) this.dicLblEdge.findElement(edge.getLabel());
                if(dicLblEdge.NO_SUCH_KEY()){
                    return false;
                }
                if(!edge.equals(myEdge)){
                    return false;
                }
            }
        }else{
            return false;
        }
        return true;
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
        for (Object oe : lst) {
            Edge e = (Edge)oe;
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
    
    public Integer outDegree(String label){     // quantidade de arestas saindo
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
    
    public Integer inDegree(String label) {     //quantidade de arestas entrando
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
        if(dicLblEdge.NO_SUCH_KEY()) {
            e = new Edge(label, o);
            e.setId(geraIDedge++);
            dicLblEdge.insertItem(label, e);
            mat[vOrigem.getId()][vDestino.getId()] = e.getId(); //se for bidirecional é só adicionar o inverso também
            quantEdges++;
        }
        else {
            e.setDado(o);
        }
        return e; 
    }
    
    //com custo no edge
    public Edge insertEdge(String origem, String destino, String label, Object o, int cost){
        Vertex vOrigem = (Vertex)dicLblVertex.findElement(origem);
        if(this.dicLblVertex.NO_SUCH_KEY()){
            return null;
        }
        Vertex vDestino = (Vertex)dicLblVertex.findElement(destino);
        if(this.dicLblVertex.NO_SUCH_KEY()){
            return null;
        }
        
        Edge e = (Edge)dicLblEdge.findElement(label);
        if(dicLblEdge.NO_SUCH_KEY()){
            e = new Edge(label, o);
            e.setId(this.geraIDedge++);
            e.setCost(cost);
            dicLblEdge.insertItem(label, e);
            mat[vOrigem.getId()][vDestino.getId()] = e.getId();
            quantEdges++;
        }
        else{
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
            //escreve tudo com zero pra zera
            if(mat[idV][i] != 0) {
                mat[idV][i] = 0;
                quantEdges--;
            }
            //escreve tudo com zero pra zera
            if((mat[i][idV] !=0) && (mat[idV][i] != mat[i][idV])) {
                quantEdges--;
                mat[i][idV] = 0;
            }
        }
        quantVertices--;
        lstEliminados.add(idV);
        return dicLblVertex.removeElement(label);   
    }
    
    public boolean areAdjacent(String origem, String destino){
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
        if(dicLblVertex.NO_SUCH_KEY()) {
            int idVertex = geraIDVertex();
            v = new Vertex(label, dado);
            v.setId(idVertex);
            dicLblVertex.insertItem(label, v);
            quantVertices++;
        }
        else {
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


    public Vertex destination (String labelE) {
    	Vertex [] vet = endVertices(labelE);
    	if(vet!= null)
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
    		if((!lstEliminados.contains(k) && (mat[k][id] != 0)))
    			lst.add(intToEdge(mat[k][id]));
    	return lst;
    }
    
    public LinkedList<Edge> outIncidentEdges (String labelV){
    	Vertex v = (Vertex)dicLblVertex.findElement(labelV);
    	if(dicLblVertex.NO_SUCH_KEY())
            return null;
    	LinkedList<Edge> lst = new LinkedList <Edge>();
    	int id = v.getId();
    	for(int i = primVertice; i<= ultiVertice; i++)
            if((!lstEliminados.contains(i) && (mat[id][i] != 0)))
                lst.add(intToEdge(mat[id][i]));
    	return lst;
    }
    
    
    public LinkedList<Vertex> outAdjacenteVertices(String labelV){ //retorna todos os vertices adjacentes a escolhido (collection) entrando
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
    
    public LinkedList<Vertex> inAdjacenteVertices(String labelV){  //retorna todos os vertices adjacentes a escolhido (collection) entrando
    	Vertex v = (Vertex)dicLblVertex.findElement(labelV);
    	if(dicLblVertex.NO_SUCH_KEY()) {
            return null;
    	}
    	LinkedList<Vertex> lst = new LinkedList<Vertex>();
    	int id = v.getId();
    	for(int k = primVertice; k<= ultiVertice; k++)
            if(!lstEliminados.contains(k) && (mat[k][id] != 0))  //se não tiver sido removido e não for 0 na matriz relação pega o numero e converte em vertex
                lst.add(intToVertex(k));
    	return lst;
    }
   
    public LinkedList<Edge> incidentEdges(String labelV){ //retorna a lista de todas as Edges conectadas (entrando e saindo)
    	LinkedList<Edge> lst = inIncidentEdges(labelV);
    	lst.addAll(outIncidentEdges(labelV));
    	return lst;
    }
    
    public LinkedList<Vertex> adjacentVertices(String labelV){ //retorna a lista de todas as vertices conectadas (entrando e saindo)
    	LinkedList<Vertex> lst = this.inAdjacenteVertices(labelV);
    	lst.addAll(outAdjacenteVertices(labelV));
    	return lst;
    	
    }

    public void csvToTGF(String pathArq, String nomearq, String output) throws FileNotFoundException, IOException{
        TADDicChain dicElements = new TADDicChain();
        TADDicChain dicRelationships = new TADDicChain();
        Integer geraID=0;
        BufferedReader buffRead = new BufferedReader(new FileReader(pathArq+nomearq));
        String line = "";
        String filme = "";
        List<Integer> atoresId = new ArrayList<>();
        while(true) {  
            line = buffRead.readLine(); 
            if(line == null) {
                dicRelationships.insertItem(filme, atoresId);
                buffRead.close();
                break;
            }
            String[] vet = line.split("/");    //Separação de valores
            for (int i = 0; i < vet.length; i++){ 
                dicElements.findElement(vet[i]);
                if(dicElements.NO_SUCH_KEY()){
                    geraID++;
                    dicElements.insertItem(vet[i], geraID); // Insere item de chave filme/ator e valor id;
                }
                if(i == 0){ //Se for filme...
                    if(!atoresId.isEmpty()){
                        dicRelationships.insertItem(filme, atoresId);
                        atoresId = new ArrayList<>();
                    }
                    filme = vet[i];
                }else{
                    atoresId.add((Integer) dicElements.findElement(vet[i]));
                }
            }                
        }
        BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(pathArq+output, true));
        LinkedList<Object> keys;
        keys = dicElements.keys();
        for (Object object : keys) {
            line = dicElements.findElement(object).toString()+" "+ object+"\n";
            bufferWriter.write(line);
        }
        bufferWriter.write("# \n");
        keys = dicRelationships.keys();
        for (Object object : keys) {
            List<Integer> lista = (List<Integer>) dicRelationships.findElement(object);
            for (Integer cell : lista) {
                line = dicElements.findElement(object).toString()+" "+cell.toString()+"\n";
                bufferWriter.write(line);
            }
        }
        bufferWriter.close();
    }
    
    public static TADGrafoDV3 carregaTGF(String fileName, int matSize){
        TADGrafoDV3 g = new TADGrafoDV3(fileName, matSize);
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader arq = new BufferedReader(fileReader);
            String linha = arq.readLine();
            boolean arestas = false;
            while(linha != null){
                if(linha.contains("#")){
                    arestas = true;
                    linha = arq.readLine();
                }
                String[] l = linha.split(" ");
                if(!arestas){    
                    String value = "";
                    for(int i=1;i<l.length;i++){
                        value += " " + l[i];
                    }
                    g.insertVertex(l[0], value);
                }
                else{
                    String edgeLabel = l[0]+'-'+l[1];
                    g.insertEdge(l[0], l[1], edgeLabel, "");
                }
                linha = arq.readLine();
            }
            arq.close();
            return g;
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TADGrafoDV3.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TADGrafoDV3.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}

