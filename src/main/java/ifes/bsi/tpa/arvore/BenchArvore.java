/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.arvore;



/**
 *
 * @author luizg
 */
public class BenchArvore {
    /*
     * Faça testes com árvore menores do que a do Jogo da Velha.
     * Utilize árvores dos sites das referências ou aquelas do livro da Judith.
     */
    public static void main(String[] args){

        String file_path = "C:\\Users\\luizg\\Documents\\NetBeansProjects\\TPA_meaven\\src\\main\\java\\ifes\\bsi\\tpa\\arvore\\arquivos_arvores\\";
        String arq_read = "aluno_jgo_da_velha.txt";
        String arq_read_prof = "PROFESSOR.txt"; //presupondo o .txt do professor
        
        TreeTPAV1<String> t = new TreeTPAV1<String>();
        TreeTPAV1<String> t2 = new TreeTPAV1<String>();
        TreeTPAV1<String> profT = new TreeTPAV1<String>();
        
        ProcessaArvore pa = new ProcessaArvore(t);
        ProcessaArvore PROFESSOR = new ProcessaArvore(profT);
        profT = PROFESSOR.carrega_tree(file_path+arq_read_prof);
        
        t = pa.povoaVelha();
        if(t.equals(PROFESSOR.t()))
            System.out.println("Tarefa 1 ok!");
        else
            System.out.println("Tarefa 1 NÃO ok!");
        pa.salva_tree(file_path+arq_read);
        
        t2 = pa.carrega_tree(file_path+arq_read); //é o mesmo de escrita para poder verificar a integridade dos dados

        if(t.equals(t2))
            System.out.println("Tarefa 2 ok!");
        else
            System.out.println("Tarefa 2 NÃO ok!");
        
        System.out.println("\nX.-._.-._.-._.-._.-._.-._.-._.Debug Section .-._.-._.-._.-._.-._.-._.X\n");
        System.out.println("Arvore Populada: "+t.toString());
        System.out.println("Arvore Lida: "+t2.toString());
        System.out.println("Arvore Lida (PROF): "+profT.toString());
        
    } /* fim de main */
} /* classe BenchArvore */