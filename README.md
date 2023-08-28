# Post Messenger

O Post Messenger é um projeto que oferece uma plataforma para criação, gerenciamento e processamento de posts.
Ele foi desenvolvido utilizando tecnologias Java e Spring Boot, e possui integração com serviços externos por meio do WebClient.

## Funcionalidades

- Criação de novos posts
- Listagem de todos os posts
- Atualização de um post existente
- Desabilitação de um post
- Reprocessamento de um post

## Tecnologias Utilizadas

- Java
- Spring Boot
- Hibernate
- H2 Database (em memória)
- WebClient (para integração com serviços externos)
- Springdoc OpenAPI UI

## Documentação da API

O projeto inclui uma documentação interativa da API, que pode ser acessada através da seguinte URL: http://localhost:8080/swagger-ui.html.
Essa documentação fornece detalhes sobre os endpoints disponíveis, seus parâmetros e respostas, permitindo testar e entender melhor as funcionalidades oferecidas pelo Post Messenger.

## Configuração do Banco de Dados

O projeto utiliza o H2 Database em memória para armazenar os dados. O banco de dados é configurado automaticamente quando a aplicação é iniciada.

## Endpoints Disponíveis

- `GET /posts`: Retorna a lista de todos os posts.
- `GET /posts/{id}`: Retorna os detalhes de um post específico.
- `POST /posts`: Cria um novo post.
- `PUT /posts/{id}`: Atualiza um post existente.
- `DELETE /posts/{id}`: Desabilita um post existente.
- `PUT /posts/{id}/reprocess`: Reprocessa um post existente.

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para criar pull requests.

## Como Executar

1. Clone o repositório para a sua máquina:
   
   "git clone https://github.com/kia735/Challenger_3-PostMessenger-"
   
2. Navegue para o diretório do projeto:

    cd Challenger_3-PostMessenger-

3. Execute a aplicação usando o Maven:

    ./mvnw spring-boot:run


4. Acesse a aplicação por:

    http://localhost:8080

   


