import java.util.ArrayList;

// Armazena os pets em uma tabela hash usando listas ligadas para tratar colisões.
public class TabelaHash {

    private final int m;
    private final NoPet[] baldes;
    private final FuncaoHash funcao;
    private int tamanho = 0; // Quantidade de pets cadastrados.

    public TabelaHash(int m, FuncaoHash funcao) {
        if (m <= 0) {
            throw new IllegalArgumentException("O tamanho da tabela deve ser maior que zero.");
        }

        this.m = m;
        this.funcao = funcao;
        this.baldes = new NoPet[m];
    }

    private int indice(String chave) {
        return funcao.calcular(chave, m);
    }

    // Adiciona o pet ao final da lista e impede microchips duplicados.
    public boolean inserir(Pet pet) {
        if (existeMicrochip(pet.getMicrochip())) {
            return false;
        }

        int i = indice(pet.getChave());
        NoPet novo = new NoPet(pet);

        if (baldes[i] == null) {
            baldes[i] = novo;
        } else {
            NoPet atual = baldes[i];

            while (atual.proximo != null) {
                atual = atual.proximo;
            }

            atual.proximo = novo;
        }

        tamanho++;
        return true;
    }

    // Busca e retorna todos os pets com o mesmo nome.
    public ArrayList<Pet> buscar(String nome) {
        String chave = Pet.normalizar(nome);
        ArrayList<Pet> achados = new ArrayList<>();

        for (NoPet n = baldes[indice(chave)]; n != null; n = n.proximo) {
            if (n.pet.getChave().equals(chave)) {
                achados.add(n.pet);
            }
        }

        return achados;
    }

    // Remove o pet pelo microchip, percorrendo os baldes da tabela.
    public boolean remover(int microchip) {
        for (int i = 0; i < m; i++) {
            NoPet anterior = null;
            NoPet atual = baldes[i];

            while (atual != null) {
                if (atual.pet.getMicrochip() == microchip) {
                    if (anterior == null) {
                        baldes[i] = atual.proximo;
                    } else {
                        anterior.proximo = atual.proximo;
                    }

                    tamanho--;
                    return true;
                }

                anterior = atual;
                atual = atual.proximo;
            }
        }

        return false;
    }

    // Verifica se o microchip já está cadastrado.
    public boolean existeMicrochip(int microchip) {
        for (int i = 0; i < m; i++) {
            for (NoPet n = baldes[i]; n != null; n = n.proximo) {
                if (n.pet.getMicrochip() == microchip) {
                    return true;
                }
            }
        }

        return false;
    }

    // Mostra todos os baldes e seus respectivos pets.
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

                if (n.proximo != null) {
                    System.out.print(" -> ");
                }
            }

            System.out.println();
        }
    }

    // Calcula o fator de carga da tabela.
    public double fatorCarga() {
        return (double) tamanho / m;
    }

    // Conta quantos baldes estão vazios.
    public int baldesVazios() {
        int vazios = 0;

        for (int i = 0; i < m; i++) {
            if (baldes[i] == null) {
                vazios++;
            }
        }

        return vazios;
    }

    // Encontra o tamanho da maior lista ligada.
    public int maiorLista() {
        int maior = 0;

        for (int i = 0; i < m; i++) {
            int cont = 0;

            for (NoPet n = baldes[i]; n != null; n = n.proximo) {
                cont++;
            }

            if (cont > maior) {
                maior = cont;
            }
        }

        return maior;
    }

    // Conta as colisões dos pets atualmente cadastrados.
    public int getColisoes() {
        int total = 0;

        for (NoPet balde : baldes) {
            if (balde == null) {
                continue;
            }

            // O primeiro pet ocupa o balde sem colisão.
            for (NoPet n = balde.proximo; n != null; n = n.proximo) {
                total++;
            }
        }

        return total;
    }

    // Conta colisões envolvendo nomes diferentes no mesmo balde.
    public int getColisoesReais() {
        int total = 0;

        for (NoPet balde : baldes) {
            if (balde == null) {
                continue;
            }

            ArrayList<String> nomesAnteriores = new ArrayList<>();

            for (NoPet n = balde; n != null; n = n.proximo) {
                String chave = n.pet.getChave();

                boolean nomeDiferente = false;

                for (String nomeAnterior : nomesAnteriores) {
                    if (!nomeAnterior.equals(chave)) {
                        nomeDiferente = true;
                        break;
                    }
                }

                if (nomeDiferente) {
                    total++;
                }

                nomesAnteriores.add(chave);
            }
        }

        return total;
    }

    public int getTamanho() {
        return tamanho;
    }

    public int getM() {
        return m;
    }

    // Exibe as estatísticas atualizadas da tabela hash.
    public void exibirEstatisticas() {
        System.out.println("--- Estatísticas (M = " + m + ", "
                + funcao.getNome() + ") ---");

        System.out.println("Pets cadastrados : " + tamanho);

        System.out.printf("Fator de carga   : %.2f%n", fatorCarga());

        System.out.println("Baldes vazios    : "
                + baldesVazios() + " de " + m);

        System.out.println("Maior lista      : " + maiorLista());

        System.out.println("Colisões (total) : " + getColisoes());

        System.out.println("Colisões reais   : " + getColisoesReais()
                + " (nomes diferentes no mesmo balde)");
    }
}
