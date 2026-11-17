import java.util.ArrayList;

public class QuickSort {

    // Contadores da execução atual (a corrida roda uma ordenação por vez)
    private static long comparacoes;
    private static long trocas;
    private static boolean narrar;

    public static Ordenacao.Resultado ordenar(ArrayList<Pet> origem, boolean narrarPassos) {
        Pet[] a = origem.toArray(new Pet[0]);   // cópia independente
        comparacoes = 0;
        trocas = 0;
        narrar = narrarPassos;

        long inicio = System.nanoTime();
        quick(a, 0, a.length - 1);
        long tempo = System.nanoTime() - inicio;

        return new Ordenacao.Resultado(a, comparacoes, trocas, tempo);
    }

    private static void quick(Pet[] a, int baixo, int alto) {
        if (baixo >= alto) return;                 // 0 ou 1 elemento: já está ordenado
        int p = particionar(a, baixo, alto);       // pivô fica na posição final p
        quick(a, baixo, p - 1);                    // ordena quem é menor que o pivô
        quick(a, p + 1, alto);                     // ordena quem é maior que o pivô
    }

    private static int particionar(Pet[] a, int baixo, int alto) {
        Pet pivo = a[alto];
        if (narrar) System.out.println("\nQuick Sort - pivô: " + pivo.getNome() + " (" + pivo.getMicrochip() + ")");
        int i = baixo - 1;                         // fim da região "menores que o pivô"

        for (int j = baixo; j < alto; j++) {
            comparacoes++;
            boolean menor = a[j].getMicrochip() < pivo.getMicrochip();
            if (narrar) System.out.printf("%s (%d) < pivô? %s%n", a[j].getNome(), a[j].getMicrochip(),
                    menor ? "SIM" : "NÃO");
            if (menor) {
                i++;
                trocar(a, i, j);
            }
        }
        trocar(a, i + 1, alto);                    // coloca o pivô no lugar definitivo
        return i + 1;
    }

    private static void trocar(Pet[] a, int x, int y) {
        if (x == y) return;                        // trocar consigo mesmo não conta como troca
        if (narrar) System.out.println("  " + a[x].getNome() + " e " + a[y].getNome() + " trocam de cadeira!");
        Pet temp = a[x];
        a[x] = a[y];
        a[y] = temp;
        trocas++;
    }
}