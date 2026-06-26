# Maquininha Projeto

API REST desenvolvida em Java com Spring Boot que simula o processamento de transações de cartão (point of sale), com cadastro de cartões, estabelecimentos comerciais e transações financeiras.

<p>
  <img src="https://img.shields.io/badge/Java-17-ED8B00?style=flat&logo=openjdk&logoColor=white" alt="Java 17"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat&logo=springboot&logoColor=white" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=flat&logo=postgresql&logoColor=white" alt="PostgreSQL"/>
  <img src="https://img.shields.io/badge/Maven-C71A36?style=flat&logo=apachemaven&logoColor=white" alt="Maven"/>
  <img src="https://img.shields.io/badge/status-conclu%C3%ADdo-brightgreen" alt="Status"/>
</p>

## Índice

- [Sobre o projeto](#sobre-o-projeto)
- [Funcionalidades](#funcionalidades)
- [Tecnologias](#tecnologias)
- [Arquitetura](#arquitetura)
- [Estrutura de pacotes](#estrutura-de-pacotes)
- [Modelo de dados](#modelo-de-dados)
- [Endpoints da API](#endpoints-da-api)
- [Como executar](#como-executar)
- [Testes](#testes)
- [Melhorias futuras](#melhorias-futuras)
- [Autor](#autor)

## Sobre o projeto

O **Maquininha Projeto** é uma API REST que simula o fluxo de uma maquininha de cartão: cadastro de cartões e estabelecimentos comerciais, e processamento de transações financeiras (débito, crédito), com validação de regras de negócio e tratamento de erros.

O projeto foi desenvolvido com foco em boas práticas de arquitetura backend, separação de responsabilidades em camadas e segurança no tratamento de dados sensíveis.

## Funcionalidades

- Cadastro e consulta de **cartões**
- Cadastro e consulta de **estabelecimentos** comerciais
- Processamento de **transações**, vinculando cartão e estabelecimento
- Suporte a tipos de transação: `DEBITO`, `CREDITO`
- Controle de status da transação: `APROVADA`, `RECUSADA`, `ESTORNADA`
- **Mascaramento automático** do número do cartão na resposta da API (ex.: `**** **** **** 7738`)
- Tratamento de erros com exceções de negócio customizadas (`CartaoNaoEncontradoException`, `EstabelecimentoNaoEncontradoException`)

## Tecnologias

| Categoria                    | Tecnologia                  |
|-------------------------------|------------------------------|
| Linguagem                     | Java 17                     |
| Framework                     | Spring Boot                 |
| Persistência                  | Spring Data JPA / Hibernate |
| Banco de dados                | PostgreSQL                  |
| Gerenciador de dependências   | Maven                       |
| Testes de API                 | Postman                     |

## Arquitetura

O projeto segue uma arquitetura em camadas, separando claramente as responsabilidades:

```
Controller   → Camada de entrada (recebe requisições HTTP)
Service      → Regras de negócio
Repository   → Acesso e persistência de dados (JPA)
Entity       → Mapeamento das tabelas do banco
DTO          → Contratos de entrada/saída da API
Mapper       → Conversão entre Entity e DTO
Enum         → Valores fixos (tipo, status, bandeira)
Exception    → Exceções de negócio customizadas
```

## Estrutura de pacotes

```
com.api.maquininha_projeto
├── controller
│   ├── CartaoController
│   ├── EstabelecimentoController
│   └── TransacaoController
├── dto
│   ├── CartaoRequest / CartaoResponse
│   ├── EstabelecimentoRequest / EstabelecimentoResponse
│   └── TransacaoRequest / TransacaoResponse
├── entity
│   ├── Cartao
│   ├── Estabelecimento
│   └── Transacao
├── enums
│   ├── BandeirasCartao
│   ├── StatusTransacao
│   └── TipoTransacao
├── exception
│   ├── CartaoNaoEncontradoException
│   └── EstabelecimentoNaoEncontradoException
├── mapper
│   ├── MapperCartao
│   ├── MapperEstabelecimento
│   └── MapperTransacao
├── repository
│   ├── CartaoRepository
│   ├── EstabelecimentoRepository
│   └── TransacaoRepository
├── service
│   ├── CartaoService
│   ├── EstabelecimentoService
│   └── TransacaoService
└── MaquininhaProjetoApplication
```

## Modelo de dados

A entidade central é a **Transação**, que se relaciona com:

- **Cartão** (N:1) — cartão utilizado na transação
- **Estabelecimento** (N:1) — estabelecimento que recebeu o pagamento

```
Cartao 1 ───── N Transacao N ───── 1 Estabelecimento
```

## Endpoints da API

| Método | Endpoint                | Descrição                          |
|--------|--------------------------|-------------------------------------|
| POST   | `/cartao`                | Cadastra um novo cartão             |
| POST   | `/estabelecimento`       | Cadastra um novo estabelecimento    |
| GET    | `/estabelecimento/{id}`  | Consulta um estabelecimento por ID  |
| POST   | `/transacoes`            | Processa uma nova transação         |

### Exemplo — criar transação

**Requisição**
```http
POST /transacoes
Content-Type: application/json

{
  "valor": 150.00,
  "tipoTransacao": "DEBITO",
  "cartaoId": "1",
  "estabelecimentoId": "1"
}
```

**Resposta**
```json
{
  "id": 2,
  "dataCriacao": "2026-06-25T14:44:26",
  "nomeEstabelecimento": "Mercado Central",
  "numeroCartaoMascarado": "**** **** **** 7738",
  "status": "APROVADA",
  "tipoTransacao": "DEBITO",
  "valor": 150.00
}
```

## Como executar

### Pré-requisitos
- Java 17 ou superior
- Maven
- PostgreSQL em execução localmente

### Passo a passo

```bash
# 1. Clone o repositório
git clone https://github.com/seu-usuario/maquininha-projeto.git
cd maquininha-projeto
```

```sql
-- 2. Crie o banco de dados
CREATE DATABASE maquininha_db;
```

```bash
# 3. Defina as variáveis de ambiente antes de executar
#    DB_USERNAME (padrão: postgres)
#    DB_PASSWORD

# 4. Execute a aplicação
./mvnw spring-boot:run
```

A API ficará disponível em `http://localhost:8080`.

## Testes

Os endpoints foram validados manualmente via **Postman**, cobrindo:

- Criação de cartões e estabelecimentos
- Processamento de transações com sucesso
- Cenários de erro (cartão ou estabelecimento inexistente)

## Melhorias futuras

- [ ] Suporte a transações via PIX
- [ ] Autenticação e autorização com Spring Security + JWT
- [ ] Testes automatizados (JUnit + Mockito)
- [ ] Documentação interativa com Swagger/OpenAPI
- [ ] Paginação nas listagens

## Autor

Desenvolvido por **Rian**, durante minha jornada de estudo e prática em desenvolvimento Backend com Java e Spring Boot.

- LinkedIn: [https://www.linkedin.com/in/riancarvalhogalcez/]
- GitHub: [https://github.com/rian-carvalho]
