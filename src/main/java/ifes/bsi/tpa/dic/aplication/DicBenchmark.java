package ifes.bsi.tpa.dic.aplication;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import _my_tools.*;
import ifes.bsi.tpa.taddic.TADDicChain;
import java.util.Arrays;


class RegMD {
	private String cpf;
	private String nome;
	private String end;
	private String cel;
	
	public RegMD(String cpf, String nome, String end, String cel) {
		this.cpf = cpf;
		this.nome = nome;
		this.end = end;
		this.cel = cel;
	}
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getCel() {
		return cel;
	}
	public void setCel(String cel) {
		this.cel = cel;
	}
	
	public boolean equals(RegMD r) {
		return (cpf == r.getCpf()) && (nome == r.getNome()) && (end == r.getEnd()) && (cel == r.getCel());
	}

    @Override
    public String toString() {
        return "RegMD{" + "cpf=" + cpf + ", nome=" + nome + ", end=" + end + ", cel=" + cel + '}';
    }
        
} /* fim RegMD */

public class DicBenchmark {
	public static void main(String[] args) {
		int TAM_TESTE = 50000;
		TADDicChain dicA = new TADDicChain();
		ArquivoTxt arqIn = ArquivoTxt.open("C:\\Users\\luizg\\Documents\\NetBeansProjects\\TPA_meaven\\src\\main\\java\\ifes\\bsi\\tpa\\dic\\maladireta.csv", "rt");
		
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		
		/* Iniciando o benchmark da classe TADDicChain.  */
		System.out.println("Iniciando o benchmark da classe TADDicChain:");
		
		/* Povoando o dicionário e HashMap Java. */
		System.out.println("Preenchendo o dicionário com " + TAM_TESTE + " entradas a partir do conteúdo de maladireta.csv.");
		String linha = arqIn.readline();
		while(linha != null) {
			String[] lst = linha.split(";");
			RegMD dado = new RegMD(lst[0],lst[1],lst[2],lst[3]);
			RegMD dadoHM = new RegMD(lst[0],lst[1],lst[2],lst[3]);
			
			dicA.insertItem(dado.getCpf(),dado);
			hm.put(dadoHM.getCpf(),dadoHM);
			
			linha = arqIn.readline();			
		}
		arqIn.close();
		
		System.out.println("preenchimento concluído com sucesso!");
		
		if(dicA.size() == TAM_TESTE)
			System.out.println("Confirmado, dicionário A contém " + TAM_TESTE + " entradas.");
		else {
			System.out.println("Problemas!, dicionário NÃO foi povoado corretamente.");
			System.exit(0);
		}
		
		/* Transferindo elementos entre dicionários. */
		System.out.println("\nTestando a transferência de entradas entre dicionários..");
		TADDicChain dicB = new TADDicChain();
		
		System.out.println("  Antes da transferência: quantidade de entradas do dicionário A: " + dicA.size());
		System.out.println("  Antes da transferência: quantidade de entradas do dicionário B: " + dicB.size());
		System.out.println("  Antes da transferência: tamanho do vetor de buckets do dicionário A: " + dicA.getSizeVetBuckets());
		System.out.println("  Antes da transferência: tamanho do vetor de buckets do dicionário B: " + dicB.getSizeVetBuckets());
		
		LinkedList<Object> lstKs = dicA.keys();
		
		int i =0;
                
		while(dicA.size() > 0) {
			RegMD dado = (RegMD)dicA.removeElement(lstKs.get(i));
			if(dicA.NO_SUCH_KEY()) {
				System.out.println("**Problemas!\nFalha na remoção da chave " +  lstKs.get(i).toString() + " do dicionário, abortando o benchmark.");
				System.exit(0);
			}
			dicB.insertItem(lstKs.get(i),dado);	
			i = i + 1;
		}
		
		if((dicA.isEmpty()) && (dicB.size() == TAM_TESTE)) {
			System.out.println(" >Entradas transferidos com sucesso!");
			System.out.println("  Após a transferência: quantidade de entradas do dicionário A: " + dicA.size());
			System.out.println("  Após a transferência: quantidade de entradas do dicionário B: " + dicB.size());
			System.out.println("  Após a transferência: tamanho do vetor de buckets do dicionário A: " + dicA.getSizeVetBuckets());
			System.out.println("  Após a transferência: tamanho do vetor de buckets do dicionário B: " + dicB.getSizeVetBuckets());
		}
		else {
			System.out.print("**Problemas!\nTransfrência de entradas entre dicionários falhou!.");
			System.exit(0);
		}	
		
		/* Testando a clonagem e a igualdade. */
		System.out.print("\nClonando o dicionário B..");
		TADDicChain dicC = dicB.clone();
		
		if(dicC.equals(dicB)) {
			System.out.println("clonagem concluida com êxito!");			
			System.out.println("  Após a clonagem: quantidade de entradas do dicionário (C)lone: " + dicC.size());
			System.out.println("  Após a clonagem: tamanho do vetor de buckets do dicionário (C)lone: " + dicC.getSizeVetBuckets());			
		}
		else
			System.out.println("**Erro! problema no processo de clonagem ou na verificação de igualdade.");
		
		System.out.print("\nTestando a integridade chave/conteúdo..");
		
        /* Obtem um conjunto de entradas do HashMap (chaves e dados). */
        Set set = hm.entrySet();
      
        /* Constrói um iterator para percorrer o conjunto. */
        Iterator it = set.iterator();
      
        /* Para cada chave e dado extraído do HashMap, verifica se a chave obtém o memso dado do TADDicChain. */
        int n = 0;
        while(it.hasNext()) {
           Map.Entry me = (Map.Entry)it.next();
          
           String k = (String)me.getKey();
           RegMD dadoHM = (RegMD)me.getValue();          
           RegMD dadoTAD = (RegMD)dicC.findElement(k);          
           
		   if(dicC.NO_SUCH_KEY() || !dadoTAD.equals(dadoHM)) {
			   System.out.println("**Problemas!\nFalha na relação chave/dado " +  k + "/" + dadoHM.getNome() + " do dicionário, abortando o benchmark.");
			   System.exit(0);
		   }		   
		   n++;
        } /* while(it.. */
       
        System.out.println("teste concluído com sucesso.");
        System.out.println(n + " relações chaves/conteúdos verificadas corretamente!");
		
		System.out.println("\nBenchmark concluído!");
		
		System.out.println("\nOs seguinte métodos foram testados:");
		System.out.println(" .insertItem(k,e);");
		System.out.println(" .findElement(k);");
		System.out.println(" .removeElement(k);");
		System.out.println(" .redimensiona();");
		System.out.println(" .isEmpty();");
		System.out.println(" .size();");
		System.out.println(" .NO_SUCH_KEY();");
		System.out.println(" .keys();");
		System.out.println(" .elements();");
		System.out.println(" .clone();");
		System.out.println(" .equals();");		
//            dicB.showCollisionsDiagram(); provavelmente não rode no PC do professor porque estou no windows. (ele mostra o grafico de colisões).
//            dicC.showCollisionsDiagram();
	} // fim main
} // fim classe DicBenchmark
