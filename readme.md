## ❄️ Sobre o projeto

Trata-se de uma aplicação que proverá a funcionalidade inerentes a um e-commerce, como cadastro de usuários, produtos e criação de carrinhos e pedidos. Foi criado utilizado Quarkus, um framework Java com foco em microsserviços e o projeto foi feito implementado tal arquitetura.

---

## 😎 Detalhes do projeto 

Tal aplicação faz parte do conjunto de aplicações que juntas dão vida ao projeto DonaFrost.

O repositório que você está agora é o responsável pelo back-end. Os end-point's disponíveis nos microsserviços são consumidos pela aplicação web front-end e pelo app que irá auxiliar o entregador.

Ambas disponiveis em seus referidos repositórios. 👉 [Front-end web](https://github.com/Sandrolaxx/frostNext) e [App do Entregador](https://github.com/Sandrolaxx/DFmobileDeliveryman)

Abaixo temos o diagrama das aplicações em conjunto: 

![diagrama](https://user-images.githubusercontent.com/61207420/150238760-bbf5ac95-a4ab-4443-b431-28e6f7c25e77.png)

* **Microsserviço dos Produtos:** É responsável por gerenciar os produtos existentes no e-commerce, tendo seu próprio banco de dados e CRUD para essas tarefas. Toda informação/alteração realizada é propagada via mensageria para o microsserviço do Marketplace. Foi criado utilizando programação imperativa.

* **Microsserviço de Usuários:** É responsável por gerenciar os usuários que se cadastram em nossa aplicação, tendo seu próprio banco de dados e CRUD para essas tarefas. Também realiza a propagação de seus dados para o microsserviço do Marketplace e também foi criado utilizando programação imperativa.

* **Microsserviço do Marketplace:** É responsável por realizar as principais funcionalidades de um e-commerce, como criar um carrinho, transformar o carrinho em pedido e etc. Também tem seu próprio banco de dados e CRUD para realizar as funcionalidades mencionadas. Diferentemente dos microsserviços anteriores ele foi criado utilizando **programação reativa** que é um dos modos que podemos criar aplicações com o Quarkus, por conta de ele ter esse modelo unificado que junta esses dois paradigmas de programação, é algo bem complicado no começo, mas depois de aprender a utilizar as API's reativas do Quarkus ai é uma delícia programar, tudo é stream, uma beleza!

---

## 🤓 Tecnologias utilizadas

* ⚡ Framework Java - [Quarkus](https://quarkus.io/)
* 🔐 Autenticação - [Keycloak](https://www.keycloak.org/)
* 📨 Mensageria - [ActiveMQ](https://activemq.apache.org/)
* 🐳 Containers - [Docker](https://www.docker.com/)
* 📖 Documentação - [Swagger UI](https://swagger.io/tools/swagger-ui/)
* 💾 Database - [Postgres v.13](https://www.postgresql.org/)
* 📌 Tracing - [Jaeger Tracing](https://www.jaegertracing.io/)
* 📊 Análise de Métricas - [Grafana](https://grafana.com/)
* 🔔 Monitoramento - [Prometheus](https://prometheus.io/docs/introduction/overview/)
* ✅ Teste Unitário - [jUnit5](https://junit.org/junit5/) e [Approval Tests](https://approvaltests.com/)
* 🔥 Programação Reativa - [SmallRye-Mutiny](https://smallrye.io/smallrye-mutiny/pages/philosophy)

---

## 🧑‍💻 Como iniciar a aplicação

Necessário java11 ou superior e docker/docker-compose, após clonar o projeto será necessário executar o seguinte comando:

```bash
docker-compose run  docker-compose.yml
```
O comando acima vai iniciar todos os container necessários para executar os microsserviços.

Após todos os containers estarem UP, vamos executar o seguinte comando:

```bash
./mvnw quarkus:dev 
```

Abra [http://localhost:8080](http://localhost:8080) com seu navegador para ver o microsserviço de Produtos.

Abra [http://localhost:8081](http://localhost:8081) com seu navegador para ver o microsserviço de Usuário.

Abra [http://localhost:8082](http://localhost:8082) com seu navegador para ver o microsserviço de Marketplace.

Documentação disponível em **/q/swagger-ui**.

---

## 📝 Próximos passos

* Criar end-point de geração de QR Code estático Pix.
* Aprimorar coleta de log's e monitoramento.

Caso queira me ajudar com o projeto ficarei muito feliz em aceitar seu pull request 🙂. 