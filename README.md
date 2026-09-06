Sistema de Gerenciamento de Contatos

Projeto em Java para gerenciamento de contatos utilizando listas encadeadas e diferentes critérios de busca.

Funcionalidades

O sistema permite:

Carregar contatos a partir de um arquivo entrada.txt.
Adicionar novos contatos.
Pesquisar contatos por nome.
Pesquisar contatos por telefone.
Remover contatos pelo telefone.
Alterar nome e telefone de contatos existentes.
Impedir o cadastro de telefones duplicados.
Utilizar listas ordenadas ou não ordenadas.
Exibir o tempo de execução das operações de leitura, busca e remoção.
Exibir a quantidade total de contatos ao encerrar o programa.
Estrutura

O projeto utiliza uma estrutura de lista encadeada (ListaEncadeada) através da interface IColecao.

São mantidas duas estruturas para os contatos:

Uma lista organizada por nome.
Uma lista organizada por telefone.

Também é utilizado um HashSet para controlar telefones já cadastrados e evitar duplicidades.

Dependências

O projeto utiliza apenas recursos padrão do Java e as classes desenvolvidas no próprio projeto.

É necessário ter:

Java JDK 11 ou superior.

Não são necessárias bibliotecas externas.

Instalação

Clone o repositório:

git clone <URL_DO_REPOSITORIO>


Entre na pasta do projeto:

cd <NOME_DO_PROJETO>


Compile o projeto utilizando o JDK configurado no ambiente.

Caso esteja utilizando uma IDE, basta importar o projeto e executar a classe Main.

Arquivo de entrada

Para utilizar a opção de carregamento de dados, o programa espera encontrar um arquivo chamado entrada.txt no diretório de execução.

Cada linha deve conter um nome e um telefone separados por ;.

Exemplo:

João Silva;11999999999
Maria Souza;11888888888
Pedro Santos;11777777777


Linhas vazias são ignoradas e telefones duplicados não são cadastrados.

Execução

Ao iniciar o programa, será perguntado se as listas devem ser ordenadas:

Lista ordenada? (S/N)


Em seguida, será apresentado o menu principal:

===== MENU =====
1 - Carregar dados de arquivo
2 - Adicionar contato
3 - Pesquisar contato por nome
4 - Pesquisar contato por telefone
5 - Remover contato por telefone
6 - Alterar dados de contato
0 - Sair


Ao selecionar a opção 0, o programa informa a quantidade total de contatos cadastrados antes de ser encerrado.

Tecnologias
Java
Estrutura de dados: Lista Encadeada
HashSet
Scanner
BufferedReader
Files e Path
Comparadores para ordenação por nome e telefone