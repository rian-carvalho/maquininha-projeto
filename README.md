# Maquininha Projeto

API REST desenvolvida em Java com Spring Boot que simula o processamento de transações de cartão (point of sale), com cadastro de cartões, estabelecimentos comerciais, transações financeiras e autenticação via JWT.

<p>
  <img src="https://img.shields.io/badge/Java-17-ED8B00?style=flat&logo=openjdk&logoColor=white" alt="Java 17"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat&logo=springboot&logoColor=white" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/Spring%20Security-6DB33F?style=flat&logo=springsecurity&logoColor=white" alt="Spring Security"/>
  <img src="https://img.shields.io/badge/JWT-000000?style=flat&logo=jsonwebtokens&logoColor=white" alt="JWT"/>
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
- [Autenticação e autorização](#autenticação-e-autorização)
- [Endpoints da API](#endpoints-da-api)
- [Como executar](#como-executar)
- [Testes](#testes)
- [Melhorias futuras](#melhorias-futuras)
- [Autor](#autor)

## Sobre o projeto

O **Maquininha Projeto** é uma API REST que simula o fluxo de uma maquininha de cartão: cadastro de cartões e estabelecimentos comerciais, processamento de transações financeiras (débito, crédito) e controle de acesso via autenticação JWT com papéis de usuário (`ADMIN`, `USER`).

O projeto foi desenvolvido com foco em boas práticas de arquitetura backend, separação de responsabilidades em camadas e segurança no tratamento de dados sensíveis.

## Funcionalidades

- Cadastro e consulta de **cartões**
- Cadastro, consulta, atualização e exclusão de **estabelecimentos** comerciais
- Processamento de **transações**, vinculando cartão e estabelecimento
- Consulta de transações por ID e listagem geral
- **Estorno** de transações
- Suporte a tipos de transação: `DEBITO`, `CREDITO`
- Controle de status da transação: `APROVADA`, `RECUSADA`, `ESTORNADA`
- **Mascaramento automático** do número do cartão na resposta da API (ex.: `**** **** **** 7738`)
- **Autenticação via JWT** (registro e login de usuários)
- **Autorização baseada em papéis** (`ADMIN` / `USER`) protegendo operações sensíveis (criar/editar/excluir cartões e estabelecimentos, estornar transações)
- Criação automática de um usuário **admin** ao subir a aplicação (`DataSeeder`)
- Tratamento de erros com exceções de negócio customizadas (`CartaoNaoEncontradoException`, `EstabelecimentoNaoEncontradoException`, `EmailJaCadastradoException`) e handler global de exceções

## Tecnologias

| Categoria                    | Tecnologia                          |
|-------------------------------|--------------------------------------|
| Linguagem                     | Java 17                             |
| Framework                     | Spring Boot                         |
| Segurança                     | Spring Security + JWT (jjwt)        |
| Persistência                  | Spring Data JPA / Hibernate         |
| Banco de dados                | PostgreSQL                          |
| Gerenciador de dependências   | Maven                               |
| Testes de API                 | Postman                             |

## Arquitetura

O projeto segue uma arquitetura em camadas, separando claramente as responsabilidades:

```
Controller   → Camada de entrada (recebe requisições HTTP)
Service      → Regras de negócio
Repository   → Acesso e persistência de dados (JPA)
Entity       → Mapeamento das tabelas do banco
DTO          → Contratos de entrada/saída da API
Mapper       → Conversão entre Entity e DTO
Enum         → Valores fixos (tipo, status, bandeira, papel de usuário)
Exception    → Exceções de negócio customizadas + handler global
Security     → Filtro JWT, geração/validação de token e configuração do Spring Security
Config       → Configurações da aplicação (encoder de senha, seed de dados)
```

## Estrutura de pacotes

```
com.api.maquininha_projeto
├── config
│   ├── DataSeeder
│   └── PasswordConfig
├── controller
│   ├── AuthController
│   ├── CartaoController
│   ├── EstabelecimentoController
│   └── TransacaoController
├── dto
│   ├── AuthResponse
│   ├── CartaoRequest / CartaoResponse
│   ├── EstabelecimentoRequest / EstabelecimentoResponse
│   ├── LoginRequest / RegisterRequest
│   └── TransacaoRequest / TransacaoResponse
├── entity
│   ├── Cartao
│   ├── Estabelecimento
│   ├── Transacao
│   └── Usuario
├── enuns
│   ├── BandeirasCartao
│   ├── Role
│   ├── StatusTransacao
│   └── TipoTransacao
├── exception
│   ├── CartaoNaoEncontradoException
│   ├── EmailJaCadastradoException
│   ├── EstabelecimentoNaoEncontradoException
│   └── GlobalExceptionHandler
├── mapper
│   ├── MapperCartao
│   ├── MapperEstabelecimento
│   └── MapperTransacao
├── repository
│   ├── CartaoRepository
│   ├── EstabelecimentoRepository
│   ├── TransacaoRepository
│   └── UsuarioRepository
├── security
│   ├── JwtAuthenticationFilter
│   ├── JwtUtil
│   └── SecurityConfig
├── service
│   ├── CartaoService
│   ├── EstabelecimentoService
│   ├── TransacaoService
│   └── UsuarioService
└── MaquininhaProjetoApplication
```

## Modelo de dados

A entidade central é a **Transação**, que se relaciona com:

- **Cartão** (N:1) — cartão utilizado na transação
- **Estabelecimento** (N:1) — estabelecimento que recebeu o pagamento

```
Cartao 1 ───── N Transacao N ───── 1 Estabelecimento
```

A entidade **Usuário** é independente do domínio de transações — ela existe apenas para autenticação/autorização de acesso à API, e possui um papel (`Role`): `ADMIN` ou `USER`.

## Autenticação e autorização

A API usa **Spring Security** com **JWT** (stateless, sem sessão no servidor).

1. Registre um usuário em `POST /auth/registrar` ou faça login em `POST /auth/login`.
2. A resposta traz um `token` JWT.
3. Envie esse token no header `Authorization` das requisições seguintes:

```http
Authorization: Bearer <token>
```

Ao subir a aplicação pela primeira vez, o `DataSeeder` cria automaticamente um usuário administrador, caso ele ainda não exista:

- **Email:** `admin@maquininha.com`
- **Senha:** `admin123`

### Regras de autorização

| Recurso                              | Papel exigido       |
|---------------------------------------|----------------------|
| `POST` / `PUT` / `DELETE` `/estabelecimento` | `ADMIN`         |
| `POST` / `DELETE` `/cartao`           | `ADMIN`              |
| `PATCH /transacoes/{id}/estorno`      | `ADMIN`              |
| Demais endpoints (exceto `/auth/**`)  | Qualquer usuário autenticado |
| `/auth/**`                            | Público              |

## Endpoints da API

| Método | Endpoint                       | Descrição                          | Acesso            |
|--------|----------------------------------|-------------------------------------|-------------------|
| POST   | `/auth/registrar`               | Registra um novo usuário            | Público           |
| POST   | `/auth/login`                   | Autentica e retorna o token JWT     | Público           |
| POST   | `/cartao`                       | Cadastra um novo cartão             | ADMIN             |
| GET    | `/cartao`                       | Lista todos os cartões              | Autenticado       |
| GET    | `/cartao/{id}`                  | Consulta um cartão por ID           | Autenticado       |
| DELETE | `/cartao/{id}`                  | Exclui um cartão                    | ADMIN             |
| POST   | `/estabelecimento`               | Cadastra um novo estabelecimento    | ADMIN             |
| GET    | `/estabelecimento`               | Lista todos os estabelecimentos     | Autenticado       |
| GET    | `/estabelecimento/{id}`          | Consulta um estabelecimento por ID  | Autenticado       |
| PUT    | `/estabelecimento/{id}`          | Atualiza um estabelecimento         | ADMIN             |
| DELETE | `/estabelecimento/{id}`          | Exclui um estabelecimento           | ADMIN             |
| POST   | `/transacoes`                    | Processa uma nova transação         | Autenticado       |
| GET    | `/transacoes`                    | Lista todas as transações           | Autenticado       |
| GET    | `/transacoes/{id}`               | Consulta uma transação por ID       | Autenticado       |
| PATCH  | `/transacoes/{id}/estorno`       | Estorna uma transação               | ADMIN             |

### Exemplo — login

**Requisição**
```http
POST /auth/login
Content-Type: application/json

{
  "email": "admin@maquininha.com",
  "senha": "admin123"
}
```

**Resposta**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "tipo": "Bearer",
  "nome": "Administrador",
  "email": "admin@maquininha.com"
}
```

### Exemplo — criar transação

**Requisição**
```http
POST /transacoes
Authorization: Bearer <token>
Content-Type: application/json

{
  "valor": 150.00,
  "tipoTransacao": "DEBITO",
  "cartaoId": 1,
  "estabelecimentoId": 1
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
#    JWT_SECRET (chave usada para assinar os tokens JWT)

# 4. Execute a aplicação
./mvnw spring-boot:run
```

A API ficará disponível em `http://localhost:8081`.

Ao iniciar, um usuário administrador é criado automaticamente (ver [Autenticação e autorização](#autenticação-e-autorização)).

## Testes

Os endpoints foram validados manualmente via **Postman**, cobrindo:

- Registro e login de usuários, incluindo tentativa de e-mail duplicado e credenciais inválidas
- Criação de cartões e estabelecimentos
- Processamento e estorno de transações com sucesso
- Cenários de erro (cartão ou estabelecimento inexistente)
- Bloqueio de acesso a operações restritas a `ADMIN` por usuários comuns

## Melhorias futuras

- [ ] Suporte a transações via PIX
- [ ] Testes automatizados (JUnit + Mockito)
- [ ] Documentação interativa com Swagger/OpenAPI
- [ ] Paginação nas listagens
- [ ] Endpoint de atualização (`PUT`/`PATCH`) para cartões

## Autor

Desenvolvido por **Rian**, durante minha jornada de estudo e prática em desenvolvimento Backend com Java e Spring Boot.

- LinkedIn: [https://www.linkedin.com/in/riancarvalhogalcez/]
- GitHub: [https://github.com/rian-carvalho]
