package dominio;

import java.util.Comparator;

public class ComparadorContatoPorTelefone implements Comparator<Contato> {

    @Override
    public int compare(Contato contato1, Contato contato2) {
        return contato1.getTelefone().compareTo(contato2.getTelefone());
    }
}