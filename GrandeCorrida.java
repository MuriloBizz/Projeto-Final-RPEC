import java.util.ArrayList;
import java.util.Locale;

/**
 * Parte 3 · A Grande Corrida.
 * Mede tempo, comparações e trocas de Bubble, Insertion e Quick Sort
 * com n = 50, 1.000 e 10.000 pets, em 3 situações: aleatório, já ordenado e inverso.
 * No fim imprime 3 tabelas (tempo, comparações, trocas) prontas para o relatório.
 */
public class GrandeCorrida {

    private static final int[] TAMANHOS = {50, 1000, 10000};
    private static final String[] SITUACOES = {"Aleatório", "Já ordenado", "Ordem inversa"};
    private static final String[] ALGORITMOS = {"Bubble Sort", "Insertion Sort", "Quick Sort"};
    private static final long SEMENTE = 42;   // mesma semente = mesma corrida (resultados reproduzíveis)

    /** Um algoritmo qualquer que recebe a lista e devolve o Resultado. */
    private interface Algoritmo {
        Ordenacao.Resultado rodar(ArrayList<Pet> lista);
    }

    private static final Algoritmo[] FUNCOES = {
            l -> Ordenacao.bubbleSort(l, false),      // narrar = false: texto na tela distorceria o tempo
            l -> Ordenacao.insertionSort(l, false),
            l -> QuickSort.ordenar(l, false)
    };

    /** Guarda uma medição. */
    private static class Medida {
        long tempoNanos, comparacoes, trocas;
        boolean ordenou;   // conferência: o resultado realmente saiu em ordem?
    }

    /** Ponto de entrada (chamado pela opção 8 do menu). */
    public static void executar() {
        // Quick Sort com pivô fixo em dados já ordenados recursa ~n vezes (n = 10.000).
        // Com a pilha padrão do Java isso pode estourar (StackOverflowError), então
        // rodamos a corrida numa thread com pilha grande (256 MB) para conseguir medir.
        Thread t = new Thread(null, GrandeCorrida::corrida, "grande-corrida", 256L * 1024 * 1024);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void corrida() {
        System.out.println("\n=== A GRANDE CORRIDA DOS ALGORITMOS ===");
        System.out.println("Aquecendo a JVM (a primeira execução de cada algoritmo é mais lenta)...");
        ArrayList<Pet> aquecimento = GeradorPets.gerar(300, SEMENTE);
        for (Algoritmo f : FUNCOES) f.rodar(aquecimento);

        // medidas[algoritmo][situação][tamanho]
        Medida[][][] medidas = new Medida[ALGORITMOS.length][SITUACOES.length][TAMANHOS.length];

        for (int t = 0; t < TAMANHOS.length; t++) {
            int n = TAMANHOS[t];
            System.out.println("\nGerando " + n + " bichinhos...");
            ArrayList<Pet> aleatorio = GeradorPets.gerar(n, SEMENTE);
            ArrayList<Pet> ordenado = ordenadoCrescente(aleatorio);
            ArrayList<Pet> inverso = inverter(ordenado);
            // Lista de listas: índice 0 = aleatório, 1 = ordenado, 2 = inverso (mesma ordem de SITUACOES)
            ArrayList<ArrayList<Pet>> cenarios = new ArrayList<>();
            cenarios.add(aleatorio);
            cenarios.add(ordenado);
            cenarios.add(inverso);

            for (int s = 0; s < SITUACOES.length; s++) {
                for (int a = 0; a < ALGORITMOS.length; a++) {
                    System.out.println("  " + ALGORITMOS[a] + " | " + SITUACOES[s] + " | n = " + n + " ...");
                    // Cada algoritmo recebe a MESMA lista e trabalha numa cópia (comparação justa)
                    Ordenacao.Resultado r = FUNCOES[a].rodar(cenarios.get(s));

                    Medida m = new Medida();
                    m.tempoNanos = r.tempoNanos;
                    m.comparacoes = r.comparacoes;
                    m.trocas = r.trocas;
                    m.ordenou = estaOrdenado(r.pets);
                    medidas[a][s][t] = m;
                }
            }
        }

        imprimirTabela("TEMPO (ms)", medidas, 0);
        imprimirTabela("COMPARAÇÕES", medidas, 1);
        imprimirTabela("TROCAS (deslocamentos, no Insertion Sort)", medidas, 2);

        boolean tudoOk = true;
        for (Medida[][] a : medidas) for (Medida[] s : a) for (Medida m : s) if (!m.ordenou) tudoOk = false;
        System.out.println(tudoOk ? "\nConferência: todos os resultados saíram ordenados."
                : "\nATENÇÃO: algum algoritmo devolveu um resultado fora de ordem!");
    }

    // ---------- montagem das situações ----------

    /** Devolve uma lista crescente por microchip. Usa o nosso Quick Sort */
    private static ArrayList<Pet> ordenadoCrescente(ArrayList<Pet> origem) {
        ArrayList<Pet> r = new ArrayList<>();
        for (Pet p : QuickSort.ordenar(origem, false).pets) r.add(p);
        return r;
    }

    /** Lista de trás para frente crescente vira decrescente. */
    private static ArrayList<Pet> inverter(ArrayList<Pet> origem) {
        ArrayList<Pet> r = new ArrayList<>();
        for (int i = origem.size() - 1; i >= 0; i--) r.add(origem.get(i));
        return r;
    }

    private static boolean estaOrdenado(Pet[] a) {
        for (int i = 1; i < a.length; i++)
            if (a[i - 1].getMicrochip() > a[i].getMicrochip()) return false;
        return true;
    }

    // ---------- impressão ----------

    /** tipo: 0 = tempo em ms, 1 = comparações, 2 = trocas. */
    private static void imprimirTabela(String titulo, Medida[][][] medidas, int tipo) {
        Locale br = Locale.forLanguageTag("pt-BR");   // separador de milhar com ponto
        System.out.println("\n--- " + titulo + " ---");
        System.out.printf("%-16s%-16s", "Algoritmo", "Situação");
        for (int n : TAMANHOS) System.out.printf("%16s", "n = " + String.format(br, "%,d", n));
        System.out.println();

        for (int a = 0; a < ALGORITMOS.length; a++) {
            for (int s = 0; s < SITUACOES.length; s++) {
                System.out.printf("%-16s%-16s", ALGORITMOS[a], SITUACOES[s]);
                for (int t = 0; t < TAMANHOS.length; t++) {
                    Medida m = medidas[a][s][t];
                    String valor;
                    if (tipo == 0) valor = String.format(br, "%,.3f", m.tempoNanos / 1_000_000.0);
                    else if (tipo == 1) valor = String.format(br, "%,d", m.comparacoes);
                    else valor = String.format(br, "%,d", m.trocas);
                    System.out.printf("%16s", valor);
                }
                System.out.println();
            }
        }
    }
}