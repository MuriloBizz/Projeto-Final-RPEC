/** Função hash 1: soma dos códigos dos caracteres, módulo M. */
public class HashSoma implements FuncaoHash {
    @Override
    public int calcular(String chave, int m) {
        int soma = 0;
        for (int i = 0; i < chave.length(); i++) {
            soma += chave.charAt(i);
        }
        return soma % m;
    }
 
    @Override
    public String getNome() { return "Soma dos caracteres"; }
}
 