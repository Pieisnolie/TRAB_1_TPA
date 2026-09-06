# Sistema de Gerenciamento de Contatos

Projeto em **Java** para gerenciamento de contatos utilizando listas encadeadas e diferentes critérios de busca.

## Funcionalidades

O sistema permite:

* Carregar contatos a partir de um arquivo `entrada.txt`.
* Adicionar novos contatos.
* Pesquisar contatos por nome.
* Pesquisar contatos por telefone.
* Remover contatos pelo telefone.
* Alterar nome e telefone de contatos existentes.
* Impedir o cadastro de telefones duplicados.
* Utilizar listas ordenadas ou não ordenadas.
* Exibir o tempo de execução das operações de leitura, busca e remoção.
* Exibir a quantidade total de contatos ao encerrar o programa.

## Estrutura

O projeto utiliza uma estrutura de **lista encadeada (`ListaEncadeada`)** através da interface `IColecao`.

São mantidas duas estruturas para os contatos:

* Uma lista organizada por **nome**.
* Uma lista organizada por **telefone**.

Também é utilizado um `HashSet` para controlar os telefones já cadastrados e evitar duplicidades.

## Dependências

O projeto utiliza apenas recursos padrão do Java e as classes desenvolvidas no próprio projeto.

É necessário ter:

* **Java JDK 11 ou superior**.

Não são necessárias bibliotecas externas.

## Instalação

### 1. Clone o repositório

```bash
git clone https://github.com/Pieisnolie/TRAB_1_TPA.git
```

### 2. Entre na pasta do projeto

```bash
cd TRAB_1_TPA
```

### 3. Compile o projeto

Compile o projeto utilizando o JDK configurado no ambiente.

Caso esteja utilizando uma IDE, basta importar o projeto e executar a classe `Main`.

## Arquivo de Entrada

Para utilizar a opção de carregamento de dados, o programa espera encontrar um arquivo chamado `entrada.txt` no diretório de execução.

Cada linha deve conter um **nome** e um **telefone**, separados por `;`.

### Exemplo de `entrada.txt`

```text
Gabriel;27999999999
Dominic;27988888888
Marcos;27977777777
Ryan;27966666666
Lucas;27955555555
Bruno;27944444444
Ana;27933333333
```

Linhas vazias são ignoradas e telefones duplicados não são cadastrados.

## Gerador de Arquivos

O projeto possui a classe GeradorArquivos, utilizada para gerar arquivos com grandes quantidades de contatos para testes de desempenho.

A quantidade de contatos é definida no método main() da classe GeradorArquivos. Por exemplo:
```text
gerarArquivo(50000);
```
A execução desse comando gera um arquivo chamado:
```text
entrada50000.txt
```
Os contatos são gerados automaticamente seguindo o formato:
```text
Contato000001;27900000001
Contato000002;27900000002
Contato000003;27900000003
```
O arquivo gerado possui o nome entrada<N>.txt, onde N representa a quantidade de contatos.

Para utilizar o arquivo no sistema principal, é necessário renomear o arquivo gerado para entrada.txt.
## Execução

Ao iniciar o programa, será perguntado se as listas devem ser ordenadas:

```text
Lista ordenada? (S/N)
```

Em seguida, será apresentado o menu principal:

```text
===== MENU =====
1 - Carregar dados de arquivo
2 - Adicionar contato
3 - Pesquisar contato por nome
4 - Pesquisar contato por telefone
5 - Remover contato por telefone
6 - Alterar dados de contato
0 - Sair
```

Ao selecionar a opção `0`, o programa informa a quantidade total de contatos cadastrados antes de ser encerrado.

## Tecnologias

* **Java**
* **Lista Encadeada**
* **HashSet**
* **Scanner**
* **BufferedReader**
* **Files**
* **Path**
* **Comparator**
* Comparadores para ordenação por **nome** e **telefone**

## Organização dos Dados

O sistema utiliza diferentes critérios para trabalhar com os contatos. Os contatos podem ser organizados e pesquisados utilizando o **nome** ou o **telefone** como referência.

Para a ordenação por nome, é utilizado um comparador que compara os nomes dos contatos. Para a ordenação por telefone, é utilizado um comparador que compara os números de telefone.