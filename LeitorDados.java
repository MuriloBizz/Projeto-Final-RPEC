import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
 
/** Lê o arquivo de dados no formato microchip;nome;especie;mania. */
public class LeitorDados {
    public static ArrayList<Pet> lerArquivo(String caminho) throws IOException {
        ArrayList<Pet> pets = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminho), StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.isBlank()) continue;
                String[] p = linha.split(";", 4);
                pets.add(new Pet(Integer.parseInt(p[0].trim()), p[1].trim(), p[2].trim(), p[3].trim()));
            }
        }
        return pets;
    }
}
 