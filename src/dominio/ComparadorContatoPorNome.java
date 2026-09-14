package dominio;

import java.util.Comparator;

public class ComparadorContatoPorNome implements Comparator<Contato> {

    @Override
    public int compare(Contato contato1, Contato contato2) {
        /*Compara dois contatos por nome (strings),
        * CompareTo compara por ordem alfabética */
        return contato1.getNome().compareTo(contato2.getNome());
    }
}