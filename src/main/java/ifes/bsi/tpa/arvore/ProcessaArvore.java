/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.arvore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luizg
 */
public class ProcessaArvore {
    private TreeTPAV1<String> t = null;
    
    public ProcessaArvore(TreeTPAV1<String> t) { this.t = t; }

    
    public boolean equals_tree(TreeTPAV1<String> outraTree) {
        return t.toString().equals(outraTree.toString());
    }

    public TreeTPAV1<String> t() { //seguindo a ideia do BenchMark isso daqui seria um get tree
        return t;
    }
    
    
    public TreeTPAV1<String> povoaVelha() {
        this.t = new TreeTPAV1();
        String table = "_________";
        Node root = new Node(table);
        t.setRootElement(root);
        LinkedList<Node<String>> toVisitNode = new LinkedList<>();
        toVisitNode.add(root);
        int nivel = 0;
        while(!toVisitNode.isEmpty()){
            Node node = toVisitNode.remove(); 
            for (int i = 0; i < table.length() ; i++) {
                StringBuilder auxBuilder = new StringBuilder((node.getData()).toString());
                if(auxBuilder.charAt(i) == '_')
                    if(nivel%2 == 0)
                        auxBuilder.setCharAt(i,'X');
                    else
                        auxBuilder.setCharAt(i,'O');
                else 
                    continue;
                Node node_filho = new Node(new String(auxBuilder));
                node.addChild(node_filho);
                toVisitNode.add(node_filho);
            }
            nivel++;
        }
        return t;
    }
    

    
    public String salva_tree(String nome_arq){
        /**
         * Crie aqui a sua lógica para salvar o conteúdo da árvore em um
            arquivo texto de nome nome_arq. A estrutura das informações no
            arquivo deve obedecer ao formato Newick, um formato de
            armazenamento de árvores n-árias utilizadas em aplicações de
            bioinformática.
         *
         * Referências:
         * http://evolution.genetics.washington.edu/phylip/newicktree.html
         * https://en.wikipedia.org/wiki/Newick_format (utilizar o formato
         * "(A,B,(C,D)E)F; all nodes are named)
         */
        
        StringBuilder fullLine = new StringBuilder();
        BufferedWriter bufferWriter;
        try {
            bufferWriter = new BufferedWriter(new FileWriter(nome_arq, true));
            Node root = this.t.getRootElement();
        
            fullLine.append("(");
            if(root.getNumberOfChildren() != 0){
                for(int i=0;i<root.getChildren().size();i++){
                    fullLine.append(eachNode((Node) root.getChildren().get(i)));
                }
            }

            fullLine.replace(0, fullLine.length(), fullLine.substring(0,fullLine.length()-1)); //substitui tudo por ele mesmo menos o ultimo char
            bufferWriter.write(fullLine+")"+root.getData().toString()+";");
            bufferWriter.close();
        
        } catch (IOException ex) {
            Logger.getLogger(ProcessaArvore.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nome_arq;
    }
    
    public String eachNode(Node root){
        StringBuilder line = new StringBuilder();
        if(root.getNumberOfChildren()!=0){
            line.append("(");
            for(int i=0;i<root.getNumberOfChildren();i++){
                line.append(eachNode((Node) root.getChildren().get(i)));
            }
            if(line.charAt(line.length()-1)==',') //deletar a ","
                line.replace(0, line.length(), line.substring(0,line.length()-1)); //substitui tudo por ele mesmo menos o ultimo char
            line.append(")"+root.getData().toString()+",");
        }
        else
            line.append(root.getData().toString()+",");
        
        return line.toString();
    }

    public TreeTPAV1<String> carrega_tree(String nome_arq) {
        
        
        /**
         * Crie aqui a sua lógica para carregar do arquivo texto de nome
         * nome_arq. O arquivo contém a estrutura de uma árvore n-ária no
         * formato Newick, um formato de armazenamento de árvores n-árias
         * utilizadas em aplicações de bioinformática.
         *
         * Referências:
         * http://evolution.genetics.washington.edu/phylip/newicktree.html
         *
         * https://en.wikipedia.org/wiki/Newick_format
         * Utilizar o formato "(A,B,(C,D)E)F; all nodes are named". Observe
         * a figura com a árvore de raiz F e compare com o equivalente
         * Newick do tipo "all nodes are named". É autoexplicativo.
         */
    


        BufferedReader buffRead;
        try {
            buffRead = new BufferedReader(new FileReader(nome_arq));
            String s = buffRead.readLine(); 
            s = s.substring (0, s.length() - 1); //remover o ; SE VIER SEM ELE NAO PRINTA O NOME DA ARVORE PRINCIPAL
            //o arquivo todo é uma linha só.
            this.t = new TreeTPAV1();
            Node node = new Node();
            this.t.setRootElement(node); // tive que setar pq o TreeTPAV1 não define um nó inicial
            this.t.setRootElement(NodeTree(s));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProcessaArvore.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProcessaArvore.class.getName()).log(Level.SEVERE, null, ex);
        }
        return t;
    }

    private Node NodeTree(String s) {

        int leftParen = s.indexOf('('); //primeiro (
        int rightParen = s.lastIndexOf(')'); //ultimo )

        if (leftParen != -1 && rightParen != -1) { //se encontra-los
            
            String name = s.substring(rightParen + 1); //o nome do No (ate o final)
            
            
            String[] childrenString = separa(s.substring(leftParen + 1, rightParen));
            
            Node node = new Node(name);
            node.children = new ArrayList<>();
            for (String sub : childrenString) {
                Node child = NodeTree(sub);
                node.children.add(child);
            }
            
            this.t.getRootElement().addChild(node);
            return node;
        } else if (leftParen == rightParen) {

            Node node = new Node(s);
            this.t.getRootElement().addChild(node);
            return node;

        } else return null;
    }
    
    private String[] separa(String s) {

        ArrayList<Integer> splitIndices = new ArrayList<>();

        int rightParenCount = 0;
        int leftParenCount = 0;
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case '(':
                    leftParenCount++;
                    break;
                case ')':
                    rightParenCount++;
                    break;
                case ',': 
                    if (leftParenCount == rightParenCount)
                        //encontrando-o , significa que ali é o nome de um/conjunto
                        splitIndices.add(i);
                        //definindo as regiões de split
                    break;
            }
        }

        int numSplits = splitIndices.size() + 1; //+1 para pegar a ultima area
        String[] splits = new String[numSplits];

        if (numSplits == 1) { //se só teve 1 então não precis separar
            splits[0] = s;
        } else {

            splits[0] = s.substring(0, splitIndices.get(0));
            //do inicio ate a 1º virgula

            for (int i = 1; i < splitIndices.size(); i++) {
                splits[i] = s.substring(splitIndices.get(i - 1) + 1, splitIndices.get(i));
            }

            splits[numSplits - 1] = s.substring(splitIndices.get(splitIndices.size() - 1) + 1);
        }

        return splits;
    }
    
}
