# Documentação da API Bushido

Esta é a documentação da API Aluno, que permite realizar operações relacionadas ao cadastramento de alunos.

```http
https://projeto-bushido-backend.onrender.com/api/V1
```

## Descrição

O desenvolvimento de uma API RESTful para cadastro de alunos em um projeto social de aulas de caratê demanda a utilização de tecnologias como Java, Spring Boot, Docker e MongoDB. Java oferece robustez e flexibilidade no desenvolvimento, enquanto Spring Boot simplifica a criação de aplicativos web. O Docker facilita a implantação e gerenciamento de contêineres, garantindo portabilidade e escalabilidade. MongoDB é ideal para armazenar dados flexíveis e não estruturados, com alta performance em consultas. Essas tecnologias combinadas garantem uma solução eficiente, escalável e altamente performática para o projeto.

## Autorização

Antes de tudo o usuario deve possuir um cadastro de admnistrador, o cadastro devera ser feito por outro administrador com acesso ilimitados. Apos efetuar o cadastro, o usuário devera relizar um _[login](#login)_ para receber o token de autorização para cada requisição.

### Sign up

#### Cadastro para novos admintradores que serão responsaveis por realizar o cadastro dos alunos.

```http
POST /admin/signup
```

```body
{
  "nome": string,
  "email": string,
  "cargo": string,
  "senha": string,
  "role": string
}
```

### Login

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

</details>

</p>

# Aluno

## Buscar aluno por RG

Retorna um objeto de uma aluno com base no rg passado uma Query String como parâmetro.

### Request

- **rg:** string

```http
GET /aluno?rg={rg}
```

#### Response

<p>
<details>
<summary><i>200</i></summary>

```json
    {
      "nome": "string",
      "genero": "M" | "F",
      "dataNascimento": "YYYY-MM-DD. YYYY-MM-DDThh:mm:ss",
      "dadosSociais":{
        "bolsaFamilia": true,
        "auxilioBrasil": false,
        "imovel": "CEDIDO", -> enum
        "numerosDePessoasNaCasa": 0,
        "contribuintesDaRendaFamiliar": 0,
        "alunoContribuiParaRenda": true,
        "rendaFamiliarEmSalariosMinimos": 0
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
        "numero":"string"
      },
      "rg": "{rg}",
      "responsaveis": [
          {
          "nome":"string",
          "cpf": "string",
          "telefone":"string",
          "email":"string",
          "filiacao":"PAI" -> enum
          }
        ],
      "graduacao": {
            "kyu": 0,
            "frequencia": 0
        },
        "historicoSaude": {
        "tipoSanguineo": "A_POSITIVO",
        "fatorRh": "POSITIVO",
        "usoMedicamentoContinuo": {
          "resposta": "string",
          "qualMedicamento": "string"
        },
        "cirurgia": {
          "resposta": true,
          "tipo": "string"
        },
        "alergia": {
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
| dadosSociais                                          | objeto           | Dados sociais do aluno                            |
| dadosSociais.bolsaFamilia                             | boolean          | Indica se o aluno recebe Bolsa Família            |
| dadosSociais.auxilioBrasil                            | boolean          | Indica se o aluno recebe Auxílio Brasil           |
| dadosSociais.imovel`*`                                | string (enum)    | </br>CEDIDO</br>ALUGADO</br>PROPRIO</li></ul>     |
| dadosSociais.numerosDePessoasNaCasa                   | integer          | Número de pessoas na casa do aluno                |
| dadosSociais.contribuintesDaRendaFamiliar             | integer          | Número de contribuintes da renda familiar         |
| dadosSociais.alunoContribuiParaRenda                  | boolean          | Indica se o aluno contribui para a renda familiar |
| dadosSociais.rendaFamiliarEmSalariosMinimos`*`        | integer          | Renda familiar em salários mínimos                |
| dadosEscolares                                        | objeto           | Dados escolares do aluno                          |
| dadosEscolares.turno`*`                               | string (enum)    | Turno escolar do aluno (MANHA ou outro)           |
| dadosEscolares.escola`*`                              | string           | Nome da escola do aluno                           |
| dadosEscolares.serie`*`                               | integer          | Série em que o aluno está matriculado             |
| endereco                                              | objeto           | Endereço do aluno                                 |
| endereco.cidade                                       | string           | Cidade do aluno                                   |
| endereco.estado                                       | string           | Estado do aluno                                   |
| endereco.cep`*`                                       | string           | CEP do aluno                                      |
| endereco.numero`*`                                    | string           | Número do endereço do aluno                       |
| rg`*`                                                 | string           | Número do RG do aluno                             |
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
| historicoSaude.tipoSanguineo                          | string (enum)    | Tipo sanguíneo do aluno                           |
| historicoSaude.fatorRh                                | string (enum)    | Fator Rh do aluno                                 |
| historicoSaude.usoMedicamentoContinuo                 | objeto           | Uso de medicamento contínuo pelo aluno            |
| historicoSaude.usoMedicamentoContinuo.resposta        | string           | Resposta sobre uso de medicamento contínuo        |
| historicoSaude.usoMedicamentoContinuo.qualMedicamento | string           | Nome do medicamento contínuo                      |
| historicoSaude.cirurgia                               | objeto           | Informações sobre cirurgia do aluno               |
| historicoSaude.cirurgia.resposta                      | boolean          | Indica se o aluno fez alguma cirurgia             |
| historicoSaude.cirurgia.tipo                          | string           | Tipo de cirurgia realizada, se houver             |
| historicoSaude.alergia                                | objeto           | Informações sobre alergias do aluno               |
| historicoSaude.alergia.resposta                       | boolean          | Indica se o aluno tem alguma alergia              |
| historicoSaude.alergia.tipo                           | string           | Tipo de alergia, se houver                        |
| historicoSaude.doencaCronica                          | objeto           | Informações sobre doenças crônicas do aluno       |
| historicoSaude.doencaCronica.resposta                 | boolean          | Indica se o aluno tem alguma doença crônica       |
| historicoSaude.doencaCronica.tipo                     | string           | Tipo de doença crônica, se houver                 |
| historicoSaude.deficiencia                            | array de strings | Deficiências do aluno                             |
| historicoSaude.acompanhamentoSaude                    | array de strings | Acompanhamento médico do aluno                    |

### Request

```http
POST /aluno
```

```body
{
  "nome": "string",
  "genero": "M" ou "F",
  "dataNascimento": "dd-MM-yyyy",
  "dadosSociais":{
    "bolsaFamilia": true,
    "auxilioBrasil": false,
    "imovel": "CEDIDO", -> enum
    "numerosDePessoasNaCasa": 0,
    "contribuintesDaRendaFamiliar": 0,
    "alunoContribuiParaRenda": true,
    "rendaFamiliarEmSalariosMinimos": 0
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
    "numero":"string"
  },
  "rg": "{rg}",
  "responsaveis": [
      {
      "nome":"string",
      "cpf": "string",
      "telefone":"string",
      "email":"string",
      "filiacao":"PAI" -> enum
      }
    ],
   "graduacao": {
        "kyu": 0,
        "frequencia": 0
    },
    "historicoSaude": {
    "tipoSanguineo": "A_POSITIVO",
    "fatorRh": "POSITIVO",
    "usoMedicamentoContinuo": {
      "resposta": "string",
      "qualMedicamento": "string"
    },
    "cirurgia": {
      "resposta": true,
      "tipo": "string"
    },
    "alergia": {
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
<summary><i>409</i></summary>

```json
{
  "timestamp": 0,
  "status": 409,
  "error": "Object is already Registered",
  "message": "string",
  "path": "/api/V1/aluno/adicionarFalta/123456789"
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

</details>

</p>

## Adicionar falta ao aluno no dia atual

Adciona uma falta ao aluno na data no momento exato em que a requisição foi feita.

### Request

- **rg:** string

```http
POST /aluno/adicionarFalta/{rg}
```

```body
{
  "motivo":"string",
  "observacao":"string"
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
<summary><i>404</i></summary>

```json
{
  "timestamp": 0,
  "status": 404,
  "error": "Object Not Found",
  "message": "string",
  "path": "/api/V1/aluno/adicionarFalta/1e23456789"
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
  "path": "/api/V1/aluno/adicionarFalta/123456789"
}
```

</details>
</details>
</p>

## Adicionar falta ao aluno na data especifica

#### Request

```http
POST /aluno/adicionarFalta/{rg}/00000000000
```

```body
{
  "motivo":"Aluno se encontra doente","Aluno se encontra doente"
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
</details>

</p>

## Retirar falta do aluno

#### Request

```http
DELETE /aluno/retirarFalta/{rg}?data=dd-MM-yyyy
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
</details>
</p>

## Adicionar responsável ao aluno

```http
POST /aluno/adicionarResponsavel/{rg}
```

#### Request

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
</details>
</p>

## Remover responsável do aluno

#### Request

```http
DELETE /aluno/removerResponsavel/{rg}?cpf=string
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
</details>
</p>

## Adicionar deficiência ao aluno

- **rg:** string
- **deficiencia:** string

```http
POST /aluno/deficiencia/{rg}?deficiencia=string
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
</details>
</p>

## Remover deficiência do aluno

#### Request

```http
DELETE /aluno/deficiencia/{rg}?deficiencia=string
```

#### Response

<p>
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
</p>

## Adicionar acompanhamento de saúde ao aluno

#### Request

- **rg:** string
- **acompanhamento:** string

```http
POST /aluno/acompanhamentoSaude/{rg}?acompanhamento=string
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
</details>
</p>

## Remover acompanhamento de saúde do aluno

```http
DELETE /aluno/acompanhamentoSaude/{rg}
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
</details>
</p>
