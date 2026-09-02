package colecao;

public interface IColecao<T> {

    boolean adicionar(T novoValor);

    T pesquisar(T valor);

    boolean remover(T valor);

    int quantidadeNos();
}