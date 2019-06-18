/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.grafo;
import ifes.bsi.tpa.taddic.TADDicChain;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.Object;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author luizg
 */


public class Conversor {
    
    private String pathArq;
    private TADDicChain dicElements;
    private TADDicChain dicRelationships;
    private Integer geraID=0;
    
    public Conversor(String nome_arq){
        this.dicElements = new TADDicChain();
        this.dicRelationships = new TADDicChain();
        this.pathArq=nome_arq;
    }
    
    public Integer geraIDVertex(){
        return this.geraID++;
    }

    public void converte(String nomearq,String output) throws FileNotFoundException, IOException{
        BufferedReader buffRead = new BufferedReader(new FileReader(this.pathArq+nomearq));
        String line = "";
        
        String filme = "";
        List<Integer> atoresId = new ArrayList<>();
        
        while(true) {  
            line = buffRead.readLine(); 
            
            if(line == null) {
//                System.out.println("filme: "+filme+"| autoresId: "+atoresId.toString());
                dicRelationships.insertItem(filme, atoresId);
//                System.out.println("BEFORE: "+dicRelationships.findElement(filme));
                buffRead.close();
                break;
            }
            
            String[] vet = line.split("/");    //Separação de valores
            
            for (int i = 0; i < vet.length; i++){ 
                dicElements.findElement(vet[i]);
                if(dicElements.NO_SUCH_KEY())
                    dicElements.insertItem(vet[i], this.geraIDVertex()); // Insere item de chave filme/ator e valor id;

                if(i == 0){ //Se for filme...
//                    System.out.println("adicionando filme: "+vet[i]+"| ID: "+(Integer) dicElements.findElement(vet[i]));
                    if(!atoresId.isEmpty()){
//                        System.out.println("filme: "+filme+"| autoresId: "+atoresId.toString());
                        dicRelationships.insertItem(filme, atoresId);
//                        System.out.println("BEFORE: "+dicRelationships.findElement(filme));
                        atoresId = new ArrayList<>();
                    }
                    filme = vet[i];
                    
                }else{
//                    System.out.println("adicionado ator: "+vet[i]+"| ID: "+(Integer) dicElements.findElement(vet[i]));
                    atoresId.add((Integer) dicElements.findElement(vet[i]));
                }
            }                
        }
        
        // 2 dicionarios formados, o de relação e o de entidades
        //TIME TO WRITE
        
//        System.out.println("DEBUG SECTION");
//        dicRelationships.showall();
//        System.out.println("");
//        dicElements.showall();
//        
//        System.out.println("valores R:"+dicRelationships.elements());
//        System.out.println("valores E:"+dicElements.elements());
        
        
        BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(this.pathArq+output, true));
        
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
}