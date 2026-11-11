import java.util.ArrayList;
 
/** Tabela hash própria, com encadeamento (lista ligada em cada balde). */
public class TabelaHash {
    private final int m;
    private final NoPet[] baldes;
    private final FuncaoHash funcao;
    private int tamanho = 0;            // quantidade de pets
    private int colisoes = 0;           // inserções em balde que já tinha alguém
    private int colisoesReais = 0;      // ... e com algum nome DIFERENTE no balde
 
    public TabelaHash(int m, FuncaoHash funcao) {
        this.m = m;
        this.funcao = funcao;
        this.baldes = new NoPet[m];
    }
 
    private int indice(String chave) {
        return funcao.calcular(chave, m);
    }
 
    /** Insere no fim da lista do balde. Retorna false se o microchip já existe. */
    public boolean inserir(Pet pet) {
        if (existeMicrochip(pet.getMicrochip())) return false;
 
        int i = indice(pet.getChave());
        NoPet novo = new NoPet(pet);
 
        if (baldes[i] == null) {
            baldes[i] = novo;
        } else {
            colisoes++;
            NoPet atual = baldes[i];
            boolean nomeDiferente = false;
            while (true) {
                if (!atual.pet.getChave().equals(pet.getChave())) nomeDiferente = true;
                if (atual.proximo == null) break;
                atual = atual.proximo;
            }
            if (nomeDiferente) colisoesReais++;
            atual.proximo = novo;
        }
        tamanho++;
        return true;
    }
 
    /** Busca por nome: devolve TODOS os pets com aquele nome. */
    public ArrayList<Pet> buscar(String nome) {
        String chave = Pet.normalizar(nome);
        ArrayList<Pet> achados = new ArrayList<>();
        for (NoPet n = baldes[indice(chave)]; n != null; n = n.proximo) {
            if (n.pet.getChave().equals(chave)) achados.add(n.pet);
        }
        return achados;
    }
 
    /** Remove por microchip. Como a chave é o NOME, precisa varrer os baldes. */
    public boolean remover(int microchip) {
        for (int i = 0; i < m; i++) {
            NoPet anterior = null;
            for (NoPet n = baldes[i]; n != null; anterior = n, n = n.proximo) {
                if (n.pet.getMicrochip() == microchip) {
                    if (anterior == null) baldes[i] = n.proximo;
                    else anterior.proximo = n.proximo;
                    tamanho--;
                    return true;
                }
            }
        }
        return false;
    }
 
    public boolean existeMicrochip(int microchip) {
        for (int i = 0; i < m; i++)
            for (NoPet n = baldes[i]; n != null; n = n.proximo)
                if (n.pet.getMicrochip() == microchip) return true;
        return false;
    }
 
    /** Exibe a tabela balde por balde. */
    public void exibir() {
        System.out.println("Tabela M = " + m + " | função: " + funcao.getNome());
        for (int i = 0; i < m; i++) {
            System.out.print("[" + i + "] ");
            if (baldes[i] == null) {
                System.out.println("vazio");
                continue;
            }
            for (NoPet n = baldes[i]; n != null; n = n.proximo) {
                System.out.print(n.pet.getNome() + " (" + n.pet.getEspecie() + ")");
                if (n.proximo != null) System.out.print(" -> ");
            }
            System.out.println();
        }
    }
 
    // ---------- Estatísticas ----------
    public double fatorCarga() { return (double) tamanho / m; }
 
    public int baldesVazios() {
        int vazios = 0;
        for (int i = 0; i < m; i++) if (baldes[i] == null) vazios++;
        return vazios;
    }
 
    public int maiorLista() {
        int maior = 0;
        for (int i = 0; i < m; i++) {
            int cont = 0;
            for (NoPet n = baldes[i]; n != null; n = n.proximo) cont++;
            if (cont > maior) maior = cont;
        }
        return maior;
    }
 
    public int getColisoes() { return colisoes; }
    public int getColisoesReais() { return colisoesReais; }
    public int getTamanho() { return tamanho; }
    public int getM() { return m; }
 
    public void exibirEstatisticas() {
        System.out.println("--- Estatísticas (M = " + m + ", " + funcao.getNome() + ") ---");
        System.out.println("Pets cadastrados : " + tamanho);
        System.out.printf ("Fator de carga   : %.2f%n", fatorCarga());
        System.out.println("Baldes vazios    : " + baldesVazios() + " de " + m);
        System.out.println("Maior lista      : " + maiorLista());
        System.out.println("Colisões (total) : " + colisoes);
        System.out.println("Colisões reais   : " + colisoesReais + " (nomes diferentes no mesmo balde)");
    }
}