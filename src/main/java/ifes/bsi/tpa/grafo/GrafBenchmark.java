package ifes.bsi.tpa.grafo;

import java.util.LinkedList;

import _my_tools.Comemora;
import _my_tools.CoresConsole;
import java.io.IOException;

/**
 * @author ernani
 * ref: https://www.mkyong.com/java/find-out-your-java-heap-memory-size/
 * 
 * Instruções para execução:
 * No terminal, na pasta bin, executar: java -cp ./ ifes.bsi.tpa.grafo.GrafBenchmark
 * 
 * Para visualizar o grafo com GraphStream, desabilitar os comentários que ativam a exibição.
 * Os comentários estão no final do método main. Uma vez desabilitados, use a seguinte linha de chamada no
 * console:
 * java -cp ./:../../../GraphStream1.3/gs-core-1.3/gs-core-1.3.jar:../../../GraphStream1.3/gs-ui-1.3/gs-ui-1.3.jar ifes.bsi.tpa.grafo.GrafBenchmark
 * 
 * Substitua ../../.. pelo caminho até a pasta GraphStream (a artir de bin) no seu computador.
 * 
 */
public class GrafBenchmark {
	public static void main(String args[]){
		String pasta_tgf = "C:/Users/luizg/Documents/NetBeansProjects/TPA_meaven/src/main/java/Base-Grafos/";
		String nome_grafo_tgf = "tgfmovies15.txt";
		String arq_grafo = pasta_tgf + nome_grafo_tgf;
		TADGrafoDV3 g = TADGrafoDV3.carregaTGF(arq_grafo, 12000);	
		
		long tempoInicial = System.currentTimeMillis();
		
		System.out.println("Benchmark do grafo " + g.getNome() + "\n");
		System.out.println("Quantidade de vértices: " + g.numVertices());
		System.out.println("Quantidade de arestas: " + g.numEdges());
		
		System.out.print("\nTestando os serviços de vértices ..");
		LinkedList<Vertex> lstVertices = g.vertices();
		
		for(int i=0; i < lstVertices.size(); i++) {
			Vertex v = g.getVertex(lstVertices.get(i).getLabel());
			if(v == null) {
				System.err.println("\nErro! problemas com g.getVertex(<label vértice>);");
				System.exit(1);
			}		
			
			Integer nO = g.outDegree(lstVertices.get(i).getLabel());
			if(nO == null) {
				System.err.println("\nErro! problemas com g.outDegree(<label vértice>);");
				System.exit(1);
			}
			
			Integer nI = g.inDegree(lstVertices.get(i).getLabel());
			if(nI == null) {
				System.err.println("\nErro! abortando benchmark: problemas com g.inDegree(<label vértice>);");
				System.exit(1);
			}
			
			Integer n = g.degree(lstVertices.get(i).getLabel());
			if((n == null) || (n != (nI + nO))) {
				System.err.println("\nErro! abortando benchmark: problemas com g.degree(<label vértice>);");
				System.exit(1);
			}
			
			LinkedList<Edge> lstEs = g.outIncidentEdges(lstVertices.get(i).getLabel());
			if(lstEs == null) {
				System.err.println("\nErro! abortando benchmark: problemas com g.outIncidentEdges(<label vértice>);");
				System.exit(1);
			}
			
			lstEs = g.inIncidentEdges(lstVertices.get(i).getLabel());
			if(lstEs == null) {
				System.err.println("\nErro! abortando benchmark: problemas com g.inIncidentEdges(<label vértice>);");
				System.exit(1);
			}
			
			lstEs = g.incidentEdges(lstVertices.get(i).getLabel());
			if(lstEs == null) {
				System.err.println("\nErro! abortando benchmark: problemas com  g.incidentEdges(<label vértice>);");
				System.exit(1);
			}
			
			LinkedList<Vertex> lstVs = g.outAdjacenteVertices(lstVertices.get(i).getLabel());
			if(lstVs == null) {
				System.err.println("\nErro! abortando benchmark: problemas com g.outAdjacenteVertices(<label vértice>);");
				System.exit(1);
			}
			
			lstVs = g.inAdjacenteVertices(lstVertices.get(i).getLabel());
			if(lstVs == null) {
				System.err.println("\nErro! abortando benchmark: problemas com g.inAdjacenteVertices(<label vértice>);");
				System.exit(1);
			}
			
			lstVs = g.adjacentVertices(lstVertices.get(i).getLabel());
			if(lstVs == null) {
				System.err.println("\nErro! abortando benchmark: problemas com g.adjacentVertices(<label vértice>);");
				System.exit(1);
			}
		}
		
		System.out.println("concluído, ok.");
		System.out.println("Os seguintes serviços de grafo e vértices foram testados:");
		System.out.println("	public TADGrafoDV3(String nome, int totalNos)");
		System.out.println("	public int numVertices() ");
		System.out.println("	public int numEdges()");
		System.out.println("	public String getNome()");
		System.out.println("	public LinkedList<Vertex> vertices()");
		System.out.println("	public Vertex getVertex(String lbl)");
		System.out.println("	private Vertex intToVertex(int id)");
		System.out.println("	private Edge intToEdge(int id)");
		System.out.println("	public Integer outDegree(String lbl)");
		System.out.println("	public Integer inDegree(String lbl)");
		System.out.println("	public Integer degree(String lbl)");
		System.out.println("	public LinkedList<Edge> outIncidentEdges(String lbl)");
		System.out.println("	public LinkedList<Edge> inIncidentEdges(String lbl)");
		System.out.println("	public LinkedList<Edge> incidentEdges(String lbl)");
		System.out.println("	public LinkedList<Vertex> outAdjacenteVertices(String lbl)");
		System.out.println("	public LinkedList<Vertex> inAdjacenteVertices(String lbl)");
		System.out.println("	public LinkedList<Vertex> adjacentVertices(String lbl)");
		System.out.println("	public Vertex insertVertex(String lbl, Object o)");
		System.out.println("	public static TADGrafoDV3 carregaTGF(String nome_arq_tgf, int quant_vertices)");
		
		System.out.print("\nTestando os serviços de aresta ..");
		LinkedList<Edge> lstEdges = g.edges();

		for(int i=0; i < lstEdges.size(); i++) {
			Edge oE = g.getEdge(lstEdges.get(i).getLabel());
			if(oE == null) {
				System.err.println("\nErro! abortando benchmark: problemas com g.getEdge(<label aresta>);");
				System.exit(1);
			}
			
			Vertex[] v = g.endVertices(lstEdges.get(i).getLabel());
			if(v.length != 2) {
				System.err.println("\nErro! abortando benchmark: problemas com g.endVertices(<label aresta>);");
				System.exit(1);
			}
			
			Vertex vd = g.destination(lstEdges.get(i).getLabel());
			Vertex vo = g.origin(lstEdges.get(i).getLabel());
			if((vd == null) || (vo == null)) {
				System.err.println("\nErro! abortando benchmark: problemas com g.destination(<label aresta>);");
				System.exit(1);
			}
		}
		System.out.println("concluído, ok.");	
		System.out.println("Os seguintes serviços de aresta foram testados:");
		System.out.println("	public Edge getEdge(String lbl)");
		System.out.println("	public Vertex[] endVertices(String lbl)");
		System.out.println("	public Vertex destination(String lbl)");
		System.out.println("	public Vertex origin(String lbl)");		

		System.out.print("\nTestando os serviços de relação entre vértices ..");
		int cont_edges = 0;
		for(int i=0; i < lstVertices.size(); i++) {
			for(int k=0; k < lstVertices.size(); k++) {
				if(k != i) {
					Edge oE = g.getEdge(lstVertices.get(i).getLabel(),lstVertices.get(k).getLabel());
	
					if(oE != null) {
						if(!g.areAdjacent(lstVertices.get(i).getLabel(),lstVertices.get(k).getLabel())) {
							System.err.println("\nErro! abortando benchmark: problemas com g.getEdge(<label vértice u>,<label vértice v>) ou com g.areAdjacent(<label vertex u>,<label vertex v>);");
							System.exit(1);
						}
						else
							cont_edges++;
					}/* if(oE !=.. */				
				} /* if(k != i) { */
			} /* for(int k.. */			
		} /* for(int i.. */	
		
		if(cont_edges != lstEdges.size()) {
			System.out.println("problemas!\n");
			System.err.println("	getEdge(<label vertex u>,<label vertex v>) detecta uma quantidade inconsistnte de arestas.");
		}
		else
			System.out.println("concluído, ok.");	
		
		System.out.println("Os seguintes serviços de relação entre vértices foram testados:");
		System.out.println("	public Edge getEdge(String u, String v)");
		System.out.println("	public boolean areAdjacent(String u, String v)");		
		
		System.out.print("\nTestando os serviços de relação entre vértices e arestas ..");
		for(int i=0; i < lstVertices.size(); i++) {
			for(int k=0; k < lstEdges.size(); k++) {
				g.opposite(lstVertices.get(i).getLabel(), lstEdges.get(k).getLabel());
			} /* for(int k.. */			
		} /* for(int i.. */	
		System.out.println("concluído, ok.");
		
		System.out.println("Os seguintes serviços de relação entre vértices e arestas foram testados:");
		System.out.println("	public Vertex opposite(String v, String e)");
		
		/* Testando clonagem e igualdade. */
		System.out.print("\nTestando clonagem..");
		TADGrafoDV3 irmao = g.clone();
		if(irmao == null) {
			System.err.println("\nErro! abortando benchmark: problemas com g.clone(<tad grafo>);");
			System.exit(1);
		}
		System.out.println("concluído, ok.");
		
		System.out.print("Testando igualdade..");
		boolean twins = g.equals(irmao);
		if(!twins) {
			System.err.println("\nErro! abortando benchmark: problemas com g.equals(<tad grafo>);");
			System.exit(1);
		}
		System.out.println("concluído, ok.");
		
		System.out.println("Os seguintes serviços de clonagem e igualdade foram testados:");
		System.out.println("	public boolean equals(TADGrafoDV3 oOutroG)");
		System.out.println("	public TADGrafoDV3 clone()");

		System.out.print("\nTestando a remoção de vértices..");
		int bkpV = irmao.numVertices();
		for(int i=0; i < lstVertices.size();i++) {
			irmao.removeVertex(lstVertices.get(i).getLabel());
		}
		int bkpE = irmao.numEdges();
		if((irmao.numVertices() == 0) && (irmao.numEdges() == 0)) {
			System.out.println("concluído, ok.");
			System.out.println(bkpV + " vértices removidos.");
			System.out.println(bkpE + " arestas mantidas.");
			System.out.println("Os seguintes serviços de remoção de vértices foram testados:");
			System.out.println("	public Object removeVertex(String lbl)");			
		}
		else {
			System.err.println("\nErro! abortando benchmark: problemas com g.removeVertex(<label vértice u>");
			System.exit(1);
		}
		
		System.out.print("\nTestando a remoção de arestas/arcos..");
		irmao = g.clone();
		bkpE = irmao.numEdges();
		for(int i=0; i < lstEdges.size();i++) {
			irmao.removeEdge(lstEdges.get(i).getLabel());
		}
		bkpV = irmao.numVertices();
		if((irmao.numVertices() == bkpV) && (irmao.numEdges() == 0)) {
			System.out.println("concluído, ok.");
			System.out.println(bkpE + " arestas removidas.");
			System.out.println(bkpV + " vértices mantidos.");
			System.out.println("Os seguintes serviços de remoção de vértices foram testados:");
			System.out.println("	public Object removeVertex(String lbl)");			
		}
		else {
			System.err.println("\nErro! abortando benchmark: problemas com g.removeEdge(<label aresta a ser removida>");
			System.exit(1);
		}
		
		long tempoFinal = System.currentTimeMillis();
		
		long duracao = tempoFinal - tempoInicial;
		
		System.out.println(CoresConsole.BLUE_BOLD);
		System.out.println(String.format("Benchmark finalizado com sucesso em %.2f minutos.",(duracao/1000.0)/60.0));
		System.out.println(CoresConsole.RESET);
		
		System.out.println(CoresConsole.GREEN_BOLD);
		System.out.println(Comemora.msgSuc2);
		System.out.println(CoresConsole.RESET);

		System.out.print("Gerando GraphStream..");
		ToGStream togs = new ToGStream(g,true,true,true);
		System.out.println("concluído!");		
		togs.exibe();
	} /* fim de main */

} /* fim da classe GrafBenchmark */
