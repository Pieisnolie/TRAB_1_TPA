package dominio;

import colecao.IColecao;
import listaencadeada.ListaEncadeada;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        System.out.println("Lista ordenada? (S/N)");
        String resposta = entrada.nextLine();

        boolean ordenada = resposta.equalsIgnoreCase("S");

        IColecao<Contato> contatosPorNome =
                new ListaEncadeada<Contato>(
                        new ComparadorContatoPorNome(),
                        ordenada
                );

        IColecao<Contato> contatosPorTelefone =
                new ListaEncadeada<Contato>(
                        new ComparadorContatoPorTelefone(),
                        ordenada
                );

        System.out.println("Lista criada com sucesso.");

        if (ordenada) {
            System.out.println("Modo: lista ordenada.");
        } else {
            System.out.println("Modo: lista desordenada.");
        }

        entrada.close();
    }
}