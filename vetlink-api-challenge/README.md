# VetLink API - Rede Inteligente de Atendimento Veterinário

**VetLink** é uma API REST desenvolvida em **Java com Spring Boot** para o Challenge Clyvo Vet. A plataforma centraliza informações de saúde animal, conectando tutores de pets a uma rede integrada de clínicas veterinárias.

---

# 📋 Visão Geral

O VetLink resolve a fragmentação do histórico veterinário de animais de estimação. Quando um tutor leva seu pet a diferentes clínicas, o histórico completo (vacinas, exames, medicamentos, consultas) fica centralizado e acessível a qualquer clínica da rede.

---

# 🚀 Principais Funcionalidades

* ✅ Gerenciamento de Tutores
* ✅ Gerenciamento de Pets
* ✅ Clínicas Veterinárias
* ✅ Cadastro de Veterinários
* ✅ Controle de Consultas
* ✅ Registro de Vacinas
* ✅ Controle de Medicamentos
* ✅ Registro de Exames
* ✅ Sistema de Assinaturas
* ✅ Paginação e Ordenação
* ✅ Validações com Bean Validation
* ✅ Tratamento Global de Exceções
* ✅ Documentação Swagger/OpenAPI

---

# 🛠️ Stack Tecnológico

| Tecnologia      | Versão | Propósito                     |
| --------------- | ------ | ----------------------------- |
| Java            | 23     | Linguagem principal           |
| Spring Boot     | 3.2.0  | Framework backend             |
| Spring Data JPA | 3.2.0  | Persistência ORM              |
| Hibernate       | 6.x    | ORM                           |
| Oracle Database | 23c    | Banco de dados relacional     |
| Swagger/OpenAPI | 2.1.0  | Documentação API              |
| Lombok          | 1.18.x | Redução de boilerplate        |
| Maven           | 3.8+   | Gerenciamento de dependências |

---

# 📦 Estrutura do Projeto

```text
vetlink-api/
├── src/
│   ├── main/
│   │   ├── java/br/com/fiap/
│   │   │   ├── entity/
│   │   │   ├── dto/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   ├── controller/
│   │   │   ├── exception/
│   │   │   ├── config/
│   │   │   └── VetLinkApplication.java
│   │   └── resources/
│   │       └── application.properties
│
├── documentos/
│   ├── DER.png
│   ├── DiagramaClasses.png
│   ├── Cronograma.md
│   └── Insomnia.json
│
├── pom.xml
└── README.md
```

---

# 🧠 Conceitos Aplicados

O projeto foi desenvolvido respeitando os conceitos fundamentais de:

* Programação Orientada a Objetos (POO)
* Arquitetura RESTful
* JPA/Hibernate
* DTO Pattern
* Repository Pattern
* Service Layer Pattern
* Injeção de Dependência
* Tratamento Global de Exceções
* Validação de Dados
* Paginação e Ordenação

---

# 🔗 Modelo REST

A API segue os princípios RESTful:

* Uso correto dos verbos HTTP
* Recursos organizados por entidades
* Respostas padronizadas
* Status HTTP apropriados
* API Stateless
* Paginação e ordenação de recursos

---

# 🚀 Como Executar

## Pré-requisitos

* Java 23
* Maven 3.8+
* Oracle Database
* Git

---

## Clone o repositório

```bash
git clone https://github.com/GabrielRobertoni/vetlink-api-challenge
cd vetlink-api-challenge
```

---

## Compile o projeto

```bash
mvn clean install
```

---

## Execute a aplicação

```bash
mvn spring-boot:run
```

Ou execute diretamente a classe:

```text
VetLinkApplication.java
```

---

# 🌐 Endereços da Aplicação

| Recurso    | URL                                         |
| ---------- | ------------------------------------------- |
| API        | http://localhost:8082                       |
| Swagger UI | http://localhost:8082/swagger-ui/index.html |

---

# 📚 Endpoints Principais

TESTE NA ORDEM DE CLASSES!

## 👤 Tutores

```http
GET    /api/v1/tutores
GET    /api/v1/tutores/{id}
POST   /api/v1/tutores
PUT    /api/v1/tutores/{id}
DELETE /api/v1/tutores/{id}
```

---

## 🐶 Pets

```http
GET    /api/v1/pets
GET    /api/v1/pets/{id}
GET    /api/v1/pets/tutor/{id}
POST   /api/v1/pets
PUT    /api/v1/pets/{id}
DELETE /api/v1/pets/{id}
```

---

## 🏥 Clínicas

```http
GET    /api/v1/clinicas
GET    /api/v1/clinicas/{id}
POST   /api/v1/clinicas
PUT    /api/v1/clinicas/{id}
DELETE /api/v1/clinicas/{id}
```

---

## 👨‍⚕️ Veterinários

```http
GET    /api/v1/veterinarios
GET    /api/v1/veterinarios/{id}
GET    /api/v1/veterinarios/clinica/{id}
POST   /api/v1/veterinarios
PUT    /api/v1/veterinarios/{id}
DELETE /api/v1/veterinarios/{id}
```

---

## 📋 Consultas

```http
GET    /api/v1/consultas
GET    /api/v1/consultas/{id}
GET    /api/v1/consultas/tutor/{id}
GET    /api/v1/consultas/veterinario/{id}
POST   /api/v1/consultas
PUT    /api/v1/consultas/{id}
DELETE /api/v1/consultas/{id}
```

---

## 🧪 Exames

```http
GET    /api/v1/exames
GET    /api/v1/exames/{id}
GET    /api/v1/exames/consulta/{id}
POST   /api/v1/exames
PUT    /api/v1/exames/{id}
DELETE /api/v1/exames/{id}
```

---

## 💉 Vacinas

```http
GET    /api/v1/vacinas
GET    /api/v1/vacinas/{id}
GET    /api/v1/vacinas/pet/{id}
POST   /api/v1/vacinas
PUT    /api/v1/vacinas/{id}
DELETE /api/v1/vacinas/{id}
```

---

## 💊 Medicamentos

```http
GET    /api/v1/medicamentos
GET    /api/v1/medicamentos/{id}
GET    /api/v1/medicamentos/pet/{id}
POST   /api/v1/medicamentos
PUT    /api/v1/medicamentos/{id}
DELETE /api/v1/medicamentos/{id}
```

---

## 💳 Assinaturas

```http
GET    /api/v1/assinaturas
GET    /api/v1/assinaturas/{id}
GET    /api/v1/assinaturas/tutor/{id}
POST   /api/v1/assinaturas
PUT    /api/v1/assinaturas/{id}
DELETE /api/v1/assinaturas/{id}
```

---

# 📄 Recursos Implementados

## ✅ Paginação

```http
GET /api/v1/pets?page=0&size=5
```

---

## ✅ Ordenação

```http
GET /api/v1/consultas?sort=valor,desc
```

---

## ✅ Busca por parâmetros

```http
GET /api/v1/pets/tutor/8
```

---

# ✅ Bean Validation

Utilização de:

* `@NotBlank`
* `@NotNull`
* `@Pattern`
* `@Size`
* `@Positive`

---

## ⚠️ Tratamento de Exceções

Tratamento global utilizando a anotação:

`@RestControllerAdvice`

Principais erros tratados:
- 400 Bad Request
- 404 Not Found
- 500 Internal Server Error
- Violação de integridade Oracle

---

# 📊 Banco de Dados

## Principais tabelas

| Tabela            | Descrição    |
| ----------------- | ------------ |
| T_VET_TUTOR       | Tutores      |
| T_VET_PET         | Pets         |
| T_VET_CLINICA     | Clínicas     |
| T_VET_VETERINARIO | Veterinários |
| T_VET_CONSULTA    | Consultas    |
| T_VET_EXAME       | Exames       |
| T_VET_VACINA      | Vacinas      |
| T_VET_MEDICAMENTO | Medicamentos |
| T_VET_ASSINATURA  | Assinaturas  |

---

# 🧪 Testes da API

Os endpoints foram testados utilizando:

* Insomnia
* Swagger UI

A coleção de testes está disponível na pasta:

```text
/documentos
```

---

# 📁 Documentação Complementar

Na pasta `documentos/` estão disponíveis:

* DER do sistema
* Diagrama de Classes
* Cronograma
* Export do Insomnia
* Prints da aplicação

---

# 👥 Integrantes

Bruno Ferreira 563489
Gabriel Robertoni Padilha 566293
Leonardo Aragaki Rodrigues 562944

---

# 🎓 Projeto Acadêmico

Projeto desenvolvido para o Challenge FIAP — Clyvo Vet.

---

# 🏁 Considerações Finais

O VetLink foi desenvolvido com foco em centralização de dados veterinários, integração entre clínicas e organização do histórico completo dos pets.

A aplicação implementa conceitos modernos de APIs RESTful utilizando Java, Spring Boot e Oracle Database, respeitando boas práticas de arquitetura de software e Programação Orientada a Objetos.
