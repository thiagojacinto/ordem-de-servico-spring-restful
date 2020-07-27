# Gestão de Ordens de Serviço

API RESTful para sistema de gestão de Ordem de Serviços, construída com Java 11, Spring 2.3+ e MySQL 8+. O [MySQL Workbench](https://dev.mysql.com/downloads/workbench/) foi utilizado para melhor visualização do conteúdo do banco de dados durante o desenvolvimento. O deploy realizado no serviço Heroku adaptou o banco de dados para uso do PostgreSQL -> https://os-restapi.herokuapp.com/swagger-ui/

## ***O quê?***

Projeto Spring Boot com as melhores práticas divulgadas no curso expresso da [AlgaWorks](https://github.com/algaworks/curso-spring-rest-para-iniciantes), realizado entre **15 e 19 de junho de 2020**. 

O projeto consiste de uma API que faça a gestão de Ordens de Serviços, com bases de dados de clientes, ordens de serviços e comentários nas ordens.

## ***Por quê?***

Revisar e aprender técnicas de desenvolvimento de API RESTful com as potencialidades trazidas pelo framework / ecossistema [**Spring**](https://spring.io/quickstart).

## B Ô N U S

> *Testes, testes e mais testes!*
### a. _Testando_

Testes realizados com JUint e Mockito direto do Spring Boot Test Starter e também com [SpringMockMvc](https://mvnrepository.com/artifact/io.rest-assured/spring-mock-mvc). Confira o link direto [clicando aqui](https://github.com/thiagojacinto/ordem-de-servico-spring-restful/tree/master/os-api/src/test/java/com/thiagojacinto/osrestapi).

### b. _Documentação da API_

Utilizando a biblioteca [SpringFox](http://springfox.github.io/springfox/docs/current/#configuring-springfox) que implementa o Swagger, em sua versão 3.0.0, foi implementada a documentação da API. Essa pode ser acessada pelo endpoint `localhost:8080/swagger-ui/` pelo browser assim que o programa estiver rodando, após digitar `java -jar #RELEASE-VERSION` no terminal.

<p align="center">
  <img src="https://github.com/thiagojacinto/ordem-de-servico-spring-restful/blob/master/swagger-ui-output.png?raw=true" alt="Swagger-UI printscreen"/>
</p>
