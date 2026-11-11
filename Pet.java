import java.text.Normalizer;
 
/** Um bichinho cadastrado. O microchip tem 8 dígitos e é único. */
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
 
    /** Chave da tabela hash: maiúsculas e sem acento (Paçoca -> PACOCA). */
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