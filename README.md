# account transfer api

### Sobre:
Está API tem o objetivo de realizar transferencia de valores entre contas.
Para a realização da jornada o fluxo é executado da seguinte forma: O serviço realiza integração com 3 APIs externas:  
* Cadastro:
  * Informaões do cliente como nome, telefone e tipo pessoa.
* Conta:
  * Informações da conta como saldo, ativo e limiteDiario
* Bacen:
  * Essa API faz-se necessario, pois temos que notificar ao bacen quando uma tranferencia é realizada com sucesso.

A API conta com as seguintes implementações:
* cache e tempo de expiração: Foi implementado cache nas chamadas das APIs de Cadastro e Conta para ajudar a reduzir o número de chamadas a APIs externas, o que pode significativamente melhorar a performance e reduzir a carga sobre os sistemas de backend.
* Solução para limitações de rate limit: Foi implementado estratégias para minimizar o impacto das limitações de rate limit. Para esse solução foi implementado um ErrorDecoder customizado e um Retryer para garantir que a aplicação lide de forma mais robusta, minimizando o impacto de falhas e melhorando a confiabilidade da integração entre sistemas.

### Tecnologia listada abaixo:

* Java 17
* Spring boot
* Maven
* FeignClient
* Lombok
* Cache Redis
* Docker
* Junit 5
* Mockito
* [Wiremock](https://wiremock.org/)

### 

### Arquitetura:
![Alt text](/documentacao/arch.png "Optional title")

### Informações sobre as requisições via Postman:
* Requisição realiza com sucesso:
  ![Alt text](/documentacao/Sucesso.png "Optional title")
* Requisição realiza com erro de cliente não encontrado:
  ![Alt text](/documentacao/erro_cliente.png "Optional title") 
* Requisição realiza com erro  de limite diario excedido:
  ![Alt text](/documentacao/erro_limite.png "Optional title")  



