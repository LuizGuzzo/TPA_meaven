/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.grafo;

import java.util.LinkedList;

/**
 *
 * @author luizg
 */
public class ProcessaGrafo {
    TADGrafo grafo;
    public  ProcessaGrafo(TADGrafo g) {
     //Armazenar o grafo g
     grafo = g;

    }

//	Pesquisa em profundidade (Depth-first search – DFS)
    public LinkedList<Vertex> dfs(String labelV){
        LinkedList<Vertex> output = new LinkedList<Vertex>();
        LinkedList<Vertex> stack = new LinkedList<Vertex>();

        Vertex pointed = grafo.getVertex(labelV);
        stack.add(pointed);

        if(pointed == null) return null; // NO_SUCH_KEY seria bem vindo aqui

        while(stack.size() != 0){ //enquanto a pilha não acabar
            pointed = stack.pop(); //retira o valor da pilha
            if(!output.contains(pointed)) //verifica se já foi visitado
                output.add(pointed); //salva como visitado

            LinkedList<Vertex> vizinhos = grafo.adjacentVertices(pointed.getLabel()); //recebe todos os vizinhos do vertice
            if (vizinhos.size()!=0){ //se tiver vizinho
                for (Vertex vizinho : vizinhos){  //percorre todos os vizinhos
                    if(!output.contains(vizinho)){  //verifica se ja foram visitados
                        stack.addFirst(vizinho);  //adiciona na pilha para serem visitados0
                    }
                }
            }
        }

        return output;
    }

//        Pesquisa em largura (Breadth-first search – BFS)
    public LinkedList<Vertex> bfs(String labelV){
        LinkedList<Vertex> output = new LinkedList<Vertex>();
        LinkedList<Vertex> queue = new LinkedList<Vertex>();

        Vertex pointed = grafo.getVertex(labelV);
        queue.add(pointed); //adiciona na fila o primeiro dado de pesquisa
        
        if (pointed == null) return null;

        while(queue.size() != 0){
            pointed = queue.remove(); //remove o dado na fila
            LinkedList<Vertex> vizinhos = grafo.adjacentVertices(pointed.getLabel()); //pega os vizinhos
            if(!output.contains(pointed)) //e não estiverem ja sido verificados
                    output.add(pointed); // adiciona o apontado
            if(vizinhos.size() !=0){ //se tiver vizinhos
                for (Vertex vizinho : vizinhos)  //para cada vizinho
                    if(!output.contains(vizinho)) //que não tenha sido verificado
                        queue.add(vizinho); //adiciona na fila o vizinho
            }
        }
        return output;
    }


        
    public void printAnswer(LinkedList<Vertex> anwser){
        for (Vertex vertex : anwser) {
            System.out.printf(vertex.getLabel()+"||");
        }
    }
}
