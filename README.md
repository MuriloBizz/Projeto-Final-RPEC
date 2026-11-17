# 🐾 Pet Hash & Sort · Clínica Veterinária

Projeto Final — Resolução de Problemas Estruturados em Computação  
Turma A Manhã · Profa. Lisiane Reips  
**Cenário A: Clínica Veterinária**

## Grupo

- Matheus  
- Murilo  
- Adriano  
- Bernardo
- Eduardo

## Sobre o projeto

Sistema de console em Java que simula o fichário de uma clínica veterinária.  
Os pets são armazenados numa **tabela hash própria** (sem `HashMap` ou similares),  
usando o nome do pet como chave. Pets com o mesmo nome caem no mesmo balde —  
exatamente como na recepção da clínica, onde todas as Lunas ficam na mesma pasta.

Na sala de espera, os pacientes são ordenados pelo número do microchip usando  
**Bubble Sort**, **Insertion Sort** e **Quick Sort**, implementados do zero,  
com contagem de comparações e trocas.


> O arquivo `Dados.txt` precisa estar na mesma pasta de onde você executa o programa.


## Estrutura dos arquivos

| Arquivo | Descrição |
|---|---|
| `Main.java` | Menu principal e fluxo do programa |
| `Pet.java` | Classe do pet (microchip, nome, espécie, mania) |
| `NoPet.java` | Nó da lista encadeada usada no encadeamento |
| `TabelaHash.java` | Tabela hash com encadeamento |
| `FuncaoHash.java` | Interface das funções hash |
| `HashSoma.java` | Função hash 1: soma dos códigos ASCII |
| `HashPolinomial.java` | Função hash 2 (do grupo): polinomial base 37 com peso de posição |
| `Ordenacao.java` | Bubble Sort e Insertion Sort com narração |
| `QuickSort.java` | Quick Sort (pivô no último elemento) com narração |
| `GeradorPets.java` | Gerador de 1.000 e 10.000 pets aleatórios com microchips únicos |
| `GrandeCorrida.java` | Comparação de desempenho dos 3 algoritmos (Parte 3) |
| `LeitorDados.java` | Lê `Dados.txt` e cria a lista inicial de pets |
| `TesteTabela.java` | Testa a tabela com M = 13, 17 e 31 nas duas funções hash |
| `TesteCarga.java` | Testa com 10.000 pets em M = 17, 101, 1009 e 10007 |
| `Dados.txt` | 50 pets iniciais (formato: `microchip;nome;especie;mania`) |

## Menu do programa

```
╔══════════════════════════════════════════╗
║          ♥ PET HASH & SORT ♥             ║
╠══════════════════════════════════════════╣
║ 1 - Cadastrar pet                        ║
║ 2 - Buscar pets por nome                 ║
║ 3 - Remover pet (microchip)              ║
║ 4 - Mostrar fichário (tabela hash)       ║
║ 5 - Estatísticas da tabela               ║
║ 6 - Organizar a sala de espera           ║
║ 7 - Modo passo a passo                   ║
║ 8 - A Grande Corrida                     ║
║ 0 - Fechar a clínica                     ║
╚══════════════════════════════════════════╝
```

## Decisões técnicas

- **Chave da tabela hash:** nome do pet em maiúsculas e sem acento (`LUNA`, `THOR`, `PACOCA`).  
- **Colisões:** tratadas por encadeamento (lista ligada em cada balde).  
- **Tamanhos testados:** M = 13, M = 17 e M = 31 (todos primos).  
- **Funções hash:** soma dos códigos ASCII e polinomial própria (base 37 com peso de posição).  
- **Pivot do Quick Sort:** último elemento (esquema de Lomuto) — degrada para O(n²) em dados já ordenados, o que aparece na Grande Corrida e é analisado no relatório.  
- **Grande Corrida:** executa numa thread com 256 MB de pilha para evitar `StackOverflowError` com n = 10.000 no Quick Sort em ordem inversa.

