# Documentação da API Bushido

Esta é a documentação da API Aluno, que permite realizar operações relacionadas ao cadastramento de alunos.

```http
https://projeto-bushido-backend.onrender.com/api/V1
```

# Descrição

O desenvolvimento de uma API RESTful para cadastro de alunos em um projeto social de aulas de caratê demanda a utilização de tecnologias como Java, Spring Boot, Docker e MongoDB. Java oferece robustez e flexibilidade no desenvolvimento, enquanto Spring Boot simplifica a criação de aplicativos web. O Docker facilita a implantação e gerenciamento de contêineres, garantindo portabilidade e escalabilidade. MongoDB é ideal para armazenar dados flexíveis e não estruturados, com alta performance em consultas. Essas tecnologias combinadas garantem uma solução eficiente, escalável e altamente performática para o projeto.

# Autorização

Antes de tudo o usuario deve possuir um cadastro de admnistrador, o cadastro devera ser feito por outro administrador com acesso ilimitados. Apos efetuar o cadastro, o usuário devera relizar um _[login](#login)_ para receber o token de autorização para cada requisição.

## Sign up

Cadastro para novos admnistradores que serão responsaveis por realizar o cadastro dos alunos.

#### Request

```http
POST /admin/signup
```

```body
{
  "nome": string,
  "email": string,
  "cacpfo": string,
  "senha": string,
  "role": string
}
```

#### Response

<p>
<details>
<summary><i>200</i></summary>

```json
{
  "token": "string"
}
```

</details>

<details>
<summary><i>422</i></summary>

```json
{
  "timestamp": 0,
  "status": 422,
  "error": "Invalid Property",
  "message": "string",
  "path": "string"
}
```
</details>


## Login

Retorna um token que sera utilizado nas demais requisições

### Request

```http
POST /admin/login
```

```body
{
  email: string
  password: string
}
```

### Response


<details>
<summary><i>200</i></summary>

```json
{
  "token": "string",
  "role": "string",
  "status": 200,
  "success": true,
  "turmas": [
    {
      "endereco": "string",
      "nome": "string"
    },
    {
      "endereco": "string",
      "nome": "string"
    }
  ]
}
```
</details>

<details>
<summary><i>401</i></summary>

```json
{
  "type": "string",
  "title": "Erro de Autenticação",
  "status": 401,
  "detail": "string",
  "instance": "/api/V1/admin/login",
  "message:": "string",
  "error:": "Credenciais Inválidas"
}
```

</details>

## Buscar admin pelo nome

Retorna os admin cadastrados no sistema

### Request

- **nome**: string

```http
GET /admin/buscar?nome={nome}
```

### Response


<details>
<summary><i>200</i></summary>

```json
[
  {
    "nome": "admin",
    "email": "string",
    "role": "string",
    "cacpfo": "string"
  },
  {
    "nome": "admin",
    "email": "string",
    "role": "string",
    "cacpfo": "string"
  }
]
```
</details>

<details>
<summary><i>401</i></summary>

```json
{
  "type": "string",
  "title": "Erro de Autenticação",
  "status": 401,
  "detail": "string",
  "instance": "/api/V1/admin/login",
  "message:": "string",
  "error:": "Credenciais Inválidas"
}
```

</details>

# Aluno

## Buscar aluno por CPF

Retorna um objeto de uma aluno com base no cpf passado uma Query String como parâmetro.

#### Request

- **cpf:** string

```http
GET /aluno?cpf={cpf}
```

#### Response

<p>
<details>
<summary><i>200</i></summary>

```json
[
    {
      "nome": "string",
      "dataNascimento": "string",
      "genero": "string",
      "turma": "string",
      "dadosSociais": {
        "bolsaFamilia": true,
        "auxilioBrasil": true,
        "imovel": "string",
        "numerosDePessoasNaCasa": 4,
        "contribuintesDaRendaFamiliar": 2,
        "alunoContribuiParaRenda": true,
        "rendaFamiliar": 3000
      },
      "dadosEscolares": {
        "turno": "string",
        "escola": "string",
        "serie": "string"
      },
      "dataPreenchimento": "string",
      "endereco": {
        "cidade": "string",
        "estado": "string",
        "cep": "string",
        "numero": "string",
        "logradouro": "string"
      },
      "cpf": "string",
      "responsaveis": [
        {
          "nome": "string",
          "cpf": "string",
          "telefone": "string",
          "email": "string",
          "filiacao": "string"
        }
      ],
      "graduacao": [
        {
          "kyu": 0,
          "faltas": [],
          "status": false,
          "inicioGraduacao": "string",
          "fimGraduacao": "string",
          "frequencia": 100,
          "aprovado": true,
          "cacpfaHoraria": 39,
          "dan": 1,
          "nota": 8
        },
      ],
      "historicoSaude": {
        "tipoSanguineo": "string",
        "usoMedicamentoContinuo": {
          "resposta": false,
          "tipo": ""
        },
        "alecpfia": {
          "resposta": true,
          "tipo": "string"
        },
        "cirucpfia": {
          "resposta": true,
          "tipo": "string"
        },
        "doencaCronica": {
          "resposta": true,
          "tipo": "string"
        },
        "deficiencias": [
          "string"
        ],
        "acompanhamentoSaude": [
          "string"
        ]
      }
    }
]
```

</details>

<details>
<summary><i>404</i></summary>

```json
{
  "timestamp": 0,
  "status": 404,
  "error": "Object Not Found",
  "message": "string",
  "path": "string"
}
```

</details>

</details>

</p>

## Buscar aluno por Nome

Retorna um objeto de um array de alunos com base no nome passado uma Query String como parâmetro.

#### Request

- **nome:** string
- Parametros Opcionais
  - **page** int
    - index da pagina
  - **size** int
    - tamanho de documentos por pagina
  - **sortBy** string
    - filtragem por propriedade
  - **sortOrder** string
    - tipo de filtragem por valor
      - asc
      - desc

```http
GET /aluno?nome={nome}
```

#### Response

<p>
<details>
<summary><i>200</i></summary>

```json
[
    {
      "nome": "string",
      "dataNascimento": "string",
      "genero": "string",
      "turma": "string",
      "dadosSociais": {
        "bolsaFamilia": true,
        "auxilioBrasil": true,
        "imovel": "string",
        "numerosDePessoasNaCasa": 4,
        "contribuintesDaRendaFamiliar": 2,
        "alunoContribuiParaRenda": true,
        "rendaFamiliar": 3000
      },
      "dadosEscolares": {
        "turno": "string",
        "escola": "string",
        "serie": "string"
      },
      "dataPreenchimento": "string",
      "endereco": {
        "cidade": "string",
        "estado": "string",
        "cep": "string",
        "numero": "string",
        "logradouro": "string"
      },
      "cpf": "string",
      "responsaveis": [
        {
          "nome": "string",
          "cpf": "string",
          "telefone": "string",
          "email": "string",
          "filiacao": "string"
        }
      ],
      "graduacao": [
        {
          "kyu": 0,
          "faltas": [],
          "status": false,
          "inicioGraduacao": "string",
          "fimGraduacao": "string",
          "frequencia": 100,
          "aprovado": true,
          "cacpfaHoraria": 39,
          "dan": 1,
          "nota": 8
        },
      ],
      "historicoSaude": {
        "tipoSanguineo": "string",
        "usoMedicamentoContinuo": {
          "resposta": false,
          "tipo": ""
        },
        "alecpfia": {
          "resposta": true,
          "tipo": "string"
        },
        "cirucpfia": {
          "resposta": true,
          "tipo": "string"
        },
        "doencaCronica": {
          "resposta": true,
          "tipo": "string"
        },
        "deficiencias": [
          "string"
        ],
        "acompanhamentoSaude": [
          "string"
        ]
      }
    }
]
```

</details>

<details>
<summary><i>404</i></summary>

```json
{
  "timestamp": 0,
  "status": 404,
  "error": "Object Not Found",
  "message": "string",
  "path": "string"
}
```

</details>

</details>

</p>

## Adicionar aluno

Adiciona um aluno baseado nos dados cadastrais abaixo.

`*` Campos Obrigatórios

| Campo                                                 | Tipo             | Descrição                                         |
| ----------------------------------------------------- | ---------------- | :------------------------------------------------ |
| nome`*`                                               | string           | Nome completo do aluno                            |
| genero`*`                                             | string           | Gênero do aluno (M ou F)                          |
| dataNascimento`*`                                     | Date             | Data de nascimento do aluno (ISO String)          |
| turma`*`                                              | string           | Turma registrada do aluno                         |
| dadosSociais                                          | objeto           | Dados sociais do aluno                            |
| dadosSociais.bolsaFamilia                             | boolean          | Indica se o aluno recebe Bolsa Família            |
| dadosSociais.auxilioBrasil                            | boolean          | Indica se o aluno recebe Auxílio Brasil           |
| dadosSociais.imovel`*`                                | string (enum)    | </br>CEDIDO</br>ALUGADO</br>PROPRIO</li></ul>     |
| dadosSociais.numerosDePessoasNaCasa                   | integer          | Número de pessoas na casa do aluno                |
| dadosSociais.contribuintesDaRendaFamiliar             | integer          | Número de contribuintes da renda familiar         |
| dadosSociais.alunoContribuiParaRenda                  | boolean          | Indica se o aluno contribui para a renda familiar |
| dadosSociais.rendaFamiliar`*`                         | integer          | Renda familiar em salários mínimos                |
| dadosEscolares                                        | objeto           | Dados escolares do aluno                          |
| dadosEscolares.turno`*`                               | string (enum)    | Turno escolar do aluno (MANHA ou outro)           |
| dadosEscolares.escola`*`                              | string           | Nome da escola do aluno                           |
| dadosEscolares.serie`*`                               | integer          | Série em que o aluno está matriculado             |
| endereco                                              | objeto           | Endereço do aluno                                 |
| endereco.cidade                                       | string           | Cidade do aluno                                   |
| endereco.estado                                       | string           | Estado do aluno                                   |
| endereco.cep`*`                                       | string           | CEP do aluno                                      |
| endereco.numero`*`                                    | string           | Número do endereço do aluno                       |
| cpf`*`                                                 | string           | Número do cpf do aluno                             |
| responsaveis                                          | array de objetos | Responsáveis legais do aluno                      |
| responsaveis.nome                                     | string           | Nome do responsável legal                         |
| responsaveis.cpf                                      | string           | CPF do responsável legal                          |
| responsaveis.telefone`*`                              | string           | Telefone do responsável legal                     |
| responsaveis.email                                    | string           | Email do responsável legal                        |
| responsaveis.filiacao`*`                              | string (enum)    | Filiação do responsável legal (PAI ou outro)      |
| graduacao                                             | objeto           | Informações sobre a graduação do aluno            |
| graduacao.kyu                                         | integer          | Nível de graduação (Kyu) do aluno                 |
| graduacao.frequencia                                  | integer          | Frequência nas aulas de caratê                    |
| historicoSaude                                        | objeto           | Histórico de saúde do aluno                       |
| historicoSaude.tipoSanguineo`*`                       | string (enum)    | Tipo sanguíneo do aluno                           |
| historicoSaude.fatorRh`*`                             | string (enum)    | Fator Rh do aluno                                 |
| historicoSaude.usoMedicamentoContinuo                 | objeto           | Uso de medicamento contínuo pelo aluno            |
| historicoSaude.usoMedicamentoContinuo.resposta        | string           | Resposta sobre uso de medicamento contínuo        |
| historicoSaude.usoMedicamentoContinuo.qualMedicamento | string           | Nome do medicamento contínuo                      |
| historicoSaude.cirucpfia                               | objeto           | Informações sobre cirucpfia do aluno               |
| historicoSaude.cirucpfia.resposta                      | boolean          | Indica se o aluno fez alguma cirucpfia             |
| historicoSaude.cirucpfia.tipo                          | string           | Tipo de cirucpfia realizada, se houver             |
| historicoSaude.alecpfia                                | objeto           | Informações sobre alecpfias do aluno               |
| historicoSaude.alecpfia.resposta                       | boolean          | Indica se o aluno tem alguma alecpfia              |
| historicoSaude.alecpfia.tipo                           | string           | Tipo de alecpfia, se houver                        |
| historicoSaude.doencaCronica                          | objeto           | Informações sobre doenças crônicas do aluno       |
| historicoSaude.doencaCronica.resposta                 | boolean          | Indica se o aluno tem alguma doença crônica       |
| historicoSaude.doencaCronica.tipo                     | string           | Tipo de doença crônica, se houver                 |
| historicoSaude.deficiencia                            | array de strings | Deficiências do aluno                             |
| historicoSaude.acompanhamentoSaude                    | array de strings | Acompanhamento médico do aluno                    |

#### Request

```http
POST /aluno
```

```body
{
  "nome": "string",
  "genero": "M" ou "F",
  "turma":"string",
  "dataNascimento": long (data em milisegundo),
  "dadosSociais":{
    "bolsaFamilia": true,
    "auxilioBrasil": false,
    "imovel": "CEDIDO", -> enum
    "numerosDePessoasNaCasa": 0,
    "contribuintesDaRendaFamiliar": 0,
    "alunoContribuiParaRenda": true,
    "rendaFamiliar": 0
  },
   "dadosEscolares": {
    "turno": "MANHA", -> enum
    "escola": "string",
    "serie": 0
  },
  "dataPreenchimento": "2023-01-01T12:00:00",
  "endereco":{
    "cidade": "string",
    "estado": "string",
    "cep":"string",
    "numero":"string",
    "logradouro":"string"
  },
  "cpf": "{cpf}",
  "responsaveis": {
      "nome":"string",
      "cpf": "string",
      "telefone":"string",
      "email":"string",
      "filiacao":"PAI" -> enum
    },
   "graduacao": {
        "kyu": 0,
        "dan": 0
    },
    "historicoSaude": {
    "tipoSanguineo": "A_POSITIVO",
    "fatorRh": "POSITIVO",
    "usoMedicamentoContinuo": {
      "resposta": "string",
      "qualMedicamento": "string"
    },
    "cirucpfia": {
      "resposta": true,
      "tipo": "string"
    },
    "alecpfia": {
      "resposta": true,
      "tipo": "string"
    },
    "doencaCronica": {
      "resposta": true,
      "tipo": "string"
    },
    "deficiencia":["string"],
    "acompanhamentoSaude":["string"]
  }
}
```

#### Response

<p>
<details>
<summary><i>201</i></summary>

```json
{
  "id": "string",
  "status": 201,
  "message": "string",
  "entity": "string"
}
```

</details>

<details>
<summary><i>403</i></summary>

```json
{
  "timestamp": 0,
  "status": 403,
  "error": "Propriedade Invalida",
  "message": "string",
  "path": "/api/V1/aluno"
}
```

</details>

<details>
<summary><i>403</i></summary>

```json
{
  "timestamp": 0,
  "status": 406,
  "error": "Propriedade Invalida",
  "message": "string",
  "path": "/api/V1/aluno"
}
```

</details>

<details>
<summary><i>409</i></summary>

```json
{
  "timestamp": 0,
  "status": 409,
  "error": "Object is already Registered",
  "message": "string",
  "path": "/api/V1/aluno/falta/123456789"
}
```

</details>

<details>
<summary><i>422</i></summary>

```json
{
  "timestamp": 0,
  "status": 422,
  "error": "Metodo inválido",
  "message": "string",
  "path": "/api/V1/aluno"
}
```

</details>

## Editar aluno por cpf

#### Request

- *cpf*: string

```http
PUT /aluno/{cpf}
```

```body
{
  "nome": "string",
  "genero": "M" ou "F",
  "dataNascimento": long (data em milisegundo),
  "turma": "string",
  "dadosSociais":{
    "bolsaFamilia": true,
    "auxilioBrasil": false,
    "imovel": "CEDIDO", -> enum
    "numerosDePessoasNaCasa": 0,
    "contribuintesDaRendaFamiliar": 0,
    "alunoContribuiParaRenda": true,
    "rendaFamiliar": 0
  },
   "dadosEscolares": {
    "turno": "MANHA", -> enum
    "escola": "string",
    "serie": 0
  },
  "dataPreenchimento": "2023-01-01T12:00:00",
  "endereco":{
    "cidade": "string",
    "estado": "string",
    "cep":"string",
    "numero":"string",
    "logradouro":"string"
  }
}
```

#### Response

<p>
<details>
<summary><i>201</i></summary>

```json
{
  "id": "string",
  "status": 201,
  "message": "string",
  "entity": "string"
}
```

</details>

<details>
<summary><i>403</i></summary>

```json
{
  "timestamp": 0,
  "status": 403,
  "error": "Propriedade Invalida",
  "message": "string",
  "path": "/api/V1/aluno"
}
```

</details>

<details>
<summary><i>409</i></summary>

```json
{
  "timestamp": 0,
  "status": 409,
  "error": "Object is already Registered",
  "message": "string",
  "path": "/api/V1/aluno/falta/123456789"
}
```

</details>

<details>
<summary><i>422</i></summary>

```json
{
  "timestamp": 0,
  "status": 422,
  "error": "Metodo inválido",
  "message": "string",
  "path": "/api/V1/aluno"
}
```

</details>

## Adicionar falta ao aluno na data especifica

Adiciona uma falta ao aluno na data especificada no parâmetro data.

#### Request

- **cpf:** string
- **data:** inteiro 
  - formato: _UTC datetime in milliseconds_
    - exemplo: **1609459200000**

```http
POST /aluno/falta/{cpf}/{data}
```

```body
{
  "motivo":"Aluno se encontra doente",
  "observacao":"Luis trouxe atestado"
}
```

#### Response

<p>
<details>
<summary><i>200</i></summary>

```json
{
  "id": "string",
  "status": 200,
  "message": "string",
  "entity": "string"
}
```

</details>

<details>
<summary><i>409</i></summary>

```json
{
  "timestamp": 0,
  "status": 409,
  "error": "Object is already Registered",
  "message": "string",
  "path": "string"
}
```

</details>
<details>
<summary><i>422</i></summary>

```json
{
  "timestamp": 0,
  "status": 422,
  "error": "Invalid Property",
  "message": "string",
  "path": "string"
}
```

</details>

## Retirar falta do aluno

Retira a falta do aluno na data especificada no parâmetro data.

#### Request

- **cpf:** string
- **data:** dd-MM-yyyy

```http
DELETE /aluno/falta/{cpf}?data=dd-MM-yyyy
```

#### Response

<p>
<details>
<summary><i>200</i></summary>

```json
{
  "id": "string",
  "status": 200,
  "success": true,
  "message": "string",
  "entity": "string"
}
```

</details>

<details>
<summary><i>404</i></summary>

```json
{
  "timestamp": 0,
  "status": 404,
  "error": "Object Not Found",
  "message": "string",
  "path": "string"
}
```

</details>

## Adicionar responsável ao aluno

Adiciona um responsável ao aluno com base nos dados abaixo.

#### Request

- **cpf:** string

```http
POST /aluno/responsavel/{cpf}
```


```body
{
  "nome": "string",
  "cpf": "cpf",
  "telefone": "string",
  "email": "string",
  "filiacao": "MAE" -> enum
}
```

#### Response

<p>
<details>
<summary><i>200</i></summary>

```json
{
  "id": "string",
  "status": 200,
  "message": "string",
  "entity": "string"
}
```

</details>

<details>
<summary><i>400</i></summary>

```json
{
  "timestamp": 0,
  "status": 400,
  "error": "string",
  "message": "string",
  "path": "string"
}
```

</details>

<details>
<summary><i>409</i></summary>

```json
{
  "timestamp": 0,
  "status": 409,
  "error": "string",
  "message": "string",
  "path": "string"
}
```

</details>

<details>
<summary><i>422</i></summary>

```json
{
  "timestamp": 1709406663.54420566,
  "status": 422,
  "error": "Invalid Property",
  "message": "string",
  "path": "string"
}
```

</details>

## Remover responsável do aluno

Remover responsável do aluno com base no cpf informado.

#### Request

- **cpf:** string
- **deficiencia:** string

```http
DELETE /aluno/responsavel/{cpf}?cpf=string
```

#### Response

<p>
<details>
<summary><i>200</i></summary>

```json
{
  "id": "string",
  "status": 200,
  "message": "string",
  "entity": "string"
}
```

</details>

<details>
<summary><i>411</i></summary>

```json
{
  "timestamp": 0,
  "status": 411,
  "error": "Object has no quantity limit",
  "message": "string",
  "path": "string"
}
```

</details>

## Adicionar deficiência ao aluno

Adiciona uma string no campo deficiência ao aluno com base no parâmetro deficiência.

- **cpf:** string
- **deficiencia:** string

```http
POST /aluno/deficiencia/{cpf}?deficiencia=string
```

#### Response

<p>
<details>
<summary><i>200</i></summary>

```json
{
  "id": "string",
  "status": 200,
  "message": "string",
  "entity": "string"
}
```

</details>

<details>
<summary><i>404</i></summary>

```json
{
  "timestamp": 0,
  "status": 404,
  "error": "Object Not Found",
  "message": "string",
  "path": "string"
}
```

</details>

## Remover deficiência do aluno

Remove uma string no campo deficiência do aluno com base no parâmetro deficiência.

#### Request

- **cpf:** string
- **deficiencia:** string
```http
DELETE /aluno/deficiencia/{cpf}?deficiencia=string
```

#### Response

<details>
<summary><i>200</i></summary>

```json
{
  "id": "amidalite",
  "status": 200,
  "success": true,
  "message": "Deficiência amidalite foi removida com sucesso.",
  "entity": null
}
```

</details>

<details>
<summary><i>404</i></summary>

```json
{
  "timestamp": 1709410251.949585896,
  "status": 404,
  "error": "Object Not Found",
  "message": "string",
  "path": "string"
}
```

</details>

## Adicionar acompanhamento de saúde ao aluno

Adiciona uma string no campo acompanhamento de alunos ao aluno com base no parâmetro acompanhamento.

#### Request

- **cpf:** string
- **acompanhamento:** string

```http
POST /aluno/acompanhamentoSaude/{cpf}?acompanhamento=string
```

#### Response

<p>
<details>
<summary><i>200</i></summary>

```json
{
  "id": "string",
  "status": 200,
  "message": "string",
  "entity": "string"
}
```

</details>
<details>
<summary><i>409</i></summary>

```json
{
  "timestamp": 0,
  "status": 409,
  "error": "Object is already Registered",
  "message": "string",
  "path": "string"
}
```

</details>

## Remover acompanhamento de saúde do aluno

Remove uma string no campo acompanhamento de alunos do aluno com base no parâmetro acompanhamento.

#### Request

- **cpf:** string

```http
DELETE /aluno/acompanhamentoSaude/{cpf}
```

#### Response

<p>
<details>
<summary><i>200</i></summary>

```json
{
  "id": "string",
  "status": 200,
  "message": "string",
  "entity": "string"
}
```

</details>
<details>
<summary><i>404</i></summary>

```json
{
  "timestamp": 0,
  "status": 404,
  "error": "Object Not Found",
  "message": "string",
  "path": "string"
}
```

</details>
<details>
<summary><i>400</i></summary>

```json
{
  "timestamp": 0,
  "status": 400,
  "error": "Bad Request",
  "message": "string",
  "path": "string"
}
```

</details>

# Turma

## Criar nova turma

Cria uma nova turma com um admin

#### Request

- **email_admin:** string

```http
POST /turma
```

```body
{
    "nome": "string",
    "endereco": "string",
    "tutor": {
        "nome":"string",
        "email": "string"
    }
    ""
}
```

#### Response

<p>
<details>
<summary><i>200</i></summary>

```json
{
  "id": "string",
  "status": 200,
  "message": "string",
  "entity": "string"
}
```

</details>
<details>
<summary><i>409</i></summary>

```json
{
  "timestamp": 0,
  "status": 409,
  "error": "Object is already Registered",
  "message": "string",
  "path": "string"
}
```

</details>

## Deletar turma

Deletar uma turma sem alunos ativos

#### Request

- **nome_turma:** string
- **email_admin:** string

```http
DELETE /turma/{nome_turma}/{email_admin}
```

#### Response

<p>
<details>
<summary><i>200</i></summary>

```json
{
  "id": "string",
  "status": 200,
  "message": "string",
  "entity": "string"
}
```

</details>
<details>
<summary><i>404</i></summary>

```json
{
  "timestamp": 0,
  "status": 404,
  "error": "Object not found",
  "message": "string",
  "path": "string"
}
```

</details>
<details>
<summary><i>409</i></summary>

```json
{
  "timestamp": 0,
  "status": 409,
  "error": "Object is already Registered",
  "message": "string",
  "path": "string"
}
```

</details>

## Buscar todas as turmas

#### Request
- Parametros Opcionais
  - **dataInicial**: inteiro
    - formato: UTC datetime in milliseconds
    - exemplo: 1609459200000
  - **dataFinal**: inteiro
    - formato: UTC datetime in milliseconds
    - exemplo: 1609459200000

```http
GET /turma
```

#### Response

<p>
<details>
<summary><i>200</i></summary>

```json
[
  {
    "nome": "string",
    "tutor": {
      "nome":"string",
      "email": "string"
    },
    "endereco": "string"
  },
  {
    "nome": "string",
    "tutor": {
      "nome":"string",
      "email": "string"
    },
    "endereco": "string"
  }
]
```

</details>

## Buscar turma pelo nome

Buscar unica turma pelo nome

#### Request

- **nome_turma:** string

```http
GET /admin/{nome_turma}
```

#### Response

<p>
<details>
<summary><i>200</i></summary>

```json
{
  "nome": "string",
  "tutor": {
    "nome":"string",
    "email": "string"
  },
  "endereco": "string"
}
```

</details>

## Buscar alunos pela turma

Buscar todos os alunos registrados em uma turma

#### Request

- **nome_turma:** string

```http
GET /admin/{nome_turma}/alunos
```

#### Response

<p>
<details>
<summary><i>200</i></summary>

```json
[
  {
    "nome": "string",
    "cpf": "string",
    "genero": "M",
    "dataNascimento": "2014-03-16T18:39:50.968+00:00"
  },
  {
    "nome": "string",
    "cpf": "string",
    "genero": "F",
    "dataNascimento": "2014-03-04T18:39:50.969+00:00"
  }
]
```

</details>
<details>
<summary><i>404</i></summary>

```json
{
  "timestamp": 0,
  "status": 404,
  "error": "Object not found",
  "message": "string",
  "path": "string"
}
```

</details>