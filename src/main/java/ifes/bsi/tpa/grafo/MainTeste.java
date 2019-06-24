///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package ifes.bsi.tpa.grafo;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.LinkedList;
//
///**
// *
// * @author luizg
// */
//public class MainTeste {
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) throws FileNotFoundException, IOException {
//        
//        
//        String path = "C:\\Users\\luizg\\Documents\\NetBeansProjects\\TPA_meaven\\src\\main\\java\\ifes\\bsi\\tpa\\grafo\\";
//        String arq = "movies.txt";
//        
//        TADGrafoDV3 g = new TADGrafoDV3("MERASMUS IS HERE");
//        g.csvToTGF(path, arq, "saida.txt");
//        g
//        
//        
//
//////                g.insertVertex("A", 0);
//////                g.insertVertex("B", 1);
//////                g.insertVertex("C", 2);
//////                g.insertVertex("D", 3);
//////                
//////                g.insertEdge("A", "C", "1", 1);
//////                g.insertEdge("A", "D", "2", 2);
//////                g.insertEdge("B", "A", "3", 3);
//////                g.insertEdge("B", "C", "4", 4);
//////                g.insertEdge("C", "D", "5", 5);
//////                g.insertEdge("D", "A", "6", 6);
//////                g.insertEdge("D", "B", "7", 7);
////
////                g.insertVertex("A", 0);
////                g.insertVertex("B", 1);
////                g.insertVertex("C", 2);
////                g.insertVertex("D", 3);
////                g.insertVertex("E", 0);
////                g.insertVertex("F", 1);
////                g.insertVertex("G", 2);
////                g.insertVertex("H", 3);
////
////                g.insertEdge("A", "B", "1", 0);
////                g.insertEdge("A", "D", "2", 0);
////                g.insertEdge("A", "E", "3", 0);
////                g.insertEdge("B", "C", "4", 0);
////                g.insertEdge("E", "F", "5", 0);
////                g.insertEdge("F", "G", "6", 0);
////                g.insertEdge("G", "A", "7", 0);
////                g.insertEdge("H", "C", "8", 0);
////                g.insertEdge("H", "D", "9", 0);
////                g.insertEdge("H", "F", "10", 0);
////		
////		g.printgrafo();
////                g.printmat();
////                
//////                g.removeVertex("B");
//////                
//////		g.printgrafo();
//////                g.printmat();
////		
////                System.out.println("");
////                
////		ProcessaGrafo md = new ProcessaGrafo(g);
////                LinkedList<Vertex> resultado = new LinkedList<Vertex>();
////                
////		System.out.println("Grafo em busca de profundidade: ");
////                resultado = md.dfs("H");
////		md.printAnswer(resultado);
////                
////		System.out.println();
////
////                System.out.println("Grafo em busca de nivel: ");
////		resultado = md.bfs("H");
////                md.printAnswer(resultado);
////                System.out.println("");
////                System.out.println("Edge value:"+ g.getEdge("H", "D").getLabel());
////                System.out.println("oposto:"+ g.opposite("G", "7").getLabel());
////                
////		/*
////		ToGStream show = new ToGStream(g);
////		String css = "graph { fill-color: red; }";
////		String teste = null;
////		show.exibe(teste);
////		*/
////		
////        
//////                LinkedList vertices = new LinkedList();
//////                LinkedList arestas = new LinkedList();
//////                ToTGF conv = new ToTGF("C:\\Users\\jenny\\eclipse-workspace\\Tpa\\TadGrafo\\movies.txt");
//////                conv.converte("C:\\Users\\jenny\\eclipse-workspace\\Tpa\\TadGrafo\\movies.txt");
//////
//////                vertices=conv.filmesEAtores();
//////                for(int i=0; i<vertices.size();i++){
//////                    System.out.println("Pos: "+i+": "+vertices.get(i));
//////
//////                }
//////
//////                arestas= conv.relacionamentos();
//////                for(int i=0; i< arestas.size();i++){
//////                    System.out.println("Rela��o "+i+": "+arestas.get(i));
//////
//////                }
//////                conv.write();
//////
//////                TADGrafoDV3 grafo = conv.carrega("C:\\Users\\jenny\\eclipse-workspace\\Tpa\\saidafinal.tgf");
//////                ToGStream show = new ToGStream(grafo);
//////                        String css = "graph { fill-color: red; }";
//////                        String teste = null;
//////                        show.exibe(teste);
//    }
//    
//}
