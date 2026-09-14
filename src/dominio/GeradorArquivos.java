package dominio;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GeradorArquivos {

    public static void main(String[] args) {
        /*Metodo para gerar arquivos de teste de desempenho com diferentes tamanhos de entrada
        * Comentar ou remover gerarArquivo, ou mudar parametro
        * Mudar nome do arquivo desejado de entrada<N> para entrada.txt no root */
        gerarArquivo(100000);
        gerarArquivo(200000);
        gerarArquivo(400000);
        gerarArquivo(800000);
        gerarArquivo(25000);
        gerarArquivo(50000);
        gerarArquivo(75000);

        System.out.println("Arquivos gerados com sucesso!");
    }

    private static void gerarArquivo(int quantidade) {

        String nomeArquivo = "entrada" +  quantidade + ".txt";
        /* BufferedWriter para escrever em um fluxo de saida com buffer
        * newBufferedWriter cria um BufferedWriter para o arquivo
        * Path.of cria um objeto Path a partir do caminho*/
        try (BufferedWriter escritor = Files.newBufferedWriter(Path.of(nomeArquivo))) {
            // Gera nome padronizado com escritor e quebra de linha
            for (int i = 1; i <= quantidade; i++) {
                String nome = String.format("Contato%06d", i);
                String telefone = String.format("279%08d", i);
                escritor.write(nome + ";" + telefone);
                escritor.newLine();
            }

            System.out.println(nomeArquivo + " criado com " + quantidade + " contatos.");
        } catch (IOException e) {
            System.out.println("Erro ao gerar " + nomeArquivo + ": " + e.getMessage());
        }
    }
}