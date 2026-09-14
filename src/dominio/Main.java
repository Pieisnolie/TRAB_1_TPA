package dominio;

import colecao.IColecao;
import listaencadeada.ListaEncadeada;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {

    // Scanner para ler as entradas do usuário
    private static final Scanner entrada = new Scanner(System.in);

    // Coleção de contatos organizada/ordenada por nome
    private static IColecao<Contato> contatosPorNome;

    // Coleção de contatos organizada/ordenada por telefone
    private static IColecao<Contato> contatosPorTelefone;

    // Conjunto auxiliar para checar rapidamente se um telefone já está cadastrado
    private static final Set<String> telefonesCadastrados =
            new HashSet<>();

    public static void main(String[] args) {

        System.out.println("Lista ordenada? (S/N)");
        String resposta = entrada.nextLine();

        boolean ordenada = resposta.equalsIgnoreCase("S");

        // Inicializa a lista de contatos comparando por nome
        contatosPorNome =
                new ListaEncadeada<Contato>(
                        new ComparadorContatoPorNome(),
                        ordenada
                );

        // Inicializa a lista de contatos comparando por telefone
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

        // Libera o recurso do Scanner ao final da execução
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

        // try-with-resources: garante o fechamento automático do BufferedReader
        try (BufferedReader leitor =
                     Files.newBufferedReader(Path.of("entrada.txt"))) {

            String linha;

            while ((linha = leitor.readLine()) != null) {

                // Ignora linhas vazias
                if (linha.isBlank()) {
                    continue;
                }

                // Divide a linha em no máximo 2 partes: nome e telefone
                String[] dados = linha.split(";", 2);

                // Linha que não tem exatamente nome e telefone é descartada
                if (dados.length != 2) {

                    System.out.println(
                            "Linha inválida ignorada: " + linha
                    );

                    continue;
                }

                String nome = dados[0].trim();
                String telefone = dados[1].trim();

                // Evita cadastrar telefone duplicado
                if (telefonesCadastrados.contains(telefone)) {
                    continue;
                }

                Contato contato = new Contato(nome, telefone);

                // Adiciona o mesmo contato nas duas listas
                contatosPorNome.adicionar(contato);
                contatosPorTelefone.adicionar(contato);

                telefonesCadastrados.add(telefone);

                quantidadeAdicionada++;
            }

            long fim = System.nanoTime();

            System.out.println(quantidadeAdicionada + " contatos carregados.");

            // Converte nanossegundos para milissegundos
            double tempoMs  = (fim - inicio) / 1_000_000.0;

            System.out.println("Tempo de leitura e montagem das listas: " + tempoMs + " ms");

        } catch (IOException e) {
            // Trata erro de leitura (ex: arquivo não encontrado)
            System.out.println("Erro ao ler entrada.txt: " + e.getMessage());
        }
    }


    private static void adicionarContato() {

        System.out.print("Nome: ");
        String nome = entrada.nextLine();

        System.out.print("Telefone: ");
        String telefone = entrada.nextLine();

        if (telefonesCadastrados.contains(telefone)) {

            System.out.println("Já existe um contato com esse telefone.");

            return;
        }

        Contato novoContato = new Contato(nome, telefone);

        contatosPorNome.adicionar(novoContato);
        contatosPorTelefone.adicionar(novoContato);

        telefonesCadastrados.add(telefone);

        System.out.println("Contato adicionado com sucesso.");
    }


    private static void pesquisarPorNome() {

        System.out.print("Nome do contato: ");
        String nome = entrada.nextLine();

        // Cria um contato "chave" apenas com o nome, usado só para a busca
        Contato chave = new Contato(nome, "");

        long inicio = System.nanoTime();

        Contato encontrado = contatosPorNome.pesquisar(chave);

        long fim = System.nanoTime();

        if (encontrado == null) {

            System.out.println("Contato não existe.");

        } else {
            System.out.println("Telefone: " + encontrado.getTelefone());
        }

        double tempoMs  = (fim - inicio) / 1_000_000.0;

        System.out.println("Tempo da busca: " + tempoMs + " ms");
    }


    private static void pesquisarPorTelefone() {

        System.out.print("Telefone do contato: ");
        String telefone = entrada.nextLine();

        // Cria um contato "chave" apenas com o telefone, usado só para a busca
        Contato chave = new Contato("", telefone);

        long inicio = System.nanoTime();

        Contato encontrado = contatosPorTelefone.pesquisar(chave);

        long fim = System.nanoTime();

        if (encontrado == null) {

            System.out.println("Contato não existe.");

        } else {

            System.out.println("Nome: " + encontrado.getNome());
        }

        double tempoMs  = (fim - inicio) / 1_000_000.0;

        System.out.println("Tempo da busca: " + tempoMs + " ms");
    }


    private static void removerPorTelefone() {

        System.out.print("Telefone do contato: ");
        String telefone = entrada.nextLine();

        Contato chave = new Contato("", telefone);

        // Primeiro verifica se o contato existe antes de tentar remover
        Contato encontrado = contatosPorTelefone.pesquisar(chave);

        if (encontrado == null) {

            System.out.println("Contato não existe.");

            return;
        }

        long inicio = System.nanoTime();

        boolean removido = contatosPorTelefone.remover(chave);

        long fim = System.nanoTime();

        if (removido) {

            // Remove também da lista por nome, usando o objeto já encontrado
            contatosPorNome.remover(encontrado);

            telefonesCadastrados.remove(telefone);

            System.out.println("Contato excluído com sucesso.");

        } else {
            System.out.println("Contato não existia.");
        }

        double tempoMs  = (fim - inicio) / 1_000_000.0;

        System.out.println("Tempo da remoção: " + tempoMs + " ms");
    }


    private static void alterarContato() {

        System.out.print("Nome do contato: ");
        String nome = entrada.nextLine();

        Contato contato = contatosPorNome.pesquisar(new Contato(nome, ""));

        if (contato == null) {

            System.out.println("Contato não existe.");

            return;
        }

        System.out.println("Telefone atual: " + contato.getTelefone());

        System.out.print("Novo nome: ");
        String novoNome = entrada.nextLine();

        System.out.print("Novo telefone: ");
        String novoTelefone = entrada.nextLine();

        // Se o telefone for alterado, verifica se o novo já está em uso
        if (!novoTelefone.equals(contato.getTelefone()) && telefonesCadastrados.contains(novoTelefone)) {

            System.out.println("Já existe outro contato com esse telefone.");

            return;
        }

        // Remove o contato das duas listas antes de alterar seus dados,
        // pois a posição dele nelas depende dos valores atuais
        String telefoneAntigo = contato.getTelefone();

        contatosPorNome.remover(contato);
        contatosPorTelefone.remover(contato);

        contato.setNome(novoNome);
        contato.setTelefone(novoTelefone);

        // Reinsere o contato já atualizado, na posição correta
        contatosPorNome.adicionar(contato);
        contatosPorTelefone.adicionar(contato);

        // Atualiza o conjunto de telefones cadastrados
        telefonesCadastrados.remove(telefoneAntigo);
        telefonesCadastrados.add(novoTelefone);

        System.out.println("Contato alterado com sucesso.");
    }
}