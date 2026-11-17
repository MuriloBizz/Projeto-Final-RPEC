import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner in = new Scanner(System.in);

    static int lerInt(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Integer.parseInt(in.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }

    static void menu() {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║          ♥ PET HASH & SORT ♥             ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.println("║ 1 - Cadastrar pet                        ║");
        System.out.println("║ 2 - Buscar pets por nome                 ║");
        System.out.println("║ 3 - Remover pet (microchip)              ║");
        System.out.println("║ 4 - Mostrar fichário (tabela hash)       ║");
        System.out.println("║ 5 - Estatísticas da tabela               ║");
        System.out.println("║ 6 - Organizar a sala de espera           ║");
        System.out.println("║ 7 - Modo passo a passo                  ║");
        System.out.println("║ 8 - A Grande Corrida                     ║");
        System.out.println("║ 0 - Fechar a clínica                     ║");
        System.out.println("╚══════════════════════════════════════════╝");
    }

    // Mostra até 10 pets e executa os três algoritmos com narração.
    static void modoPassoAPasso(TabelaHash tabela, ArrayList<Pet> iniciais,
                                ArrayList<Pet> novos) {
        ArrayList<Pet> disponiveis = new ArrayList<>();
        for (Pet p : iniciais)
            if (tabela.existeMicrochip(p.getMicrochip())) disponiveis.add(p);
        for (Pet p : novos)
            if (tabela.existeMicrochip(p.getMicrochip())) disponiveis.add(p);
        if (disponiveis.isEmpty()) {
            System.out.println("Não há pets cadastrados para organizar.");
            return;
        }
        int quantidade;
        do {
            quantidade = lerInt("Quantos pets deseja acompanhar (1 a 10)? ");
            if (quantidade < 1 || quantidade > 10 || quantidade > disponiveis.size())
                System.out.println("Escolha de 1 até " + Math.min(10, disponiveis.size()) + " pets.");
        } while (quantidade < 1 || quantidade > 10 || quantidade > disponiveis.size());

        ArrayList<Pet> amostra = new ArrayList<>();
        for (int i = 0; i < quantidade; i++) amostra.add(disponiveis.get(i));
        System.out.println("\nFila inicial da clínica:");
        for (Pet p : amostra) System.out.println("  " + p.getNome() + " (" + p.getMicrochip() + ")");

        String[] nomes = {"Bubble Sort", "Insertion Sort", "Quick Sort"};
        for (int a = 1; a <= 3; a++) {
            System.out.println("\n========== " + nomes[a - 1] + " ==========");
            Ordenacao.Resultado r = a == 1 ? Ordenacao.bubbleSort(amostra, true)
                    : a == 2 ? Ordenacao.insertionSort(amostra, true)
                    : QuickSort.ordenar(amostra, true);
            System.out.println("Fila organizada por microchip:");
            for (Pet pet : r.pets)
                System.out.println("  " + pet.getNome() + " (" + pet.getMicrochip() + ")");
            System.out.println("Comparações: " + r.comparacoes + " | "
                    + (a == 2 ? "Deslocamentos: " : "Trocas: ") + r.trocas);
        }
    }

    public static void main(String[] args) throws IOException {

        // Define o tamanho da tabela e a função hash utilizada.
        int m;
        do {
            m = lerInt("Tamanho da tabela M (ex.: 13, 17...): ");
            if (m <= 0) System.out.println("M precisa ser maior que zero.");
        } while (m <= 0);
        int f = lerInt("Função hash (1 = soma dos caracteres, 2 = polinomial): ");
        FuncaoHash funcao = (f == 2) ? new HashPolinomial() : new HashSoma();
        TabelaHash tabela = new TabelaHash(m, funcao);

        // Carrega os pets iniciais na tabela hash.
        ArrayList<Pet> pets = LeitorDados.lerArquivo("Dados.txt");
        for (Pet p : pets) tabela.inserir(p);
        System.out.println(tabela.getTamanho() + " pets carregados do arquivo.");

        ArrayList<Pet> cadastradosNoMenu = new ArrayList<>();
        int opcao;
        do {
            menu();
            opcao = lerInt("Escolha: ");
            switch (opcao) {
                case 1:
                    int chip = lerInt("Microchip (8 dígitos): ");
                    if (chip < 10000000 || chip > 99999999) {
                        System.out.println("O microchip precisa ter 8 dígitos.");
                        break;
                    }
                    System.out.print("Nome: ");
                    String nome = in.nextLine().trim();
                    System.out.print("Espécie: ");
                    String especie = in.nextLine().trim();
                    System.out.print("Mania: ");
                    String mania = in.nextLine().trim();
                    if (nome.isEmpty()) {
                        System.out.println("O nome não pode ficar vazio.");
                    } else if (tabela.inserir(new Pet(chip, nome, especie, mania))) {
                        cadastradosNoMenu.add(new Pet(chip, nome, especie, mania));
                        System.out.println(nome + " foi cadastrado(a) no fichário!");
                    } else {
                        System.out.println("Esse microchip já está cadastrado.");
                    }
                    break;
                case 2:
                    System.out.print("Nome do pet: ");
                    ArrayList<Pet> achados = tabela.buscar(in.nextLine());
                    if (achados.isEmpty()) {
                        System.out.println("Nenhum pet encontrado com esse nome.");
                    } else {
                        System.out.println(achados.size() + " pet(s) encontrado(s):");
                        for (Pet p : achados) System.out.println("  " + p);
                    }
                    break;
                case 3:
                    int chipRem = lerInt("Microchip a remover: ");
                    System.out.println(tabela.remover(chipRem)
                            ? "Pet removido do fichário."
                            : "Microchip não encontrado.");
                    break;
                case 4:
                    tabela.exibir();
                    break;
                case 5:
                    tabela.exibirEstatisticas();
                    break;
                case 6:
                    ArrayList<Pet> fila = new ArrayList<>();
                    for (Pet p : pets) {
                        // Atualiza a lista de pets após cadastros e remoções na tabela hash.
                        if (tabela.existeMicrochip(p.getMicrochip())) fila.add(p);
                    }
                    // Adiciona também os pets cadastrados pelo menu.
                    for (Pet p : cadastradosNoMenu) {
                        if (tabela.existeMicrochip(p.getMicrochip())) fila.add(p);
                    }
                    System.out.println("1 - Bubble Sort | 2 - Insertion Sort | 3 - Quick Sort");
                    int algoritmo = lerInt("Algoritmo: ");
                    if (algoritmo < 1 || algoritmo > 3) {
                        System.out.println("Algoritmo inválido.");
                        break;
                    }
                    // Seleciona o algoritmo de ordenação e retorna o resultado.
                    Ordenacao.Resultado resultado;
                    if (algoritmo == 1) resultado = Ordenacao.bubbleSort(fila, false);
                    else if (algoritmo == 2) resultado = Ordenacao.insertionSort(fila, false);
                    else resultado = QuickSort.ordenar(fila, false);
                    System.out.println("Sala de espera em ordem de microchip:");
                    for (Pet p : resultado.pets) System.out.println("  " + p);
                    System.out.println("Comparações: " + resultado.comparacoes);
                    System.out.println(algoritmo == 2 ? "Deslocamentos: " + resultado.trocas
                            : "Trocas: " + resultado.trocas);
                    System.out.printf("Tempo: %.3f ms%n", resultado.tempoNanos / 1_000_000.0);
                    break;
                case 7:
                    modoPassoAPasso(tabela, pets, cadastradosNoMenu);
                    break;
                case 8:
                    GrandeCorrida.executar();   // Parte 3: mede os 3 algoritmos e imprime as tabelas
                    break;
                case 0:
                    System.out.println("Clínica fechada. Até amanhã!");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
}