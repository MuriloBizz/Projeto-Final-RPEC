import java.util.ArrayList;
import java.util.Random;


public class TesteCarga {

    private static final int TOTAL_PETS = 10_000;
    private static final int BUSCAS = 2_000;       // quantas buscas por nome fazemos para medir o tempo médio

    public static void main(String[] args) {
        // Usa o gerador da Grande Corrida: 10.000 pets, nomes repetidos, microchips únicos
        ArrayList<Pet> pets = GeradorPets.gerar(TOTAL_PETS, 42);

        int[] tamanhos = {17, 101, 1009, 10007};   // 17 = o pedido da pergunta; os outros mostram o efeito de aumentar M
        FuncaoHash[] funcoes = { new HashSoma(), new HashPolinomial() };

        System.out.println("=== " + TOTAL_PETS + " pets na tabela hash ===");
        for (FuncaoHash f : funcoes) {
            for (int m : tamanhos) {
                TabelaHash t = new TabelaHash(m, f);

                long ini = System.nanoTime();
                for (Pet p : pets) t.inserir(p);
                double msInsercao = (System.nanoTime() - ini) / 1_000_000.0;

                // Busca por nomes sorteados dos próprios pets, sempre existem
                Random rnd = new Random(7);
                for (int i = 0; i < 200; i++) t.buscar(pets.get(rnd.nextInt(pets.size())).getNome()); // aquecimento
                long somaResultados = 0;                                  // evita o compilador descartar o laço
                ini = System.nanoTime();
                for (int i = 0; i < BUSCAS; i++) {
                    somaResultados += t.buscar(pets.get(rnd.nextInt(pets.size())).getNome()).size();
                }
                double usPorBusca = (System.nanoTime() - ini) / 1000.0 / BUSCAS;

                System.out.println();
                System.out.println("Função: " + f.getNome() + " | M = " + m);
                System.out.printf("  Fator de carga   : %.2f (média de pets por balde)%n", t.fatorCarga());
                System.out.printf("  Baldes vazios    : %d de %d%n", t.baldesVazios(), m);
                System.out.printf("  Maior lista      : %d%n", t.maiorLista());
                System.out.printf("  Tempo p/ inserir : %.1f ms (todos)%n", msInsercao);
                System.out.printf("  Tempo por busca  : %.1f microssegundos (média de %d buscas; resultados: %d)%n",
                        usPorBusca, BUSCAS, somaResultados);
            }
        }
    }
}