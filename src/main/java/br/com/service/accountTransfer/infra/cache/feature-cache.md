
# 🧠 História Técnica: Tornar Redis Cache Mais Resiliente e Evitar Forte Acoplamento

## 🎯 Objetivo

O objetivo desta tarefa é reforçar a **resiliência da aplicação** ao utilizar Redis como cache distribuído, garantindo que:

- A aplicação não dependa exclusivamente da disponibilidade do Redis para funcionar corretamente.
- Falhas de cache sejam tratadas de forma segura e silenciosa, sem impactar o fluxo principal.
- Os caches sejam criados e gerenciados com controle explícito, evitando criação dinâmica desgovernada.
- O sistema suporte **fallback seguro** quando Redis estiver indisponível.

## 📌 Contexto

Atualmente, utilizamos o Redis como cache distribuído com a implementação baseada em `LettuceConnectionFactory` e `RedisCacheManager`. A aplicação pode apresentar falhas severas caso Redis esteja indisponível ou instável, pois ainda há um **acoplamento forte** e ausência de estratégia de tolerância a falhas.

---

## 🛠️ O Que Será Entregue

1. **Configuração robusta do Redis** usando `LettuceConnectionFactory` com tentativas de reconexão e tratamento de falhas.
2. **CacheErrorHandler personalizado** (`CustomCacheErrorHandler`) para tratar exceções de forma centralizada.
3. Evitar criação automática de caches não definidos previamente.
4. Gerar dinamicamente apenas caches mapeados por anotações `@Cacheable` que ainda não estão definidos.
5. Validação e documentação clara da estratégia de fallback em caso de indisponibilidade do Redis.

---

## ✅ Critérios de Aceite

- [ ] A aplicação **deve continuar funcionando** mesmo que o Redis esteja **fora do ar**.
- [ ] Todos os caches utilizados por `@Cacheable` devem estar registrados ou adicionados automaticamente durante o boot.
- [ ] A configuração de cache deve utilizar `RedisCacheManager.disableCreateOnMissingCache()`.
- [ ] O `CacheErrorHandler` deve tratar exceções como `RedisConnectionFailureException`, `RedisCommandTimeoutException` e logar como `WARN`.
- [ ] Redis deve ter reconexão automática configurada com `Delay.constant(Duration.ofSeconds(10))`.
- [ ] O log da aplicação deve evidenciar início e fim da configuração de cache.
- [ ] Não pode haver stacktraces em produção caso Redis esteja fora, apenas logs informativos e comportamento de fallback.

---

## 🧪 Casos de Teste

### 🔄 Cenários de Reconexão

- [ ] Parar Redis e verificar se a aplicação continua servindo requisições sem falha.
- [ ] Reiniciar Redis e validar se a aplicação retoma a conexão corretamente.
- [ ] Verificar se há logs de tentativa de reconexão.

### ⚠️ Cenários de Falha

- [ ] Simular `RedisConnectionFailureException` no uso do cache e validar que a exceção é capturada no `CacheErrorHandler`.
- [ ] Verificar se os dados ainda são retornados pela aplicação mesmo com Redis fora.

### 🧼 Teste de Configuração

- [ ] Verificar se apenas caches definidos ou anotados com `@Cacheable` estão sendo registrados.
- [ ] Remover um cache da config e manter anotação `@Cacheable`: validar se ele é adicionado dinamicamente.

---

## 📋 Considerações de Engenharia de Software

- **Observabilidade**: Adicione métricas e logs claros sobre falhas de cache.
- **Boas práticas de falha graciosa (graceful degradation)**: a aplicação deve continuar operando com consistência eventual ou sem cache.
- **Documentação**: Atualizar o README técnico do projeto explicando a estratégia de resiliência de cache.
- **Automação**: Garantir que os testes automatizados cubram pelo menos um cenário com Redis indisponível.

---

## 📎 Referências

- [Spring Cache Abstraction](https://docs.spring.io/spring-framework/reference/integration/cache.html)
- [Spring Redis Cache with Lettuce](https://docs.spring.io/spring-data/redis/docs/current/reference/html/)
- [Best Practices Redis + Spring Boot](https://reflectoring.io/spring-boot-cache/)

---

## 👨‍💻 Tarefa

**Crie uma task no Jira/Trello com o título:**

```
[Infra][Cache] Tornar Redis mais resiliente e desacoplado da aplicação principal
```

**Atribuir a:** `@dev.nome`

**Tipo:** Melhoria técnica  
**Esforço estimado:** 4h - 8h  
**Sprint:** Atual
