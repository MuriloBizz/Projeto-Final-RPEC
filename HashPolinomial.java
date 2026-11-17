// Função hash própria que utiliza a posição das letras e cálculos para distribuir os pets na tabela.
public class HashPolinomial implements FuncaoHash {
    private static final int BASE = 37;
    @Override
    public int calcular(String chave, int m) {
        long h = 7;
        for (int i = 0; i < chave.length(); i++) {
            h = (h * BASE + chave.charAt(i) + (long)(i + 1) * 17) % m;
        }
        return (int) h;
    }
    @Override
    public String getNome() { return "Hash posicional própria (base 37)"; }
}
