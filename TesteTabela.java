import java.io.IOException;
import java.util.ArrayList;
 
/** Teste rápido da tabela hash (depois vira o menu da Parte 4). */
public class TesteTabela {
 
    public static void main(String[] args) throws IOException {
        ArrayList<Pet> pets = LeitorDados.lerArquivo("dados.txt");
        int[] tamanhos = {13, 17, 23};            // 23: troquem pelo tamanho escolhido pelo grupo
        FuncaoHash[] funcoes = { new HashSoma(), new HashPolinomial() };
 
        for (FuncaoHash f : funcoes) {
            for (int m : tamanhos) {
                TabelaHash t = new TabelaHash(m, f);
                for (Pet p : pets) t.inserir(p);
                t.exibirEstatisticas();
                System.out.println();
            }
        }
 
        TabelaHash t = new TabelaHash(13, new HashSoma());
        for (Pet p : pets) t.inserir(p);
        t.exibir();
 
        System.out.println("\nBuscando 'luna' (todas as Lunas):");
        for (Pet p : t.buscar("luna")) System.out.println("  " + p);
 
        System.out.println("\nRemovendo microchip 60218841: " + t.remover(60218841));
        System.out.println("Lunas restantes: " + t.buscar("Luna").size());
    }
}