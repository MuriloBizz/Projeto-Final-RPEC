import java.util.ArrayList;

public class QuickSort {

    // Armazena as comparações e trocas de cada execução.
    private static long comparacoes;
    private static long trocas;
    private static boolean narrar;

    public static Ordenacao.Resultado ordenar(ArrayList<Pet> origem, boolean narrarPassos) {
        Pet[] a = origem.toArray(new Pet[0]);   // Cria uma cópia separada da lista original.
        comparacoes = 0;
        trocas = 0;
        narrar = narrarPassos;

        long inicio = System.nanoTime();
        quick(a, 0, a.length - 1);
        long tempo = System.nanoTime() - inicio;

        return new Ordenacao.Resultado(a, comparacoes, trocas, tempo);
    }

    private static void quick(Pet[] a, int baixo, int alto) {
        if (baixo >= alto) return;                 // Se houver 0 ou 1 elemento, a lista já está ordenada.
        int p = particionar(a, baixo, alto);       // Coloca o pivô na posição correta.
        quick(a, baixo, p - 1);               // Ordena os elementos menores que o pivô.
        quick(a, p + 1, alto);               // Ordena os elementos maiores que o pivô.
    }

    private static int particionar(Pet[] a, int baixo, int alto) {
        Pet pivo = a[alto];
        if (narrar) System.out.println("\nQuick Sort - pivô: " + pivo.getNome() + " (" + pivo.getMicrochip() + ")");
        int i = baixo - 1;                         // Marca o fim da região com elementos menores que o pivô.
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
        trocar(a, i + 1, alto);                 // coloca o pivô no lugar definitivo
        return i + 1;
    }

    private static void trocar(Pet[] a, int x, int y) {
        if (x == y) return;                        // Não conta como troca quando o elemento permanece na mesma posição.
        if (narrar) System.out.println("  " + a[x].getNome() + " e " + a[y].getNome() + " trocam de cadeira!");
        Pet temp = a[x];
        a[x] = a[y];
        a[y] = temp;
        trocas++;
    }
}