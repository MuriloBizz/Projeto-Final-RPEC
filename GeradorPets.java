import java.util.ArrayList;
import java.util.Random;


public class GeradorPets {

    // Lista reduzida de nomes para gerar repetições e colisões na tabela hash.
    private static final String[] NOMES = {
            "Luna", "Thor", "Bob", "Mel", "Zeus", "Lola", "Nina", "Simba", "Amora", "Pipoca",
            "Paçoca", "Fred", "Nala", "Toby", "Mia", "Rex", "Maya", "Cacau", "Pingo", "Zeca",
            "Bolinha", "Frida", "Chico", "Toddy", "Pandora", "Branca"
    };

    private static final String[] ESPECIES = {
            "cão", "gato", "cachorra", "gata", "coelho", "coelha", "hamster", "papagaio", "calopsita", "tartaruga"
    };

    private static final String[] MANIAS = {
            "rouba meias", "mia para a geladeira", "late para o aspirador", "dorme em cima do teclado",
            "derruba copos da mesa", "pula quando ouve a palavra passeio", "enterra brinquedos no sofá",
            "gira atrás do próprio rabo", "esconde chinelos debaixo da cama", "uiva com a sirene",
            "escala as cortinas", "come a ração do irmão", "dorme dentro de caixas de papelão",
            "rói o fio do carregador", "acorda o dono às cinco da manhã", "late para o próprio reflexo"
    };

    private static final int CHIP_MIN = 10_000_000;   // menor número de 8 dígitos
    private static final int CHIP_MAX = 99_999_999;   // maior número de 8 dígitos

    // Gera pets aleatórios usando uma semente para repetir os mesmos resultados.
    public static ArrayList<Pet> gerar(int n, long semente) {
        Random rnd = new Random(semente);
        ArrayList<Pet> pets = new ArrayList<>(n);

        while (pets.size() < n) {
            // Gera um número aleatório de 8 dígitos para o microchip.
            int chip = CHIP_MIN + rnd.nextInt(CHIP_MAX - CHIP_MIN + 1);
            boolean repetido = false;
            for (Pet existente : pets) {
                if (existente.getMicrochip() == chip) { repetido = true; break; }
            }
            if (repetido) continue;

            String nome = NOMES[rnd.nextInt(NOMES.length)];
            String especie = ESPECIES[rnd.nextInt(ESPECIES.length)];
            String mania = MANIAS[rnd.nextInt(MANIAS.length)];
            pets.add(new Pet(chip, nome, especie, mania));
        }
        return pets;
    }
}