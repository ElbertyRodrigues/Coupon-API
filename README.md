# Cupon API

API REST para gestão de cupons — desenvolvida como desafio técnico para vaga de Desenvolvedor Java Pleno.

## Stack

- **Java 21** + **Spring Boot 3.4**
- **H2** (banco em memória)
- **MapStruct** (mapeamento domínio ↔ entidade)
- **Lombok**
- **Springdoc OpenAPI** (Swagger UI)
- **JUnit 5 + Mockito + MockMvc** (testes)
- **Docker + Docker Compose**
- **React** (frontend — repositório separado)

## Arquitetura

\```
com.couponapi
├── controller/          # Camada REST (entrada e saída HTTP)
├── service/             # Orquestração dos casos de uso
├── domain/              # Regras de negócio e modelo de domínio (sem JPA)
├── infrastructure/
│   ├── persistence/     # Entidade JPA + adapter do repositório
│   └── mapper/          # MapStruct: domínio ↔ entidade
├── dto/
│   ├── request/         # Payloads de entrada
│   └── response/        # Payloads de saída
└── exception/           # Tratamento global de erros
\```

> O objeto de domínio `Coupon` encapsula todas as regras de negócio e é **intencionalmente separado** da `CouponEntity` (JPA).


## Rodando localmente

### Com Maven

\```bash
./mvnw spring-boot:run
\```

### Com Docker Compose

\```bash
docker compose up --build
\```

## Endpoints

| Método   | Path           | Status | Descrição               |
|----------|----------------|--------|-------------------------|
| `POST`   | `/coupon`      | 201    | Criar um cupom          |
| `GET`    | `/coupon/{id}` | 200    | Buscar cupom por ID     |
| `DELETE` | `/coupon/{id}` | 204    | Soft-delete de um cupom |

## Swagger UI

Após subir a aplicação, acesse:

\```
http://localhost:8080/swagger-ui.html
\```

## H2 Console

\```
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:coupondb
Usuário: sa  |  Senha: (vazia)
\```

## Executando os testes

\```bash
./mvnw test
\```

## Regras de Negócio

- **Código**: 6 caracteres alfanuméricos. Caracteres especiais são removidos automaticamente antes de salvar.
- **Valor de desconto**: Mínimo de `0.5` (valor absoluto, sem preocupação com moeda). Sem máximo definido.
- **Data de expiração**: Deve ser estritamente no futuro. Cupons com data no passado são rejeitados com `422`.
- **Publicação**: Um cupom pode ser criado já como publicado.
- **Soft delete**: O campo `deletedAt` é preenchido e o `status` passa para `DELETED`. Nenhum dado é apagado do banco. Tentar deletar um cupom já deletado retorna `422`.

## Deploy

A API está disponível publicamente em:

\```
http://72.61.35.50:3032
\```

Swagger:
\```
http://72.61.35.50:3032/swagger-ui.html
\```

Frontend:
\```
http://72.61.35.50:3031
\```