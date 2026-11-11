/**
 * Função hash 2 (ponto de partida): polinomial, que leva em conta a POSIÇÃO de cada letra.
 * Ela já diferencia anagramas (ex.: LOLA x ALOL), coisa que a soma não faz.
 * O grupo pode trocar o multiplicador ou inventar outra!
 */
public class HashPolinomial implements FuncaoHash {
    private static final int BASE = 31;
 
    @Override
    public int calcular(String chave, int m) {
        long h = 0;
        for (int i = 0; i < chave.length(); i++) {
            h = (h * BASE + chave.charAt(i)) % m;   // % a cada passo evita estouro
        }
        return (int) h;
    }
 
    @Override
    public String getNome() { return "Polinomial (base " + BASE + ")"; }
}
 