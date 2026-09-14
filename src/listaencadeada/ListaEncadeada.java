package listaencadeada;

// Importa a interface IColecao, que define os métodos que a ListaEncadeada precisa implementar
import colecao.IColecao;
// Comparator permite comparar contatos por nome ou telefone
import java.util.Comparator;

public class ListaEncadeada<T> implements IColecao<T> {

    // Guarda o primeiro nó da lista
    // A partir dele conseguimos acessar todos os outros nós
    private No<T> prim;
    private final Comparator<T> comparador;
    // Indica se a lista será ordenada ou não. true = ordenada;false = não ordenada
    private final boolean ehOrdenada;

    // Construtor da lista, recebe o comparador e a informação se a lista será ordenada
    public ListaEncadeada(Comparator<T> comparador, boolean ehOrdenada) {
        this.comparador = comparador;
        this.ehOrdenada = ehOrdenada;
    }

    @Override
    public boolean adicionar(T novoValor) {

        // Cria um novo nó para armazenar o valor recebido
        No<T> novo = new No<>(novoValor);
        // Se prim for null, significa que a lista está vazia
        // Nesse caso, o novo nó passa a ser o primeiro da lista
        if (prim == null) {
            prim = novo;
            return true;
        }
        // Se a lista não for ordenada, o novo elemento é inserido diretamente no início. (complexidade O(1) )
        if (!ehOrdenada) {
            novo.setProx(prim);
            prim = novo;
            return true;
        }

        // Lista ordenada, compara o novo valor com o primeiro valor da lista
        // Se o novo valor for menor ou igual ao primeiro, ele deve ser colocado no início
        if (comparador.compare(novoValor, prim.getValor()) <= 0) {
            novo.setProx(prim);
            prim = novo;
            return true;
        }
        // Variável usada para percorrer a lista, começamos pelo primeiro nó
        No<T> atual = prim;

        // Percorre a lista procurando a posição para inserir o novo elemento
        // atual.getProx() != null: verifica se existe um próximo nó
        // comparador.compare(...) < 0: verifica se o próximo elemento ainda vem antes do novo valor na ordenação
        // No pior caso, pode ser necessário percorrer toda a lista,por isso O(n)
        while (atual.getProx() != null
                && comparador.compare(
                atual.getProx().getValor(),
                novoValor) < 0) {

            // Avança para o próximo nó
            atual = atual.getProx();
        }
        // O nó "atual" passa a apontar para o novo nó, assim o novo elemento é encaixado na lista
        novo.setProx(atual.getProx());
        atual.setProx(novo);

        return true;
    }

    @Override
    public T pesquisar(T valor) {
    // Começa a pesquisa pelo primeiro nó da lista
        No<T> atual = prim;
        // Continua enquanto ainda existir um nó para verificar, no pior caso, pode percorrer todos os elementos O(n)
        while (atual != null) {
            // Compara o valor do nó atual com o valor procurado
            // resultado = 0  -> são iguais
            // resultado < 0  -> atual vem antes do valor procurado
            // resultado > 0  -> atual vem depois do valor procurado
            int resultadoComparacao = comparador.compare(atual.getValor(), valor);

            // Se a comparação for igual a zero o elemento foi encontrado.
            if (resultadoComparacao == 0) {
                return atual.getValor();
            }

            if (ehOrdenada && resultadoComparacao > 0) {
                return null;
            }

            atual = atual.getProx();
        }

        return null;
    }

    @Override
    public boolean remover(T valor) {

        if (prim == null) {
            return false;
        }

        int comparacaoPrimeiro = comparador.compare(prim.getValor(), valor);

        if (comparacaoPrimeiro == 0) {
            prim = prim.getProx();
            return true;
        }

        if (ehOrdenada && comparacaoPrimeiro > 0) {
            return false;
        }
        // "anterior" será necessário para fazer a ligação correta quando um elemento for removido.
        No<T> anterior = prim;
        No<T> atual = prim.getProx();

        while (atual != null) {

            int resultadoComparacao = comparador.compare(atual.getValor(), valor);

            if (resultadoComparacao == 0) {
                anterior.setProx(atual.getProx());
                return true;
            }

            if (ehOrdenada && resultadoComparacao > 0) {
                return false;
            }

            anterior = atual;
            atual = atual.getProx();
        }

        return false;
    }

    @Override
    public int quantidadeNos() {

        int quantidade = 0;
        No<T> atual = prim;

        while (atual != null) {
            quantidade++;
            atual = atual.getProx();
        }

        return quantidade;
    }

    @Override
    public String toString() {

        StringBuilder resultado = new StringBuilder("[");

        No<T> atual = prim;

        while (atual != null) {

            resultado.append(atual.getValor());

            if (atual.getProx() != null) {
                resultado.append(",");
            }

            atual = atual.getProx();
        }

        resultado.append("]");

        return resultado.toString();
    }
}