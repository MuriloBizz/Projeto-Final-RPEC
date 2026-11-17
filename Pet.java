import java.text.Normalizer;

// Representa um pet cadastrado com um microchip único de 8 dígitos.
public class Pet {
    private final int microchip;
    private final String nome;     // nome original (ex.: "Paçoca")
    private final String especie;
    private final String mania;
 
    public Pet(int microchip, String nome, String especie, String mania) {
        this.microchip = microchip;
        this.nome = nome;
        this.especie = especie;
        this.mania = mania;
    }
 
    public int getMicrochip() { return microchip; }
    public String getNome() { return nome; }
    public String getEspecie() { return especie; }
    public String getMania() { return mania; }

    // Converte o nome do pet para letras maiúsculas e remove os acentos.
    public String getChave() { return normalizar(nome); }
 
    public static String normalizar(String texto) {
        String semAcento = Normalizer.normalize(texto.trim(), Normalizer.Form.NFD)
                                     .replaceAll("\\p{M}", "");
        return semAcento.toUpperCase();
    }
 
    @Override
    public String toString() {
        return nome + " (" + especie + ", chip " + microchip + ") - " + mania;
    }
}