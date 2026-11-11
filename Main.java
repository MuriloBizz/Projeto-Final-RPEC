import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
 
/** Cenário A · Clínica Veterinária: menu do fichário (tabela hash). */
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
        System.out.println("║ 6 - Organizar a sala de espera (em breve)║");
        System.out.println("║ 7 - Modo passo a passo (em breve)        ║");
        System.out.println("║ 8 - A Grande Corrida (em breve)          ║");
        System.out.println("║ 0 - Fechar a clínica                     ║");
        System.out.println("╚══════════════════════════════════════════╝");
    }
 
    public static void main(String[] args) throws IOException {
        // Escolha do tamanho M e da função hash
        int m = lerInt("Tamanho da tabela M (ex.: 13, 17...): ");
        int f = lerInt("Função hash (1 = soma dos caracteres, 2 = polinomial): ");
        FuncaoHash funcao = (f == 2) ? new HashPolinomial() : new HashSoma();
        TabelaHash tabela = new TabelaHash(m, funcao);
 
        // Carrega o fichário inicial
        ArrayList<Pet> pets = LeitorDados.lerArquivo("dados.txt");
        for (Pet p : pets) tabela.inserir(p);
        System.out.println(tabela.getTamanho() + " pets carregados do arquivo.");
 
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
                case 6: case 7: case 8:
                    System.out.println("Ainda vamos implementar (Parte 2 e 3).");
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