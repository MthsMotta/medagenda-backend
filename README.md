# MedAgenda

API REST para agendamento de consultas médicas, desenvolvida como projeto de portfólio para candidatura a vagas de estágio em desenvolvimento Backend.

O sistema simula uma plataforma de agendamento, onde pacientes se cadastram, atendentes/administradores gerenciam médicos e horários, e consultas são marcadas, confirmadas, canceladas ou realizadas seguindo uma máquina de estados.

## Tecnologias

- Java 25
- Spring Boot 3
- Spring Data JPA / Hibernate
- Spring Security + JWT
- MySQL
- Bean Validation
- Springdoc OpenAPI (Swagger)
- Maven
- Git

## Arquitetura

O projeto segue uma arquitetura em camadas:

```
Controller -> Service -> Repository -> Model (Entidade)
```

Com DTOs (Request/Response/Update) para desacoplar a API dos modelos internos, Mappers estáticos para conversão entre entidades e DTOs, e um GlobalExceptionHandler centralizando o tratamento de erros.

## Modelagem

7 tabelas principais: `tb_usuario`, `tb_medico`, `tb_paciente`, `tb_especialidade`, `tb_horario_trabalho`, `tb_consulta`, `tb_medico_especialidade` (relação N:N com chave composta via `@EmbeddedId`).

Relacionamentos: 1:1 (Usuario-Medico, Usuario-Paciente), 1:N (Medico-Consulta, Paciente-Consulta, Medico-HorarioTrabalho), N:N (Medico-Especialidade).

## Funcionalidades

### Autenticação e autorização
- Cadastro público de pacientes (`/auth/registrar`), com criação conjunta de Usuario e Paciente
- Cadastro de médicos e administradores restrito a ADMIN
- Login com geração de token JWT
- Autorização por role (ADMIN, MEDICO, PACIENTE) em nível de endpoint
- Verificação de propriedade do recurso na camada de Service (ex.: um paciente só pode alterar seus próprios dados; apenas o paciente da consulta pode confirmá-la; apenas o médico da consulta pode marcá-la como realizada)
- Filtro automático de resultados por usuário autenticado na listagem de consultas (paciente e médico não precisam informar o próprio id)

### Regras de negócio
- Verificação de conflito de horário na marcação de consultas, considerando duração e sobreposição de intervalos
- Máquina de estados para consultas: AGENDADA -> CONFIRMADA -> REALIZADA, ou AGENDADA/CONFIRMADA -> CANCELADA
- Bloqueio de exclusão de médico ou paciente com consultas ativas (AGENDADA ou CONFIRMADA)
- Exclusão em cascata orquestrada: ao excluir um Usuario, o Medico ou Paciente vinculado (e suas dependências) são removidos
- Prevenção de duplicidade em CPF, CRM e email
- Filtros dinâmicos de consulta (médico, paciente, status, intervalo de data) via JPA Specification, combinados com paginação

## Documentação da API

Com a aplicação em execução, a documentação interativa (Swagger UI) fica disponível em:

```
http://localhost:8080/swagger-ui/index.html
```

Os endpoints protegidos exigem um token JWT, informado através do botão "Authorize" na própria interface.

## Como executar localmente

### Pré-requisitos
- Java 25
- MySQL
- Maven

### Passos

1. Clone o repositório
2. Crie um banco de dados MySQL
3. Copie `application.properties.example` para `application.properties` em `src/main/resources` e preencha com suas credenciais
4. Rode a aplicação:

```
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

## Endpoints principais

| Método | Endpoint | Descrição |
|---|---|---|
| POST | /auth/registrar | Cadastro público de paciente |
| POST | /auth/login | Login e geração de token JWT |
| GET/POST/PUT/DELETE | /medicos | CRUD de médicos (restrito a ADMIN para escrita) |
| GET/PUT/DELETE | /pacientes | CRUD de pacientes |
| GET/POST/PUT/DELETE | /especialidades | CRUD de especialidades |
| GET/POST/PUT/DELETE | /agenda | CRUD de horário de trabalho dos médicos |
| POST/GET/PUT/DELETE | /consultas | CRUD de consultas, com filtros e paginação |
| PUT | /consultas/{id}/confirmar | Confirmação de consulta pelo paciente |
| PUT | /consultas/{id}/cancelar | Cancelamento de consulta |
| PUT | /consultas/{id}/realizada | Marcação de consulta como realizada pelo médico |
| POST/GET/DELETE | /medico-especialidades | Vínculo entre médico e especialidade |

A lista completa de endpoints, parâmetros e respostas está disponível no Swagger.

## Decisões de design

- O domínio foi modelado como marketplace (sem obrigação de retenção de histórico), o que justifica a exclusão física (hard delete) em cascata ao invés de soft delete
- A criação de consultas é validada tanto por regras de negócio (conflito de horário, status) quanto por autorização (um paciente não pode marcar consulta em nome de outro)
- Consultas de horário disponível por especialidade/dia da semana com dados mais ricos (nome do médico, especialidade) ficaram fora do escopo atual e são uma melhoria futura considerada

## Melhorias futuras

- Containerização com Docker e Docker Compose
- Testes unitários com JUnit e Mockito nas camadas de Service
- Endpoint de busca de horários disponíveis por especialidade e dia da semana
- Migração de scripts SQL para Flyway
- Validação de formato de CPF via regex ou algoritmo de dígito verificador

## Autor

Matheus da Motta Santos
Estudante de Análise e Desenvolvimento de Sistemas - USCS
[LinkedIn](https://linkedin.com/in/matheus-da-motta) | [GitHub](https://github.com/MthsMotta)