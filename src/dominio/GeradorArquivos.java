package dominio;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GeradorArquivos {

    public static void main(String[] args) {

        gerarArquivo(100000);
        gerarArquivo(200000);
        gerarArquivo(400000);
        gerarArquivo(800000);

        System.out.println("Arquivos gerados com sucesso!");
    }

    private static void gerarArquivo(int quantidade) {

        String nomeArquivo = "entrada" + quantidade + ".txt";

        try (BufferedWriter escritor =
                     Files.newBufferedWriter(Path.of(nomeArquivo))) {

            for (int i = 1; i <= quantidade; i++) {

                String nome = String.format("Contato%06d", i);

                String telefone =
                        String.format("279%08d", i);

                escritor.write(nome + ";" + telefone);
                escritor.newLine();
            }

            System.out.println(
                    nomeArquivo + " criado com "
                            + quantidade + " contatos.");

        } catch (IOException e) {

            System.out.println(
                    "Erro ao gerar "
                            + nomeArquivo
                            + ": "
                            + e.getMessage());
        }
    }
}