/** Nó da lista ligada de cada balde. */
public class NoPet {
    Pet pet;
    NoPet proximo;
 
    NoPet(Pet pet) {
        this.pet = pet;
        this.proximo = null;
    }
}
 