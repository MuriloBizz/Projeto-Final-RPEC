/** Contrato de uma função hash: recebe a chave e o tamanho M, devolve o índice do balde. */
public interface FuncaoHash {
    int calcular(String chave, int m);
    String getNome();
}
 