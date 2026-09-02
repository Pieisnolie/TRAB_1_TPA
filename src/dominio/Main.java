package dominio;

import colecao.IColecao;
import listaencadeada.ListaEncadeada;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {

    private static final Scanner entrada = new Scanner(System.in);

    private static IColecao<Contato> contatosPorNome;
    private static IColecao<Contato> contatosPorTelefone;

    public static void main(String[] args) {

        System.out.println("Lista ordenada? (S/N)");
        String resposta = entrada.nextLine();

        boolean ordenada = resposta.equalsIgnoreCase("S");

        contatosPorNome =
                new ListaEncadeada<Contato>(
                        new ComparadorContatoPorNome(),
                        ordenada
                );

        contatosPorTelefone =
                new ListaEncadeada<Contato>(
                        new ComparadorContatoPorTelefone(),
                        ordenada
                );

        int opcao;

        do {

            exibirMenu();

            opcao = Integer.parseInt(entrada.nextLine());

            switch (opcao) {

                case 1:
                    carregarArquivo();
                    break;

                case 2:
                    adicionarContato();
                    break;

                case 3:
                    pesquisarPorNome();
                    break;

                case 4:
                    pesquisarPorTelefone();
                    break;

                case 5:
                    removerPorTelefone();
                    break;

                case 6:
                    alterarContato();
                    break;

                case 0:
                    System.out.println(
                            "Quantidade total de contatos: "
                                    + contatosPorTelefone.quantidadeNos()
                    );

                    System.out.println("Programa encerrado.");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);

        entrada.close();
    }


    private static void exibirMenu() {

        System.out.println();
        System.out.println("===== MENU =====");
        System.out.println("1 - Carregar dados de arquivo");
        System.out.println("2 - Adicionar contato");
        System.out.println("3 - Pesquisar contato por nome");
        System.out.println("4 - Pesquisar contato por telefone");
        System.out.println("5 - Remover contato por telefone");
        System.out.println("6 - Alterar dados de contato");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }


    private static void carregarArquivo() {

        long inicio = System.nanoTime();

        int quantidadeAdicionada = 0;

        try (BufferedReader leitor =
                     Files.newBufferedReader(Path.of("entrada.txt"))) {

            String linha;

            while ((linha = leitor.readLine()) != null) {

                if (linha.isBlank()) {
                    continue;
                }

                String[] dados = linha.split(";", 2);

                if (dados.length != 2) {
                    System.out.println(
                            "Linha inválida ignorada: " + linha
                    );
                    continue;
                }

                String nome = dados[0].trim();
                String telefone = dados[1].trim();

                Contato contatoExistente =
                        contatosPorTelefone.pesquisar(
                                new Contato("", telefone)
                        );

                if (contatoExistente != null) {
                    continue;
                }

                Contato contato =
                        new Contato(nome, telefone);

                contatosPorNome.adicionar(contato);
                contatosPorTelefone.adicionar(contato);

                quantidadeAdicionada++;
            }

            long fim = System.nanoTime();

            System.out.println(
                    quantidadeAdicionada
                            + " contatos carregados."
            );

            System.out.println(
                    "Tempo de leitura e montagem das listas: "
                            + (fim - inicio)
                            + " ns"
            );

        } catch (IOException e) {

            System.out.println(
                    "Erro ao ler entrada.txt: "
                            + e.getMessage()
            );
        }
    }


    private static void adicionarContato() {

        System.out.print("Nome: ");
        String nome = entrada.nextLine();

        System.out.print("Telefone: ");
        String telefone = entrada.nextLine();

        Contato contatoExistente =
                contatosPorTelefone.pesquisar(
                        new Contato("", telefone)
                );

        if (contatoExistente != null) {

            System.out.println(
                    "Já existe um contato com esse telefone."
            );

            return;
        }

        Contato novoContato =
                new Contato(nome, telefone);

        contatosPorNome.adicionar(novoContato);
        contatosPorTelefone.adicionar(novoContato);

        System.out.println("Contato adicionado com sucesso.");
    }


    private static void pesquisarPorNome() {

        System.out.print("Nome do contato: ");
        String nome = entrada.nextLine();

        Contato chave =
                new Contato(nome, "");

        long inicio = System.nanoTime();

        Contato encontrado =
                contatosPorNome.pesquisar(chave);

        long fim = System.nanoTime();

        if (encontrado == null) {

            System.out.println("Contato não existe.");

        } else {

            System.out.println(
                    "Telefone: "
                            + encontrado.getTelefone()
            );
        }

        System.out.println(
                "Tempo da busca: "
                        + (fim - inicio)
                        + " ns"
        );
    }


    private static void pesquisarPorTelefone() {

        System.out.print("Telefone do contato: ");
        String telefone = entrada.nextLine();

        Contato chave =
                new Contato("", telefone);

        long inicio = System.nanoTime();

        Contato encontrado =
                contatosPorTelefone.pesquisar(chave);

        long fim = System.nanoTime();

        if (encontrado == null) {

            System.out.println("Contato não existe.");

        } else {

            System.out.println(
                    "Nome: "
                            + encontrado.getNome()
            );
        }

        System.out.println(
                "Tempo da busca: "
                        + (fim - inicio)
                        + " ns"
        );
    }


    private static void removerPorTelefone() {

        System.out.print("Telefone do contato: ");
        String telefone = entrada.nextLine();

        Contato chave =
                new Contato("", telefone);

        Contato encontrado =
                contatosPorTelefone.pesquisar(chave);

        if (encontrado == null) {

            System.out.println("Contato não existe.");
            return;
        }

        long inicio = System.nanoTime();

        boolean removido =
                contatosPorTelefone.remover(chave);

        long fim = System.nanoTime();

        if (removido) {

            contatosPorNome.remover(encontrado);

            System.out.println(
                    "Contato excluído com sucesso."
            );

        } else {

            System.out.println(
                    "Contato não existia."
            );
        }

        System.out.println(
                "Tempo da remoção: "
                        + (fim - inicio)
                        + " ns"
        );
    }


    private static void alterarContato() {

        System.out.print("Nome do contato: ");
        String nome = entrada.nextLine();

        Contato contato =
                contatosPorNome.pesquisar(
                        new Contato(nome, "")
                );

        if (contato == null) {

            System.out.println("Contato não existe.");
            return;
        }

        System.out.println(
                "Telefone atual: "
                        + contato.getTelefone()
        );

        System.out.print("Novo nome: ");
        String novoNome = entrada.nextLine();

        System.out.print("Novo telefone: ");
        String novoTelefone = entrada.nextLine();

        Contato contatoMesmoTelefone =
                contatosPorTelefone.pesquisar(
                        new Contato("", novoTelefone)
                );

        if (contatoMesmoTelefone != null
                && contatoMesmoTelefone != contato) {

            System.out.println(
                    "Já existe outro contato com esse telefone."
            );

            return;
        }

        contatosPorNome.remover(contato);
        contatosPorTelefone.remover(contato);

        contato.setNome(novoNome);
        contato.setTelefone(novoTelefone);

        contatosPorNome.adicionar(contato);
        contatosPorTelefone.adicionar(contato);

        System.out.println(
                "Contato alterado com sucesso."
        );
        }
    }