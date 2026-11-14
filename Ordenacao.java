import java.util.ArrayList;

public class Ordenacao {
    public static class Resultado {
        public final Pet[] pets;
        public final long comparacoes;
        public final long trocas;
        public final long tempoNanos;

        Resultado(Pet[] pets, long comparacoes, long trocas, long tempoNanos) {
            this.pets = pets;
            this.comparacoes = comparacoes;
            this.trocas = trocas;
            this.tempoNanos = tempoNanos;
        }
    }

    public static Resultado bubbleSort(ArrayList<Pet> origem, boolean narrar) {
        Pet[] a = origem.toArray(new Pet[0]); // cópia independente
        long comparacoes = 0, trocas = 0;
        long inicio = System.nanoTime();
        for (int i = 0; i < a.length - 1; i++) {
            boolean mudou = false;
            if (narrar) System.out.println("\nBubble Sort - passagem " + (i + 1));
            for (int j = 0; j < a.length - i - 1; j++) {
                comparacoes++;
                boolean trocar = a[j].getMicrochip() > a[j + 1].getMicrochip();
                if (narrar) System.out.printf("%s (%d) > %s (%d)? %s%n",
                        a[j].getNome(), a[j].getMicrochip(), a[j + 1].getNome(),
                        a[j + 1].getMicrochip(), trocar ? "SIM - trocam de cadeira!" : "NÃO - ficam no lugar.");
                if (trocar) {
                    Pet temp = a[j]; a[j] = a[j + 1]; a[j + 1] = temp;
                    trocas++;
                    mudou = true;
                }
            }
            if (narrar) System.out.printf("Até agora: comparações=%d | trocas=%d%n", comparacoes, trocas);
            if (!mudou) break;
        }
        return new Resultado(a, comparacoes, trocas, System.nanoTime() - inicio);
    }

    public static Resultado insertionSort(ArrayList<Pet> origem, boolean narrar) {
        Pet[] a = origem.toArray(new Pet[0]);
        long comparacoes = 0, deslocamentos = 0;
        long inicio = System.nanoTime();
        for (int i = 1; i < a.length; i++) {
            Pet atual = a[i];
            int j = i - 1;
            if (narrar) System.out.println("\nInsertion Sort - inserindo " + atual.getNome() + " (" + atual.getMicrochip() + ")");
            while (j >= 0) {
                comparacoes++;
                if (a[j].getMicrochip() <= atual.getMicrochip()) {
                    if (narrar) System.out.println("  " + a[j].getNome() + " fica na frente; posição encontrada.");
                    break;
                }
                if (narrar) System.out.println("  " + a[j].getNome() + " anda uma cadeira para a direita.");
                a[j + 1] = a[j];
                deslocamentos++;
                j--;
            }
            a[j + 1] = atual;
            if (narrar) System.out.printf("Até agora: comparações=%d | deslocamentos=%d%n", comparacoes, deslocamentos);
        }
        return new Resultado(a, comparacoes, deslocamentos, System.nanoTime() - inicio);
    }
}
