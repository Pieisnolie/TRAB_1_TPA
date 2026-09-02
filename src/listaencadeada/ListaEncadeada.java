package listaencadeada;

import colecao.IColecao;

import java.util.Comparator;

public class ListaEncadeada<T> implements IColecao<T> {

    private No<T> prim;
    private final Comparator<T> comparador;
    private final boolean ehOrdenada;

    public ListaEncadeada(Comparator<T> comparador, boolean ehOrdenada) {
        this.comparador = comparador;
        this.ehOrdenada = ehOrdenada;
    }

    @Override
    public boolean adicionar(T novoValor) {

        No<T> novo = new No<>(novoValor);

        if (prim == null) {
            prim = novo;
            return true;
        }

        if (!ehOrdenada) {
            novo.setProx(prim);
            prim = novo;
            return true;
        }

        if (comparador.compare(novoValor, prim.getValor()) <= 0) {
            novo.setProx(prim);
            prim = novo;
            return true;
        }

        No<T> atual = prim;

        while (atual.getProx() != null
                && comparador.compare(
                atual.getProx().getValor(),
                novoValor) < 0) {

            atual = atual.getProx();
        }

        novo.setProx(atual.getProx());
        atual.setProx(novo);

        return true;
    }

    @Override
    public T pesquisar(T valor) {

        No<T> atual = prim;

        while (atual != null) {

            int resultadoComparacao =
                    comparador.compare(atual.getValor(), valor);

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

        int comparacaoPrimeiro =
                comparador.compare(prim.getValor(), valor);

        if (comparacaoPrimeiro == 0) {
            prim = prim.getProx();
            return true;
        }

        if (ehOrdenada && comparacaoPrimeiro > 0) {
            return false;
        }

        No<T> anterior = prim;
        No<T> atual = prim.getProx();

        while (atual != null) {

            int resultadoComparacao =
                    comparador.compare(atual.getValor(), valor);

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